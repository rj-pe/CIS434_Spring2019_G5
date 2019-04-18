package chess.pieces;

import boardlogic.*;
import chess.chessPieceImages.SpriteContainer;

import java.awt.*;
import java.util.ArrayList;

import static boardlogic.Team.*;

public class Pawn extends BoardPiece {
    private SpriteContainer sprites;

    public Pawn(BoardSpace currentSpace, Team team) {
        super(currentSpace, team);
        type = PieceType.PAWN;
        sprites = new SpriteContainer();
        setImage();
    }

    //generate potential moves
    @Override
    public boolean getPotentialMoves(Board board) {return false;}
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
        if (team == WHITE) {
            if(y_pos == 1){ //can move upto 2 spaces forward if it's piece's first move
                if (!Occupied(chess.board[yw][x_pos]))
                    moves.add(new Point(x_pos, yw));
            }
            if (y_p < 8 && !Occupied(chess.board[y_p][x_pos]))
                moves.add(new Point(x_pos, y_p));
        }
        else {     //for BlP moves only
            if(y_pos == 6) { //can move upto 2 spaces forward if it's piece's first move
                if (!Occupied(chess.board[yb][x_pos]))
                    moves.add(new Point(x_pos, yb));
            }
            if (y_m > 0 && !Occupied(chess.board[y_m][x_pos]))
                moves.add(new Point(x_pos, y_m));
        }

        /*  moves forward-diagonally to capture pieces  */
        // capture function done by Wh pieces (captures pieces in Q3 and Q4)
        if(team == WHITE) {
            if (y_p < 8 && x_m > 0 && checkForEnemy(chess.board[y_p][x_m]) && !checkForFriend(chess.board[y_p][x_m])){
                player.capture(chess.board[y_p][x_m].getOccupyingPiece());
                moves.add(new Point(x_m, y_p));
            }
            if (x_p < 8 && y_p < 8 && checkForEnemy(chess.board[y_p][x_p]) && !checkForFriend(chess.board[y_p][x_p])){
                player.capture(chess.board[y_p][x_p].getOccupyingPiece());
                moves.add(new Point(x_p,y_p));
            }
        }
        // capture function done by Bl pieces (captures pieces in Q1 and Q2)
        else {
            if (x_p < 8 && y_m < 8 && checkForEnemy(chess.board[y_m][x_p]) && !checkForFriend(chess.board[y_m][x_p])){
                player.capture(chess.board[y_m][x_p].getOccupyingPiece());
                moves.add(new Point(x_p, y_m));
            }
            if ( x_m > 0 && y_m > 0 && checkForEnemy(chess.board[y_m][x_m]) && !checkForFriend(chess.board[y_m][x_m])){
                player.capture(chess.board[y_m][x_m].getOccupyingPiece());
                moves.add(new Point(x_m, y_m));
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
