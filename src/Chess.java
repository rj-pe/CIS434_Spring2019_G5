import chessgui.*;
import gamelogic.Board;
import gamelogic.GameType;

public class Chess {
    public static void main(String[] args) {
        /*REMOVE COMMENTS TO RUN BASIC GUI
        ChessBoardGUI frame = new ChessBoardGUI();
        frame.run(); */
        final Board board = new Board(8, GameType.CHESS);
    }
}
