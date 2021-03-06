package boardlogic;

import java.util.ArrayList;

/**
 * Keeps track of a player's captured pieces.
 */
public class Graveyard {
    //storage for Pieces
    /**
     * The team to which this graveyard belongs.
     */
    protected Team team;
    /**
     * A list of a player's captured pieces.
     */
    private ArrayList<BoardPiece> captured;

    // Constructor

    /**
     * Creates a graveyard object to store the graveyard data in the captured array.
     *
     * @param t The team to which the graveyard belongs.
     */
    Graveyard(Team t) {
        this.team = t;
        this.captured = new ArrayList<>();
    }

    /**
     * Adds a piece to the captured array.
     * @param piece The piece to be captured.
     */
    void addPiece(BoardPiece piece){
        captured.add(piece);
    }

    /**
     * Returns the captured ArrayList for the Graveyard Object.
     * @return captured
     */
    public ArrayList<BoardPiece> getGraveyard(){
        return captured;
    }
    //TODO Display captured pieces.

    @Override
    public String toString(){
        return captured.toString();
    }
}