package boardlogic;

import java.util.ArrayList;
import java.util.HashSet;

public class HumanPlayer extends Player {
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
