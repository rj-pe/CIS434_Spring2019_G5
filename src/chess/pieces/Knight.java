package chess.pieces;

import boardlogic.BoardSpace;
import boardlogic.BoardPiece;
import boardlogic.PieceType;
import boardlogic.Player;

import java.awt.*;
import java.util.ArrayList;

public class Knight extends BoardPiece {

    public Knight(BoardSpace currentSpace, Player player) {
        super(currentSpace, player);
        type = PieceType.KNIGHT;
    }

    @Override
    protected ArrayList<Point> getPotentialMoves() {
        return null;
    }

    @Override
    public String toString() {
        return (player == Player.WHITE) ? "WhKn" : "BlKn";
    }
}
