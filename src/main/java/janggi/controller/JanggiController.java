package janggi.controller;

import static janggi.domain.Function.MOVE;
import static janggi.domain.GameStatus.PROGRESS;
import static janggi.domain.StopInput.Y;
import static janggi.domain.Team.BLUE;
import static janggi.domain.Team.RED;

import janggi.domain.BoardSetup;
import janggi.domain.Game;
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
        final Game game = generateBoard();
        final List<Piece> pieces = game.getPieces();
        boolean isProgress = true;
        while (isProgress) {
            try {
                isProgress = startGame(game, pieces);
            } catch (final IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
        outputView.printGameResult(game.getStatus(), game.getScoreByTeam(RED), game.getScoreByTeam(BLUE));
    }

    private boolean startGame(final Game game, final List<Piece> pieces) {
        displayGameState(game.getTurn(), pieces);
        if (inputView.inputSelectFunction() == MOVE) {
            final Piece selectedPiece = selectPieceToMove(game);
            final Set<Route> possibleRoutes = findPossibleRoutesForPiece(game, selectedPiece);
            movePieceIfValid(game, selectedPiece, possibleRoutes);
            return checkGameOver(game);
        }
        return stopGame();
    }

    private boolean checkGameOver(final Game game) {
        return game.getStatus() == PROGRESS;
    }

    private void displayGameState(final Team team, final List<Piece> pieces) {
        outputView.printBoard(pieces);
        outputView.printTurn(team);
    }

    private Piece selectPieceToMove(final Game game) {
        final Position position = inputView.inputPiecePosition();
        return game.selectPiece(position);
    }

    private Set<Route> findPossibleRoutesForPiece(final Game game, final Piece selectedPiece) {
        final Set<Route> possibleRoutes = game.findPossibleRoutes(selectedPiece);
        outputView.printPossibleRoutes(possibleRoutes);
        return possibleRoutes;
    }

    private void movePieceIfValid(final Game game, final Piece selectedPiece, final Set<Route> possibleRoutes) {
        final Position destination = inputView.inputDestination();

        if (canMove(possibleRoutes, destination)) {
            game.movePiece(destination, selectedPiece);
            game.changeTurn();
            return;
        }
        throw new IllegalArgumentException("해당 위치로 갈 수 없습니다.");
    }

    private boolean canMove(final Set<Route> possibleRoutes, final Position destination) {
        return possibleRoutes.stream()
                .anyMatch(route -> route.isDestination(destination));
    }

    private Game generateBoard() {
        while (true) {
            try {
                final BoardSetup redBoardSetup = inputView.inputBoardSetup(RED);
                final BoardSetup blueBoardSetup = inputView.inputBoardSetup(BLUE);
                return new Game(redBoardSetup, blueBoardSetup);
            } catch (final IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private boolean stopGame() {
        return !(inputView.inputStopGame() == Y & inputView.inputStopGame() == Y);
    }
}
