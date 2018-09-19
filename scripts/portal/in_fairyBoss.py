# Portal for entering Ephenia

EPHENIAS_HIDING_PLACE = 2133006 # NPC ID
QUEENS_HIDING_PLACE = 300030310 # MAP ID
FAIRY_FOREST_2 = 300030300 # MAP ID 2
LADY_OF_THE_CORRUPTED_FOREST = 31229 # QUEST ID
time = 15 * 60 # 15 MINUTES

sm.setSpeakerID(EPHENIAS_HIDING_PLACE)

response = sm.sendAskYesNo("This is Ephenia's Hideout.\r\n"
                "If you haven't gotten permission, you shouldn't be here.\r\n"
                "Enter anyway?")

if response == 1:
    chrHasQuest = True
    if not sm.getParty() is None:
        for partyChr in sm.getPartyMembersInSameField(chr):
            partysm = partyChr.getScriptManager()
            if not partysm.hasQuest(LADY_OF_THE_CORRUPTED_FOREST) and not partysm.hasQuestCompleted(LADY_OF_THE_CORRUPTED_FOREST):
                chrHasQuest = False
                break

        if chrHasQuest:
            sm.warpPartyIn(QUEENS_HIDING_PLACE)
            sm.createClock(time)
            for partyChr in sm.getPartyMembersInSameField(chr):
                partysm = partyChr.getScriptManager()
                partysm.invokeAfterDelay(time * 1000, "clearPartyInfo", FAIRY_FOREST_2)
        else:
            sm.sendSayOkay("You may not approach the Queen's Hiding Place. "
                           "Some of your party members have not completed the fairy elimination quest.")

    else:
        sm.sendSayOkay("You may enter in a party.")