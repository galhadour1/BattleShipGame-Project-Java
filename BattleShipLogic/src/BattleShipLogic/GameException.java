package BattleShipLogic;

public class GameException extends Exception {

    public GameException(){
        super();
    }

    public GameException(String messageToThrow){
        super(messageToThrow);
    }

    public GameException(String messageToThrow, Throwable reason) {
        super(messageToThrow, reason); }

    public GameException(Throwable reason) {
        super(reason); }
}
