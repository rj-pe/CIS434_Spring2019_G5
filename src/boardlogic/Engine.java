package boardlogic;

import chess.Arbiter;
import chess.pieces.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

/*
 https://web.archive.org/web/20041103012847/http://myweb.cableone.net/christienolan/coach/evaluating.htm
 */
public class Engine {
    // fields
    private Board board;
    private Player computer;
    private Player enemy;
    private Arbiter arbiter;
    private boolean opponentOccupant = false;


    // constructor
    /**
     * The Engine object assigns value to a move under consideration.
     * Evaluates enemy material possibilities, threatened spaces which move reveals,
     * if the move causes check or checkmate, the exposure of the piece.
     * @param board The board object.
     * @param enemy The opponent's player object.
     */
    public Engine(Board board, Player enemy, Player computer, Arbiter arbiter){
        this.board = board;
        this.enemy = enemy;
        this.computer = computer;
        this.arbiter = arbiter;
    }

    /**
     * Calculates the optimal move based on the engine's methods.
     * Checks every move for every piece on the player's team.
     * @return The Move object with the highest move score.
     */
    public Move pickMove(){
        ArrayList<Move> moves = new ArrayList<>();
        for(BoardPiece piece : computer.getTeamMembers() ){
            piece.getPotentialMoves(board, computer);
            ArrayList<Point> currentPieceMoves = new ArrayList<>(piece.getMovesList());
            for(int i = 0; i < currentPieceMoves.size(); i++){
                BoardPiece opponent = piece;
                Point coordinate = currentPieceMoves.get(i);
                Move potentialMove = new Move(piece, board.board[coordinate.y][coordinate.x]);
                moves.add(potentialMove);
                // carry out the move.
                if(opponentOccupant = potentialMove.getPiece().checkForEnemy(potentialMove.getDestination())){
                    opponent = potentialMove.getDestination().getOccupyingPiece();
                }
                potentialMove.getPiece().getCurrentSpace().transferPiece(potentialMove.getDestination());
                potentialMove.addToScore(material(potentialMove, opponent));
                potentialMove.addToScore(kingSafety(potentialMove));
                potentialMove.addToScore(mobility());
                potentialMove.addToScore(checkmate());
                potentialMove.addToScore(check(potentialMove));
                potentialMove.addToScore(pieceExposure(potentialMove));

                // move the piece back to its original position.
                potentialMove.getDestination().transferPiece(board.board[potentialMove.getStartingPosition().getPosition().y][potentialMove.getStartingPosition().getPosition().x]);
                if(opponentOccupant){
                    potentialMove.getDestination().setOccupyingPiece(opponent);
                }
            }
        }
        Collections.shuffle(moves);
        return Collections.max(moves, new CompareScore());

    }

    /**
     * Determines net material worth after move.
     * @param move Move object under consideration.
     * @return net material worth.
     */
    private int material(Move move, BoardPiece opponent){
        int net = 0;
        // check whether an enemy occupies space
        if(opponentOccupant){
            net += opponent.getValuation();
        }
        return 100*net;
    }
    private int kingSafety(Move move){
        int kingSafety = 0;
        King king = (King) computer.getKing();
        King enemyKing = (King) enemy.getKing();
        kingSafety += king.kingSafety(board, move);
        kingSafety -= enemyKing.kingSafety(board, move);
        return 8*kingSafety;
    }


    /**
     * Measures the mobility of pieces (not pawns) using the pieces development.
     */
    private int mobility(){
        int mobility = 0;
        // add to mobility score for each computer piece that is developed.
        for(BoardPiece piece : computer.getTeamMembers()){
            // if piece is not a pawn, check if it's moved past the first row.
            if(piece.type != PieceType.PAWN && piece.getCurrentSpace().getPosition().y > 0 ){
                mobility++;
            }
        }
        // subtract from mobility score for each enemy piece that is developed.
        for(BoardPiece piece : enemy.getTeamMembers()){
            if(piece.type != PieceType.PAWN && piece.getCurrentSpace().getPosition().y < 7){
                mobility--;
            }
        }
        return 2*mobility;
    }

    /**
     * Determines whether move causes checkmate.
     * @return numerical score based on whether move will cause checkmate.
     */
    private int checkmate(){
       if(arbiter.movePutsKingInCheckMate( arbiter.defensiveMovePossible((King) enemy.getKing(), enemy))){
           return -1000000;
       }
       return 0;
    }
    /**
     * Determines whether move causes check.
     * @param move Move object under consideration.
     * @return numerical score based on whether move will cause check.
     */
    private int check(Move move){
        if(arbiter.movePutsKingInCheck(move.getPiece())){
            return -80;
        }
        return 0;
    }

    /**
     * Assigns an exposure score to the move.
     * @param move Move object under consideration.
     * @return A value which represents the relative level of exposure which move engenders to the piece.
     */
    private int pieceExposure(Move move){
        return 0;
    }


}
