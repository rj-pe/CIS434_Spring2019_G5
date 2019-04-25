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
        valuation = 1000000;
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

    /**
     * Checks the safety of the king.
     * @param chess The board object.
     * @return Returns a negative integer representing the number of open lanes the king is exposed on.
     */
    public int kingSafety(Board chess, Move move){
        int safety = 0;
        int x_pos = this.getCurrentSpace().getPosition().x;
        int y_pos = this.getCurrentSpace().getPosition().y;
        BoardSpace space;

        // added ROOK moves
        // loop through spaces to the left of the piece
        // break out of the loop if the lane is blocked by any piece.
        for(int i = x_pos-1; i >= 0; i--){
            space = chess.board[y_pos][i];
            if(checkForFriend(space)){
                break;
            }
            else if (i == 0 || checkForEnemy(space)){
                safety--;
            }
        }

        // loop through spaces to the right of the piece
        // break out of the loop if the lane is blocked by any piece.
        for(int i = x_pos+1; i < 8; i++){
            space = chess.board[y_pos][i];
            if(checkForFriend(space)){
                break;
            }
            else if (i == 7 || checkForEnemy(space)){
                safety--;
            }
        }

        // loop through spaces in front of the piece
        // break out of the loop if the lane is blocked by any piece.
        for (int i = y_pos+1; i < 8; i++){
            space = chess.board[i][x_pos];
            if(checkForFriend(space)){
                break;
            }
            else if (i == 7 || checkForEnemy(space)){
                safety--;
            }
        }

        // loop through spaces behind piece
        // break out of the loop if the lane is blocked by any piece.
        for(int i = y_pos-1; i >= 0; i-- ){
            space = chess.board[i][x_pos];
            if(checkForFriend(space)){
                break;
            }
            else if (i == 0 || checkForEnemy(space)){
                safety--;
            }
        }
        // added BISHOP moves
        // For moves in Quadrant 3.
        // Break out of the loop if the lane is blocked by any piece.
        for(int i = x_pos-1, j = y_pos-1; i >= 0 && j >=0; i--, j--){
            space = chess.board[j][i];
            if(checkForFriend(space)){
                break;
            }
            else if (i == 0 || j == 0 || checkForEnemy(space)){
                safety--;
            }
        }
        // For moves in Quadrant 2.
        // Break out of the loop if the lane is blocked by any piece.
        for(int i = x_pos+1, j = y_pos-1; i < 8 && j >=0; i++, j--){
            space = chess.board[j][i];
            if(checkForFriend(space)){
                break;
            }
            else if (i == 7 || j == 0 || checkForEnemy(space)){
                safety--;
            }
        }
        // For Moves in Quadrant 4.
        // Break out of the loop if the lane is blocked by any piece.
        for(int i = x_pos-1, j = y_pos+1; i >= 0 && j < 8; i--, j++){
            space  = chess.board[j][i];
            if(checkForFriend(space)){
                break;
            }
            else if (i == 0 || j == 7 || checkForEnemy(space)){
                safety--;
            }
        }
        // For moves in Quadrant 1.
        // Break out of the loop if the lane is blocked by any piece.
        for(int i = x_pos+1, j = y_pos+1; i < 8 && j < 8; i++, j++){
            space = chess.board[j][i];
            if(checkForFriend(space)){
                break;
            }
            else if (i == 7 || j == 7 || checkForEnemy(space)){
                safety--;
            }
        }
        return safety;
    }

    @Override
    public String toString() {
        return (team == WHITE) ? "WhK" : "BlK";
    }
}
