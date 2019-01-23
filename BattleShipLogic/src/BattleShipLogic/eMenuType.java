package BattleShipLogic;

import java.util.HashMap;
import java.util.Map;

public enum eMenuType {
    LoadXmlFile(1, "Load the game from XMl file", true),
    StartTheGame(2, "Start the amazing game!", false),
    GameStatus(3, "Show game status", false),
    AttackOpponent(4, "Attack the opponent", false),
    GameStatistics(5, "Show game satistics", false),
    EndCurrentGame(6, "End current game", false),
    Trap(7, "Place trap in board", false),
    ExitGame(8, "Exit game", true),
    WaitingForAction(0, "", false);

    private final String k_MenuTypeDescription;
    private final int k_MenuTypeNumber;
    private static Map<Integer, eMenuType> m_MyMenu = new HashMap<>();
    private boolean m_IsMenuTypeActive = false;

    static {
        for (eMenuType menuType : eMenuType.values()) {
            m_MyMenu.put(menuType.k_MenuTypeNumber, menuType);
        }
    }

    private eMenuType(int menuTypeNumber, String menuTypeDescription, boolean isMenuTypeActive) {
        this.k_MenuTypeNumber = menuTypeNumber;
        this.k_MenuTypeDescription = menuTypeDescription;
        this.m_IsMenuTypeActive = isMenuTypeActive;
    }

    public boolean getIsMenuTypeActive() {
        return m_IsMenuTypeActive;
    }

    public String getMenuTypeDescription() {
        return k_MenuTypeDescription;
    }

    public static eMenuType getMenuType(int num) {
        return m_MyMenu.get(num);
    }

    public int getMenuTypeNumber() {
        return k_MenuTypeNumber;
    }

    public void setIsMenuTypeActive(boolean isActive) {
        m_IsMenuTypeActive = isActive;
    }
}


