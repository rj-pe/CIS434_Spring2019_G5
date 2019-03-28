package chess.pieces;

import boardlogic.*;

import java.awt.*;
import java.util.ArrayList;

public class Knight extends BoardPiece {

    public Knight(BoardSpace currentSpace, Player player) {
        super(currentSpace, player);
        type = PieceType.KNIGHT;
    }

    @Override
    public boolean getPotentialMoves(Board chess) {

        // clean up anything previously stored in the potential moves ArrayList
        moves.clear();
        // calculate the spaces where the piece can move given it's current position on the board.
        int x = this.getCurrentSpace().getPosition().x;
        int y = this.getCurrentSpace().getPosition().y;
        int x_p = this.getCurrentSpace().getPosition().x + 1;
        int y_p = this.getCurrentSpace().getPosition().y + 1;
        int x_m = this.getCurrentSpace().getPosition().x - 1;
        int y_m = this.getCurrentSpace().getPosition().y - 1;
        int x_k = this.getCurrentSpace().getPosition().x + 2;
        int y_k = this.getCurrentSpace().getPosition().y + 2;
        int x_t = this.getCurrentSpace().getPosition().x - 2;
        int y_t = this.getCurrentSpace().getPosition().y - 2;


        // if friendly piece does not occupy space and if space exists
        // then store space as a Point object in the moves ArrayList.
        if (x_p < 8 && y_k < 8 && !checkForFriend(chess.board[y_k][x_p]) ){
            moves.add(new Point(x_p, y_k));
        }
        if (x_m >= 0 && y_k < 8 && !checkForFriend(chess.board[y_k][x_m])){
            moves.add(new Point(x_m, y_k));
        }
        if (x_p < 8 && y_t >= 0 && !checkForFriend(chess.board[y_t][x_p])){
            moves.add(new Point(x_p,y_t));
        }
        if (x_m >= 0 && y_k >= 0 && !checkForFriend(chess.board[y_k][x_m])){
            moves.add(new Point(x_m, y_k));
        }
        if (y_p < 8 && x_k < 8 && !checkForFriend(chess.board[y_p][x_k])){
            moves.add(new Point(x_m, y_p));
        }
        if (y_p < 8 && x_t >= 0 && !checkForFriend(chess.board[y_p][x_t])){
            moves.add(new Point(x_t, y_p));
        }
        if (y_m >= 0 && x_k < 8 && !checkForFriend(chess.board[y_m][x_k])){
            moves.add(new Point(x_k, y_m));
        }
        if (y_m >= 0 && x_t >= 0 && !checkForFriend(chess.board[y_m][x_t])){
            moves.add(new Point(x_t, y_m));
        }
        return !moves.isEmpty();
    }

    @Override
    public String toString() {
        return (player == Player.WHITE) ? "WhKn" : "BlKn";
    }
}
