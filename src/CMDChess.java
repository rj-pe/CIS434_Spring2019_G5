//import chessgui.*;
import boardlogic.*;

import java.awt.*;
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
                System.out.print("\nSelect Piece to Move (column, row): ");
                pieceToMove = reader.next();
                y1 = Character.getNumericValue(pieceToMove.charAt(1));
                x1 = Character.getNumericValue(pieceToMove.charAt(0));
                if (chess.board[y1][x1].getOccupyingPiece() != null) {
                    if (chess.board[y1][x1].getOccupyingPiece().getPlayer() == currentPlayer){
                        /*
                          Active player owns the selected piece, calculate its potential moves.
                          Each piece class will override the getPotentialMoves() method with its movement pattern.
                          If no moves are possible, user must select a different piece.
                          TODO implement the getPotentialMoves() method for each piece type. See King.java for example.
                        */
                        if( chess.board[y1][x1].getOccupyingPiece().getPotentialMoves(chess)){
                            // potential moves list is not empty proceed with turn.
                            break;
                        }
                        // the selected player has no open moves
                        System.out.println("You Selected a Piece with No Moves Available!");
                    }
                }
                System.out.print("Invalid Choice!");
            }
            chess.board[y1][x1].toggleActive(); //Mimics what GUI would do

            Point destination = promptUserForMove();

            /*
             Check the chosen space against the list of potential moves calculated by getPotentialMoves() method.
             If the move is not valid prompt user to choose another space.
             */
             while( ! chess.board[y1][x1].getOccupyingPiece().isValidMove( destination ) ) {
                 // requested move is invalid, choose another space to move to.
                 System.out.print("you can't move there!\n");
                 destination = promptUserForMove();
             }
            // The requested move is valid, proceed with the piece swap.
            x2 = destination.x;
            y2 = destination.y;

            chess.board[y2][x2].toggleActive(); //This only exists while move validation hasn't been implemented

            //TODO check for capture
            chess.board[y1][x1].transferPiece(chess.board[y2][x2]);

            chess.board[y1][x1].toggleActive();
            chess.board[y2][x2].toggleActive();

            currentPlayer = changePlayer(currentPlayer);
        }
    }

    /**
     * Asks the user for a destination for the selected piece.
     * @return Point object representing the x and y coordinates of the destination board space.
     */
    private static Point promptUserForMove(){
        Scanner reader = new Scanner(System.in);
        System.out.print("Select Space to Move to (column, row): ");
        String spaceToMoveTo = reader.next();
        int y = Character.getNumericValue(spaceToMoveTo.charAt(1));
        int x = Character.getNumericValue(spaceToMoveTo.charAt(0));
        return new Point(x, y);
    }

    static Player changePlayer(Player player) {
        if (player == Player.WHITE) return Player.BLACK;
        return Player.WHITE;
    }
}
