package chess.pieces;

import boardlogic.*;
import chess.chessPieceImages.SpriteContainer;

import java.awt.*;

import static boardlogic.Team.WHITE;

public class Bishop extends BoardPiece {
    private SpriteContainer sprites;

    public Bishop(BoardSpace currentSpace, Team team) {
        super(currentSpace, team);
        type = PieceType.BISHOP;
        sprites = new SpriteContainer();
        setImage();
        valuation = 3;
    }


    @Override
    public boolean getPotentialMoves(Board chess, Player player) {
        moves.clear();
        BoardSpace space;

        int x_pos = this.getCurrentSpace().getPosition().x;
        int y_pos = this.getCurrentSpace().getPosition().y;

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
        return !moves.isEmpty();
    }

    @Override
    protected void setImage() {
        if (team == Team.BLACK) {
            super.image = sprites.getImage(4,0);
        } else {
            super.image = sprites.getImage(4,1);
        }
    }

    @Override
    public String toString() {
        return (team == WHITE) ? "WhB" : "BlB";
    }
}
