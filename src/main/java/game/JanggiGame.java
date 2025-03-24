package game;

import domain.Board;
import domain.chessPiece.ChessPiece;
import domain.position.ChessPiecePositions;
import domain.position.ChessPosition;
import domain.position.InitialChessPiecePositionsGenerator;
import domain.type.ChessTeam;
import view.InputView;
import view.OutputView;

import java.util.List;
import java.util.Map;

public class JanggiGame {
    private final Board board;
    private ChessTeam currentTeam;
    private final InputView inputView;
    private final OutputView outputView;

    public JanggiGame(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        ChessPiecePositions positions = new ChessPiecePositions(new InitialChessPiecePositionsGenerator());
        this.board = new Board(positions);
        this.currentTeam = ChessTeam.firstTurn();
    }

    public void run() {
        while (true) {
            try {
                showBoard();
                showCurrentTeam();
                ChessPosition startPosition = getStartPosition();
                showAvailableDestinations(startPosition);
                ChessPosition destinationPosition = getDestinationPosition(startPosition);
                board.move(currentTeam, startPosition, destinationPosition);
                switchTeam();
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void showBoard() {
        Map<ChessPosition, ChessPiece> boardPositions = board.getPositions();
        outputView.printBoard(boardPositions);
    }

    private void showCurrentTeam() {
        outputView.printCurrentTeam(currentTeam);
    }

    private void switchTeam() {
        switch (currentTeam) {
            case RED -> currentTeam = ChessTeam.BLUE;
            case BLUE -> currentTeam = ChessTeam.RED;
        }
    }

    private ChessPosition getStartPosition() {
        while (true) {
            ChessPosition targetPosition = inputView.readStartPosition();
            if (!board.isExistPieceAt(targetPosition)) {
                outputView.printNotExistPieceAt(targetPosition);
                continue;
            }
            board.validateTeam(currentTeam, targetPosition);
            List<ChessPosition> availableDestinations = board.getAvailableDestination(targetPosition);
            if (!availableDestinations.isEmpty()) {
                return targetPosition;
            }
            outputView.printNotExistPath();
        }
    }

    private void showAvailableDestinations(ChessPosition startPosition) {
        List<ChessPosition> availableDestinations = board.getAvailableDestination(startPosition);
        outputView.printAvailableDestinations(availableDestinations);
    }

    private ChessPosition getDestinationPosition(ChessPosition startPosition) {
        while (true) {
            ChessPosition destinationPosition = inputView.readDestinationPosition();
            List<ChessPosition> availableDestinations = board.getAvailableDestination(startPosition);
            if (availableDestinations.contains(destinationPosition)) {
                return destinationPosition;
            }
            outputView.printInvalidDestination(destinationPosition);
        }
    }
}
