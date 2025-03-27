package janggi;

import janggi.board.Board;
import janggi.board.BoardGenerator;
import janggi.piece.Piece;
import janggi.piece.Team;
import janggi.position.Position;
import janggi.view.InputView;
import janggi.view.OutputView;

public class JanggiGame {

    private static final Team FIRST_TURN_TEAM = Team.CHU;

    private final OutputView outputView;
    private final InputView inputView;
    private GameState gameState;

    public JanggiGame(final OutputView outputView, final InputView inputView) {
        this.outputView = outputView;
        this.inputView = inputView;
        this.gameState = GameState.IN_PROGRESS;
    }

    public void startGame() {
        final Board board = setJanggiBoard();
        playJanggi(board);
    }

    private void playJanggi(final Board board) {
        Team currentTurnTeam = FIRST_TURN_TEAM;

        while (!isEnd()) {
            playTurn(board, currentTurnTeam);
            currentTurnTeam = changeTurn(currentTurnTeam);
        }
        outputView.printEndGame();
    }

    private boolean isEnd() {
        return gameState == GameState.END;
    }

    private void playTurn(final Board board, final Team currentTurnTeam) {
        try {
            outputView.printJanggiBoard(board.getJanggiBoard());
            final Position presentPosition = readPresentPosition(currentTurnTeam.getDescription());
            board.validateEmptyPieceBy(presentPosition);
            validateCurrentTeamBy(board, presentPosition, currentTurnTeam);

            final Position futurePosition = readFuturePosition();

            gameState = board.pieceMove(presentPosition, futurePosition);
            outputView.printSuccessMove();
        } catch (IllegalArgumentException exception) {
            outputView.printErrorMessage(exception.getMessage());
            playTurn(board, currentTurnTeam);
        }
    }

    private void validateCurrentTeamBy(final Board board, final Position presentPosition, final Team currentTurnTeam) {
        final Piece piece = board.getJanggiBoard().get(presentPosition);
        piece.validateTeam(currentTurnTeam);
    }

    private Team changeTurn(final Team currentTurnTeam) {
        return currentTurnTeam.changeTeam();
    }

    private Board setJanggiBoard() {
        final BoardGenerator boardGenerator = new BoardGenerator();
        return boardGenerator.generate();
    }

    private Position readPresentPosition(final String currentTurnTeam) {
        while (true) {
            try {
                return inputView.readPresentPosition(currentTurnTeam);
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private Position readFuturePosition() {
        while (true) {
            try {
                return inputView.readFuturePosition();
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }
}
