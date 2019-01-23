package BattleShipLogic;

import java.awt.Point;

public class Trap extends BoardItems {
    public Trap(Point point) {
        super(eItems.Trap.getChar());
        this.m_SpreadingItemPoints = new Point[1];
        this.m_SpreadingItemPoints[0]=new Point(point);
    }

    public void BuildItem(){}

    public void SetTrapOnBoard(Point point){
        this.m_SpreadingItemPoints[0].setLocation(point);
    }
}
