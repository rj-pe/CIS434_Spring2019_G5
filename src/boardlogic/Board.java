package boardlogic;

import chess.pieces.*;

import static boardlogic.Team.*;

/**
 * The board object is a foundational element of the chess game class hierarchy.
 * The board class holds a two dimensional array of BoardSpace objects upon which the game is played.
 */
public class Board {
    /**
     * A two dimensional array of BoardSpace objects that represents the chess board's surface.
     */
    public BoardSpace[][] board;
    /**
     * A factory class that initializes the chess piece's that play on the board.
     */
    private ChessPieceFactory chessPieceFactory = new ChessPieceFactory();
    private static final String[] CHESS_STARTING_ROW = {"ROOK", "KNIGHT", "BISHOP", "QUEEN", "KING", "BISHOP", "KNIGHT", "ROOK"};

    /**
     * Constructs a board obect based on the gametype and number of rows and columns on the board.
     * @param size The number of rows and columns that the board should have.
     * @param game The game type that is to be created.
     */
    public Board(int size, GameType game) {
        board = new BoardSpace[size][size];
        setupBoard(game);
    }

    /**
     * Calls the corresponding setup method based on the gametype required.
     * @param game Can be either chess or checkers.
     */
    private void setupBoard(GameType game) {
        switch (game) {
            case CHESS: setupChess();
                        break;
            case CHECKER: setupCheckers();
                        break;
            default:
        }
    }

    /**
     * Instantiates the required BoardSpace and ChessPiece objects for a chess game.
     */
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

    /**
     * Instantiates the required BoardSpace and CheckPiece objects for a checkers game.
     */
    private void setupCheckers() {}
}