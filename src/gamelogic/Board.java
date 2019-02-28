package gamelogic;

import pieces.Pawn;

public class Board {
    private BoardSpace[][] board;

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
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new BoardSpace(null, i, j);
            }
        }
        //TODO Add code to setup white and black starting pieces
        for (int i = 0; i < board[i].length; i++) {
            board[1][i].setOccupyingPiece(new Pawn(board[1][i], "white"));
        }
    }

    private void setupCheckers() {}
}
