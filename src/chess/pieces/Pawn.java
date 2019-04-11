package chess.pieces;

import boardlogic.*;

import java.awt.*;
import java.util.ArrayList;

public class Pawn extends BoardPiece {

    public Pawn(BoardSpace currentSpace, Player player) {
        super(currentSpace, player);
        type = PieceType.PAWN;
        //image = PawnImage;
    }

    //generate potential moves
    @Override
    public boolean getPotentialMoves(Board chess) {
        moves.clear();

        int x_pos = this.getCurrentSpace().getPosition().x;
        int y_pos = this.getCurrentSpace().getPosition().y;

        /*  no backward moves allowed
        *   loop through spaces in front of the piece
        */
            if (player == Player.WHITE) {
                for (int i = y_pos; i < 8; i++) {
                    if (!Occupied(chess.board[i][x_pos]) )
                        moves.add(new Point(x_pos, i)); 
                }
              } 
            //for BlP moves only 
            else {
                for (int i = y_pos; i >= 0; i--) {
                    if (!Occupied(chess.board[i][x_pos]) ) 
                        moves.add(new Point(x_pos, i));
                }
            }
        
        /*  moves forward-diagonally to capture pieces  */
        // capturing for Wh pieces (Q3 and Q4)
        if(player == Player.WHITE) {
            //  capturing in Quadrant 3
            for(int i = x_pos, j = y_pos; i >= 0 && j < 8; i--, j++) {
                if (checkForEnemy(chess.board[j][i]) && !checkForFriend(chess.board[j][i])) //captures enemy piece and checks for friend piece diagonally before capturing
                   moves.add(new Point(i, j));
            }
            // capturing in Quadrant 4
            for(int i = x_pos, j = y_pos; i < 8 && j < 8; i++, j++) { 
                if (checkForEnemy(chess.board[j][i]) && !checkForFriend(chess.board[j][i]))
                    moves.add(new Point(i, j));
            }
        }
        // capturing for Bl pieces (Q1 and Q2)
        else {
            //capturing in Quadrant 2
            for (int i = x_pos, j = y_pos; i >= 0 && j >= 0; i--, j--) {
                if (checkForEnemy(chess.board[j][i]) && !checkForFriend(chess.board[j][i]))
                    moves.add(new Point(i, j));
            }
            //capturing in Quadrant 1
            for(int i = x_pos, j = y_pos; i < 8 && j >=0; i++, j--) {
                if (checkForEnemy(chess.board[j][i]) && !checkForFriend(chess.board[j][i]))
                    moves.add(new Point(i, j));
            }
        }
      
        // return false if piece has no move options
        return !moves.isEmpty();
    }
    
    //TODO Promotion to Queen or Knight or Rook or Bishop
    
    
    @Override
    public String toString() {
        return (player == Player.WHITE) ? "WhP" : "BlP";
    }
}
