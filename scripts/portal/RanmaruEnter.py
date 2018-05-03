def init():
    sm.sendAskYesNo("Would you like to battle Ranmaru?")

def action(response, answer):
    if response == 1:
        if sm.getParty() is None:
            sm.sendSayOkay("Please create a party before going in.")
        elif not sm.isPartyLeader():
            sm.sendSayOkay("Please have your party leader enter if you wish to face Ranmaru.")
        elif sm.checkParty():
            sm.warpParty(807300110)
    sm.dispose()