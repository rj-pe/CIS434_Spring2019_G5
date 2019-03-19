package chess.pieces;

import boardlogic.BoardPiece;
import boardlogic.BoardSpace;
import boardlogic.Player;

public class ChessPieceFactory {

    public BoardPiece getChessPiece(String pieceType, BoardSpace currentSpace, Player player) {
        if (pieceType == null) {
            return null;
        }
        if (pieceType.equalsIgnoreCase("KNIGHT")) {
            return new Knight(currentSpace, player);
        } else if (pieceType.equalsIgnoreCase("ROOK")) {
            return new Rook(currentSpace, player);
        } else if (pieceType.equalsIgnoreCase("BISHOP")) {
            return new Bishop(currentSpace, player);
        } else if (pieceType.equalsIgnoreCase("QUEEN")) {
            return new Queen(currentSpace, player);
        } else if (pieceType.equalsIgnoreCase("KING")) {
            return new King(currentSpace, player);
        } else if (pieceType.equalsIgnoreCase("PAWN")) {
            return new Pawn(currentSpace, player);
        }
        return null;
    }
}
