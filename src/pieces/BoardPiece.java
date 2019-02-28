package pieces;

import gamelogic.BoardSpace;
import gamelogic.PieceType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract class BoardPiece {
    BoardSpace currentSpace;
    PieceType type;
    String player;
    ImageIcon image;

    public BoardPiece(BoardSpace currentSpace, String player) {
        this.currentSpace = currentSpace;
        this.player = player;
    }

    abstract ArrayList<Point> getPotentialMoves();
}
