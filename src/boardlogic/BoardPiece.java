package boardlogic;

import java.awt.*;
import java.util.ArrayList;
import javafx.scene.image.Image;

/**
 * A super class that provides core functionality for chess pieces.
 * All pieces share the methods and fields defined in this class.
 * Provides fields that hold information about position, team, and available moves.
 */
public abstract class BoardPiece{
    /**
     * The BoardSpace object upon which this is situated.
     */
    private BoardSpace currentSpace;
    /**
     * The type of piece that this represents. Can be one of the following: King, Queen, Rook, Bishop, Knight, or Pawn.
     */
    protected PieceType type;
    /**
     * The team that this piece is on. Can be either WHITE or BLACK team.
     */
    protected Team team;
    /**
     * The image that this piece will use to display itself.
     */
    protected Image image;
    /**
     * A list of available moves that this piece can legally carry out.
     */
    protected ArrayList<Point> moves;
    /**
     * The value of this piece. Value is assigned according to the following scheme:
     * <ul>
     *     <li>Pawn -- 1 pt.</li>
     *     <li>Knight -- 3 pts.</li>
     *     <li>Bishop -- 3 pts.</li>
     *     <li>Rook -- 5 pts</li>
     *     <li>Queen -- 9 pts.</li>
     * </ul>
     */
    protected int valuation;
    /**
     * A boolean which is set to true when this piece moves off of its original position.
     */
    public boolean hasMoved = false;
    public boolean castlingKingL = false;
    public boolean castlingKingR = false;

    /**
     * Constructor for the BoardPiece object, sets this piece's starting position.
     * @param currentSpace The space which this piece should be placed on to start the game.
     * @param team The team to which this piece is assigned.
     */
    public BoardPiece(BoardSpace currentSpace, Team team) {
        this.setCurrentSpace(currentSpace);
        this.team = team;
        this.moves = new ArrayList<>();
    }

    /**
     * Setter for this pieces current space field. Allows programmatic access to the pieces board position.
     * @param currentSpace
     */
    void setCurrentSpace(BoardSpace currentSpace) {
        this.currentSpace = currentSpace;
    }

    /**
     * Setter method for this pieces team. Team can be either WHITE or BLACK.
     * @param team The team to which this piece will be assigned.
     */
    public void setTeam(Team team) {
        this.team = team;
    }

    /**
     * Accessor method for this pieces team field.
     * @return The team to which this piece belongs.
     */
    public Team getTeam() {
        return team;
    }

    /**
     * Accessor method for this pieces list of available moves, returns null if list is empty.
     * @return Returns an ArrayList of Points that represent this piece's currently available moves.
     */
    public ArrayList<Point> getMovesList(){return moves; }

    /**
     * Builds an array of the potential moves available to the calling piece based on its current position.
     * @param board a board object to check whether potential spaces are occupied.
     * @return returns false if no potential moves exist for the selected piece.
     */
    //public abstract boolean getPotentialMoves(Board board);

    /**
     * Calculates the potential moves that are available to this piece based on chess' rules of piece movement.
     * Each piece type has a unique movement pattern and will override this method to implement thier pattern.
     * @param board The board object that holds information about the boardpieces and spaces they occupy.
     * @param player The player which owns this piece.
     * @return Returns true if any potential moves are available, false if none are available.
     */
    public abstract boolean getPotentialMoves(Board board, Player player);

    /**
     * Checks whether a potential move is valid by cross-referencing with the moves ArrayList.
     * @param requestedMove an (x,y) coordinate representing the requested space.
     * @return a boolean indicating whether the requested move is valid.
     */
    public boolean isValidMove(Point requestedMove){
        return this.moves.contains(requestedMove);
    }

    /**
     * Accessor method for this piece's current position on the board.
     * @return The BoardSpace object upon which this piece is currently on.
     */
    public BoardSpace getCurrentSpace() {
        return currentSpace;
    }

    /**
     * Checks whether the given space is occupied by a piece on the same team.
     * @param space a space under consideration for inclusion on the list of potential moves.
     * @return returns true if space is occupied by a team-mate.
     */
    protected boolean checkForFriend(BoardSpace space){
        return (space.getOccupyingPiece() != null && space.getOccupyingPiece().getTeam() == this.team);
    }

    /**
     * Checks whether the given space is occupied by a piece on the opposing team.
     * @param space a space under consideration for inclusion on the list of potential moves.
     * @return returns true is space is occupied by an enemy.
     */
    protected boolean checkForEnemy(BoardSpace space){
        return (space.getOccupyingPiece() != null && space.getOccupyingPiece().getTeam() != this.team);
    }
    
    /**
    *  for pawns: does not capture own or opponent's piece in forward move
    *   checks whether space is occupied by any player's piece before moving forward; if true, piece cannot move forward
    *   @param space a space under consideration for inclusion on the list of potential moves.
    *   @return returns true is space is occupied by any piece.
    */
    public boolean Occupied(BoardSpace space){
        return space.getOccupyingPiece() != null;
    }

    /**
     * Adds the given space to the piece's list of potential moves if space is empty or occupied by an enemy.
     * Method is used to enforce an upper bound on the piece's open lane. If the lane is blocked by any piece,
     * this method will inform the caller.
     * @param space The space under consideration.
     * @return Returns true if the space under consideration is occupied by any piece.
     */
    protected boolean addSpace(BoardSpace space){
        boolean friend = checkForFriend(space);
        if (!friend){
            moves.add(new Point(space.getPosition().x, space.getPosition().y));
        }
        return ( friend || checkForEnemy(space) );
    }

    /**
     * Accessor method for the hasMoved boolean.
     * @return True if hasMoved is set, false if this piece has yet to move.
     */
    public boolean getHasMoved(){
        return hasMoved;
    }

    /**
     * Setter for this piece's hasMoved boolean.
     */
    public void setHasMoved(){
        hasMoved = true;
    }

    /**
     * Accessor method for this piece's PieceType.
     * @return This piece's PieceType
     * @see PieceType
     */
    public PieceType getType(){
        return type;
    }

    /**
     * Abstract method for setting this piece's image.
     * Overridden in each piece class that inherits from this super class.
     */
    protected abstract void setImage();

    /**
     * Accessor method for this piece's image.
     * @return The image that represents this piece.
     */
    public Image getImage() {
        return image;
    }

    /**
     * Accessor method for the value of the piece.
     * @return The point value of the piece.
     */
    public int getValuation(){
        return valuation;
    }


}
