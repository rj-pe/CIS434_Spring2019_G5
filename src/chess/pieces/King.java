package chess.pieces;

import boardlogic.BoardSpace;
import boardlogic.BoardPiece;
import boardlogic.PieceType;
import boardlogic.Player;

import java.awt.*;
import java.util.ArrayList;

public class King extends BoardPiece {

    public King(BoardSpace currentSpace, Player player) {
        super(currentSpace, player);
        type = PieceType.KING;
    }

    @Override
    protected ArrayList<Point> getPotentialMoves() {
        ArrayList<Point> moves = new ArrayList<Point>();
        //Calculate and add potential moves to array
        return moves;
    }

    @Override
    public String toString() {
        return (player == Player.WHITE) ? "WhK" : "BlK";
    }
}
