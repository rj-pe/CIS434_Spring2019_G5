package boardlogic;

import java.awt.*;

public class BoardSpace {
    private BoardPiece occupyingPiece;
    private int column;
    private int rows;
    private Point position; //X/Y position referring to ROW/COL on GUI
    private boolean active = false; //Will reflect in GUI if BoardSpace is currently selected/valid move of occupyingPiece

    public BoardSpace(BoardPiece startingPiece, int row, int col) {
        occupyingPiece = startingPiece;
        column = col;
        rows = row;
        position = new Point(row, col);
    }

    //Returns 1 on successful transfer else -1
    public int transferPiece(BoardSpace space, Board chess) {
        if (space != this) {
            if (this.getOccupyingPiece().getType() == PieceType.ROOK || this.getOccupyingPiece().getType() == PieceType.KING)
                this.getOccupyingPiece().setHasMoved();
            if (this.getOccupyingPiece().getType() == PieceType.KING && (this.column - space.column) > 1){
                chess.board[0][rows].transferPiece(chess.board[3][rows], chess);
            }
            if (this.getOccupyingPiece().getType() == PieceType.KING && (this.column - space.column) < -1){
                chess.board[0][rows].transferPiece(chess.board[5][rows], chess);
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

    public int getCol(){return column;
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
