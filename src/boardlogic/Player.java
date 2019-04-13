package boardlogic;

import java.awt.*;
import java.util.ArrayList;


public class Player {
    // fields
    /**
     * Each player keeps track of which spaces are accessible by the enemy.
     */
    private ArrayList<Point> threatenedSpaces;
    /**
     * Each player is assigned to a team, either WHITE or BLACK.
     * The player commands any board piece which is part of her team.
     */
    private Team team;
    /**
     * A list of the board pieces which are part of the player's team.
     * Each piece is assigned to a team when created by the board class.
     */
    private ArrayList<BoardPiece> teamMembers;

    // constructor
    /**
     * The player object can belong to either the WHITE or BLACK team.
     * The player is responsible for all pieces on her team.
     * @param team Either WHITE or BLACK
     * @param board the Board object which holds all of the pieces and spaces.
     */
    public Player(Team team, Board board){
        this.team = team;
        this.teamMembers = new ArrayList<>();
        buildTeamList(board);
    }

    // methods

    /**
     * Builds a list of the pieces which are assigned to the player's team.
     * Team assignment is done by enumeration into either WHITE or BLACK.
     * The method searches the entire board for pieces which match it's team enum,
     * any matches are added to the ArrayList teamMembers.
     * @param board An object which holds an two dimensional array of spaces.
     */
    private void buildTeamList(Board board){
        // Loop through each space on the board &
        // search for pieces on my team.
        for (BoardSpace[] boardRow : board.board){
            for( BoardSpace space: boardRow){
                if(space.getOccupyingPiece() != null && space.getOccupyingPiece().team == team){
                    // Add my pieces to my teamMembers list.
                    teamMembers.add((BoardPiece) space.getOccupyingPiece());
                }
            }
        }
    }

    /**
     * Accessor method which allows querying of which team the player represents.
     * @return either WHITE or BLACK
     */
    public Team getTeam() {
        return team;
    }

    /**
     * Adds the provided BoardSpace object to a list spaces at which the player is threatened.
     * @param threatenedSpace The space to add to the list.
     */
    public void addToThreatenedSpaces(BoardSpace threatenedSpace){
        threatenedSpaces.add(threatenedSpace.getPosition());
    }

    /**
     * Checks whether the provided space is in the player's list of threatened spaces.
     * @param candidate A space which could potentially be on the list of threatened spaces.
     * @return True if the candidate is on the player's list of threatened spaces.
     */
    public boolean isThreatenedSpace(BoardSpace candidate){
        return threatenedSpaces.contains(candidate.getPosition());
    }

    @Override
    public String toString(){
        return team.toString();
    }
}
