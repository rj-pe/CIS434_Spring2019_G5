package chess.pieces;

import boardlogic.*;

import java.awt.*;
import java.util.ArrayList;

public class Knight extends BoardPiece {

    public Knight(BoardSpace currentSpace, Player player) {
        super(currentSpace, player);
        type = PieceType.KNIGHT;
    }

    @Override
    public boolean getPotentialMoves(Board chess) {
        return true;
    }

    public boolean isValidMove(Point requestedMove) {return true;}

    @Override
    public String toString() {
        return (player == Player.WHITE) ? "WhKn" : "BlKn";
    }
}
