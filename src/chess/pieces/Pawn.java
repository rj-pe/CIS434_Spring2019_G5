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

    @Override
    public boolean getPotentialMoves(Board chess) {
        return true;
    }

    public boolean isValidMove(Point requestedMove) {return true;}

    @Override
    public String toString() {
        return (player == Player.WHITE) ? "WhP" : "BlP";
    }
}
