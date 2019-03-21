package boardlogic;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract class BoardPiece{
    private BoardSpace currentSpace;
    protected PieceType type;
    protected Player player;
    protected ImageIcon image;
    protected ArrayList<Point> moves;

    public BoardPiece(BoardSpace currentSpace, Player player) {
        this.setCurrentSpace(currentSpace);
        this.player = player;
        this.moves = new ArrayList<>();
    }

    public void setCurrentSpace(BoardSpace currentSpace) {
        this.currentSpace = currentSpace;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    /**
     * Builds an array of the potential moves available to the calling piece based on its current position.
     * @param board a board object to check whether potential spaces are occupied.
     * @return returns false if no potential moves exist for the selected piece.
     */
    public abstract boolean getPotentialMoves(Board board);

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
        return space.getOccupyingPiece().getPlayer() == this.player;
    }
}
