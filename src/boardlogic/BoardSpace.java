package boardlogic;

import java.awt.*;
import java.io.Serializable;

public class BoardSpace implements Serializable {
    private BoardPiece occupyingPiece;
    private Point position; //X/Y position referring to ROW/COL on GUI
    private boolean active = false;
    private int column;
    private int rows;//Will reflect in GUI if BoardSpace is currently selected/valid move of occupyingPiece

    public BoardSpace(BoardPiece startingPiece, int row, int col) {
        occupyingPiece = startingPiece;
        column = col;
        rows = row;
        position = new Point(row, col);
    }

    //Returns 1 on successful transfer else -1
    public int transferPiece(BoardSpace space) {
        if (space != this) {
            if (this.getOccupyingPiece().getType() == PieceType.ROOK || this.getOccupyingPiece().getType() == PieceType.KING)
                this.getOccupyingPiece().setHasMoved();

            space.setOccupyingPiece(this.getOccupyingPiece());
            space.getOccupyingPiece().setCurrentSpace(space);

            this.setOccupyingPieceAsNull();
            return 1;
        }
        return -1;
    }

    public BoardPiece getOccupyingPiece() {
        return occupyingPiece;
    }

    void setOccupyingPiece(BoardPiece occupyingPiece) {
        this.occupyingPiece = occupyingPiece;
    }

    /**
     * Used when piece moves off of it's current space.
     */
    private void setOccupyingPieceAsNull(){
        this.occupyingPiece = null;
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
