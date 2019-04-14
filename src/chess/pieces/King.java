package chess.pieces;

import boardlogic.*;

import java.awt.*;
import java.util.ArrayList;
import static boardlogic.Team.*;

public class King extends BoardPiece {

    public King(BoardSpace currentSpace, Team team) {
        super(currentSpace, team);
        type = PieceType.KING;
    }

    @Override
    public boolean getPotentialMoves(Board board) {
        return false;
    }

    public boolean getPotentialMoves(Board chess, Player player) {
        // clean up anything previously stored in the potential moves ArrayList
        moves.clear();
        // calculate the spaces where the piece can move given it's current position on the board.
        int x = this.getCurrentSpace().getPosition().x;
        int y = this.getCurrentSpace().getPosition().y;
        int x_p = x + 1;
        int y_p = y + 1;
        int x_m = x - 1;
        int y_m = y - 1;

        // if friendly piece does not occupy space and if space exists and if space is not threatened by an enemy
        // then store space as a Point object in the moves ArrayList.
        if (x_p < 8 && !checkForFriend(chess.board[y][x_p]) && !player.isThreatenedSpace(chess.board[y][x_p]) ){
            if (checkForEnemy(chess.board[y][x_p])){
                player.capture(getCurrentSpace().getOccupyingPiece());
            }
            moves.add(new Point(x_p, y));
        }
        if (y_p < 8 && !checkForFriend(chess.board[y_p][x]) && !player.isThreatenedSpace(chess.board[y_p][x])){
            if (checkForEnemy(chess.board[y_p][x])){
                player.capture(getCurrentSpace().getOccupyingPiece());
            }
            moves.add(new Point(x, y_p));
        }
        if (x_p < 8 && y_p < 8 && !checkForFriend(chess.board[y_p][x_p]) && !player.isThreatenedSpace(chess.board[y_p][x_p])){
            if (checkForEnemy(chess.board[y_p][x_p])){
                player.capture(getCurrentSpace().getOccupyingPiece());
            }
            moves.add(new Point(x_p,y_p));
        }
        if (x_p < 8 && y_m > 0 && !checkForFriend(chess.board[y_m][x_p]) && !player.isThreatenedSpace(chess.board[y_m][x_p])){
            if (checkForEnemy(chess.board[y_m][x_p])){
                player.capture(getCurrentSpace().getOccupyingPiece());
            }
            moves.add(new Point(x_p, y_m));
        }
        if (y_p < 8 && x_m > 0 && !checkForFriend(chess.board[y_p][x_m]) && !player.isThreatenedSpace(chess.board[y_p][x_m])){
            if (checkForEnemy(chess.board[y_p][x_m])){
                player.capture(getCurrentSpace().getOccupyingPiece());
            }
            moves.add(new Point(x_m, y_p));
        }
        if ( x_m > 0 && y_m > 0 && !checkForFriend(chess.board[y_m][x_m]) && !player.isThreatenedSpace(chess.board[y_m][x_m])){
            if (checkForEnemy(chess.board[y_m][x_m])){
                player.capture(getCurrentSpace().getOccupyingPiece());
            }
            moves.add(new Point(x_m, y_m));
        }
        if ( x_m > 0 && !checkForFriend(chess.board[y][x_m]) && !player.isThreatenedSpace(chess.board[y][x_m])){
            if (checkForEnemy(chess.board[y][x_m])){
                player.capture(getCurrentSpace().getOccupyingPiece());
            }
            moves.add(new Point(x_m, y));
        }
        if ( y_m > 0 && !checkForFriend(chess.board[y_m][x]) && !player.isThreatenedSpace(chess.board[y_m][x])){
            if (checkForEnemy(chess.board[y_m][x])){
                player.capture(getCurrentSpace().getOccupyingPiece());
            }
            moves.add(new Point(x, y_m));
        }
        // return false if piece has no move options
        return !moves.isEmpty();
    }

    @Override
    public String toString() {
        return (team == WHITE) ? "WhK" : "BlK";
    }
}
