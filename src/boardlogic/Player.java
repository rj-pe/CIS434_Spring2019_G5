package boardlogic;

import chess.pieces.King;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static boardlogic.PieceType.KING;

/**
 * The player class keeps track of all pieces on a single chess side.
 */
public abstract class Player {
    // fields
    /**
     * Each player keeps track of which spaces are accessible by the enemy.
     */
    Set<Point> threatenedSpaces;
    /**
     * Each player is assigned to a team, either WHITE or BLACK.
     * The player commands any board piece which is part of her team.
     */
    Team team;
    /**
     * A list of the board pieces which are part of the player's team.
     * Each piece is assigned to a team when created by the board class.
     */
    ArrayList<BoardPiece> teamMembers;
    /**
     * An object that stores the player's captured pieces.
     */
    public Graveyard graveyard;
    /**
     * An integer value that represents the total value of the player's active pieces.
     */
    int material;

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
        this.threatenedSpaces = new HashSet<>();
        buildTeamList(board);
        this.graveyard = new Graveyard(team);
        this.material = 6+3*2+3*2+5*2+9;
    }
    // default constructor
    public Player(){}

    // methods

    /**
     * Builds a list of the pieces which are assigned to the player's team.
     * Team assignment is done by enumeration into either WHITE or BLACK.
     * The method searches the entire board for pieces which match it's team enum,
     * any matches are added to the ArrayList teamMembers.
     * @param board An object which holds an two dimensional array of spaces.
     */
    public void buildTeamList(Board board){
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
     * Accessor method which provides access to list of teammates.
     * @return List of activeBoardPiece's on the player's team.
     */
    public ArrayList<BoardPiece> getTeamMembers(){
        return teamMembers;
    }

    /**
     * Adds the provided BoardSpace objects to a list spaces at which the player is threatened.
     * threatenedSpaces is a set and will not contain duplicates.
     * @param threatenedSpaces The space to add to the list.
     */
    public void addToThreatenedSpaces(ArrayList<Point> threatenedSpaces){
        this.threatenedSpaces.addAll(threatenedSpaces);
    }

    public void clearThreatenedSpaces() {
        this.threatenedSpaces = new HashSet<>();
    }

    /**
     * Checks whether the provided space is in the player's list of threatened spaces.
     * @param candidate A space which could potentially be on the list of threatened spaces.
     * @return True if the candidate is on the player's list of threatened spaces.
     */
    public boolean isThreatenedSpace(BoardSpace candidate){
        return threatenedSpaces.contains(candidate.getPosition());
    }

    /**
     * Goes through the teamMembers list and finds the king piece.
     * @return The player's king.
     */
    public BoardPiece getKing(){
        King king = null;
        for (BoardPiece piece : this.teamMembers) {
            if( piece.type == KING){
                king = (King) piece;
            }
        }
        return king;
    }

    /**
     * Adds a piece to the player's graveyard.
     * @param piece The piece which will be added to this player's graveyard.
     */
    public void capture(BoardPiece piece){
        graveyard.addPiece(piece);
        this.teamMembers.remove(piece);
        this.adjustMaterial(piece.getValuation());
    }

    /**
     * Adds a piece to the player's graveyard.
     * Is used for special moves En Passant and can be used for Castling
     * The captured piece is removed from the board
     * @param piece The piece which will be added to this player's graveyard.
     */
    public void captureEnPassant(BoardPiece piece){
        graveyard.addPiece(piece);
        piece.getCurrentSpace().setOccupyingPiece(null);
        this.teamMembers.remove(piece);
    }

    /**
     * Adjusts the material value that the player possesses.
     * @param value An integer value that should be subtracted from the current material value.
     */
    private void adjustMaterial(int value){
        material -= value;
    }

    /**
     * Accessor method for the player's material field.
     * @return An integer value that represents the combined value of the player's pieces.
     */
    public int getMaterial(){
        return material;
    }

    @Override
    public String toString(){
        return team.toString();
    }
    abstract public BoardSpace pickPiece();
    abstract public BoardSpace generateMove(BoardPiece piece);
}
