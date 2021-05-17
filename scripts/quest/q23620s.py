# A Warm(er) Welcome (23620)

charName = chr.getName()
claudine = 2151003

sm.setSpeakerID(claudine)
sm.sendNext("Hello, #b" + charName + "#k? Do you have time to come by Edelstein?")

sm.setPlayerAsSpeaker()
sm.sendSay("Is everything okay?")

sm.setSpeakerID(claudine)
response = sm.sendAskYesNo("Yes, we're fine, but I could use your help. Can you come right away?")
if response:
    sm.startQuest(parentID)
    sm.sendSayOkay("Come talk to me again later.")