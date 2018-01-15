package client.jobs.nova;

import client.Client;
import client.character.Char;
import client.character.HitInfo;
import client.character.skills.*;
import client.jobs.Job;
import client.life.Mob;
import client.life.MobTemporaryStat;
import connection.InPacket;
import constants.JobConstants;
import enums.ChatMsgColour;
import enums.MobStat;
import loaders.SkillData;
import packet.WvsContext;
import util.Util;

import java.util.Arrays;

import static client.character.skills.CharacterTemporaryStat.*;
import static client.character.skills.SkillStat.*;

/**
 * Created on 12/14/2017.
 */
public class AngelicBuster extends Job {
    public static final int AB_NORMAL_ATTACK = 60011216; //TODO Recharge Attack

    public static final int STAR_BUBBLE = 65001100; //TODO Recharge Attack
    public static final int MELODY_CROSS = 65001002; //Buff

    public static final int LOVELY_STING = 65101100; //TODO Recharge Attack + (Detonate Debuff)
    public static final int PINK_PUMMEL = 65101001; //TODO Recharge Attack
    public static final int POWER_TRANSFER = 65101002; //Buff

    public static final int SOUL_SEEKER = 65111100; //TODO Recharge Attack
    public static final int SHINING_STAR_BURST = 65111101; //TODO Recharge Attack
    public static final int HEAVENLY_CRASH = 65111002; //TODO Recharge Attack
    public static final int IRON_BLOSSOM = 65111004; //Buff

    public static final int CELESTIAL_ROAR = 65121100; //TODO Recharge Attack + (Stun Debuff)
    public static final int TRINITY = 65121101; //TODO Recharge Attack
    public static final int FINALE_RIBBON = 65121002; //TODO Recharge Attack + (DmgUp Debuff)
    public static final int STAR_GAZER = 65121004; //Buff
    public static final int NOVA_WARRIOR_AB = 65121009; //Buff

    private final int[] buffs = new int[]{
            MELODY_CROSS,
            POWER_TRANSFER,
            IRON_BLOSSOM,
            STAR_GAZER,
            NOVA_WARRIOR_AB,
    };

    public AngelicBuster(Char chr) {
        super(chr);
    }

    public void handleBuff(Client c, InPacket inPacket, int skillID, byte slv) {
        Char chr = c.getChr();
        SkillInfo si = SkillData.getSkillInfoById(skillID);
        TemporaryStatManager tsm = c.getChr().getTemporaryStatManager();
        Option o1 = new Option();
        Option o2 = new Option();
        Option o3 = new Option();
        switch (skillID) {
            case MELODY_CROSS:
                o1.nReason = skillID;
                o1.nValue = si.getValue(indieBooster, slv);
                o1.tStart = (int) System.currentTimeMillis();
                o1.tTerm = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieBooster, o1);
                o2.nOption = si.getValue(mhpX, slv);
                o2.rOption = skillID;
                o2.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(EMHP, o2);
                break;
            case POWER_TRANSFER:
                //TODO
                break;
            case IRON_BLOSSOM:
                o1.nOption = si.getValue(prop, slv);
                o1.rOption = skillID;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(Stance, o1);
                break;
            case STAR_GAZER:
                o1.nOption = si.getValue(x, slv);
                o1.rOption = skillID;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IncCriticalDamMax, o1);
                o2.nOption = si.getValue(y, slv);
                o2.rOption = skillID;
                o2.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IncCriticalDamMin, o2);
                break;
            case NOVA_WARRIOR_AB:
                o1.nReason = skillID;
                o1.nValue = si.getValue(x, slv);
                o1.tStart = (int) System.currentTimeMillis();
                o1.tTerm = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieStatR, o1);
                break;
        }
        c.write(WvsContext.temporaryStatSet(tsm));
    }

    private boolean isBuff(int skillID) {
        return Arrays.stream(buffs).anyMatch(b -> b == skillID);
    }

    @Override
    public void handleAttack(Client c, AttackInfo attackInfo) {
        Char chr = c.getChr();
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Skill skill = chr.getSkill(attackInfo.skillId);
        int skillID = 0;
        SkillInfo si = null;
        boolean hasHitMobs = attackInfo.mobAttackInfo.size() > 0;
        int slv = 0;
        if (skill != null) {
            si = SkillData.getSkillInfoById(skill.getSkillId());
            slv = skill.getCurrentLevel();
            skillID = skill.getSkillId();
        }
        Option o1 = new Option();
        Option o2 = new Option();
        Option o3 = new Option();
        switch (attackInfo.skillId) {
            case AB_NORMAL_ATTACK:
            case STAR_BUBBLE:
            case PINK_PUMMEL:
            case SOUL_SEEKER:
            case SHINING_STAR_BURST:
            case HEAVENLY_CRASH:
            case TRINITY:           //Only Recharge
                for(MobAttackInfo mai : attackInfo.mobAttackInfo) {
                    if(Util.succeedProp(si.getValue(OnActive, slv))) {
                        Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                        MobTemporaryStat mts = mob.getTemporaryStat(); //TODO Replace with Character Temp Stat
                        o1.nOption = 1;
                        o1.rOption = skill.getSkillId();
                        o1.tOption = si.getValue(time, slv);
                        //mts.addStatOptionsAndBroadcast(AB Recharge, o1); //TODO AB Recharge Temp Stat/Method
                    }
                }
                break;
            case LOVELY_STING:      //Unknown Debuff + Recharge
                for(MobAttackInfo mai : attackInfo.mobAttackInfo) {
                    if (Util.succeedProp(si.getValue(prop, slv))) {
                        Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                        MobTemporaryStat mts = mob.getTemporaryStat();
                        o1.nOption = 1;
                        o1.rOption = skill.getSkillId();
                        o1.tOption = si.getValue(time, slv);
                        mts.addStatOptionsAndBroadcast(MobStat.SoulExplosion, o1); //TODO Look for exact Debuff
                    } else if(Util.succeedProp(si.getValue(OnActive, slv))) {
                        Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                        MobTemporaryStat mts = mob.getTemporaryStat(); //TODO Replace with Character Temp Stat
                        o1.nOption = 1;
                        o1.rOption = skill.getSkillId();
                        o1.tOption = si.getValue(time, slv);
                        //mts.addStatOptionsAndBroadcast(AB Recharge, o1); //TODO AB Recharge Temp Stat/Method
                    }
                }
                break;
            case FINALE_RIBBON:     //DmgUp Debuff + Recharge
                for(MobAttackInfo mai : attackInfo.mobAttackInfo) {
                    if (Util.succeedProp(si.getValue(prop, slv))) {
                        Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                        MobTemporaryStat mts = mob.getTemporaryStat();
                        o1.nOption = 1;
                        o1.rOption = skill.getSkillId();
                        o1.tOption = si.getValue(time, slv);
                        mts.addStatOptionsAndBroadcast(MobStat.AddDamParty, o1); //TODO Check if this is the Correct MobStat
                    } else if(Util.succeedProp(si.getValue(OnActive, slv))) {
                        Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                        MobTemporaryStat mts = mob.getTemporaryStat(); //TODO Replace with Character Temp Stat
                        o1.nOption = 1;
                        o1.rOption = skill.getSkillId();
                        o1.tOption = si.getValue(time, slv);
                        //mts.addStatOptionsAndBroadcast(AB Recharge, o1); //TODO AB Recharge Temp Stat/Method
                    }
                }
                break;
            case CELESTIAL_ROAR:    //Stun Debuff + Recharge
                for(MobAttackInfo mai : attackInfo.mobAttackInfo) {
                    if (Util.succeedProp(si.getValue(prop, slv))) {
                        Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                        MobTemporaryStat mts = mob.getTemporaryStat();
                        o1.nOption = 1;
                        o1.rOption = skill.getSkillId();
                        o1.tOption = si.getValue(time, slv);
                        mts.addStatOptionsAndBroadcast(MobStat.Stun, o1);
                    } else if(Util.succeedProp(si.getValue(OnActive, slv))) {
                        Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                        MobTemporaryStat mts = mob.getTemporaryStat(); //TODO Replace with Character Temp Stat
                        o1.nOption = 1;
                        o1.rOption = skill.getSkillId();
                        o1.tOption = si.getValue(time, slv);
                        //mts.addStatOptionsAndBroadcast(AB Recharge, o1); //TODO AB Recharge Temp Stat/Method
                    }
                }
                break;
        }
    }

    @Override
    public void handleSkill(Client c, int skillID, byte slv, InPacket inPacket) {
        Char chr = c.getChr();
        Skill skill = chr.getSkill(skillID);
        SkillInfo si = null;
        if(skill != null) {
            si = SkillData.getSkillInfoById(skillID);
        }
        chr.chatMessage(ChatMsgColour.YELLOW, "SkillID: " + skillID);
        if (isBuff(skillID)) {
            handleBuff(c, inPacket, skillID, slv);
        } else {
            Option o1 = new Option();
            Option o2 = new Option();
            Option o3 = new Option();
            switch(skillID) {
            }
        }
    }

    @Override
    public void handleHit(Client c, InPacket inPacket, HitInfo hitInfo) {

    }

    @Override
    public boolean isHandlerOfJob(short id) {
        JobConstants.JobEnum job = JobConstants.JobEnum.getJobById(id);
        switch (job) {
            case ANGELIC_BUSTER:
            case ANGELIC_BUSTER1:
            case ANGELIC_BUSTER2:
            case ANGELIC_BUSTER3:
            case ANGELIC_BUSTER4:
                return true;
            default:
                return false;
        }
    }
}
