package BattleShipLogic;

import BattleShipJAXB.BattleShipGame;

public final class BattleShipFactory {

    private BattleShipFactory(){}

    private static BattleShip PullOutShip(Object pullingOutShip) throws GameException{
        BattleShip pulledBattleShip;

        BattleShipGame.ShipTypes.ShipType ship = (BattleShipGame.ShipTypes.ShipType)pullingOutShip;
        pulledBattleShip = new BattleShip(ship.getId(),ship.getLength(),ship.getScore());

        return pulledBattleShip;
    }

    public static Object PullOutObject(Object pullingOutObject) throws GameException {
        Object pulledObject = null;

        if(pullingOutObject instanceof BattleShipGame.ShipTypes.ShipType){
            BattleShipGame.ShipTypes.ShipType ship = (BattleShipGame.ShipTypes.ShipType)pullingOutObject;
            if (ship.getCategory().equals("REGULAR")) {
                pulledObject = PullOutShip(pullingOutObject);
            }
            if (ship.getCategory().equals("L_SHAPE")) {
                //TODO
            }
        }

        return pulledObject;
    }
}