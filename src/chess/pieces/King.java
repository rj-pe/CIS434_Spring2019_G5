package chess.pieces;

import boardlogic.*;
import resources.chessPieceImages.SpriteContainer;

import java.awt.*;
import java.util.ArrayList;

import static boardlogic.Team.*;

public class King extends BoardPiece {
    private SpriteContainer sprites;
    /**
     * A list of the piece's that hold the king in check
     */
    ArrayList<BoardPiece> enemyThreats;

    public King(BoardSpace currentSpace, Team team) {
        super(currentSpace, team);
        type = PieceType.KING;
        sprites = new SpriteContainer();
        this.enemyThreats = new ArrayList<>();
        setImage();
    }


    @Override
    public boolean getPotentialMoves(Board chess, Player player) {
        // clean up anything previously stored in the potential moves ArrayList
        moves.clear();
        // calculate the spaces where the piece can move given it's current position on the board.
        int x = this.getCurrentSpace().getPosition().x;
        int y = this.getCurrentSpace().getPosition().y;
        int x_p = x + 1;
        int y_p = y + 1;
        int x_m = x - 1;
        int y_m = y - 1;

        // if friendly piece does not occupy space and if space exists and if space is not threatened by an enemy
        // then store space as a Point object in the moves ArrayList.
        if (x_p < 8 && !checkForFriend(chess.board[y][x_p]) && !player.isThreatenedSpace(chess.board[y][x_p]) ){
            moves.add(new Point(x_p, y));
        }
        if (y_p < 8 && !checkForFriend(chess.board[y_p][x]) && !player.isThreatenedSpace(chess.board[y_p][x])){
            moves.add(new Point(x, y_p));
        }
        if (x_p < 8 && y_p < 8 && !checkForFriend(chess.board[y_p][x_p]) && !player.isThreatenedSpace(chess.board[y_p][x_p])){
            moves.add(new Point(x_p,y_p));
        }
        if (x_p < 8 && y_m > 0 && !checkForFriend(chess.board[y_m][x_p]) && !player.isThreatenedSpace(chess.board[y_m][x_p])){
            moves.add(new Point(x_p, y_m));
        }
        if (y_p < 8 && x_m > 0 && !checkForFriend(chess.board[y_p][x_m]) && !player.isThreatenedSpace(chess.board[y_p][x_m])){
            moves.add(new Point(x_m, y_p));
        }
        if ( x_m > 0 && y_m > 0 && !checkForFriend(chess.board[y_m][x_m]) && !player.isThreatenedSpace(chess.board[y_m][x_m])){
            moves.add(new Point(x_m, y_m));
        }
        if ( x_m > 0 && !checkForFriend(chess.board[y][x_m]) && !player.isThreatenedSpace(chess.board[y][x_m])){
            moves.add(new Point(x_m, y));
        }
        if ( y_m > 0 && !checkForFriend(chess.board[y_m][x]) && !player.isThreatenedSpace(chess.board[y_m][x])){
            moves.add(new Point(x, y_m));
        }
        // return false if piece has no move options
        return !moves.isEmpty();
    }

    @Override
    protected void setImage() {
        if (team == Team.BLACK) {
            super.image = sprites.getImage(0,0);
        } else {
            super.image = sprites.getImage(0,1);
        }
    }
    /**
     * Accessor for the enemyThreats list
     * @return The list of the enemyThreats.
     */
    public ArrayList<BoardPiece> getThreats(){
        return enemyThreats;
    }

    /**
     * Adds the given piece to the enemyThreats list
     * @param piece The piece to be added to the list.
     */
    public void addToThreats(BoardPiece piece){
        this.enemyThreats.add(piece);
    }

    @Override
    public String toString() {
        return (team == WHITE) ? "WhK" : "BlK";
    }
}
