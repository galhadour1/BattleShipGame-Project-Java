package BattleShipLogic;

import java.awt.*;

public abstract  class BoardItems {
    protected Point[] m_SpreadingItemPoints;
    protected Point m_StartPositionPoint = new Point();
    protected char m_Sign;

    public abstract void BuildItem();

    public BoardItems(char sign) {
        m_StartPositionPoint.x = -1;
        m_StartPositionPoint.y = -1;
        this.m_Sign = sign;
    }
}

