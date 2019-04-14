package chess.pieces;

import boardlogic.*;

import java.awt.*;
import java.util.ArrayList;

import static boardlogic.Team.WHITE;

public class Queen extends BoardPiece {

    public Queen(BoardSpace currentSpace, Team team) {
        super(currentSpace, team);
        type = PieceType.QUEEN;
    }

    /* Queen moves are a combination of Rook and Bishop moves */
    @Override
    public boolean getPotentialMoves(Board board) {return false;}
    public boolean getPotentialMoves(Board chess, Player player) {
        moves.clear();

        int x_pos = this.getCurrentSpace().getPosition().x;
        int y_pos = this.getCurrentSpace().getPosition().y;

        // added ROOK moves
        // loop through spaces to the left of the piece
        for (int i = x_pos; i >= 0; i--) {
            if (!checkForFriend(chess.board[y_pos][i]))
                moves.add(new Point(i, y_pos));
            if (checkForEnemy(chess.board[y_pos][i])){
                this.player.capture(getCurrentSpace().getOccupyingPiece());
            }
        }
        // loop through spaces to the right of the piece
        for (int i = x_pos; i < 8; i++) {
            if (!checkForFriend(chess.board[y_pos][i]))
                moves.add(new Point(i, y_pos));
            if (checkForEnemy(chess.board[y_pos][i])){
                this.player.capture(getCurrentSpace().getOccupyingPiece());
            }
        }
        // loop through spaces in front of the piece
        for (int i = y_pos; i < 8; i++) {
            if (!checkForFriend(chess.board[i][x_pos]))
                moves.add(new Point(x_pos, i));
            if (checkForEnemy(chess.board[i][x_pos])){
                this.player.capture(getCurrentSpace().getOccupyingPiece());
            }
        }
        // loop through spaces behind piece
        for (int i = y_pos; i >= 0; i--) {
            if (!checkForFriend(chess.board[i][x_pos]))
                moves.add(new Point(x_pos, i));
            if (checkForEnemy(chess.board[i][x_pos])){
                this.player.capture(getCurrentSpace().getOccupyingPiece());
            }
        }
        // added BISHOP moves
        // For moves in Quadrant 3.
        for(int i = x_pos, j = y_pos; i >= 0 && j >=0; i--, j--){
            if (!checkForFriend(chess.board[j][i]))
                moves.add(new Point(i, j));
            if (checkForEnemy(chess.board[j][i])){
                this.player.capture(getCurrentSpace().getOccupyingPiece());
            }
        }
        // For moves in Quadrant 2.
        for(int i = x_pos, j = y_pos; i < 8 && j >=0; i++, j--) {
            if (!checkForFriend(chess.board[j][i]))
                moves.add(new Point(i, j));
            if (checkForEnemy(chess.board[j][i])) {
                this.player.capture(getCurrentSpace().getOccupyingPiece());
            }
        }
        // For Moves in Quadrant 4.
        for(int i = x_pos, j = y_pos; i >= 0 && j < 8; i--, j++){
            if (!checkForFriend(chess.board[j][i]))
                moves.add(new Point(i, j));
            if (checkForEnemy(chess.board[j][i])){
                this.player.capture(getCurrentSpace().getOccupyingPiece());
            }
        }
        // For moves in Quadrant 1.
        for(int i = x_pos, j = y_pos; i < 8 && j < 8; i++, j++){
            if (!checkForFriend(chess.board[j][i]))
                moves.add(new Point(i, j));
            if (checkForEnemy(chess.board[j][i])){
                this.player.capture(getCurrentSpace().getOccupyingPiece());
            }
        }
        // return false if piece has no move options
        return !moves.isEmpty();
    }

    public boolean isValidMove(Point requestedMove) {return true;}

    @Override
    public String toString() {
        return (team == WHITE) ? "WhQ" : "BlQ";
    }
}
