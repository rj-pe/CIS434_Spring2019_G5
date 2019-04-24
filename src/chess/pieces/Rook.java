package chess.pieces;

import boardlogic.*;
import resources.chessPieceImages.SpriteContainer;

import static boardlogic.Team.WHITE;

public class Rook extends BoardPiece {
    private SpriteContainer sprites;

    public Rook(BoardSpace currentSpace, Team team) {
        super(currentSpace, team);
        type = PieceType.ROOK;
        sprites = new SpriteContainer();
        setImage();
    }

    // TODO write the castling method
    

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

    @Override
    protected void setImage() {
        if (team == Team.BLACK) {
            super.image = sprites.getImage(2,0);
        } else {
            super.image = sprites.getImage(2,1);
        }
    }

    @Override
    public String toString() {
        return (team == WHITE) ? "WhR" : "BlR";
    }
}
