package boardlogic;

import java.awt.*;
import java.io.Serializable;

/**
 * This object represents a single board space on the chess board.
 * Each board space has an absolute position on the board.
 * Each board space can hold an occupant piece.
 */
public class BoardSpace implements Serializable {
    /**
     * The piece which this space holds.
     */
    private BoardPiece occupyingPiece;
    /**
     * A Point object which represents the row and column coordinate of this space.
     */
    private Point position; //X/Y position referring to ROW/COL on GUI
    /**
     * A boolean which indicates whether this space is currently selected by the user.
     */
    private boolean active = false;
    /**
     * An integer representing the column number of this space.
     */
    private int column;
    /**
     * An integer representing the row number of this space.
     */
    private int rows;//Will reflect in GUI if BoardSpace is currently selected/valid move of occupyingPiece

    /**
     * Constructor for a BoardSpace object.
     * @param startingPiece The piece which is initially placed on this space.
     * @param row The row to which this space belongs.
     * @param col The column to which this space belongs.
     */
    public BoardSpace(BoardPiece startingPiece, int row, int col) {
        occupyingPiece = startingPiece;
        column = col;
        rows = row;
        position = new Point(row, col);
    }

    /**
     * Transfers a piece from this space to the space that is provided by the parameter of this method.
     * @param space The space to which the piece on this space should be handed to.
     * @return 1 on success, -1 if an error has occurred.
     */
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

    /**
     * Accessor method for this space's current occupying piece.
     * @return The piece that is on this space, null if space is empty.
     */
    public BoardPiece getOccupyingPiece() {
        return occupyingPiece;
    }

    /**
     * Setter method for this space's occupying piece field.
     * @param occupyingPiece The piece which will be transferred to this space.
     */
    void setOccupyingPiece(BoardPiece occupyingPiece) {
        this.occupyingPiece = occupyingPiece;
    }

    /**
     * Used when piece moves off of it's current space.
     */
    private void setOccupyingPieceAsNull(){
        this.occupyingPiece = null;
    }

    /**
     * An accessor for this spaces position.
     * @return A Point object which represents the row and column to which this space belongs.
     */
    public Point getPosition() {
        return position;
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
