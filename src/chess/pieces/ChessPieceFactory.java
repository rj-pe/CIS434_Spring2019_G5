package chess.pieces;

import boardlogic.BoardPiece;
import boardlogic.BoardSpace;
import boardlogic.Player;
import boardlogic.Team;

public class ChessPieceFactory {

    public BoardPiece getChessPiece(String pieceType, BoardSpace currentSpace, Team team) {
        if (pieceType == null) {
            return null;
        }
        if (pieceType.equalsIgnoreCase("KNIGHT")) {
            return new Knight(currentSpace, team);
        } else if (pieceType.equalsIgnoreCase("ROOK")) {
            return new Rook(currentSpace, team);
        } else if (pieceType.equalsIgnoreCase("BISHOP")) {
            return new Bishop(currentSpace, team);
        } else if (pieceType.equalsIgnoreCase("QUEEN")) {
            return new Queen(currentSpace, team);
        } else if (pieceType.equalsIgnoreCase("KING")) {
            return new King(currentSpace, team);
        } else if (pieceType.equalsIgnoreCase("PAWN")) {
            return new Pawn(currentSpace, team);
        }
        return null;
    }
}
