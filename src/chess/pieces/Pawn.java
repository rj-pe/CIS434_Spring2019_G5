/*package chess.pieces;

import boardlogic.*;

import java.awt.*;
import java.util.ArrayList;

import static boardlogic.Team.*;

public class Pawn extends BoardPiece {

    public Pawn(BoardSpace currentSpace, Team team) {
        super(currentSpace, team);
        type = PieceType.PAWN;
        //image = PawnImage;
    }

    //generate potential moves
    @Override
    public boolean getPotentialMoves(Board board) {return false;}
    public boolean getPotentialMoves(Board chess, Player player) {
        moves.clear();

        int x_pos = this.getCurrentSpace().getPosition().x;
        int y_pos = this.getCurrentSpace().getPosition().y;

        /*  no backward moves allowed
        *   loop through spaces in front of the piece
        */
/*
            if (team == WHITE) {
                for (int i = y_pos; i < 8; i++) {
                    if (!Occupied(chess.board[i][x_pos]) )
                        moves.add(new Point(x_pos, i)); 
                }
              } 
            //for BlP moves only 
            else {
                for (int i = y_pos; i >= 0; i--) {
                    if (!Occupied(chess.board[i][x_pos]) ) 
                        moves.add(new Point(x_pos, i));
                }
            }
        
        /*  moves forward-diagonally to capture pieces  */
        // capturing for Wh pieces (Q3 and Q4)
 /*       if(team == WHITE) {
            //  capturing in Quadrant 3
            for(int i = x_pos, j = y_pos; i >= 0 && j < 8; i--, j++) {
                if (checkForEnemy(chess.board[j][i]) && !checkForFriend(chess.board[j][i])){ //captures enemy piece and checks for friend piece diagonally before capturing
                    player.capture(chess.board[j][i].getOccupyingPiece());
                    moves.add(new Point(i, j));
                }
            }
            // capturing in Quadrant 4
            for(int i = x_pos, j = y_pos; i < 8 && j < 8; i++, j++) { 
                if (checkForEnemy(chess.board[j][i]) && !checkForFriend(chess.board[j][i])) {
                    player.capture(chess.board[j][i].getOccupyingPiece());
                    moves.add(new Point(i, j));
                }
            }
        }
        // capturing for Bl pieces (Q1 and Q2)
        else {
            //capturing in Quadrant 2
            for (int i = x_pos, j = y_pos; i >= 0 && j >= 0; i--, j--) {
                if (checkForEnemy(chess.board[j][i]) && !checkForFriend(chess.board[j][i])) {
                    player.capture(chess.board[j][i].getOccupyingPiece());
                    moves.add(new Point(i, j));
                }
            }
            //capturing in Quadrant 1
            for(int i = x_pos, j = y_pos; i < 8 && j >=0; i++, j--) {
                if (checkForEnemy(chess.board[j][i]) && !checkForFriend(chess.board[j][i])) {
                    player.capture(chess.board[j][i].getOccupyingPiece());
                    moves.add(new Point(i, j));
                }
            }
        }
      
        // return false if piece has no move options
        return !moves.isEmpty();
    }
    
    //TODO Promotion to Queen or Knight or Rook or Bishop

    
    @Override
    public String toString() {
        return (team == WHITE) ? "WhP" : "BlP";
    }
}
*/
package chess.pieces;

import boardlogic.*;

import java.awt.*;
import java.util.ArrayList;

import static boardlogic.Team.*;

public class Pawn extends BoardPiece {

    public boolean enPassant = false;   //checks if en passant is possible

    public Pawn(BoardSpace currentSpace, Team team) {
        super(currentSpace, team);
        type = PieceType.PAWN;
        //keeps record of how many moves have been made from pawn's initial position
        //image = PawnImage;
    }

    //generate potential moves
    @Override
    public boolean getPotentialMoves(Board board) {return false;}
    public boolean getPotentialMoves(Board chess, Player player) {
        moves.clear();

        //int count = 0; //keeps record of how many moves have been made from pawn's initial position
        int FirstMove = 0;  //checks if it's pawn's first move

        int x_pos = this.getCurrentSpace().getPosition().x;
        int y_pos = this.getCurrentSpace().getPosition().y;
        int x_p = x_pos + 1;    //right
        int y_p = y_pos + 1;    //down
        int x_m = x_pos - 1;    //left
        int y_m = y_pos - 1;    //up
        int yw = y_pos + 2;  //WhP goes down moving up 2 spaces
        int yb = y_pos - 2;  //BlP goes up moving up 2 spaces

        /*  no backward moves allowed
         *   loop through spaces in front of the piece
         */
        if (team == WHITE) {
            if(y_pos == 1){ //can move upto 2 spaces forward if it's piece's first move
                if (!Occupied(chess.board[yw][x_pos])) {
                    moves.add(new Point(x_pos, yw));
                    this.hasMoved++;
                }
            }
            if (y_p < 8 && !Occupied(chess.board[y_p][x_pos])){
                moves.add(new Point(x_pos, y_p));
                this.hasMoved++;
            }
        }
        else {     //for BlP moves only
            if(y_pos == 6) { //can move upto 2 spaces forward if it's piece's first move
                if (!Occupied(chess.board[yb][x_pos])) {
                    moves.add(new Point(x_pos, yb));
                    this.hasMoved++;
                }
            }
            if (y_m > 0 && !Occupied(chess.board[y_m][x_pos])){
                moves.add(new Point(x_pos, y_m));
                this.hasMoved++;
            }
        }

        /*  moves forward-diagonally to capture pieces  */
        // capture function done by Wh pieces (captures pieces in Q3 and Q4)
        if(team == WHITE) {
            // TODO add after normal capture stuff
            if (y_pos == 3){    // && !Occupied(chess.board[yb][x_pos])) {
                //moves.add(new Point(x_pos, yw)); //keep or delete
                if (!checkForEnemy(chess.board[y_m][3])) {//! //first move is 2 spaces up + check L
                    player.capture(chess.board[y_m][3].getOccupyingPiece());
                    moves.add(new Point(x_m, y_p));
                    this.hasMoved++;
                }
                if (!checkForEnemy(chess.board[y_p][3])){ //first move is 2 spaces up + check R
                    player.capture(chess.board[y_p][3].getOccupyingPiece());
                    moves.add(new Point(x_p, y_p));
                    this.hasMoved++;
                }
            }

            else if (y_p < 8 && x_m > 0 && checkForEnemy(chess.board[y_p][x_m]) && chess.board[y_p][x_m].getOccupyingPiece() != null){
                player.capture(chess.board[y_p][x_m].getOccupyingPiece());
                moves.add(new Point(x_m, y_p));
                this.hasMoved++;
            }
            else if (x_p < 8 && y_p < 8 && checkForEnemy(chess.board[y_p][x_p]) && chess.board[y_p][x_p].getOccupyingPiece() != null){
                player.capture(chess.board[y_p][x_p].getOccupyingPiece());
                moves.add(new Point(x_p,y_p));
                this.hasMoved++;
            }
        }
        // capture function done by Bl pieces (captures pieces in Q1 and Q2)
        else {
            //en passant before or after capture
            if (y_pos == 4){    // && !Occupied(chess.board[yb][x_pos])) { //4 or yb
                //moves.add(new Point(x_pos, yb)); //keep or delete
                if (!checkForEnemy(chess.board[4][x_m])) {//! //first move is 2 spaces up + check L
                    if (chess.board[4][x_m].getOccupyingPiece().hasMoved == 1) {
                        player.capture(chess.board[y_m][4].getOccupyingPiece());
                        moves.add(new Point(x_p, y_m));
                        this.hasMoved++;
                    }
                }
                if (!checkForEnemy(chess.board[y_p][4])){ //first move is 2 spaces up + check R
                    if (chess.board[4][x_p].getOccupyingPiece().hasMoved == 1) {
                        player.capture(chess.board[y_p][4].getOccupyingPiece());
                        moves.add(new Point(x_m, y_m));
                        this.hasMoved++;
                    }
                }
            }

            else if (x_p < 8 && y_m < 8 && checkForEnemy(chess.board[y_m][x_p]) && !checkForFriend(chess.board[y_m][x_p])){
                player.capture(chess.board[y_m][x_p].getOccupyingPiece());
                moves.add(new Point(x_p, y_m));
                this.hasMoved++;
            }
            else if ( x_m > 0 && y_m > 0 && checkForEnemy(chess.board[y_m][x_m]) && !checkForFriend(chess.board[y_m][x_m])){
                player.capture(chess.board[y_m][x_m].getOccupyingPiece());
                moves.add(new Point(x_m, y_m));
                this.hasMoved++;
            }
        }

        // return false if piece has no move options
        return !moves.isEmpty();
    }


    //TODO Promotion to Queen (or Knight or Rook or Bishop)

    @Override
    public String toString() {
        return (team == WHITE) ? "WhP" : "BlP";
    }
}
