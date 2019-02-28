package pieces;

import gamelogic.BoardSpace;
import gamelogic.PieceType;

import java.awt.*;
import java.util.ArrayList;

public class King extends BoardPiece {

    public King(BoardSpace currentSpace, String player) {
        super(currentSpace, player);
        type = PieceType.KING;
    }

    @Override
    public ArrayList<Point> getPotentialMoves() {
        ArrayList moves = new ArrayList<Point>();
        //Calculate and add potential moves to array
        return moves;
    }
}
