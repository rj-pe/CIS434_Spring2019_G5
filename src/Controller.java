import chess.Arbiter;
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

import static boardlogic.Team.BLACK;
import static boardlogic.Team.WHITE;


public class Controller {
    // create board
    private Board chessBoard = new Board(8, GameType.CHESS);

    // create player objects
    private Player black = new Player(BLACK, chessBoard);
    private Player white = new Player(WHITE, chessBoard);

    // handles to player objects for use during game-play
    private Player currentPlayer = black;
    private Player inactivePlayer = white;

    // the arbiter object will keep track of whether either king is under attack.
    private Arbiter arbiter = new Arbiter(chessBoard.board[7][3].getOccupyingPiece(), chessBoard.board[0][3].getOccupyingPiece(), currentPlayer);

    // store values of last two selected grid panes
    private Pane currentlySelectedSpace, spaceToMoveCurrentlySelectedPiece;

    @FXML private GridPane chessBoardFXNode;
    @FXML private Label playerTurnWhite;
    @FXML private Label playerTurnBlack;

    public void initialize() {
        drawBoard();
    }

    private void drawBoard() {
        Point convertedCoords;
        ObservableList<Node> children = chessBoardFXNode.getChildren();


        for (int i = 0; i < children.size() - 1; i++) {
            Pane currentGridPaneSpace = (Pane) children.get(i);

            convertedCoords = convertJavaFXCoord(currentGridPaneSpace);

            if (chessBoard.board[convertedCoords.y][convertedCoords.x].getOccupyingPiece() != null) {
                currentGridPaneSpace.getChildren().add(new ImageView(chessBoard.board[convertedCoords.y][convertedCoords.x].getOccupyingPiece().getImage()));
            } else {
                currentGridPaneSpace.getChildren().removeAll();
            }
        }
        switchPlayers();
    }
    
    private void selectionHandler(Pane selectedSpace) {
        Point convertedCoords = convertJavaFXCoord(selectedSpace);
        ObservableList<Node> children = chessBoardFXNode.getChildren();
        InnerShadow activePanesEffect = new InnerShadow();

        if (currentlySelectedSpace == null) {
           if (chessBoard.board[convertedCoords.y][convertedCoords.x].getOccupyingPiece().getTeam() == currentPlayer.getTeam()) {
               currentlySelectedSpace = selectedSpace;
               selectedSpace.setEffect(activePanesEffect);
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
}
