package boardlogic;

import java.awt.*;

public class Move {
    /**
     * The piece under consideration.
     */
    private BoardPiece piece;
    /**
     * The move under consideration.
     */
    private BoardSpace space;
    /**
     *
     */
    private BoardSpace startingPoint;
    private BoardSpace start;
    /**
     * Numerical score assigned to the possible move.
     */
    private int score;

    /**
     * Represents a potential move under consideration.
     * @param piece The piece under consideration.
     * @param space The space under consideration.
     */
    Move(BoardPiece piece, BoardSpace space){
        this.piece = piece;
        this.space = space;
        this.score = 0;
        this.startingPoint = new BoardSpace(null, piece.getCurrentSpace().getPosition().x, piece.getCurrentSpace().getPosition().y);
        this.start = piece.getCurrentSpace();
    }

    /**
     * Accessor methods for score field.
     * @return The move's score.
     */
    int getScore() {
        return score;
    }

    /**
     * Accessor method for piece field.
     * @return The piece which makes the move.
     */
    public BoardPiece getPiece() {
        return piece;
    }

    /**
     * Accessor method for the space field.
     * @return The space to which the piece moves.
     */
    public BoardSpace getDestination() {
        return space;
    }

    /**
     * Accessor method for the piece's starting BoardSpace.
     * @return The piece's starting BoardSpace.
     */
    public BoardSpace getStartingPosition(){
        return this.startingPoint;
    }
    public BoardSpace getStart(){
        return this.start;
    }

    /**
     * Adds a given integer value to the move's score field.
     * @param score Value to add to the move's score field.
     */
    void addToScore(int score){
        this.score += score;
    }
}
