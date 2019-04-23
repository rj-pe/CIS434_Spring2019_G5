import chess.Arbiter;
//import com.sun.xml.internal.bind.v2.TODO;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.collections.ObservableList;
import boardlogic.*;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.util.ArrayList;

import static boardlogic.Team.BLACK;
import static boardlogic.Team.WHITE;


public class Controller {
    // create board
    private Board chessBoard;

    // create player objects
    private Player black;
    private Player white;

    // handles to player objects for use during game-play
    private Player currentPlayer;
    private Player inactivePlayer;
    private ComputerPlayer computerPlayer;

    // keeps track of whether check mate has occurred
    private boolean checkMate = false;

    // the arbiter object will keep track of whether either king is under attack.
    private Arbiter arbiter;

    // store values of last two selected grid panes
    private Pane currentlySelectedSpace, spaceToMoveCurrentlySelectedPiece;

    @FXML private GridPane chessBoardFXNode, whiteGraveyardFXNode, blackGraveyardFXNode;
    @FXML private Label playerTurnWhite;
    @FXML private Label playerTurnBlack;
    @FXML private Label kingInCheck;
    @FXML private Label kingInCheckMate;

    /**
     * Instantiates the game objects necessary for a chess game.
     * @param mode If mode is one, BLACK is instantiated as a computer player. If mode is zero, both players are human.
     */
    private void initialize(int mode) {
        chessBoard = new Board(8, GameType.CHESS);
        white = new HumanPlayer(WHITE, chessBoard);
        if( mode == 0 ){
            black = new HumanPlayer(BLACK, chessBoard);
        } else if (mode == 1){
            black = new ComputerPlayer(BLACK, chessBoard);
        }
        currentPlayer = black;
        inactivePlayer = white;
        arbiter = new Arbiter(chessBoard.board[7][3].getOccupyingPiece(), chessBoard.board[0][3].getOccupyingPiece(), currentPlayer);
        drawBoard();
    }

    /**
     * Handles the "New Game" menu item.
     */
    public void twoPlayerGame(){
        initialize(0);
    }

    /**
     * Handles the "Play Computer" menu item.
     */
    public void computerPlayerGame(){
        initialize(1);
        computerPlayer = (ComputerPlayer) black;
    }

    /**
     * Carries out a ComputerPlayer turn.
     * The selected piece is picked at random from the teamMembers list.
     * The selected move is picked at random from the potentialMoves list.
     */
    private void computerTurn(){
        BoardSpace from = computerPlayer.pickPiece();
        while( from == null){
            from = computerPlayer.pickPiece();
        }
        BoardSpace to = computerPlayer.generateMove(from.getOccupyingPiece());
        while( to == null ){
            to = computerPlayer.generateMove(from.getOccupyingPiece());
        }
        chessBoard.board[from.getPosition().y][from.getPosition().x].transferPiece(to, chessBoard);
        check(to.getOccupyingPiece(), to);
        drawBoard();
    }

    private void drawBoard() {
        Point convertedCoords;
        ObservableList<Node> chessBoardFXNodeChildren = chessBoardFXNode.getChildren();

        // drawing board to GridPane on GUI
        for (int i = 0; i < chessBoardFXNodeChildren.size() - 1; i++) {
            Pane currentGridPaneSpace = (Pane) chessBoardFXNodeChildren.get(i);

            convertedCoords = convertJavaFXCoord(currentGridPaneSpace);

            if (currentGridPaneSpace.getChildren().size() != 0){
                currentGridPaneSpace.getChildren().remove(0);
            }

            if (chessBoard.board[convertedCoords.y][convertedCoords.x].getOccupyingPiece() != null) {
                currentGridPaneSpace.getChildren().add(new ImageView(chessBoard.board[convertedCoords.y][convertedCoords.x].getOccupyingPiece().getImage()));
            }
        }

        ArrayList<BoardPiece> whiteGraveyardList = white.graveyard.getGraveyard();
        ObservableList<Node> whiteGraveyardFXNodeChildren = whiteGraveyardFXNode.getChildren();
        // drawing white player's graveyard
        for (int i = 0; i < whiteGraveyardList.size(); i++) {
            Pane currentGraveyardGridPaneSpace = (Pane) whiteGraveyardFXNodeChildren.get(i);

            if (currentGraveyardGridPaneSpace.getChildren().size() != 0) {
                currentGraveyardGridPaneSpace.getChildren().remove(0);
            }

            currentGraveyardGridPaneSpace.getChildren().add(new ImageView(whiteGraveyardList.get(i).getImage()));
        }

        ArrayList<BoardPiece> blackGraveyardList = black.graveyard.getGraveyard();
        ObservableList<Node> blackGraveyardFXNodeChildren = blackGraveyardFXNode.getChildren();
        // drawing white player's graveyard
        for (int i = 0; i < blackGraveyardList.size(); i++) {
            Pane currentGraveyardGridPaneSpace = (Pane) blackGraveyardFXNodeChildren.get(i);

            if (currentGraveyardGridPaneSpace.getChildren().size() != 0) {
                currentGraveyardGridPaneSpace.getChildren().remove(0);
            }

            currentGraveyardGridPaneSpace.getChildren().add(new ImageView(blackGraveyardList.get(i).getImage()));
        }

        switchPlayers();
    }

    //TODO javadoc explaining selection handling flow
    private void selectionHandler(Pane selectedSpace) {
        Point convertedCoords = convertJavaFXCoord(selectedSpace);
        BoardPiece currentlySelectedBoardPiece = chessBoard.board[convertedCoords.y][convertedCoords.x].getOccupyingPiece();

        // Piece not yet selected
        if (currentlySelectedSpace == null && currentlySelectedBoardPiece != null) {
            // Is the selected piece on your team?
            if (currentlySelectedBoardPiece.getTeam() == currentPlayer.getTeam()) {
                // You have selected a teammate.
                setCurrentlySelectedSpace(selectedSpace, currentlySelectedBoardPiece);
            }
        // You have already selected a piece, but haven't decided on where to move it.
        } else if (currentlySelectedSpace != null && spaceToMoveCurrentlySelectedPiece == null) {
            // You have selected a space where there is already a piece.
            if (currentlySelectedBoardPiece != null) {
                // The selected space contains a teammate.
                if (currentlySelectedBoardPiece.getTeam() == currentPlayer.getTeam()) {
                    // You want to change which piece is selected.
                    setCurrentlySelectedSpace(selectedSpace, currentlySelectedBoardPiece);
                // The selected space contains an enemy, and is a legal move for your selected piece.
                } else if (currentlySelectedBoardPiece.getTeam() == inactivePlayer.getTeam() && selectedSpace.getEffect() != null) {
                    // Capture the enemy and move your piece onto the its space.
                    inactivePlayer.capture(currentlySelectedBoardPiece);
                    spaceToMoveCurrentlySelectedPiece = selectedSpace;
                    pieceMovementHandler();

                }
            // You have selected empty space and can legally move your selected piece there.
            } else if (selectedSpace.getEffect() != null) {
                // Move the piece into the new space.
                spaceToMoveCurrentlySelectedPiece = selectedSpace;
                pieceMovementHandler();
            // The selected space is not legal. Please try again.
            } else {
                clearCurrentlySelectedSpace();
            }
        }
    }

    private void switchPlayers() {
        currentPlayer = changeActivePlayer(currentPlayer, white, black, arbiter);
        inactivePlayer = changeInactivePlayer(inactivePlayer, white, black);

        if (currentPlayer.getTeam() == Team.WHITE) {
            playerTurnBlack.setVisible(false);
            playerTurnWhite.setVisible(true);
        } else {
            playerTurnBlack.setVisible(true);
            playerTurnWhite.setVisible(false);
        }
    }

    /**
     * Change which player is inactive.
     * @param from The player who is currently inactive.
     * @param white White player object.
     * @param black Black player object.
     * @return The new inactive player.
     */
    private static Player changeInactivePlayer(Player from, Player white, Player black){
        if (from.getTeam() == WHITE){
            return black;
        }
        return white;
    }

    /**
     * Changes which player is active.
     * @param from The player who is currently active
     * @param white White player object.
     * @param black Black player object.
     * @param arbiter The arbiter object also keeps track of the active player.
     * @return The new active player.
     */
    private static Player changeActivePlayer(Player from, Player white, Player black, Arbiter arbiter) {
        if (from.getTeam() == WHITE) {
            arbiter.setActivePlayer(black);
            return black;
        }
        arbiter.setActivePlayer(white);
        return white;
    }

    /**
     * When space on GUI grid is clicked this method receives the mouse event given by the object clicked.
     * The true target of the mouse event is then converted to the correct pane object user visually clicked.
     * This pane object is then sent to the selectionHandler method to determine what should happen.
     * @param event Click event on grid space.
     */
    @FXML
    private void getBoardCoord(MouseEvent event) {
        Pane clickedGridPosition;
        if (event.getTarget() instanceof Pane) {
            clickedGridPosition = (Pane) event.getTarget();
            selectionHandler(clickedGridPosition);
        } else if (event.getTarget() instanceof ImageView) {
            clickedGridPosition = (Pane) ((ImageView) event.getTarget()).getParent();
            selectionHandler(clickedGridPosition);
        }
    }

    /**
     * Takes a pane object that is the chile of a gridpane object and stores its row and
     * column within a returned point object. Any row and column values of null are properly converted to 0.
     * @param gridSpace Grid space that needs to be converted.
     * @return Point object containing the converted x and y points.
     */
    private Point convertJavaFXCoord(Pane gridSpace) {
        Point point;
        Integer xCoord, yCoord;

        yCoord = GridPane.getRowIndex(gridSpace);
        xCoord = GridPane.getColumnIndex(gridSpace);

        if (xCoord == null) xCoord = 0;
        if (yCoord == null) yCoord = 0;

        point = new Point(xCoord, yCoord);

        return point;
    }

    /**
     * Takes the currently clicked pane object on the gui grid and the BoardPiece object that occupies that space.
     * The clicked pane object and all potential panes the BoardPiece and move to are then given an InnerShadow effect
     * to indicate them as active. The global currentlySelectedSpace is then set to equal the passed selectedSpace.
     * @param selectedSpace Currently clicked pane object.
     * @param currentlySelectedBoardPiece BoardPiece object occupying the selectedSpace object.
     */
    private void setCurrentlySelectedSpace(Pane selectedSpace, BoardPiece currentlySelectedBoardPiece) {
        clearActiveEffects();
        ObservableList<Node> children = chessBoardFXNode.getChildren();

        InnerShadow activePanesEffect = new InnerShadow();
        selectedSpace.setEffect(activePanesEffect);

        // check piece's potential moves and mark them as active
        currentlySelectedBoardPiece.getPotentialMoves(chessBoard, currentPlayer);
        for (Point point: currentlySelectedBoardPiece.getMovesList()) {
            // if point x or y is 0 it must be converted to null
            Integer xCoord = point.x;
            Integer yCoord = point.y;
            if (xCoord == 0) xCoord = null;
            if (yCoord == 0) yCoord = null;

            // checks converted x and y coordinate against all Panes on the GridPane
            for (int i = 0; i < children.size() - 1; i++) {
                Pane currentIteratedPane = (Pane) children.get(i);
                if (GridPane.getColumnIndex(currentIteratedPane) == xCoord && GridPane.getRowIndex(currentIteratedPane) == yCoord) {
                    currentIteratedPane.setEffect(activePanesEffect);
                }
            }
        }
        currentlySelectedSpace = selectedSpace;
        spaceToMoveCurrentlySelectedPiece = null;
    }

    private void clearCurrentlySelectedSpace() {
        currentlySelectedSpace = null;
        spaceToMoveCurrentlySelectedPiece = null;
        clearActiveEffects();
    }

    private void clearActiveEffects() {
        ObservableList<Node> children = chessBoardFXNode.getChildren();

        for (int i = 0; i < children.size() - 1; i++) {
            Pane currentIteratedPane = (Pane) children.get(i);
            currentIteratedPane.setEffect(null);
        }
    }

    /**
     * Exits the currently running application.
     */
    @FXML
    private void exitGame(){
        System.exit(0);
    }

    /**
     * If currentlySelectedSpace and spaceToMoveCurrentlySelectedPiece are both Pane objects
     * the pieces are transferred using the board objects transferPiece method. This method also then clears
     * the currentlySelectedSpace and re-draws the board.
     */
    private void pieceMovementHandler() {
        BoardPiece piece;
        if (currentlySelectedSpace != null && spaceToMoveCurrentlySelectedPiece != null) {
            Point convertedCurrentlySelectedSpaceCoords = convertJavaFXCoord(currentlySelectedSpace);
            Point convertedSpaceToMoveCurrentlySelectedPieceCoords = convertJavaFXCoord(spaceToMoveCurrentlySelectedPiece);
            BoardSpace moveTo = chessBoard.board[convertedSpaceToMoveCurrentlySelectedPieceCoords.y][convertedSpaceToMoveCurrentlySelectedPieceCoords.x];

            chessBoard.board[convertedCurrentlySelectedSpaceCoords.y][convertedCurrentlySelectedSpaceCoords.x].transferPiece(moveTo, chessBoard);
            piece = chessBoard.board[convertedSpaceToMoveCurrentlySelectedPieceCoords.y][convertedSpaceToMoveCurrentlySelectedPieceCoords.x].getOccupyingPiece();
            // Add the potential moves list to the enemy list of threatened spaces.
            inactivePlayer.addToThreatenedSpaces(piece.getMovesList());
            // The enemy king must update his list of potential moves based on the move that just occurred.
            inactivePlayer.getKing().getPotentialMoves(chessBoard, inactivePlayer);

            check(piece, moveTo);
            clearCurrentlySelectedSpace();
            drawBoard();
        }
        if(currentPlayer == computerPlayer){
            computerTurn();
        }
        if(checkMate){
            exitGame();
        }
    }

    /**
     * Checks if the piece's move will put the enemy king in check.
     * If check has occurred, the method determines if check-mate has occurred.
     * Displays a label on the GUI if check has occurred.
     * Displays a label on the GUI if check-mate has occurred.
     * @param piece The BoardPiece which was moved.
     * @param moveTo The BoardSpace object to which the piece moved.
     */
    private void check(BoardPiece piece, BoardSpace moveTo){
        if (arbiter.movePutsKingInCheck(piece)){
            // check has occurred
            // print alert to console
            kingInCheck.setVisible(true);
            // remove space from list of enemy king's potential moves
            arbiter.removeSpaceFromMoves(moveTo, inactivePlayer);
            // arbiter object checks if the move puts the enemy king in checkmate
            if( arbiter.movePutsKingInCheckMate(arbiter.defensiveMovePossible())) {
                // check mate has occurred
                // game is over
                kingInCheckMate.setVisible(true);
                checkMate = true;
            }
        }
    }
}
