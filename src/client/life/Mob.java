package client.life;

import client.Client;
import client.field.Field;
import client.field.Foothold;
import packet.CField;
import server.EventManager;
import util.Position;

public class Mob extends Life {

    private boolean sealedInsteadDead, patrolMob, isLeft;
    private int option, effectItemID, patrolScopeX1, patrolScopeX2, detectX, senseX, phase, curZoneDataType;
    private int refImgMobID, lifeReleaseOwnerAID, afterAttack, currentAction, scale, eliteGrade, eliteType, targetUserIdFromServer;
    private long hp, maxHp;
    private long mp, maxMp;
    private byte calcDamageIndex = 1, moveAction, appearType, teamForMCarnival;
    private Position prevPos;
    private Foothold curFoodhold;
    private Foothold homeFoothold;
    private String lifeReleaseOwnerName = "", lifeReleaseMobName = "";
    private ShootingMoveStat shootingMoveStat;
    private ForcedMobStat forcedMobStat;
    private MobTemporaryStat temporaryStat;
    private int firstAttack;
    private int summonType;
    private int category;
    private String mobType = "";
    private int link;
    private double fs;
    private String elemAttr = "";
    private int hpTagColor;
    private int hpTagBgcolor;
    private boolean HPgaugeHide;
    private int rareItemDropLevel;
    private boolean boss;
    private int hpRecovery;
    private int mpRecovery;
    private boolean undead;
    private int mbookID;
    private boolean noRegen;
    private int chaseSpeed;
    private int explosiveReward;
    private int flySpeed;
    private boolean invincible;
    private boolean hideName;
    private boolean hideHP;
    private String changeableMobType = "";
    private boolean changeable;
    private boolean noFlip;
    private boolean tower;
    private boolean partyBonusMob;
    private int wp;
    private boolean useReaction;
    private boolean publicReward;
    private boolean minion;
    private boolean forward;
    private boolean isRemoteRange;
    private boolean ignoreFieldOut;
    private boolean ignoreMoveImpact;
    private int summonEffect;
    private boolean skeleton;
    private boolean hideUserDamage;
    private int fixedDamage;
    private boolean individualReward;
    private int removeAfter;
    private boolean notConsideredFieldSet;
    private String fixedMoveDir = "";
    private boolean noDoom;
    private boolean useCreateScript;
    private boolean knockback;
    private boolean blockUserMove;
    private int bodyDisease;
    private int bodyDiseaseLevel;
    private int point;
    private int partyBonusR;
    private boolean removeQuest;
    private int passiveDisease;
    private int coolDamageProb;
    private int coolDamage;
    private int damageRecordQuest;
    private int sealedCooltime;
    private int willEXP;
    private boolean onFieldSetSummon;
    private boolean userControll;
    private boolean noDebuff;
    private boolean targetFromSvr;
    private int charismaEXP;

    public Mob(int templateId, int objectId) {
        super(objectId);
        super.templateId = templateId;
        forcedMobStat = new ForcedMobStat();
        temporaryStat = new MobTemporaryStat(this);
        scale = 100;
        calcDamageIndex = 1;
    }

    public Mob deepCopy() {
        Mob copy = new Mob(getTemplateId(), getObjectId());
        // start life
        copy.setLifeType(getLifeType());
        copy.setTemplateId(getTemplateId());
        copy.setX(getX());
        copy.setY(getY());
        copy.setMobTime(getMobTime());
        copy.setF(getF());
        copy.setHide(isHide());
        copy.setFh(getFh());
        copy.setCy(getCy());
        copy.setRx0(getRx0());
        copy.setRx1(getRx1());
        copy.setLimitedName(getLimitedName());
        copy.setUseDay(isUseDay());
        copy.setUseNight(isUseNight());
        copy.setHold(isHold());
        copy.setNoFoothold(isNoFoothold());
        copy.setDummy(isDummy());
        copy.setSpine(isSpine());
        copy.setMobTimeOnDie(isMobTimeOnDie());
        copy.setRegenStart(getRegenStart());
        copy.setMobAliveReq(getMobAliveReq());
        // end life
        copy.setSealedInsteadDead(isSealedInsteadDead());
        copy.setPatrolMob(isPatrolMob());
        copy.setLeft(isLeft());
        copy.setOption(getOption());
        copy.setEffectItemID(getEffectItemID());
        copy.setPatrolScopeX1(getPatrolScopeX1());
        copy.setPatrolScopeX2(getPatrolScopeX2());
        copy.setDetectX(getDetectX());
        copy.setSenseX(getSenseX());
        copy.setPhase(getPhase());
        copy.setCurZoneDataType(getCurZoneDataType());
        copy.setRefImgMobID(getRefImgMobID());
        copy.setLifeReleaseOwnerAID(getLifeReleaseOwnerAID());
        copy.setAfterAttack(getAfterAttack());
        copy.setCurrentAction(getCurrentAction());
        copy.setScale(getScale());
        copy.setEliteGrade(getEliteGrade());
        copy.setEliteType(getEliteType());
        copy.setTargetUserIdFromServer(getTargetUserIdFromServer());
        copy.setHp(getHp());
        copy.setMaxHp(getMaxHp());
        copy.setCalcDamageIndex(getCalcDamageIndex());
        copy.setMoveAction(getMoveAction());
        copy.setAppearType(getAppearType());
        copy.setTeamForMCarnival(getTeamForMCarnival());
        if (getPrevPos() != null) {
            copy.setPrevPos(getPrevPos().deepCopy());
        }
        if(getCurFoodhold() != null) {
            copy.setCurFoodhold(getCurFoodhold().deepCopy());
        }
        if(getHomeFoothold() != null) {
            copy.setHomeFoothold(getHomeFoothold().deepCopy());
        }
        copy.setLifeReleaseOwnerName(getLifeReleaseOwnerName());
        copy.setLifeReleaseMobName(getLifeReleaseMobName());
        copy.setShootingMoveStat(null);
        if(getForcedMobStat() != null) {
            copy.setForcedMobStat(getForcedMobStat().deepCopy());
        }
        if(getTemporaryStat() != null) {
            copy.setTemporaryStat(getTemporaryStat().deepCopy());
        }
        copy.setFirstAttack(getFirstAttack());
        copy.setSummonType(getSummonType());
        copy.setCategory(getCategory());
        copy.setMobType(getMobType());
        copy.setLink(getLink());
        copy.setFs(getFs());
        copy.setElemAttr(getElemAttr());
        copy.setHpTagColor(getHpTagColor());
        copy.setHpTagBgcolor(getHpTagBgcolor());
        copy.setHPgaugeHide(isHPgaugeHide());
        copy.setRareItemDropLevel(getRareItemDropLevel());
        copy.setBoss(isBoss());
        copy.setHpRecovery(getHpRecovery());
        copy.setMpRecovery(getMpRecovery());
        copy.setUndead(isUndead());
        copy.setMbookID(getMbookID());
        copy.setNoRegen(isNoRegen());
        copy.setChaseSpeed(getChaseSpeed());
        copy.setExplosiveReward(getExplosiveReward());
        copy.setFlySpeed(getFlySpeed());
        copy.setInvincible(isInvincible());
        copy.setHideName(isHideName());
        copy.setHideHP(isHideHP());
        copy.setChangeableMobType(getChangeableMobType());
        copy.setChangeable(isChangeable());
        copy.setNoFlip(isNoFlip());
        copy.setTower(isTower());
        copy.setPartyBonusMob(isPartyBonusMob());
        copy.setWp(getWp());
        copy.setUseReaction(isUseReaction());
        copy.setPublicReward(isPublicReward());
        copy.setMinion(isMinion());
        copy.setForward(isForward());
        copy.setIsRemoteRange(isRemoteRange());
        copy.setIgnoreFieldOut(isIgnoreFieldOut());
        copy.setIgnoreMoveImpact(isIgnoreMoveImpact());
        copy.setSummonEffect(getSummonEffect());
        copy.setSkeleton(isSkeleton());
        copy.setHideUserDamage(isHideUserDamage());
        copy.setFixedDamage(getFixedDamage());
        copy.setIndividualReward(isIndividualReward());
        copy.setRemoveAfter(getRemoveAfter());
        copy.setNotConsideredFieldSet(isNotConsideredFieldSet());
        copy.setFixedMoveDir(getFixedMoveDir());
        copy.setNoDoom(isNoDoom());
        copy.setUseCreateScript(isUseCreateScript());
        copy.setKnockback(isKnockback());
        copy.setBlockUserMove(isBlockUserMove());
        copy.setBodyDisease(getBodyDisease());
        copy.setBodyDiseaseLevel(getBodyDiseaseLevel());
        copy.setPoint(getPoint());
        copy.setPartyBonusR(getPartyBonusR());
        copy.setRemoveQuest(isRemoveQuest());
        copy.setPassiveDisease(getPassiveDisease());
        copy.setCoolDamageProb(getCoolDamageProb());
        copy.setCoolDamage(getCoolDamage());
        copy.setDamageRecordQuest(getDamageRecordQuest());
        copy.setSealedCooltime(getSealedCooltime());
        copy.setWillEXP(getWillEXP());
        copy.setOnFieldSetSummon(isOnFieldSetSummon());
        copy.setUserControll(isUserControll());
        copy.setNoDebuff(isNoDebuff());
        copy.setTargetFromSvr(isTargetFromSvr());
        copy.setCharismaEXP(getCharismaEXP());
        copy.setMp(getMp());
        copy.setMaxMp(getMaxMp());
        return copy;
    }

    public boolean isSealedInsteadDead() {
        return sealedInsteadDead;
    }

    public void setSealedInsteadDead(boolean sealedInsteadDead) {
        this.sealedInsteadDead = sealedInsteadDead;
    }

    public ForcedMobStat getForcedMobStat() {
        return forcedMobStat;
    }

    public void setForcedMobStat(ForcedMobStat forcedMobStat) {
        this.forcedMobStat = forcedMobStat;
    }

    public boolean isPatrolMob() {
        return patrolMob;
    }

    public void setPatrolMob(boolean patrolMob) {
        this.patrolMob = patrolMob;
    }

    public boolean isLeft() {
        return isLeft;
    }

    public void setLeft(boolean left) {
        isLeft = left;
    }

    public int getOption() {
        return option;
    }

    public void setOption(int option) {
        this.option = option;
    }

    public int getEffectItemID() {
        return effectItemID;
    }

    public void setEffectItemID(int effectItemID) {
        this.effectItemID = effectItemID;
    }

    public int getPatrolScopeX1() {
        return patrolScopeX1;
    }

    public void setPatrolScopeX1(int patrolScopeX1) {
        this.patrolScopeX1 = patrolScopeX1;
    }

    public int getPatrolScopeX2() {
        return patrolScopeX2;
    }

    public void setPatrolScopeX2(int patrolScopeX2) {
        this.patrolScopeX2 = patrolScopeX2;
    }

    public int getDetectX() {
        return detectX;
    }

    public void setDetectX(int detectX) {
        this.detectX = detectX;
    }

    public int getSenseX() {
        return senseX;
    }

    public void setSenseX(int senseX) {
        this.senseX = senseX;
    }

    public int getPhase() {
        return phase;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public int getCurZoneDataType() {
        return curZoneDataType;
    }

    public void setCurZoneDataType(int curZoneDataType) {
        this.curZoneDataType = curZoneDataType;
    }

    public int getRefImgMobID() {
        return refImgMobID;
    }

    public void setRefImgMobID(int refImgMobID) {
        this.refImgMobID = refImgMobID;
    }

    public int getLifeReleaseOwnerAID() {
        return lifeReleaseOwnerAID;
    }

    public void setLifeReleaseOwnerAID(int lifeReleaseOwnerAID) {
        this.lifeReleaseOwnerAID = lifeReleaseOwnerAID;
    }

    public int getAfterAttack() {
        return afterAttack;
    }

    public void setAfterAttack(int afterAttack) {
        this.afterAttack = afterAttack;
    }

    public int getCurrentAction() {
        return currentAction;
    }

    public void setCurrentAction(int currentAction) {
        this.currentAction = currentAction;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public int getEliteGrade() {
        return eliteGrade;
    }

    public void setEliteGrade(int eliteGrade) {
        this.eliteGrade = eliteGrade;
    }

    public int getEliteType() {
        return eliteType;
    }

    public void setEliteType(int eliteType) {
        this.eliteType = eliteType;
    }

    public int getTargetUserIdFromServer() {
        return targetUserIdFromServer;
    }

    public void setTargetUserIdFromServer(int targetUserIdFromServer) {
        this.targetUserIdFromServer = targetUserIdFromServer;
    }

    public long getHp() {
        return hp;
    }

    public void setHp(long hp) {
        this.hp = hp;
    }

    public byte getCalcDamageIndex() {
        return calcDamageIndex;
    }

    public void setCalcDamageIndex(byte calcDamageIndex) {
        this.calcDamageIndex = calcDamageIndex;
    }

    public byte getMoveAction() {
        return moveAction;
    }

    public void setMoveAction(byte moveAction) {
        this.moveAction = moveAction;
    }

    public byte getAppearType() {
        return appearType;
    }

    public void setAppearType(byte appearType) {
        this.appearType = appearType;
    }

    public byte getTeamForMCarnival() {
        return teamForMCarnival;
    }

    public void setTeamForMCarnival(byte teamForMCarnival) {
        this.teamForMCarnival = teamForMCarnival;
    }

    public Position getPrevPos() {
        return prevPos;
    }

    public void setPrevPos(Position prevPos) {
        this.prevPos = prevPos;
    }

    public Foothold getCurFoodhold() {
        return curFoodhold;
    }

    public void setCurFoodhold(Foothold curFoodhold) {
        this.curFoodhold = curFoodhold;
    }

    public String getLifeReleaseOwnerName() {
        return lifeReleaseOwnerName;
    }

    public void setLifeReleaseOwnerName(String lifeReleaseOwnerName) {
        this.lifeReleaseOwnerName = lifeReleaseOwnerName;
    }

    public String getLifeReleaseMobName() {
        return lifeReleaseMobName;
    }

    public void setLifeReleaseMobName(String lifeReleaseMobName) {
        this.lifeReleaseMobName = lifeReleaseMobName;
    }

    public ShootingMoveStat getShootingMoveStat() {
        return shootingMoveStat;
    }

    public void setShootingMoveStat(ShootingMoveStat shootingMoveStat) {
        this.shootingMoveStat = shootingMoveStat;
    }

    public Foothold getHomeFoothold() {
        return homeFoothold;
    }

    public void setHomeFoothold(Foothold homeFoothold) {
        this.homeFoothold = homeFoothold;
    }

    public long getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(long maxHp) {
        this.maxHp = maxHp;
    }

    public long getMp() {
        return mp;
    }

    public void setMp(long mp) {
        this.mp = mp;
    }

    public long getMaxMp() {
        return maxMp;
    }

    public void setMaxMp(long maxMp) {
        this.maxMp = maxMp;
    }

    public void setTemporaryStat(MobTemporaryStat temporaryStat) {
        this.temporaryStat = temporaryStat;
    }

    public MobTemporaryStat getTemporaryStat() {
        return temporaryStat;
    }

    public void setFirstAttack(int firstAttack) {
        this.firstAttack = firstAttack;
    }

    public int getFirstAttack() {
        return firstAttack;
    }

    public void setSummonType(int summonType) {
        this.summonType = summonType;
    }

    public int getSummonType() {
        return summonType;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getCategory() {
        return category;
    }

    public void setMobType(String mobType) {
        this.mobType = mobType;
    }

    public String getMobType() {
        return mobType;
    }

    public void setLink(int link) {
        this.link = link;
    }

    public int getLink() {
        return link;
    }

    public void setFs(double fs) {
        this.fs = fs;
    }

    public double getFs() {
        return fs;
    }

    public void setElemAttr(String elemAttr) {
        this.elemAttr = elemAttr;
    }

    public String getElemAttr() {
        return elemAttr;
    }

    public void setHpTagColor(int hpTagColor) {
        this.hpTagColor = hpTagColor;
    }

    public int getHpTagColor() {
        return hpTagColor;
    }

    public void setHpTagBgcolor(int hpTagBgcolor) {
        this.hpTagBgcolor = hpTagBgcolor;
    }

    public int getHpTagBgcolor() {
        return hpTagBgcolor;
    }

    public void setHPgaugeHide(boolean HPgaugeHide) {
        this.HPgaugeHide = HPgaugeHide;
    }

    public boolean isHPgaugeHide() {
        return HPgaugeHide;
    }

    public void setRareItemDropLevel(int rareItemDropLevel) {
        this.rareItemDropLevel = rareItemDropLevel;
    }

    public int getRareItemDropLevel() {
        return rareItemDropLevel;
    }

    public void setBoss(boolean boss) {
        this.boss = boss;
    }

    public boolean isBoss() {
        return boss;
    }

    public void setHpRecovery(int hpRecovery) {
        this.hpRecovery = hpRecovery;
    }

    public int getHpRecovery() {
        return hpRecovery;
    }

    public void setMpRecovery(int mpRecovery) {
        this.mpRecovery = mpRecovery;
    }

    public int getMpRecovery() {
        return mpRecovery;
    }

    public void setUndead(boolean undead) {
        this.undead = undead;
    }

    public boolean isUndead() {
        return undead;
    }

    public void setMbookID(int mbookID) {
        this.mbookID = mbookID;
    }

    public int getMbookID() {
        return mbookID;
    }

    public void setNoRegen(boolean noRegen) {
        this.noRegen = noRegen;
    }

    public boolean isNoRegen() {
        return noRegen;
    }

    public void setChaseSpeed(int chaseSpeed) {
        this.chaseSpeed = chaseSpeed;
    }

    public int getChaseSpeed() {
        return chaseSpeed;
    }

    public void setExplosiveReward(int explosiveReward) {
        this.explosiveReward = explosiveReward;
    }

    public int getExplosiveReward() {
        return explosiveReward;
    }

    public void setFlySpeed(int flySpeed) {
        this.flySpeed = flySpeed;
    }

    public int getFlySpeed() {
        return flySpeed;
    }

    public void setInvincible(boolean invincible) {
        this.invincible = invincible;
    }

    public boolean isInvincible() {
        return invincible;
    }

    public void setHideName(boolean hideName) {
        this.hideName = hideName;
    }

    public boolean isHideName() {
        return hideName;
    }

    public void setHideHP(boolean hideHP) {
        this.hideHP = hideHP;
    }

    public boolean isHideHP() {
        return hideHP;
    }

    public void setChangeableMobType(String changeableMobType) {
        this.changeableMobType = changeableMobType;
    }

    public String getChangeableMobType() {
        return changeableMobType;
    }

    public void setChangeable(boolean changeable) {
        this.changeable = changeable;
    }

    public boolean isChangeable() {
        return changeable;
    }

    public void setNoFlip(boolean noFlip) {
        this.noFlip = noFlip;
    }

    public boolean isNoFlip() {
        return noFlip;
    }

    public void setTower(boolean tower) {
        this.tower = tower;
    }

    public boolean isTower() {
        return tower;
    }

    public void setPartyBonusMob(boolean partyBonusMob) {
        this.partyBonusMob = partyBonusMob;
    }

    public boolean isPartyBonusMob() {
        return partyBonusMob;
    }

    public void setWp(int wp) {
        this.wp = wp;
    }

    public int getWp() {
        return wp;
    }

    public void setUseReaction(boolean useReaction) {
        this.useReaction = useReaction;
    }

    public boolean isUseReaction() {
        return useReaction;
    }

    public void setPublicReward(boolean publicReward) {
        this.publicReward = publicReward;
    }

    public boolean isPublicReward() {
        return publicReward;
    }

    public void setMinion(boolean minion) {
        this.minion = minion;
    }

    public boolean isMinion() {
        return minion;
    }

    public void setForward(boolean forward) {
        this.forward = forward;
    }

    public boolean isForward() {
        return forward;
    }

    public void setIsRemoteRange(boolean isRemoteRange) {
        this.isRemoteRange = isRemoteRange;
    }

    public boolean isRemoteRange() {
        return isRemoteRange;
    }

    public void setRemoteRange(boolean isRemoteRange) {
        this.isRemoteRange = isRemoteRange;
    }

    public void setIgnoreFieldOut(boolean ignoreFieldOut) {
        this.ignoreFieldOut = ignoreFieldOut;
    }

    public boolean isIgnoreFieldOut() {
        return ignoreFieldOut;
    }

    public void setIgnoreMoveImpact(boolean ignoreMoveImpact) {
        this.ignoreMoveImpact = ignoreMoveImpact;
    }

    public boolean isIgnoreMoveImpact() {
        return ignoreMoveImpact;
    }

    public void setSummonEffect(int summonEffect) {
        this.summonEffect = summonEffect;
    }

    public int getSummonEffect() {
        return summonEffect;
    }

    public void setSkeleton(boolean skeleton) {
        this.skeleton = skeleton;
    }

    public boolean isSkeleton() {
        return skeleton;
    }

    public void setHideUserDamage(boolean hideUserDamage) {
        this.hideUserDamage = hideUserDamage;
    }

    public boolean isHideUserDamage() {
        return hideUserDamage;
    }

    public void setFixedDamage(int fixedDamage) {
        this.fixedDamage = fixedDamage;
    }

    public int getFixedDamage() {
        return fixedDamage;
    }

    public void setIndividualReward(boolean individualReward) {
        this.individualReward = individualReward;
    }

    public boolean isIndividualReward() {
        return individualReward;
    }

    public void setRemoveAfter(int removeAfter) {
        this.removeAfter = removeAfter;
    }

    public int getRemoveAfter() {
        return removeAfter;
    }

    public void setNotConsideredFieldSet(boolean notConsideredFieldSet) {
        this.notConsideredFieldSet = notConsideredFieldSet;
    }

    public boolean isNotConsideredFieldSet() {
        return notConsideredFieldSet;
    }

    public void setFixedMoveDir(String fixedMoveDir) {
        this.fixedMoveDir = fixedMoveDir;
    }

    public String getFixedMoveDir() {
        return fixedMoveDir;
    }

    public void setNoDoom(boolean noDoom) {
        this.noDoom = noDoom;
    }

    public boolean isNoDoom() {
        return noDoom;
    }

    public void setUseCreateScript(boolean useCreateScript) {
        this.useCreateScript = useCreateScript;
    }

    public boolean isUseCreateScript() {
        return useCreateScript;
    }

    public void setKnockback(boolean knockback) {
        this.knockback = knockback;
    }

    public boolean isKnockback() {
        return knockback;
    }

    public void setBlockUserMove(boolean blockUserMove) {
        this.blockUserMove = blockUserMove;
    }

    public boolean isBlockUserMove() {
        return blockUserMove;
    }

    public void setBodyDisease(int bodyDisease) {
        this.bodyDisease = bodyDisease;
    }

    public int getBodyDisease() {
        return bodyDisease;
    }

    public void setBodyDiseaseLevel(int bodyDiseaseLevel) {
        this.bodyDiseaseLevel = bodyDiseaseLevel;
    }

    public int getBodyDiseaseLevel() {
        return bodyDiseaseLevel;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getPoint() {
        return point;
    }

    public void setPartyBonusR(int partyBonusR) {
        this.partyBonusR = partyBonusR;
    }

    public int getPartyBonusR() {
        return partyBonusR;
    }

    public void setRemoveQuest(boolean removeQuest) {
        this.removeQuest = removeQuest;
    }

    public boolean isRemoveQuest() {
        return removeQuest;
    }

    public void setPassiveDisease(int passiveDisease) {
        this.passiveDisease = passiveDisease;
    }

    public int getPassiveDisease() {
        return passiveDisease;
    }

    public void setCoolDamageProb(int coolDamageProb) {
        this.coolDamageProb = coolDamageProb;
    }

    public int getCoolDamageProb() {
        return coolDamageProb;
    }

    public void setCoolDamage(int coolDamage) {
        this.coolDamage = coolDamage;
    }

    public int getCoolDamage() {
        return coolDamage;
    }

    public void setDamageRecordQuest(int damageRecordQuest) {
        this.damageRecordQuest = damageRecordQuest;
    }

    public int getDamageRecordQuest() {
        return damageRecordQuest;
    }

    public void setSealedCooltime(int sealedCooltime) {
        this.sealedCooltime = sealedCooltime;
    }

    public int getSealedCooltime() {
        return sealedCooltime;
    }

    public void setWillEXP(int willEXP) {
        this.willEXP = willEXP;
    }

    public int getWillEXP() {
        return willEXP;
    }

    public void setOnFieldSetSummon(boolean onFieldSetSummon) {
        this.onFieldSetSummon = onFieldSetSummon;
    }

    public boolean isOnFieldSetSummon() {
        return onFieldSetSummon;
    }

    public void setUserControll(boolean userControll) {
        this.userControll = userControll;
    }

    public boolean isUserControll() {
        return userControll;
    }

    public void setNoDebuff(boolean noDebuff) {
        this.noDebuff = noDebuff;
    }

    public boolean isNoDebuff() {
        return noDebuff;
    }

    public void setTargetFromSvr(boolean targetFromSvr) {
        this.targetFromSvr = targetFromSvr;
    }

    public boolean isTargetFromSvr() {
        return targetFromSvr;
    }

    public void setCharismaEXP(int charismaEXP) {
        this.charismaEXP = charismaEXP;
    }

    public int getCharismaEXP() {
        return charismaEXP;
    }

    public void damage(Long totalDamage) {
        long oldHp = getHp();
        Field field = getField();
        long newHp = oldHp - totalDamage;
        setHp(newHp);
        double percDamage = ((double) newHp / getMaxHp());
        if(newHp <= 0) {
            getField().broadcastPacket(CField.mobLeaveField(getObjectId(), DeathType.ANIMATION_DEATH.getVal()));
            if(!isNotRespawnable()) { // double negative
                EventManager.addEvent(field, "respawn", (long) (5000 * (1 / field.getMobRate())), this);
            }
            field.addLifeController(this, null);
        } else {
            getField().broadcastPacket(CField.mobHpIndicator(getObjectId(), (byte) (percDamage * 100)));
        }
    }
}