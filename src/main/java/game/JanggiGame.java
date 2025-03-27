package game;

import piece.Piece;
import store.Board;
import location.Position;
import location.PathUtility;
import store.Pieces;
import store.Player;
import view.InputView;
import view.OutputView;

public class JanggiGame {

    private final Board gameBoard;

    public JanggiGame(Board gameBoard) {
        this.gameBoard = gameBoard;
    }

    public void showInitialBoard() {
        OutputView.displayBoard(gameBoard);
    }

    public void run() {
        for (Team team : Team.values()) {
            Player currentPlayer = gameBoard.findPlayerBy(team);

            Position start = requestMovementStartPosition(currentPlayer);
            Position end = InputView.requestMovementEndPosition();

            currentPlayer.validateAlreadyPlayerPieceInDestination(end);
            PathUtility.checkNotSameStartWithEnd(start, end);

            move(currentPlayer, start, end);
            OutputView.displayBoard(gameBoard);
        }
    }

    private Position requestMovementStartPosition(Player player) {
        while (true) {
            Position start = InputView.requestMoveStartPosition();

            if (player.isContainedPiece(start)) {
                return start;
            }
            OutputView.displayWrongPoint();
        }
    }

    public void move(Player currentPlayer, Position start, Position end) {
        Pieces allPieces = gameBoard.findAllPieces();
        Piece piece = currentPlayer.getPieceByPoint(start);

        piece.validateDestination(end);
        piece.validatePaths(allPieces, end);
        Piece movedPiece = piece.move(end);

        currentPlayer.delete(piece);
        currentPlayer.add(movedPiece);
    }
}
