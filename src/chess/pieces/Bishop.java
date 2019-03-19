package chess.pieces;

import boardlogic.BoardSpace;
import boardlogic.BoardPiece;
import boardlogic.PieceType;
import boardlogic.Player;

import java.awt.*;
import java.util.ArrayList;

public class Bishop extends BoardPiece {

    public Bishop(BoardSpace currentSpace, Player player) {
        super(currentSpace, player);
        type = PieceType.BISHOP;
    }

    @Override
    protected ArrayList<Point> getPotentialMoves() {
        return null;
    }

    @Override
    public String toString() {
        return (player == Player.WHITE) ? "WhB" : "BlB";
    }
}
