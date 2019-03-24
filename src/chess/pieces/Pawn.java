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
        for (int i = y_pos; i < 8; i++) {
            if (!checkForFriend(chess.board[i][x_pos])) {
                moves.add(new Point(x_pos, i));
            }
        }

        //TODO capture pieces diagonally
        //moves forward-diagonally to capture pieces
        for (int i = y_pos; i < 8; i++) {        //moving forward
            if (!checkForFriend(chess.board[i][x_pos])) {
                for (int j = x_pos; j >= 0; j--) {   //forward-left (left diagonal)
                    if (!checkForFriend(chess.board[y_pos][j])) {
                        moves.add(new Point(j, y_pos));
                    }
                    for (int k = x_pos; k < 8; k++) {   //forward-right (right diagonal)
                        if (!checkForFriend(chess.board[y_pos][k])) {
                            moves.add(new Point(k, y_pos));
                        }
                    }
                }
            }
        }

        // return false if piece has no move options
        return !moves.isEmpty();
    }

    @Override
    public String toString() {
        return (player == Player.WHITE) ? "WhP" : "BlP";
    }
}
