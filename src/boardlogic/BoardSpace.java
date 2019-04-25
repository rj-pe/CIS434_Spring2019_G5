package boardlogic;

import java.awt.*;

public class BoardSpace {
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
    public int transferPiece(BoardSpace space, Board chess) {
        if (space != this) {
            if (this.getOccupyingPiece().getHasMoved() == false) {
                if (this.getOccupyingPiece().getType() == PieceType.KING && space.equals(chess.board[2][rows])) {
                    chess.board[0][rows].transferPiece(chess.board[3][rows], chess);
                }
                if (this.getOccupyingPiece().getType() == PieceType.KING && space.equals(chess.board[5][rows])) {
                    chess.board[7][rows].transferPiece(chess.board[5][rows], chess);
                }
            }
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
