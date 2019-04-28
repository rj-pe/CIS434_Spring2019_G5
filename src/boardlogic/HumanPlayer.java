package boardlogic;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Represents a human player.
 * @see Player
 */
public class HumanPlayer extends Player {
    /**
     * Uses the constructor defined in the super Player class.
     * @param team The team to which this human player will play for.
     * @param board The board object which holds the active pieces and their relative positions.
     */
    public HumanPlayer(Team team, Board board){
        super.team = team;
        super.teamMembers = new ArrayList<>();
        super.threatenedSpaces = new HashSet<>();
        super.buildTeamList(board);
        super.graveyard = new Graveyard(team);
    }

    @Override
    public BoardSpace pickPiece() {return null;}
    @Override
    public BoardSpace generateMove(BoardPiece piece){return null;}

}
