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

    private Board setJanggiBoard() {
        final BoardGenerator boardGenerator = new BoardGenerator();
        return boardGenerator.generate();
    }

    private void playJanggi(final Board board) {
        Team currentTurnTeam = FIRST_TURN_TEAM;

        while (!isEnd()) {
            playTurn(board, currentTurnTeam);
            currentTurnTeam = changeTurn(currentTurnTeam);
        }
        showGameResult(board, currentTurnTeam);
    }

    private boolean isEnd() {
        return gameState == GameState.END;
    }

    private void playTurn(final Board janggiBoard, final Team currentTurnTeam) {
        try {
            outputView.printJanggiBoard(janggiBoard.getJanggiBoard());
            showScore(janggiBoard);

            final Position currentPosition = readCurrentPosition(currentTurnTeam.getDescription());
            janggiBoard.validateEmptyPieceBy(currentPosition);
            validateCurrentTeamBy(janggiBoard, currentPosition, currentTurnTeam);

            final Position targetPosition = readTargetPosition();

            gameState = janggiBoard.pieceMove(currentPosition, targetPosition);
            outputView.printSuccessMove();
        } catch (IllegalArgumentException exception) {
            outputView.printErrorMessage(exception.getMessage());
            playTurn(janggiBoard, currentTurnTeam);
        }
    }

    private void showScore(final Board janggiBoard) {
        final double chuScore = janggiBoard.calculateTotalScore(Team.CHU);
        final double hanScore = janggiBoard.calculateTotalScore(Team.HAN);
        outputView.printScore(chuScore, hanScore);
    }

    private void validateCurrentTeamBy(final Board board, final Position currentPosition, final Team currentTurnTeam) {
        final Piece piece = board.getJanggiBoard().get(currentPosition);
        piece.validateTeam(currentTurnTeam);
    }

    private Team changeTurn(final Team currentTurnTeam) {
        return currentTurnTeam.changeTeam();
    }

    private void showGameResult(final Board board, final Team currentTurnTeam) {
        outputView.printJanggiBoard(board.getJanggiBoard());
        outputView.printEndGame();
        outputView.printWinner(currentTurnTeam.changeTeam().getDescription());
        outputView.printScore(board.calculateTotalScore(Team.CHU),
                board.calculateTotalScore(Team.HAN));
    }

    private Position readCurrentPosition(final String currentTurnTeam) {
        while (true) {
            try {
                return inputView.readCurrentPosition(currentTurnTeam);
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private Position readTargetPosition() {
        while (true) {
            try {
                return inputView.readTargetPosition();
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }
}
