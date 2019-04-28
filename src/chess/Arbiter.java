package chess;

import boardlogic.Board;
import boardlogic.BoardPiece;
import boardlogic.BoardSpace;
import boardlogic.Player;
import chess.pieces.King;

import java.awt.*;

import static boardlogic.Team.*;


/**
 * The Arbiter class ensures that the rules of chess are followed.
 * One of its principle responsibilities is to determine whether either king is in check.
 * In addition, the Arbiter class determines whether a player is in checkmate.
 */
public class Arbiter {
    // fields
    // TODO method to keep track of king position maybe by holding pointers to the king objects
    /**
     * The player which is currently allowed to move one of his pieces.
     */
    private Player activePlayer;
    /**
     * The position of the white king
     */
    private BoardSpace whiteKingPosition;
    /**
     * The white team's king.
     */
    private King whiteKing;
    /**
     * Booleans which track whether the white king is in check or checkmate.
     */
    private boolean whiteKingInCheck, whiteKingInCheckMate;
    /**
     * The position of the black king.
     */
    private BoardSpace blackKingPosition;
    /**
     * The black team's king.
     */
    private King blackKing;
    /**
     * Booleans which track whether the black king is in check or checkmate.
     */
    private boolean blackKingInCheck, blackKingInCheckMate;
    /**
     * The board which holds the active pieces and their relative positions.
     */
    private Board board;

    // constructor
    /**
     * The default constructor for the arbiter class. An instance of the arbiter class should
     * be constructed after the board has been set up but before any player moves take place.
     * @param whtKng The white king piece.
     * @param blkKng The black king piece.
     * @param player The currently active player when the arbiter object is created.
     * @param board The board object which holds the active pieces and their relative positions.
     */
    public Arbiter(BoardPiece whtKng, BoardPiece blkKng, Player player, Board board){
        whiteKingPosition = whtKng.getCurrentSpace();
        whiteKing = (King) whtKng;
        whiteKingInCheck = false;
        whiteKingInCheckMate = false;
        blackKingPosition = blkKng.getCurrentSpace();
        blackKing = (King) blkKng;
        blackKingInCheck = false;
        blackKingInCheckMate = false;
        activePlayer = player;
        this.board = board;
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
        King enemyKing;
        boolean kingInCheck;
        if(activePlayer.getTeam() == BLACK){
            enemyKing = whiteKing;
        } else{
            enemyKing = blackKing;
        }
        selectedPiece.getPotentialMoves(board, activePlayer);

        for (Point move : selectedPiece.getMovesList()) {
            kingInCheck = enemyKing.getCurrentSpace().getPosition().equals(move) ;
            if(kingInCheck){
                enemyKing.addToThreats(selectedPiece);
                return true;
            }
        }
        return false;
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

        return  defensePossible && king.getMovesList().isEmpty();
    }

    /**
     * Calculates if any defensive moves are possible that may diminish the attack against the king.
     * The defensive possibilities are:
     * <ul>
     *     <li>Capturing an enemy that holds the king in check.</li>
     *     <li>Blocking an open threatened lane to give the king space to move.</li>
     * </ul>
     * @param king The king which this method checks.
     * @param player The player which owns the king which this method checks.
     * @return True if some defensive move is possible, false if no options were found.
     */
    public boolean defensiveMovePossible(King king, Player player){
        // capturing an enemy that holds the king in check
        for(BoardPiece threat: king.getThreats() ){
            BoardSpace target = threat.getCurrentSpace();
            for(BoardPiece teammate: player.getTeamMembers()){
                teammate.getPotentialMoves(board, player);
                if(teammate.getMovesList().contains(target.getPosition())){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * A setter method for which player is currently actively making a move.
     * @param player the player which is currently allowed to move.
     */
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
        king.getMovesList().remove(space.getPosition());
    }
}
