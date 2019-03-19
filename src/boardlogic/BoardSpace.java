package boardlogic;

import java.awt.*;

public class BoardSpace {
    private BoardPiece occupyingPiece;
    private Point position; //X/Y position referring to ROW/COL on GUI
    private boolean active = false; //Will reflect in GUI if BoardSpace is currently selected/valid move of occupyingPiece

    public BoardSpace(BoardPiece startingPiece, int row, int col) {
        occupyingPiece = startingPiece;
        position = new Point(row, col);
    }

    //Returns 1 on successful transfer else -1
    public int transferPiece(BoardSpace space) {
        if (space.isActive() && space != this) {
            BoardPiece temp = space.occupyingPiece;
            space.setOccupyingPiece(getOccupyingPiece());
            space.getOccupyingPiece().setCurrentSpace(space);

            this.setOccupyingPiece(temp);
            this.occupyingPiece.setCurrentSpace(this);

            return 1;
        }
        return -1;
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

    public void toggleActive() {
        active = !active;
    }

    @Override
    public String toString() {
        if (occupyingPiece == null) {
            return "";
        } else {
            return occupyingPiece.toString();
        }
    }
}
