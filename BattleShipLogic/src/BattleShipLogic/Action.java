package BattleShipLogic;

import java.awt.*;

public class Action {
    private GameTime m_GameTime;
    private BattleShipLogic.Moves m_GameMove;
    private Point m_Point;

    public Action(BattleShipLogic.Moves m_GameMove){

        this.m_GameMove = m_GameMove;
    }

    public void SetTime(GameTime gameTime){

        this.m_GameTime = gameTime;
    }

    public void SetPoint(Point point){

        this.m_Point = point;
    }

}
