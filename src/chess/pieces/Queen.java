package chess.pieces;

import boardlogic.*;
import chess.chessPieceImages.SpriteContainer;

import java.awt.*;
import java.util.ArrayList;

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
    public boolean getPotentialMoves(Board board) {return false;}
    public boolean getPotentialMoves(Board chess, Player player) {
        moves.clear();

        int x_pos = this.getCurrentSpace().getPosition().x;
        int y_pos = this.getCurrentSpace().getPosition().y;

        // added ROOK moves
        // loop through spaces to the left of the piece
        for (int i = x_pos; i >= 0; i--) {
            if (!checkForFriend(chess.board[y_pos][i]))
                if (checkForEnemy(chess.board[y_pos][i])){
                    player.capture(chess.board[y_pos][i].getOccupyingPiece());
                }
                moves.add(new Point(i, y_pos));
        }
        // loop through spaces to the right of the piece
        for (int i = x_pos; i < 8; i++) {
            if (!checkForFriend(chess.board[y_pos][i]))
                if (checkForEnemy(chess.board[y_pos][i])){
                    player.capture(chess.board[y_pos][i].getOccupyingPiece());
                }
                moves.add(new Point(i, y_pos));
        }
        // loop through spaces in front of the piece
        for (int i = y_pos; i < 8; i++) {
            if (!checkForFriend(chess.board[i][x_pos]))
                if (checkForEnemy(chess.board[i][x_pos])){
                    player.capture(chess.board[i][x_pos].getOccupyingPiece());
                }
                moves.add(new Point(x_pos, i));
        }
        // loop through spaces behind piece
        for (int i = y_pos; i >= 0; i--) {
            if (!checkForFriend(chess.board[i][x_pos]))
                if (checkForEnemy(chess.board[i][x_pos])){
                    player.capture(chess.board[i][x_pos].getOccupyingPiece());
                }
                moves.add(new Point(x_pos, i));
        }
        // added BISHOP moves
        // For moves in Quadrant 3.
        for(int i = x_pos, j = y_pos; i >= 0 && j >=0; i--, j--){
            if (!checkForFriend(chess.board[j][i]))
                if (checkForEnemy(chess.board[j][i])){
                    player.capture(chess.board[j][i].getOccupyingPiece());
                }
                moves.add(new Point(i, j));
        }
        // For moves in Quadrant 2.
        for(int i = x_pos, j = y_pos; i < 8 && j >=0; i++, j--) {
            if (!checkForFriend(chess.board[j][i]))
                if (checkForEnemy(chess.board[j][i])) {
                    player.capture(chess.board[j][i].getOccupyingPiece());
                }
                moves.add(new Point(i, j));
        }
        // For Moves in Quadrant 4.
        for(int i = x_pos, j = y_pos; i >= 0 && j < 8; i--, j++){
            if (!checkForFriend(chess.board[j][i]))
                if (checkForEnemy(chess.board[j][i])){
                    player.capture(chess.board[j][i].getOccupyingPiece());
                }
                moves.add(new Point(i, j));
        }
        // For moves in Quadrant 1.
        for(int i = x_pos, j = y_pos; i < 8 && j < 8; i++, j++){
            if (!checkForFriend(chess.board[j][i]))
                if (checkForEnemy(chess.board[j][i])){
                    player.capture(chess.board[j][i].getOccupyingPiece());
                }
                moves.add(new Point(i, j));
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
