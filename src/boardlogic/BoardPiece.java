package boardlogic;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract class BoardPiece{
    private BoardSpace currentSpace;
    protected PieceType type;
    protected Team team;
    protected ImageIcon image;
    protected ArrayList<Point> moves;

    public BoardPiece(BoardSpace currentSpace, Team team) {
        this.setCurrentSpace(currentSpace);
        this.team = team;
        this.moves = new ArrayList<>();
    }

    public void setCurrentSpace(BoardSpace currentSpace) {
        this.currentSpace = currentSpace;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }

    public ArrayList<Point> getMovesList(){return moves; }

    /**
     * Builds an array of the potential moves available to the calling piece based on its current position.
     * @param board a board object to check whether potential spaces are occupied.
     * @return returns false if no potential moves exist for the selected piece.
     */
    public abstract boolean getPotentialMoves(Board board);
    public boolean getPotentialMoves(Board board, Player player){return false;}

    /**
     * Checks whether a potential move is valid by cross-referencing with the moves ArrayList.
     * @param requestedMove an (x,y) coordinate representing the requested space.
     * @return a boolean indicating whether the requested move is valid.
     */
    public boolean isValidMove(Point requestedMove){
        return this.moves.contains(requestedMove);
    }

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
    protected boolean Occupied(BoardSpace space){ 
        if (space.getOccupyingPiece() != null){ 
            return true;
        }
        else return false;
    }
    
}
