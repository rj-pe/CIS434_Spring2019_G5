package boardlogic;

import chess.pieces.*;

import static boardlogic.Team.*;

public class Board {
    public BoardSpace[][] board;
    private ChessPieceFactory chessPieceFactory = new ChessPieceFactory();
    private static final String[] CHESS_STARTING_ROW = {"ROOK", "KNIGHT", "BISHOP", "KING", "QUEEN", "BISHOP", "KNIGHT", "ROOK"};

    public Board(int size, GameType game) {
        board = new BoardSpace[size][size];
        setupBoard(game);
    }

    private void setupBoard(GameType game) {
        switch (game) {
            case CHESS: setupChess();
                        break;
            case CHECKER: setupCheckers();
                        break;
            default:
        }
    }

    private void setupChess() {

        //Initializes board spaces and adding them to Board array
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new BoardSpace(null, j, i);
            }
        }

        //Adding pieces to board
        for (int i = 0; i < board[0].length; i++) {
            //Placing starting rows
            BoardSpace blackPlayerSpace = board[0][i];
            BoardSpace whitePlayerSpace = board[7][i];
            whitePlayerSpace.setOccupyingPiece(chessPieceFactory.getChessPiece(CHESS_STARTING_ROW[i], whitePlayerSpace, WHITE));
            blackPlayerSpace.setOccupyingPiece(chessPieceFactory.getChessPiece(CHESS_STARTING_ROW[i], blackPlayerSpace, BLACK));

            //Placing Pawns
            board[1][i].setOccupyingPiece(chessPieceFactory.getChessPiece("PAWN", board[1][i], BLACK));
            board[6][i].setOccupyingPiece(chessPieceFactory.getChessPiece("PAWN", board[6][i], WHITE));
        }
    }

    private void setupCheckers() {}
}