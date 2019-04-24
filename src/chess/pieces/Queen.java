package chess.pieces;

import boardlogic.*;
import resources.chessPieceImages.SpriteContainer;

import java.awt.*;

import static boardlogic.Team.WHITE;

public class Queen extends BoardPiece {
    private SpriteContainer sprites;

    public Queen(BoardSpace currentSpace, Team team) {
        super(currentSpace, team);
        type = PieceType.QUEEN;
        sprites = new SpriteContainer();
        setImage();
    }

    /* Queen moves are a combination of Rook and Bishop moves */

    @Override
    public boolean getPotentialMoves(Board chess, Player player) {
        moves.clear();
        BoardSpace space;

        int x_pos = this.getCurrentSpace().getPosition().x;
        int y_pos = this.getCurrentSpace().getPosition().y;

        // added ROOK moves
        // loop through spaces to the left of the piece
        // break out of the loop if the lane is blocked by any piece.
        for(int i = x_pos-1; i >= 0; i--){
            space = chess.board[y_pos][i];
            if(addSpace(space)){
                break;
            }
        }

        // loop through spaces to the right of the piece
        // break out of the loop if the lane is blocked by any piece.
        for(int i = x_pos+1; i < 8; i++){
            space = chess.board[y_pos][i];
            if(addSpace(space)){
                break;
            }
        }

        // loop through spaces in front of the piece
        // break out of the loop if the lane is blocked by any piece.
        for (int i = y_pos+1; i < 8; i++){
            space = chess.board[i][x_pos];
            if(addSpace(space)){
                break;
            }
        }

        // loop through spaces behind piece
        // break out of the loop if the lane is blocked by any piece.
        for(int i = y_pos-1; i >= 0; i-- ){
            space = chess.board[i][x_pos];
            if(addSpace(space)){
                break;
            }
        }
        // added BISHOP moves
        // For moves in Quadrant 3.
        // Break out of the loop if the lane is blocked by any piece.
        for(int i = x_pos-1, j = y_pos-1; i >= 0 && j >=0; i--, j--){
            space = chess.board[j][i];

            if (addSpace(space)){
                break;
            }
        }
        // For moves in Quadrant 2.
        // Break out of the loop if the lane is blocked by any piece.
        for(int i = x_pos+1, j = y_pos-1; i < 8 && j >=0; i++, j--){
            space = chess.board[j][i];

            if (addSpace(space)){
                break;
            }
        }
        // For Moves in Quadrant 4.
        // Break out of the loop if the lane is blocked by any piece.
        for(int i = x_pos-1, j = y_pos+1; i >= 0 && j < 8; i--, j++){
            space  = chess.board[j][i];

            if (addSpace(space)){
                break;
            }
        }
        // For moves in Quadrant 1.
        // Break out of the loop if the lane is blocked by any piece.
        for(int i = x_pos+1, j = y_pos+1; i < 8 && j < 8; i++, j++){
            space = chess.board[j][i];

            if (addSpace(space)){
                break;
            }
        }

        // return false if piece has no move options
        return !moves.isEmpty();
    }

    public boolean isValidMove(Point requestedMove) {return true;}

    @Override
    protected void setImage() {
        if (team == Team.BLACK) {
            super.image = sprites.getImage(1,0);
        } else {
            super.image = sprites.getImage(1,1);
        }
    }

    @Override
    public String toString() {
        return (team == WHITE) ? "WhQ" : "BlQ";
    }
}
