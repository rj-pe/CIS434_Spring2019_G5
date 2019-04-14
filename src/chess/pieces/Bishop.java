package chess.pieces;

import boardlogic.*;

import java.awt.*;
import java.util.ArrayList;

import static boardlogic.Team.WHITE;

public class Bishop extends BoardPiece {

    public Bishop(BoardSpace currentSpace, Team team) {
        super(currentSpace, team);
        type = PieceType.BISHOP;
    }

    @Override

    public boolean getPotentialMoves(Board board) {return false;}
    public boolean getPotentialMoves(Board chess, Player player) {
        moves.clear();

        int x_pos = this.getCurrentSpace().getPosition().x;
        int y_pos = this.getCurrentSpace().getPosition().y;

        // For moves in Quadrant 3.
        for(int i = x_pos, j = y_pos; i >= 0 && j >=0; i--, j--){

            if (!checkForFriend(chess.board[j][i])){
                moves.add(new Point(i, j));
                if (checkForEnemy(chess.board[j][i])){
                    this.player.capture(getCurrentSpace().getOccupyingPiece());
                }
            }
        }
        // For moves in Quadrant 2.
        for(int i = x_pos, j = y_pos; i < 8 && j >=0; i++, j--){

            if (!checkForFriend(chess.board[j][i])){
                moves.add(new Point(i, j));
                if (checkForEnemy(chess.board[j][i])){
                    this.player.capture(getCurrentSpace().getOccupyingPiece());
                }
            }
        }
        // For Moves in Quadrant 4.
        for(int i = x_pos, j = y_pos; i >= 0 && j < 8; i--, j++){

            if (!checkForFriend(chess.board[j][i])){
                moves.add(new Point(i, j));
                if (checkForEnemy(chess.board[j][i])){
                    this.player.capture(getCurrentSpace().getOccupyingPiece());
                }
            }
        }
        // For moves in Quadrant 1.
        for(int i = x_pos, j = y_pos; i < 8 && j < 8; i++, j++){

            if (!checkForFriend(chess.board[j][i])){
                moves.add(new Point(i, j));
                if (checkForEnemy(chess.board[j][i])){
                    this.player.capture(getCurrentSpace().getOccupyingPiece());
                }
            }
        }
        return !moves.isEmpty();
    }

    @Override
    public String toString() {
        return (team == WHITE) ? "WhB" : "BlB";
    }
}
