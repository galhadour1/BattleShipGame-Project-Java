package BattleShipLogic;

public enum eDirection {
    Row("ROW"),
    Column("COLUMN"),
    UpRight("UP_RIGHT"),
    DownRight("DOWN_RIGHT"),
    RightDowm("RIGHT_DOWN"),
    RightUp("RIGHT_UP");

    private  String m_Direction;

    private eDirection(String direction)
    {
        this.m_Direction = direction;
    }

    static public eDirection StringToDirection(String Direction)
    {
        switch (Direction)
        {
            case "ROW": return Row;
            case "COLUMN": return Column;
            case "RIGHT_UP": return RightUp;
            case "DOWN_RIGHT": return DownRight;
            case "RIGHT_DOWN":return RightDowm;
            case "UP_RIGHT": return UpRight;
            default: return null;
        }
    }

    public String toString() {
        return this.m_Direction;
    }

    public boolean EqualsDirection(String directionToCheck) {
        // (otherName == null) check is not needed because name.equals(null) returns false
        return m_Direction.equals(directionToCheck);
    }
}

