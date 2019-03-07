package boardlogic;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract class BoardPiece {
    private BoardSpace currentSpace;
    protected PieceType type;
    protected Player player;
    protected ImageIcon image;

    public BoardPiece(BoardSpace currentSpace, Player player) {
        this.setCurrentSpace(currentSpace);
        this.player = player;
    }

    public void setCurrentSpace(BoardSpace currentSpace) {
        this.currentSpace = currentSpace;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    protected abstract ArrayList<Point> getPotentialMoves();

    public BoardSpace getCurrentSpace() {
        return currentSpace;
    }
}
