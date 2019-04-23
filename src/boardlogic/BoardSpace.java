package boardlogic;

import java.awt.*;

public class BoardSpace {
    private BoardPiece occupyingPiece;
    private Point position; //X/Y position referring to ROW/COL on GUI
    private boolean active = false; //Will reflect in GUI if BoardSpace is currently selected/valid move of occupyingPiece

    public BoardSpace(BoardPiece startingPiece, int row, int col) {
        occupyingPiece = startingPiece;
        protected int col = col;
        protected int row = row;
        position = new Point(row, col);
    }

    //Returns 1 on successful transfer else -1
    public int transferPiece(BoardSpace space) {
        if (space != this) {
            if (this.getOccupyingPiece().getType() == pieceTypeROOK || this.getOccupyingPiece().getType() == pieceTypeKING)
                this.getOccupyingPiece().setHasMoved();
            if (this.getOccupyingPiece().getType() == pieceTypeKING && (this.col - space.col) > 1){
                chess.board[0][row].getOccupyingPiece().castleRookLeft();
            }
            if (this.getOccupyingPiece().getType() == pieceTypeKING && (this.col - space.col) < -1){
                chess.board[7][row].getOccupyingPiece().castleRookRight();
            }
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
