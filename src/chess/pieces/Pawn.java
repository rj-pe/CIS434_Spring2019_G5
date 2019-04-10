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

        // loop through spaces in front of the piece
        if (player == Player.WHITE) {
             for (int i = y_pos; i < 8; i++) {
                 if (!checkForFriend(chess.board[i][x_pos]))
                     moves.add(new Point(x_pos, i)); 
             }
        }
         else { // for BlP
             for (int i = y_pos; i >= 0; i--) {
                    if (!checkForFriend(chess.board[i][x_pos])) 
                        moves.add(new Point(x_pos, i));
             }
         }

        //TODO capture pieces diagonally
        //moves forward-diagonally to capture pieces
      
        // return false if piece has no move options
        return !moves.isEmpty();
    }
    
    //TODO Promotion to Queen or Knight or Rook or Bishop
    
    
    @Override
    public String toString() {
        return (player == Player.WHITE) ? "WhP" : "BlP";
    }
}
