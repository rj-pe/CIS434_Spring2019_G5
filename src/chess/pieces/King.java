package chess.pieces;

import boardlogic.*;
import resources.chessPieceImages.SpriteContainer;

import java.awt.*;
import java.util.ArrayList;

import static boardlogic.Team.*;
/**
 * A class that describes the king.
 * @see BoardPiece
 */
public class King extends BoardPiece {
    private SpriteContainer sprites;
    /**
     * Keeps track of whether this king can castle on the right.
     */
    private boolean canCastleR = false;
    /**
     * Keeps track of whether this king can castle on the left.
     */
    private boolean canCastleL = false;
    /**
     * A list of the piece's that hold the king in check
     */
    ArrayList<BoardPiece> enemyThreats;

    /**
     * Constructor which sets the piece type, image, enemy threats, and valuation fields for this king.
     * @param currentSpace The space on which this king starts the game.
     * @param team The team to which this king is assigned.
     */
    public King(BoardSpace currentSpace, Team team) {
        super(currentSpace, team);
        type = PieceType.KING;
        sprites = new SpriteContainer();
        this.enemyThreats = new ArrayList<>();
        setImage();
        valuation = 1000000;
    }

    /**
     * The king can move one space in any direction.
     * @param chess The Board object which holds the active pieces and their relative positions.
     * @param player The player which owns this piece.
     * @return True if this king has any available moves, false if this king cannot move.
     */
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
        // Castling Logic to determine if King is able to exchange with Rook;

        if (!hasMoved) {
                if (!checkForFriend(chess.board[y][x+1]) && !player.isThreatenedSpace(chess.board[y][x+1])) {
                    if (!checkForFriend(chess.board[y][x+2]) && !player.isThreatenedSpace(chess.board[y][x+2])) {
                        canCastleR = true;
                }
            }
            if (canCastleR){
                if (chess.board[y][7].getOccupyingPiece() != null){
                    if (chess.board[y][7].getOccupyingPiece().getType()== PieceType.ROOK){
                        if (!chess.board[y][7].getOccupyingPiece().getHasMoved()) {
                            moves.add(new Point(6, y));
                        }
                    }
                }
            }
        }

        if (!hasMoved) {
            if (!checkForFriend(chess.board[y][x-1]) && !player.isThreatenedSpace(chess.board[y][x-1])) {
                if (!checkForFriend(chess.board[y][x-2]) && !player.isThreatenedSpace(chess.board[y][x-2])) {
                    if (!checkForFriend(chess.board[y][x-3]) && !player.isThreatenedSpace(chess.board[y][x-3])) {
                            canCastleL = true;
                    }
                }
            }

            if (canCastleL){
                if (chess.board[y][0].getOccupyingPiece() != null){
                    if (chess.board[y][0].getOccupyingPiece().getType() == PieceType.ROOK){
                        if (!chess.board[y][0].getOccupyingPiece().getHasMoved()) {
                            moves.add(new Point(2, y));

                        }
                    }

                }
            }
        }
        // return false if piece has no move options
        return !moves.isEmpty();
    }
    /**
     * Setter method for this king's image.
     */
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
     * @param move The move under consideration.
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
    /**
     * Provides a string which identifies this king object as a king.
     * @return A string that identifies that this king is a white or a black king.
     */
    @Override
    public String toString() {
        return (team == WHITE) ? "WhK" : "BlK";
    }
}
