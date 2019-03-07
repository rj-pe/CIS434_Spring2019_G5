//import chessgui.*;
import boardlogic.*;

import java.util.Scanner;

public class CMDChess {

    public static void main(String[] args) {

        //REMOVE FOLLOWING COMMENTS AND IMPORT CHESSGUI TO RUN BASIC GUI ELSE CHESS WILL RUN IN CMD
        //ChessBoardGUI frame = new ChessBoardGUI();
        //frame.run();

        Board chess = new Board(8, GameType.CHESS);
        String pieceToMove, spaceToMoveTo;
        int x1, x2, y1, y2;
        Player currentPlayer = Player.WHITE;
        Scanner reader = new Scanner(System.in);
        while (true) {
            //Board Creation
            System.out.println("\n                     Board /////////////////////////////////////////////////// Graveyard");

            //Printing column numbers
            for (int i = 0; i < 8; i++) {
                System.out.print("       " + i);
            }
            System.out.print("\n");

            //Prints all rows from left to right and top to bottom starting with row number
            for (int i = 0; i < 8; i++) {
                System.out.print(i + "   ");
                for (int j = 0; j < 8; j++) {
                    StringBuilder output = new StringBuilder();
                    output.append("[  " + chess.board[i][j]);

                    //Keeps spaces lined up
                    while (output.length() < 7) {
                        output.append(" ");
                    }
                    output.append("]");
                    System.out.print(output.toString());
                }
                System.out.print("\n");
            }

            if (currentPlayer == Player.WHITE) {
                System.out.print("White player's turn!");
            } else System.out.print("Black player's turn!");
            //Takes coordinates as two consecutive integers. Example: 11
            while (true) {
                System.out.print("\nSelect Piece to Move: ");
                pieceToMove = reader.next();
                x1 = Character.getNumericValue(pieceToMove.charAt(1));
                y1 = Character.getNumericValue(pieceToMove.charAt(0));
                if (chess.board[x1][y1].getOccupyingPiece() != null) {
                    if (chess.board[x1][y1].getOccupyingPiece().getPlayer() == currentPlayer) break;
                }
                System.out.print("Invalid Choice!");
            }
            chess.board[x1][y1].toggleActive(); //Mimics what GUI would do
            System.out.print("Select Space to Move to: ");
            spaceToMoveTo = reader.next();
            x2 = Character.getNumericValue(spaceToMoveTo.charAt(1));
            y2 = Character.getNumericValue(spaceToMoveTo.charAt(0));

            chess.board[x2][y2].toggleActive(); //This only exists while move validation hasn't been implemented

            //TODO Validate move and check for capture
            chess.board[x1][y1].transferPiece(chess.board[x2][y2]);

            chess.board[x1][y1].toggleActive();
            chess.board[x2][y2].toggleActive();

            currentPlayer = changePlayer(currentPlayer);
        }
    }

    static Player changePlayer(Player player) {
        if (player == Player.WHITE) return Player.BLACK;
        return Player.WHITE;
    }
}
