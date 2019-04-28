import chess.Arbiter;
import chess.pieces.King;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.collections.ObservableList;
import boardlogic.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import static boardlogic.Team.BLACK;
import static boardlogic.Team.WHITE;

/**
 * The controller which captures user events on the graphical interface and determines what action the game will
 * take to react to the user input.
 */
public class GameController {
    // create board
    /**
     * The board object which holds the active pieces and their relative positions.
     */
    private Board chessBoard;

    // create player objects
    /**
     * The player which controls the black pieces.
     */
    private Player black;
    /**
     * The player which controls the white pieces.
     */
    private Player white;

    // handles to player objects for use during game-play
    /**
     * The player who is legally entitled to move one of his pieces.
     */
    private Player currentPlayer;
    /**
     * The player who is not currently allowed to move his pieces.
     */
    private Player inactivePlayer;
    /**
     * The player which is controlled by the applications computer play logic.
     */
    private ComputerPlayer computerPlayer;
    /**
     * The chess engine which computes an optimal move for the computer player.
     */
    private Engine engine;

    // keeps track of whether check mate has occurred
    /**
     * A boolean which keeps track of whether check mate has occurred.
     */
    private boolean checkMate = false;
    /**
     * A boolean which keeps track whether check has occurred.
     */
    private boolean check = false;

    /**
     * The arbiter object will keep track of whether either king is under attack.
     */

    private Arbiter arbiter;

    /**
     *  Store values of last two selected grid panes
     */
    private Pane currentlySelectedSpace, spaceToMoveCurrentlySelectedPiece;

    @FXML private GridPane chessBoardFXNode, whiteGraveyardFXNode, blackGraveyardFXNode;
    @FXML private Label playerTurnWhite;
    @FXML private Label playerTurnBlack;
    @FXML private Label kingInCheck;
    @FXML private Label kingInCheckMate;
    @FXML private Pane whiteWin, blackWin;

    /**
     * Instantiates the game objects necessary for a chess game.
     * @param mode If mode is one, BLACK is instantiated as a computer player. If mode is zero, both players are human.
     */
    private void setupGame(int mode) {
        chessBoard = new Board(8, GameType.CHESS);
        white = new HumanPlayer(WHITE, chessBoard);
        if( mode == 0 ){
            black = new HumanPlayer(BLACK, chessBoard);
        } else if (mode == 1){
            black = new ComputerPlayer(BLACK, chessBoard);
        }
        currentPlayer = black;
        inactivePlayer = white;

        arbiter = new Arbiter(chessBoard.board[7][4].getOccupyingPiece(), chessBoard.board[0][4].getOccupyingPiece(), currentPlayer, chessBoard);
        if(mode == 1){
            engine = new Engine(chessBoard, white, black, arbiter);
        }
        drawBoard();
    }

    /**
     * Initializes game for two players
     */
    public void twoPlayerGame(){
        setupGame(0);
    }

    /**
     * Initializes game with the computer acting as the black player
     */
    public void computerPlayerGame(){
        setupGame(1);
        computerPlayer = (ComputerPlayer) black;
    }

    /**
     * Handles the "New Game" menu item
     */
    @FXML
    private void newGame() {
        whiteWin.setVisible(false);
        blackWin.setVisible(false);
        chessBoardFXNode.setDisable(false);

        // GUI clean-up
        clearGraveyardGUI();
        check = false;
        checkMate = false;
        kingInCheckMate.setVisible(checkMate);

        if (black instanceof ComputerPlayer) {
            computerPlayerGame();
        } else {
            twoPlayerGame();
        }
    }

    /**
     * Carries out a random ComputerPlayer turn.
     * The selected piece is picked at random from the teamMembers list.
     * The selected move is picked at random from the potentialMoves list.
     */
    private void computerTurnRandom(){
        BoardSpace from = computerPlayer.pickPiece();
        while( from == null){
            from = computerPlayer.pickPiece();
        }
        BoardSpace to = computerPlayer.generateMove(from.getOccupyingPiece());
        while( to == null ){
            to = computerPlayer.generateMove(from.getOccupyingPiece());
        }
        chessBoard.board[from.getPosition().y][from.getPosition().x].transferPiece(to);
        check(to.getOccupyingPiece(), to);
        drawBoard();
    }

    /**
     * Handles a ComputerPlayer turn, by calling to the chess engine to determine the optimal move.
     */
    private void computerTurn(){
        Move idealMove = engine.pickMove();
        BoardSpace from = idealMove.getStart();
        BoardSpace to = idealMove.getDestination();
        from.transferPiece(to);
        // Add the potential moves list to the enemy list of threatened spaces.
        inactivePlayer.addToThreatenedSpaces(to.getOccupyingPiece().getMovesList());
        // The enemy king must update his list of potential moves based on the move that just occurred.
        inactivePlayer.getKing().getPotentialMoves(chessBoard, inactivePlayer);
        check(to.getOccupyingPiece(), to);

        drawBoard();
    }

    /**
     * Draws each of the elements necessary to graphically represent the chess game.
     */
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
        // drawing black player's graveyard
        for (int i = 0; i < blackGraveyardList.size(); i++) {
            Pane currentGraveyardGridPaneSpace = (Pane) blackGraveyardFXNodeChildren.get(i);

            if (currentGraveyardGridPaneSpace.getChildren().size() != 0) {
                currentGraveyardGridPaneSpace.getChildren().remove(0);
            }

            currentGraveyardGridPaneSpace.getChildren().add(new ImageView(blackGraveyardList.get(i).getImage()));
        }
        kingInCheck.setVisible(check);
        if (checkMate) {
            playerTurnWhite.setVisible(false);
            playerTurnBlack.setVisible(false);
            kingInCheck.setVisible(false);
            kingInCheckMate.setVisible(checkMate);
        } else {
            switchPlayers();
        }
    }

    /**
     * Handles the game's response to a user's board selection.
     * If the space is empty, the user must select another space.
     * If the space contains a piece which he owns the user must select a valid space to move to.
     * If the space contains an enemy piece, the user must make another selection.
     * If the user selects a space to move to that is valid the piece is moved to that space.
     * If the user selects a space to move to that is invalid, the user must select a different space to move to.
     * @param selectedSpace The space which the user has selected.
     */
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
                } else if (currentlySelectedBoardPiece.getTeam() == inactivePlayer.getTeam()) {
                    // Capture the enemy and move your piece onto the its space.
                    if (selectedSpace.getEffect() != null) {
                        inactivePlayer.capture(currentlySelectedBoardPiece);
                        inactivePlayer.buildTeamList(chessBoard);
                        spaceToMoveCurrentlySelectedPiece = selectedSpace;
                        pieceMovementHandler();
                    } else clearActiveEffects();
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

    /**
     * Switches which player's turn it is.
     */
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

    /**
     * Clears the currently selected space and any visual effects applied to that space.
     */
    private void clearCurrentlySelectedSpace() {
        currentlySelectedSpace = null;
        spaceToMoveCurrentlySelectedPiece = null;
        clearActiveEffects();
    }

    /**
     * Clears any active effects applied to the board.
     */
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
            piece = chessBoard.board[convertedCurrentlySelectedSpaceCoords.y][convertedCurrentlySelectedSpaceCoords.x].getOccupyingPiece();
            if (!piece.getHasMoved()) {
                if (piece.getType() == PieceType.KING){
                    if (convertedSpaceToMoveCurrentlySelectedPieceCoords.x - convertedCurrentlySelectedSpaceCoords.x > 1){
                        chessBoard.board[convertedCurrentlySelectedSpaceCoords.y][7].transferPiece(chessBoard.board[convertedCurrentlySelectedSpaceCoords.y][5]);
                    }
                    if (convertedSpaceToMoveCurrentlySelectedPieceCoords.x - convertedCurrentlySelectedSpaceCoords.x < -1){
                        chessBoard.board[convertedCurrentlySelectedSpaceCoords.y][0].transferPiece(chessBoard.board[convertedCurrentlySelectedSpaceCoords.y][3]);
                    }
                }
            }
            chessBoard.board[convertedCurrentlySelectedSpaceCoords.y][convertedCurrentlySelectedSpaceCoords.x].transferPiece(moveTo);

            // Calculates the potential moves for each piece on the board and adds the potential moves list to the enemy
            inactivePlayer.clearThreatenedSpaces();
            for (BoardPiece x: currentPlayer.getTeamMembers()) {
                    x.getPotentialMoves(chessBoard, currentPlayer);
                    inactivePlayer.addToThreatenedSpaces(x.getMovesList());
            }
            currentPlayer.clearThreatenedSpaces();
            for (BoardPiece x: inactivePlayer.getTeamMembers()) {
                if (x.getType() != PieceType.KING) {
                    x.getPotentialMoves(chessBoard, inactivePlayer);
                    currentPlayer.addToThreatenedSpaces(x.getMovesList());
                }
            }
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
            chessBoardFXNode.setDisable(true);
            if (currentPlayer == black) {
                blackWin.setVisible(true);
            } else {
                whiteWin.setVisible(true);
            }
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
            check = true;
            // remove space from list of enemy king's potential moves
            arbiter.removeSpaceFromMoves(moveTo, inactivePlayer);
            // arbiter object checks if the move puts the enemy king in checkmate

            if( arbiter.movePutsKingInCheckMate(arbiter.defensiveMovePossible((King) inactivePlayer.getKing(), inactivePlayer))) {
                // check mate has occurred
                // game is over
                checkMate = true;
            }
        }
        else {
            check = false;
            checkMate = false;
        }
    }

    /**
     * Handles a user request to return to the main game menu.
     */
    @FXML
    private void returnToMainMenu() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("resources/fxml/mainmenu.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage window = (Stage) (chessBoardFXNode.getScene().getWindow());
        window.setScene(new Scene(root, 600, 600));
        window.show();
    }

    /**
     * Removes any piece's from the graveyard.
     */
    private void clearGraveyardGUI() {
        ObservableList<Node> whiteGraveyardFXNodeChildren = whiteGraveyardFXNode.getChildren();
        // drawing white player's graveyard
        for (int i = 0; i < whiteGraveyardFXNodeChildren.size(); i++) {
            Pane currentGraveyardGridPaneSpace = (Pane) whiteGraveyardFXNodeChildren.get(i);

            if (currentGraveyardGridPaneSpace.getChildren().size() != 0) {
                currentGraveyardGridPaneSpace.getChildren().remove(0);
            }
        }

        ObservableList<Node> blackGraveyardFXNodeChildren = blackGraveyardFXNode.getChildren();
        // drawing black player's graveyard
        for (int i = 0; i < blackGraveyardFXNodeChildren.size(); i++) {
            Pane currentGraveyardGridPaneSpace = (Pane) blackGraveyardFXNodeChildren.get(i);

            if (currentGraveyardGridPaneSpace.getChildren().size() != 0) {
                currentGraveyardGridPaneSpace.getChildren().remove(0);
            }
        }
    }
}
