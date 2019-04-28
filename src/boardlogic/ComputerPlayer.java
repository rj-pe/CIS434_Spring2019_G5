package boardlogic;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

/**
 * This class describes the fields and methods necessary for implementing a computer based player.
 * This class is used when the user requests to play the computer.
 * @see Player
 */
public class ComputerPlayer extends Player {
    /**
     * An instance of a Random object used to calculate a random move.
     */
    private Random random;
    /**
     * The board object which holds the pieces and their relative positions.
     */
    private Board board;

    /**
     * Constructor for the ComputerPlayer class. The ComputerPlayer is responsible for selecting and moving pieces at
     * random. It is capable of playing a game of chess with a human opponent.
     * @param team The team to which the ComputerPlayer is assigned.
     * @param board The Board object which represents the chess game.
     */
    public ComputerPlayer(Team team, Board board){
        super.team = team;
        super.teamMembers = new ArrayList<>();
        super.threatenedSpaces = new HashSet<>();
        super.buildTeamList(board);
        super.graveyard = new Graveyard(team);
        this.board = board;
        random = new Random();
    }

    /**
     * Randomly selects one of the teammates on the ComputerPlayers team.
     * @return A BoardPiece object on the ComputerPlayers team. Will return null if teamMembers list is empty.
     */
    @Override
    public BoardSpace pickPiece() {
        if(teamMembers.size() > 0){
            int index = random.nextInt(teamMembers.size());
            teamMembers.get(index).getPotentialMoves(board, this);
            while(teamMembers.get(index).getMovesList().size() == 0){
                index = random.nextInt(teamMembers.size());
                teamMembers.get(index).getPotentialMoves(board, this);
            }
            return teamMembers.get(index).getCurrentSpace();
        }
        else return null;
    }

    /**
     * Randomly picks a BoardSpace from the @param piece list of available moves.
     * @param piece The method selects one of the available moves on the piece's potentialMoves list.
     * @return A randomly chosen move from the piece's potentialMoves list.
     * If the piece's potentialMoves list is empty this method returns null.
     */
    @Override
    public BoardSpace generateMove(BoardPiece piece){
        // pick randomly from piece's list of available moves
        if(piece.getMovesList().size() > 0) {
            int index = random.nextInt(piece.getMovesList().size());
            while (piece.getMovesList().get(index) == null) {
                index = random.nextInt(piece.getMovesList().size());
            }
            return board.board[piece.getMovesList().get(index).y][piece.getMovesList().get(index).x];
        }
        else return null;
    }
}
