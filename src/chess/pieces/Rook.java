package chess.pieces;

import boardlogic.*;

import java.awt.*;
import java.util.ArrayList;

import static boardlogic.Team.WHITE;

public class Rook extends BoardPiece {

    public Rook(BoardSpace currentSpace, Team team) {
        super(currentSpace, team);
        type = PieceType.ROOK;
    }

    // TODO write the castling method
    
    @Override
    public boolean getPotentialMoves(Board chess) {
        moves.clear();
        // TODO add checkForEnemy() function to limit the number of spaces that piece can move.

        int x_pos = this.getCurrentSpace().getPosition().x;
        int y_pos =  this.getCurrentSpace().getPosition().y;

        // loop through spaces to the left of the piece
        for(int i = x_pos; i >= 0; i--){
            if (!checkForFriend(chess.board[y_pos][i])){
                moves.add(new Point(i, y_pos));
            }
        }

        // loop through spaces to the right of the piece
        for(int i = x_pos; i < 8; i++){
            if (!checkForFriend(chess.board[y_pos][i])){
                moves.add(new Point(i, y_pos));
            }
        }

        // loop through spaces in front of the piece
        for (int i = y_pos; i < 8; i++){
            if (!checkForFriend(chess.board[i][x_pos])){
                moves.add(new Point(x_pos, i));
            }
        }

        // loop through spaces behind piece
        for(int i = y_pos; i >= 0; i-- ){
            if (!checkForFriend(chess.board[i][x_pos])){
                moves.add(new Point(x_pos, i));
            }
        }
        return !moves.isEmpty();
    }

    @Override
    public String toString() {
        return (team == WHITE) ? "WhR" : "BlR";
    }
}
