//import chessgui.*;
import boardlogic.*;
import chess.Arbiter;

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
        Player inactivePlayer = Player.BLACK;
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

            // The arbiter object will keep track of whether either king is under attack.
            Arbiter arbiter = new Arbiter(chess.board[0][3].getOccupyingPiece(), chess.board[7][3].getOccupyingPiece(), currentPlayer);

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
                          Each piece class will override the calculatePotentialMoves() method with its movement pattern.
                          If no moves are possible, user must select a different piece.
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
             Check the chosen space against the list of potential moves calculated by calculatePotentialMoves() method.
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

            // arbiter object checks if the move puts the enemy king in check
            if( arbiter.movePutsKingInCheck(chess.board[y2][x2].getOccupyingPiece()) ){
                // check has occurred
                // print alert to console
                System.out.println(String.format("%s in check!", inactivePlayer.toString()));
                // remove space from list of enemy king's potential moves
                arbiter.removeSpaceFromMoves(chess.board[y2][x2], inactivePlayer);
                // arbiter object checks if the move puts the enemy king in checkmate
                if( arbiter.movePutsKingInCheckMate(arbiter.defensiveMovePossible())){
                 // check mate has occurred
                 // game is over
                 System.out.println(String.format("Player %s wins", currentPlayer.toString()) );
                 System.exit(0);
                }
            }

            chess.board[y1][x1].toggleActive();
            chess.board[y2][x2].toggleActive();

            // turn ends, change players.
            currentPlayer = changeActivePlayer(currentPlayer, arbiter);
            inactivePlayer = changeInactivePlayer(inactivePlayer);
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

    /**
     * Change which player is inactive.
     * @param player The player who is currently inactive.
     * @return The new inactive player.
     */

    private static Player changeInactivePlayer(Player player){
        if (player == Player.WHITE){
            return Player.BLACK;
        }
        return Player.WHITE;
    }

    /**
     * Changes which player is active.
     * @param player The player who is currently active
     * @param arbiter The arbiter object also keeps track of the active player.
     * @return The new active player.
     */
    static Player changeActivePlayer(Player player, Arbiter arbiter) {
        if (player == Player.WHITE) {
            arbiter.setActivePlayer(Player.BLACK);
            return Player.BLACK;
        }
        arbiter.setActivePlayer(Player.WHITE);
        return Player.WHITE;
    }
}
