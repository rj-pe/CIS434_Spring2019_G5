package chess.pieces;

import boardlogic.*;
import resources.chessPieceImages.SpriteContainer;

import java.io.Serializable;

import java.io.Serializable;

import static boardlogic.Team.WHITE;
/**
 * A class that describes the rook.
 * @see BoardPiece
 */
public class Rook extends BoardPiece implements Serializable {
    private SpriteContainer sprites;
    /**
     * Constructor which sets the piecetype, image, and valuation fields for the rook.
     * @param currentSpace The space on which this rook starts the game.
     * @param team The team to which this rook is assigned.
     */
    public Rook(BoardSpace currentSpace, Team team) {
        super(currentSpace, team);
        type = PieceType.ROOK;
        sprites = new SpriteContainer();
        setImage();
        valuation = 5;
    }

    // TODO write the castling method

    /**
     * The rook can move in a straight line in any direction for as many unoccupied spaces as the player wishes.
     * @param chess The Board object which holds the active pieces and their relative positions.
     * @param player The player which owns this piece.
     * @return True if this rook has any available moves, false if this rook cannot move.
     */
    @Override
    public boolean getPotentialMoves(Board chess, Player player) {
        moves.clear();
        BoardSpace space;

        int x_pos = this.getCurrentSpace().getPosition().x;
        int y_pos =  this.getCurrentSpace().getPosition().y;

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
        return !moves.isEmpty();
    }

    /**
     * Setter method for this rook's image.
     */
    @Override
    protected void setImage() {
        if (team == Team.BLACK) {
            super.image = sprites.getImage(2,0);
        } else {
            super.image = sprites.getImage(2,1);
        }
    }
    /**
     * Provides a string which identifies this rook object as a rook.
     * @return A string that identifies that this rook is a white or a black rook.
     */
    @Override
    public String toString() {
        return (team == WHITE) ? "WhR" : "BlR";
    }
}
