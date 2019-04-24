package chess.pieces;

import boardlogic.*;
import resources.chessPieceImages.SpriteContainer;

import java.awt.*;

import static boardlogic.Team.*;

public class Pawn extends BoardPiece {
    private SpriteContainer sprites;

    private boolean enPassant = false;   //checks if en passant is possible
    private int hasMoved;

    public Pawn(BoardSpace currentSpace, Team team) {
        super(currentSpace, team);
        type = PieceType.PAWN;
        sprites = new SpriteContainer();
        setImage();
    }

    //generate potential moves
    @Override
    public boolean getPotentialMoves(Board chess, Player player) {
        moves.clear();

        int x_pos = this.getCurrentSpace().getPosition().x;
        int y_pos = this.getCurrentSpace().getPosition().y;
        int x_p = x_pos + 1;    //right
        int y_p = y_pos + 1;    //down
        int x_m = x_pos - 1;    //left
        int y_m = y_pos - 1;    //up
        int yw = y_pos + 2;  //WhP goes down moving up 2 spaces
        int yb = y_pos - 2;  //BlP goes up moving up 2 spaces

        /*  no backward moves allowed
         *   loop through spaces in front of the piece
         */
        if (team == BLACK) {
            if(y_pos == 1){ //can move upto 2 spaces forward if it's piece's first move
                if ( !Occupied(chess.board[yw][x_pos]) ){
                    moves.add(new Point(x_pos, yw));
                    enPassant = true;
                    this.hasMoved++;
                }
            }
            if (y_p < 8 && !Occupied(chess.board[y_p][x_pos])) {
                moves.add(new Point(x_pos, y_p));
                this.hasMoved++;
            }
        }
        else {     //for WhP moves only
            if(y_pos == 6) { //can move upto 2 spaces forward if it's piece's first move
                if (!Occupied(chess.board[yb][x_pos])) {
                    moves.add(new Point(x_pos, yb));
                    enPassant = true;
                    this.hasMoved++;
                }
            }
            if (y_m > 0 && !Occupied(chess.board[y_m][x_pos])) {
                moves.add(new Point(x_pos, y_m));
                this.hasMoved++;
            }
        }
        /*  moves forward-diagonally to capture pieces  */
        // capture function done by Wh pieces (captures pieces in Q4 and Q3)
        if(team == Team.BLACK) {
            if (y_p < 8 && x_m >= 0 && y_pos == 4 && enPassant) {//en passant
                if (checkForEnemy(chess.board[4][x_m])) { //WhPawn captures BlPawn en passant (Q3)
                    if (chess.board[y_p][x_m].getOccupyingPiece() == null) {//checks if space to move to is empty
                        moves.add(new Point(x_m, y_p)); //moves diagonally as if space contained opponent's piece
                        this.hasMoved++;
                    }
                }
            }
            else {
                if (x_p < 8 && y_p < 8 && y_pos == 4 && enPassant) {
                    if (checkForEnemy(chess.board[4][x_p])) { //WhPawn captures BlPawn en passant (Q4)
                        if (chess.board[y_p][x_p].getOccupyingPiece() == null) {
                            moves.add(new Point(x_p, y_p));
                            this.hasMoved++;
                        }
                    }
                }
            }

            if(x_p < 8 && y_p < 8 && checkForEnemy(chess.board[y_p][x_p])) { //WhP captures in Q4
                moves.add(new Point(x_p, y_p));
                this.hasMoved++;
            }
            if(y_p < 8 && x_m >= 0 && checkForEnemy(chess.board[y_p][x_m])) { // WhP captures in Q3
                moves.add(new Point(x_m, y_p));
                this.hasMoved++;
            }
        }

        // capture function done by Wh pieces (captures pieces in Q2 and Q1)
        else {
            if (x_m >= 0 && y_m >= 0 && y_pos == 3 && enPassant) {
                if (checkForEnemy(chess.board[3][x_m])) { //BlPawn captures WhPawn en passant (Q2)
                    if (chess.board[y_m][x_m].getOccupyingPiece() == null) {
                        moves.add(new Point(x_m, y_m));
                        this.hasMoved++;
                    }
                }
            }
            else {
                if (x_p < 8 && y_m < 8 && y_pos == 3 && enPassant) {
                    if (checkForEnemy(chess.board[3][x_p])) { //BlPawn captures WhPawn en passant (Q1)
                        if (chess.board[y_m][x_p].getOccupyingPiece() == null) {
                            moves.add(new Point(x_p, y_m));
                            this.hasMoved++;
                        }
                    }
                }
            }

            if (x_p < 8 && y_m < 8 && checkForEnemy(chess.board[y_m][x_p])) { //Capture in Q2
                moves.add(new Point(x_p, y_m));
                this.hasMoved++;
            }
            if (x_m >= 0 && y_m >= 0 && checkForEnemy(chess.board[y_m][x_m])) {//Capture in Q1
                moves.add(new Point(x_m, y_m));
                this.hasMoved++;
            }
        }

        // return false if piece has no move options
        return !moves.isEmpty();
    }

    //TODO Promotion to Queen (or Knight or Rook or Bishop)

    @Override
    protected void setImage() {
        if (team == Team.BLACK) {
            super.image = sprites.getImage(5,0);
        } else {
            super.image = sprites.getImage(5,1);
        }
    }

    @Override
    public String toString() {
        return (team == WHITE) ? "WhP" : "BlP";
    }
}
