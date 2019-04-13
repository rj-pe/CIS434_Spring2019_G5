package chess;

import boardlogic.Board;
import boardlogic.BoardPiece;
import boardlogic.BoardSpace;
import boardlogic.Player;

import java.awt.*;
import java.util.ArrayList;

import static boardlogic.Team.*;


/**
 * The Arbiter class ensures that the rules of chess are followed.
 * One of its principle responsibilities is to determine whether either king is in check.
 * In addition, the Arbiter class determines whether a player is in checkmate.
 */
public class Arbiter {
    // fields
    // TODO method to keep track of king position maybe by holding pointers to the king objects
    public Player activePlayer;
    private BoardSpace whiteKingPosition;
    private BoardPiece whiteKing;
    private boolean whiteKingInCheck, whiteKingInCheckMate;
    private BoardSpace blackKingPosition;
    private BoardPiece blackKing;
    private boolean blackKingInCheck, blackKingInCheckMate;

    // constructor
    /**
     * The default constructor for the arbiter class. An instance of the arbiter class should
     * be constructed after the board has been set up but before any player moves take place.
     * @param whtKng
     * @param blkKng
     */
    public Arbiter(BoardPiece whtKng, BoardPiece blkKng, Player player){
        whiteKingPosition = whtKng.getCurrentSpace();
        whiteKing = whtKng;
        whiteKingInCheck = false;
        whiteKingInCheckMate = false;
        blackKingPosition = blkKng.getCurrentSpace();
        blackKing = blkKng;
        blackKingInCheck = false;
        blackKingInCheckMate = false;
        activePlayer = player;
    }

    // methods
    /**
     * Checks whether the most recent move puts the enemy king in check.
     * This check is done by cross-referencing the lists of potential moves
     * of the selected piece with that of the enemy king's.
     * @param selectedPiece The active piece moved in the current turn.
     * @return True if the move puts the enemy king in check, false if not.
     */
    public boolean movePutsKingInCheck(BoardPiece selectedPiece){
        BoardPiece enemyKing;
        boolean kingInCheck = false;
        if(activePlayer.getTeam() == BLACK){
            enemyKing = whiteKing;
        } else{
            enemyKing = blackKing;
        }

        for (Point move : selectedPiece.getMoves()) {
            kingInCheck = enemyKing.getMoves().contains(move);
        }
        return kingInCheck;
        }

    /**
     * Checks whether the most recent move puts the enemy king in checkmate.
     * If the enemy king is under an inescapable threat of capture, then the game must end
     * and the current player is declared the winner.
     * @param defensePossible Indicates whether the enemy king can defend against the attack.
     * @return True if the enemy king is in checkmate, false if not.
     */
    public boolean movePutsKingInCheckMate(boolean defensePossible){
        BoardPiece king;
        if(activePlayer.getTeam() == BLACK){
            king = whiteKing;
        } else{
            king = blackKing;
        }
        return  defensePossible && king.getMoves().isEmpty();
    }

    /**
     * Calculates if any defensive moves are possible that may diminish the attack against the king.
     * The defensive possibilities are:
     * <ul>
     *     <li>Capturing an enemy that holds the king in check.</li>
     *     <li>Blocking an open threatened lane to give the king space to move.</li>
     *     <li>TODO: Other possibilities?</li>
     * <ul/>
     * @return True if some defensive move is possible, false if no options were found.
     */
    public boolean defensiveMovePossible(){
        return true;
    }

    public void setActivePlayer(Player player){
        activePlayer = player;
    }

    /**
     * Removes the specified space from the enemy's list of possible moves
     * @param space The space to remove.
     * @param enemy The enemy
     */
    public void removeSpaceFromMoves(BoardSpace space, Player enemy){
        BoardPiece king;
        // which king?
        if ( enemy.getTeam() == WHITE){
            king = whiteKing;
        } else {
            king = blackKing;
        }
        // remove the given space from the king's list of potential moves
        king.getMoves().remove(space.getPosition());
    }
}
