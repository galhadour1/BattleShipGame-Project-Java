package BattleShipLogic;

import java.awt.*;

public class BattleShip extends BoardItems {
    private String m_ShipID;
    private eDirection m_Direction;
    private int m_LengthAfterHit;
    private int m_Length;
    private int m_Score;

    public BattleShip(String shipID, int length, int score) throws GameException {
        super(eItems.Ship.getChar());
        if(length >= 1) {
            this.m_ShipID = shipID;
            this.m_Length = length;
            this.m_Score = score;
            m_LengthAfterHit = length;
            this.m_SpreadingItemPoints = new Point[length];
            for (int i = 0; i< length; i++)
                m_SpreadingItemPoints[i]=new Point();
        }
        else{
            throw new GameException("The size of the ship must be a positive integer");
        }
    }

    public void SetStartPositionPoint(Point startPositionPoint){
        this.m_StartPositionPoint = startPositionPoint;

        this.BuildItem();
    }

    public void SetDirection(eDirection Direction){
        this.m_Direction = Direction;
    }

    public String GetShipID(){return m_ShipID;}

    public boolean CheckPositionInitialized(){return m_StartPositionPoint.y!=-1;}

    public boolean CheckShipSank(){
        return m_LengthAfterHit ==0;
    }

    public boolean CheckHitOnBoard(Point pointOnBoard){
        boolean isHitSucceeded = false;

        for (Point point: this.m_SpreadingItemPoints) {
            if(point.y == pointOnBoard.y && point.x == pointOnBoard.x){
                isHitSucceeded = true;
                break;
            }
        }

        return isHitSucceeded;
    }

    @Override
    public void BuildItem(){
        int x = m_StartPositionPoint.x;
        int y = m_StartPositionPoint.y;
        for (Point point: this.m_SpreadingItemPoints) {
            point.x = x;
            point.y = y;
            if(m_Direction == m_Direction.Row){
                x++;
            }
            else if(m_Direction == m_Direction.Column){
                y++;
            }
        }
    }

    public boolean PlaceHitAndCheckSink(Point pointOnBoard){
        for (Point point: this.m_SpreadingItemPoints) {
            if(point.y == pointOnBoard.y && point.x == pointOnBoard.x){
                m_LengthAfterHit--;
                break;
            }
        }

        return CheckShipSank();
    }
}
