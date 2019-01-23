package BattleShipLogic;

import java.awt.*;

public class BoardGame {
    private final int k_BoardSize;
    static private final int k_MaxBoardSize = 20;
    static private final int k_MinBoardSize = 5;
    private char m_BoardGame[][];

    public BoardGame(int n) throws GameException {
        if(CheckBoardSizeValidation(n)){
            k_BoardSize = n;
            m_BoardGame =new char[n][n];
            ClearBoard();
        }
        else{
            throw new GameException(String.format("BoardGame's size must be between {0} - {1}", k_MinBoardSize, k_MaxBoardSize));
        }
    }

    public char[][] GetBoardGame(){
        return this.m_BoardGame;
    }

    public char GetSignOnBoardByPoint(Point pointOnBoard) throws GameException{
        if (!CheckPointInRange(pointOnBoard)){
            throw new GameException("The Point is Out of Range");
        }
        else{
            return this.m_BoardGame[pointOnBoard.y][pointOnBoard.x];
        }
    }

    public void ClearBoard(){
        for(int i = 0; i < k_BoardSize; i++){
            for(int j = 0; j < k_BoardSize; j++){
                this.m_BoardGame[i][j] = '~';
            }
        }
    }

    public void UpdateBoard(Point updatePoint, char ch) throws GameException {

        if(CheckPointInRange(updatePoint)) {
            m_BoardGame[updatePoint.y][updatePoint.x] = ch;
        }
        else{
            throw new GameException("Point is out of range");
        }
    }

    private void UpdateBoardBySign(Point pointOnBoard, char sign){
        this.m_BoardGame[pointOnBoard.y][pointOnBoard.x] = sign;
    }

    public void UpdateHitOnBoard(Point pointOnBoard){
        UpdateBoardBySign(pointOnBoard,eItems.Hit.getChar());
    }

    public void UpdateMissOnBoard(Point pointOnBoard){
        UpdateBoardBySign(pointOnBoard,eItems.Miss.getChar());
    }

    // This function adds the item to the board of game
    public boolean AddItemToBoardGame(BoardItems boardItem){
        boolean isAdditionSucceeded = false;

        // checks if all the points of the item are valid
        for (Point point: boardItem.m_SpreadingItemPoints) {
            isAdditionSucceeded = checkPointValidation(point);
            if (!isAdditionSucceeded) {
                break;
            }
        }

        // if the points of the object are valid add the object to the m_BoardGame
        if (isAdditionSucceeded) {
            for (Point point : boardItem.m_SpreadingItemPoints) {
                m_BoardGame[point.y][point.x] = boardItem.m_Sign;
            }
        }

        return isAdditionSucceeded;
    }

    public void HitBoardByPoint(Point pointOnBoard){
        this.m_BoardGame[pointOnBoard.y][pointOnBoard.x]=eItems.Hit.getChar();
    }

    protected boolean CheckPointInRange(Point checkPoint){
        boolean isPointInRange = true;

        if(checkPoint.x < 0 || checkPoint.y < 0 || checkPoint.x >= k_BoardSize || checkPoint.y >= k_BoardSize){
            isPointInRange = false;
        }

        return isPointInRange;
    }

    static public boolean CheckBoardSizeValidation(int boardSize){
        boolean isBoardSizeValid = false;

        if(boardSize >= k_MinBoardSize && boardSize <= k_MaxBoardSize){
            isBoardSizeValid = true;
        }
        return isBoardSizeValid;
    }

    // This function check if the Point is valid
    private boolean checkPointValidation(Point pointOnBoard){
        boolean isPointValid = true;

        // Not in the range of the m_BoardGame
        isPointValid = CheckPointInRange(pointOnBoard);
        if(isPointValid && m_BoardGame[pointOnBoard.y][pointOnBoard.x] != '~'){
            isPointValid = false;
        }
        else if(isPointValid){
            isPointValid = checkSquareValidation(pointOnBoard);
        }
        
        return isPointValid;
    }

    // This function helps to check if the Point is valid
    private  boolean checkSquareValidation(Point pointOnBoard) {
        boolean isSquareValid = true;
        
        for (int x = pointOnBoard.x - 1; x < pointOnBoard.x + 2 && isSquareValid; x++){
            for (int y = pointOnBoard.y - 1; y < pointOnBoard.y + 2 && isSquareValid; y++) {
                if (x >= 0 && x < k_BoardSize && y >= 0 && y < k_BoardSize) {
                    if (m_BoardGame[y][x] != '~') {
                        isSquareValid = false;
                    }
                }
            }
        }
        
        return isSquareValid;
    }

    public boolean CheckIfHitBefore(Point pointOnBoard){
        char ch= this.m_BoardGame[pointOnBoard.y][pointOnBoard.x];
        if (ch!='~')
            return true;
        return false;
    }
}
