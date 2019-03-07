package chess.pieces;

import boardlogic.BoardSpace;
import boardlogic.BoardPiece;
import boardlogic.PieceType;
import boardlogic.Player;

import java.awt.*;
import java.util.ArrayList;

public class Pawn extends BoardPiece {

    public Pawn(BoardSpace currentSpace, Player player) {
        super(currentSpace, player);
        type = PieceType.PAWN;
        //image = PawnImage;
    }

    @Override
    protected ArrayList<Point> getPotentialMoves() {
        return null;
    }

    @Override
    public String toString() {
        return (player == Player.WHITE) ? "WhP" : "BlP";
    }
}
