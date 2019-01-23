package BattleShipUI;

import BattleShipLogic.eMenuType;

import java.util.ArrayList;

public class Menu {
    ArrayList<eMenuType> m_ArrMenuTypes = new ArrayList();
    String m_MenuTitle ="Battle BattleShip Menu:";

    public ArrayList<eMenuType> getArrMenuTypes() {
        return m_ArrMenuTypes;
    }

    public Menu()
    {
        m_ArrMenuTypes.add(eMenuType.LoadXmlFile);
        m_ArrMenuTypes.add(eMenuType.StartTheGame);
        m_ArrMenuTypes.add(eMenuType.GameStatus);
        m_ArrMenuTypes.add(eMenuType.AttackOpponent);
        m_ArrMenuTypes.add(eMenuType.GameStatistics);
        m_ArrMenuTypes.add(eMenuType.EndCurrentGame);
        m_ArrMenuTypes.add(eMenuType.Trap);
        m_ArrMenuTypes.add(eMenuType.ExitGame);
    }

    public void PrintMenu()
    {
        System.out.println(m_MenuTitle);
        System.out.println("===========================");
        for (eMenuType menuType: m_ArrMenuTypes)
        {
            System.out.println(String.format("%d) %s",menuType.getMenuTypeNumber(),menuType.getMenuTypeDescription()));
        }
    }
}
