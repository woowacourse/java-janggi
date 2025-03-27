package game;

import domain.JanggiBoard;
import domain.janggiPiece.JanggiPiece;
import domain.position.JanggiPiecePositions;
import domain.position.JanggiPosition;
import domain.position.generator.InitJanggiPiecePositionsGenerator;
import domain.type.JanggiTeam;
import view.InputView;
import view.OutputView;

import java.util.List;
import java.util.Map;

public class JanggiGame {
    private final JanggiBoard board;
    private JanggiTeam currentTeam;
    private final InputView inputView;
    private final OutputView outputView;

    public JanggiGame(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        JanggiPiecePositions positions = new JanggiPiecePositions(new InitJanggiPiecePositionsGenerator());
        this.board = new JanggiBoard(positions);
        this.currentTeam = JanggiTeam.firstTurn();
    }

    public void run() {
        while (true) {
            try {
                showBoard();
                showCurrentTeam();
                JanggiPosition startPosition = getStartPosition();
                showAvailableDestinations(startPosition);
                JanggiPosition destinationPosition = getDestinationPosition(startPosition);
                if (board.isExistBossAt(destinationPosition)) {
                    showFinalScore();
                    break;
                }
                board.move(currentTeam, startPosition, destinationPosition);
                switchTeam();
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void showBoard() {
        Map<JanggiPosition, JanggiPiece> boardPositions = board.getPositions();
        outputView.printBoard(boardPositions);
    }

    private void showCurrentTeam() {
        outputView.printCurrentTeam(currentTeam);
    }

    private void switchTeam() {
        switch (currentTeam) {
            case RED -> currentTeam = JanggiTeam.BLUE;
            case BLUE -> currentTeam = JanggiTeam.RED;
        }
    }

    private JanggiPosition getStartPosition() {
        while (true) {
            JanggiPosition targetPosition = inputView.readStartPosition();
            if (!board.isExistPieceAt(targetPosition)) {
                outputView.printNotExistPieceAt(targetPosition);
                continue;
            }
            board.validateTeam(currentTeam, targetPosition);
            List<JanggiPosition> availableDestinations = board.getAvailableDestination(targetPosition);
            if (!availableDestinations.isEmpty()) {
                return targetPosition;
            }
            outputView.printNotExistPath();
        }
    }

    private void showAvailableDestinations(JanggiPosition startPosition) {
        List<JanggiPosition> availableDestinations = board.getAvailableDestination(startPosition);
        outputView.printAvailableDestinations(availableDestinations);
    }

    private JanggiPosition getDestinationPosition(JanggiPosition startPosition) {
        while (true) {
            JanggiPosition destinationPosition = inputView.readDestinationPosition();
            List<JanggiPosition> availableDestinations = board.getAvailableDestination(startPosition);
            if (availableDestinations.contains(destinationPosition)) {
                return destinationPosition;
            }
            outputView.printInvalidDestination(destinationPosition);
        }
    }

    private void showFinalScore() {
        outputView.printGameResult(currentTeam, board.getScores());
    }
}
