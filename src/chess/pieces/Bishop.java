package chess.pieces;

import boardlogic.*;

import java.awt.*;
import java.util.ArrayList;

public class Bishop extends BoardPiece {

    public Bishop(BoardSpace currentSpace, Player player) {
        super(currentSpace, player);
        type = PieceType.BISHOP;
    }

    @Override
    public boolean getPotentialMoves(Board chess) {
        moves.clear();

        int x_pos = this.getCurrentSpace().getPosition().x;
        int y_pos =  this.getCurrentSpace().getPosition().y;

        // For moves in Quadrant 3.
        for(int i = x_pos, j = y_pos; i >= 0 || j >=0; i--, j--){

            if (!checkForFriend(chess.board[j][i])){
                moves.add(new Point(i, j));
            }
            else
                break;
        }
        // For moves in Quadrant 2.
        for(int i = x_pos, j = y_pos; i < 8 || j >=0; i++, j--){

            if (!checkForFriend(chess.board[j][i])){
                moves.add(new Point(i, j));
            }
            else
                break;
        }
        // For Moves in Quadrant 4.
        for(int i = x_pos, j = y_pos; i >= 0 || j < 8; i--, j++){

            if (!checkForFriend(chess.board[j][i])){
                moves.add(new Point(i, j));
            }
            else
                break;
        }
        // For moves in Quadrant 1.
        for(int i = x_pos, j = y_pos; i < 8 || j < 8; i++, j++){

            if (!checkForFriend(chess.board[j][i])){
                moves.add(new Point(i, j));
            }
            else
                break;
        }
        return !moves.isEmpty();
    }

    @Override
    public String toString() {
        return (player == Player.WHITE) ? "WhB" : "BlB";
    }
}
