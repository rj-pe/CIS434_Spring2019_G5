package chess.pieces;

import boardlogic.*;
import resources.chessPieceImages.SpriteContainer;

import java.awt.*;

import static boardlogic.Team.WHITE;
/**
 * A class that describes the queen.
 * @see BoardPiece
 */
public class Queen extends BoardPiece {
    private SpriteContainer sprites;
    /**
     * Constructor which sets the piecetype, image, and valuation fields for the queen.
     * @param currentSpace The space on which this queen starts the game.
     * @param team The team to which this queen is assigned.
     */
    public Queen(BoardSpace currentSpace, Team team) {
        super(currentSpace, team);
        type = PieceType.QUEEN;
        sprites = new SpriteContainer();
        setImage();
        valuation = 9;
    }

    /* Queen moves are a combination of Rook and Bishop moves */
    /**
     * The queen can move diagonally for as many unoccupied spaces as the player wishes, or
     * in a straight line trajectory for as many unoccupied spaces as the player wishes.
     * @param chess The Board object which holds the active pieces and their relative positions.
     * @param player The player which owns this piece.
     * @return True if this queen has any available moves, false if this queen cannot move.
     */
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
    /**
     * Setter method for this queen's image.
     */
    @Override
    protected void setImage() {
        if (team == Team.BLACK) {
            super.image = sprites.getImage(1,0);
        } else {
            super.image = sprites.getImage(1,1);
        }
    }
    /**
     * Provides a string which identifies this queen object as a queen.
     * @return A string that identifies that this queen is a white or a black queen.
     */
    @Override
    public String toString() {
        return (team == WHITE) ? "WhQ" : "BlQ";
    }
}
