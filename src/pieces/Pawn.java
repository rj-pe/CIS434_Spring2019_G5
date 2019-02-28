package pieces;

import gamelogic.BoardSpace;
import gamelogic.PieceType;

import java.awt.*;
import java.util.ArrayList;

public class Pawn extends BoardPiece {

    public Pawn(BoardSpace currentSpace, String player) {
        super(currentSpace, player);
        type = PieceType.PAWN;
        //image = PawnImage;
    }

    @Override
    ArrayList<Point> getPotentialMoves() {
        return null;
    }
}
