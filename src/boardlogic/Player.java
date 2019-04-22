package boardlogic;

import chess.pieces.King;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static boardlogic.PieceType.KING;


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
    public Graveyard graveyard;

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
    void buildTeamList(Board board){
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
     * Adds the provided BoardSpace objects to a list spaces at which the player is threatened.
     * threatenedSpaces is a set and will not contain duplicates.
     * @param threatenedSpaces The space to add to the list.
     */
    public void addToThreatenedSpaces(ArrayList<Point> threatenedSpaces){
        this.threatenedSpaces.addAll(threatenedSpaces);
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
        BoardPiece king = null;
        for (BoardPiece piece : this.teamMembers) {
            if( piece.type == KING){
                king = piece;
            }
        }
        return king;
    }

    /**
     * Adds a piece to the player's graveyard.
     * @param piece
     */
    public void capture(BoardPiece piece){
        graveyard.addPiece(piece);
        this.teamMembers.remove(piece);

    }

    /**
     * Adds a piece to the player's graveyard.
     * Is used for special moves En Passant and can be used for Castling
     * The captured piece is removed from the board
     * @param piece
     */
    public void captureEnPassant(BoardPiece piece){
        graveyard.addPiece(piece);
        piece.getCurrentSpace().setOccupyingPiece(null);
        this.teamMembers.remove(piece);
    }

    @Override
    public String toString(){
        return team.toString();
    }
    abstract public BoardSpace pickPiece();
    abstract public BoardSpace generateMove(BoardPiece piece);
}
