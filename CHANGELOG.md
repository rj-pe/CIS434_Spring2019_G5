# Changelog

All notable changes to this project should be recorded here.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/)

[Unreleased]
### Added
* `getPotentialMoves()` method for each of the pieces, which calculates the possible moves that a piece may make given a
position on the board. 
..*  `src/chess/pieces/Bishop.java` The Bishop can move in all diagonal directions.
..*  `src/chess/pieces/King.java`  A king can move a single space in any of eight possible directions.
..*  `src/chess/pieces/Knight.java` The knight is able to move in L-shapes and can leap other pieces.
..*  `src/chess/pieces/Pawn.java`
..*  `src/chess/pieces/Queen.java`
..*  `src/chess/pieces/Rook.java` A rook can move horizontally or vertically for any number of unoccupied squares.
*  `src/boardlogic/BoardSpace.checkForFriend()` A method which returns whether a given space is occupied by a teammate.
### Changed
*  Calls to the array chess.board[][] in `main()` should refer to the y-coordinate then the x-coordinate. 
### Issues
* Implement logic for Castling functionality. Castling is a special King movement which consists of the king moving
two spaces towards the rook and the rook moves to the position over which the king moved.
* Implement testing to ensure that piece movement methods work over a wide range of scenarios.
* Implement king attack functionality. The game should keep track of when the king is attacked and put into check and/or
checkmate.
* Implement piece capture logic. If a piece captures another piece, the game should remove the captured piece and place
it in the graveyard. 
..* Implement _en passant_ functionality for pawn capture.

## [0.0.1] - 2019-03-07
### Added
*  `src/boardlogic` Includes several classes which implement the functions that the game board must carry out.
..*  `src/boardlogic/Board.java`  A class which represents a generic game board object. This object can be configured
for multiple games. The `setupChess()` method instantiates `BoardSpace` objects and places the chess pieces on those spaces.
..*  `src/boardlogic/BoardSpace.java` A class which represents a single space on the board. When a piece moves from one 
space to another it is the BoardSpace object which completes the transfer (see `transferPiece()` method).
..*  `src/boardlogic/BoardPiece.java` A Super Class which represents fundamental functionality of a game piece. The 
class provides methods and fields which are shared by all chess pieces, such as tracking the which player owns the piece.
..*  `src/boardlogic/Player.java` describes the two players in the chess game.
-  `src/chess/pieces` Includes each of the six piece Sub Classes that inherit from the `BoardPiece` Super Class and 
represent the chessman types. The functionality that is specific to a piece type shall be implemented in its Sub Class.
..*  `src/chess/pieces/ChessPieceFactory.java` A factory class which instantiates each of the required piece objects which
represent the chessmen.
-  `CMDChess.java` The main method of the program which interacts with the user, displays output of the game and instantiates
a game and it's supporting objects.

