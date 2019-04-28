package chess.pieces;

import boardlogic.*;
import resources.chessPieceImages.SpriteContainer;

import static boardlogic.Team.WHITE;

/**
 * A class that describes the bishop.
 * @see BoardPiece
 */
public class Bishop extends BoardPiece {
    private SpriteContainer sprites;

    /**
     * Constructor which sets the piecetype, image, and valuation fields for the bishop.
     * @param currentSpace The space on which this bishop starts the game.
     * @param team The team to which this bishop is assigned.
     */
    public Bishop(BoardSpace currentSpace, Team team) {
        super(currentSpace, team);
        type = PieceType.BISHOP;
        sprites = new SpriteContainer();
        setImage();
        valuation = 3;
    }

    /**
     * The bishop can move diagonally for as many unoccupied spaces as the player wishes.
     * @param chess The Board object which holds the active pieces and their relative positions.
     * @param player The player which owns this piece.
     * @return True if this bishop has any available moves, false if this bishop cannot move.
     */
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

    /**
     * Setter method for this bishop's image.
     */
    @Override
    protected void setImage() {
        if (team == Team.BLACK) {
            super.image = sprites.getImage(4,0);
        } else {
            super.image = sprites.getImage(4,1);
        }
    }

    /**
     * Provides a string which identifies this bishop object as a bishop.
     * @return A string that identifies that this bishop is a white or a black bishop.
     */
    @Override
    public String toString() {
        return (team == WHITE) ? "WhB" : "BlB";
    }
}
