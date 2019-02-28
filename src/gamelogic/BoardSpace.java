package gamelogic;

import pieces.BoardPiece;

import java.awt.*;

public class BoardSpace {
    private BoardPiece occupyingPiece;
    private Point position; // X/Y position referring to ROW/COL on GUI
    private boolean active = false; // If BoardSpace is currently selected/valid move of occupyingPiece in GUI

    public BoardSpace(BoardPiece startingPiece, int row, int col) {
        occupyingPiece = startingPiece;
        position = new Point(row, col);
    }

    public void transferPiece(BoardSpace space) {
        // Transfers pieces between two active spaces
        if (space.isActive()) {
            BoardPiece temp = space.occupyingPiece;
            space.setOccupyingPiece(getOccupyingPiece());
            this.setOccupyingPiece(temp);
        }
    }

    public BoardPiece getOccupyingPiece() {
        return occupyingPiece;
    }

    public void setOccupyingPiece(BoardPiece occupyingPiece) {
        this.occupyingPiece = occupyingPiece;
    }

    public Point getPosition() {
        return position;
    }

    public boolean isActive() {
        return active;
    }

    public void activateSpace() {
        if (this.occupyingPiece != null) {
            this.active = true;
        }
    }

    public void deactivateActive() {
        this.active = false;
    }
}
