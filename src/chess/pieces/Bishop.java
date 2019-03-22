package chess.pieces;

import boardlogic.*;

import java.awt.*;
import java.util.ArrayList;

public class Bishop extends BoardPiece {

    public Bishop(BoardSpace currentSpace, Player player) {
        super(currentSpace, player);
        type = PieceType.BISHOP;
    }

    @Override
    public boolean getPotentialMoves(Board chess) {
        return true;
    }

    public boolean isValidMove(Point requestedMove) {return true;}

    @Override
    public String toString() {
        return (player == Player.WHITE) ? "WhB" : "BlB";
    }
}
