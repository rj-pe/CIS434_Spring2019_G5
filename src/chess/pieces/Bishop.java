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
    }

    @Override
    public boolean getPotentialMoves(Board board) {return false;}

    public boolean getPotentialMoves(Board chess, Player player) {
        moves.clear();

        int x_pos = this.getCurrentSpace().getPosition().x;
        int y_pos = this.getCurrentSpace().getPosition().y;

        // For moves in Quadrant 3.
        for(int i = x_pos, j = y_pos; i >= 0 && j >=0; i--, j--){

            if (!checkForFriend(chess.board[j][i])){
                if (checkForEnemy(chess.board[j][i])){
                    player.capture(chess.board[j][i].getOccupyingPiece());
                }
                moves.add(new Point(i, j));
            }
        }
        // For moves in Quadrant 2.
        for(int i = x_pos, j = y_pos; i < 8 && j >=0; i++, j--){

            if (!checkForFriend(chess.board[j][i])){
                if (checkForEnemy(chess.board[j][i])){
                    player.capture(chess.board[j][i].getOccupyingPiece());
                }
                moves.add(new Point(i, j));
            }
        }
        // For Moves in Quadrant 4.
        for(int i = x_pos, j = y_pos; i >= 0 && j < 8; i--, j++){

            if (!checkForFriend(chess.board[j][i])){
                if (checkForEnemy(chess.board[j][i])){
                    player.capture(chess.board[j][i].getOccupyingPiece());
                }
                moves.add(new Point(i, j));
            }
        }
        // For moves in Quadrant 1.
        for(int i = x_pos, j = y_pos; i < 8 && j < 8; i++, j++){

            if (!checkForFriend(chess.board[j][i])){
                if (checkForEnemy(chess.board[j][i])){
                    player.capture(chess.board[j][i].getOccupyingPiece());
                }
                moves.add(new Point(i, j));
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
