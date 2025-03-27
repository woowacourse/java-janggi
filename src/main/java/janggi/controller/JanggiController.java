package janggi.controller;

import static janggi.domain.GameStatus.PROGRESS;
import static janggi.domain.StopInput.Y;
import static janggi.domain.Team.BLUE;
import static janggi.domain.Team.RED;

import janggi.domain.Board;
import janggi.domain.BoardSetup;
import janggi.domain.Team;
import janggi.domain.piece.Piece;
import janggi.domain.piece.direction.Position;
import janggi.domain.piece.direction.Route;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.List;
import java.util.Set;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        final Board board = generateBoard();
        final List<Piece> pieces = board.getPieces();
        boolean isProgress = true;
        while (isProgress) {
            try {
                isProgress = startGame(board, pieces);
            } catch (final IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
        outputView.printGameResult(board.getStatus(), board.getScoreByTeam(RED), board.getScoreByTeam(BLUE));
    }

    private boolean startGame(final Board board, final List<Piece> pieces) {
        displayGameState(board.getTurn(), pieces);

        final Piece selectedPiece = selectPieceToMove(board);

        final Set<Route> possibleRoutes = findPossibleRoutesForPiece(board, selectedPiece);

        movePieceIfValid(board, selectedPiece, possibleRoutes);

        return checkGameOver(board) || stopGame();
    }

    private boolean checkGameOver(final Board board) {
        return board.getStatus() != PROGRESS;
    }

    private void displayGameState(final Team team, final List<Piece> pieces) {
        outputView.printBoard(pieces);
        outputView.printTurn(team);
    }

    private Piece selectPieceToMove(final Board board) {
        final Position position = inputView.inputPiecePosition();
        return board.selectPiece(position);
    }

    private Set<Route> findPossibleRoutesForPiece(final Board board, final Piece selectedPiece) {
        final Set<Route> possibleRoutes = board.findPossibleRoutes(selectedPiece);
        outputView.printPossibleRoutes(possibleRoutes);
        return possibleRoutes;
    }

    private void movePieceIfValid(final Board board, final Piece selectedPiece, final Set<Route> possibleRoutes) {
        final Position destination = inputView.inputDestination();

        if (canMove(possibleRoutes, destination)) {
            board.movePiece(destination, selectedPiece);
            board.changeTurn();
            return;
        }
        throw new IllegalArgumentException("해당 위치로 갈 수 없습니다.");
    }

    private boolean canMove(final Set<Route> possibleRoutes, final Position destination) {
        return possibleRoutes.stream()
                .anyMatch(route -> route.isDestination(destination));
    }

    private Board generateBoard() {
        while (true) {
            try {
                final BoardSetup redBoardSetup = inputView.inputBoardSetup(RED);
                final BoardSetup blueBoardSetup = inputView.inputBoardSetup(BLUE);
                return new Board(redBoardSetup, blueBoardSetup);
            } catch (final IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private boolean stopGame() {
        return inputView.inputStopGame() == Y && inputView.inputStopGame() == Y;
    }
}
