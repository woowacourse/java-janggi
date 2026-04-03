package janggi;

import janggi.domain.board.Destinations;
import janggi.domain.board.Position;
import janggi.domain.game.GameManager;
import janggi.domain.game.Player;
import janggi.dto.BoardDTO;
import janggi.dto.PieceDTO;
import janggi.dto.PlayerDTO;
import janggi.dto.PositionDTO;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class Runner {

    private final OutputView outputView;
    private final InputView inputView;
    private final GameManager gameManager;

    public Runner(InputView inputView, OutputView outputView, GameManager gameManager) {
        this.outputView = outputView;
        this.inputView = inputView;
        this.gameManager = gameManager;
    }

    public void run() {
        printBoard();
        play();
    }

    private void play() {
        while (!gameManager.isFinished()) {
            Player currentPlayer = gameManager.currentPlayer();
            PlayerDTO currentPlayerDTO = currentPlayer.map(PlayerDTO::new);
            double currentPlayerScore = gameManager.currentPlayerScore();
            printPlayerTurnNotice(currentPlayerDTO, currentPlayerScore);
            playerTurn();
            gameManager.switchTurn();
        }
    }

    private void printPlayerTurnNotice(PlayerDTO currentPlayer, double currentPlayerScore) {
        outputView.printPlayerTurnNotice(currentPlayer.name(), currentPlayer.sideName(), currentPlayerScore);
    }

    private void playerTurn() {
        Position selected = selectPiecePosition();
        if (!gameManager.isThereMoveablePiece(selected)) {
            outputView.printNotOwnPiece();
            gameManager.switchTurn();
            return;
        }
        Destinations destinations = gameManager.findDestinations(selected);
        movePiece(selected, destinations);
    }

    private Position selectPiecePosition() {
        outputView.printSelectPiecePosition();
        Position position = readTargetPosition();
        if (!gameManager.isPieceExist(position)) {
            outputView.printPieceNotExist();
        }
        return position;
    }

    private Position readTargetPosition() {
        return retry(() -> {
            outputView.printMovePositionRowNotice();
            int row = inputView.readPosition();
            outputView.printMovePositionColumnNotice();
            int column = inputView.readPosition();

            return new Position(row, column);
        });
    }

    private BoardDTO mapToBoardDTO() {
        return new BoardDTO(gameManager.exportBoardState(PositionDTO::new, PieceDTO::new));
    }

    private void movePiece(Position selected, Destinations destinations) {
        List<PositionDTO> positionDTOS = destinations.getDestinations().stream()
                .map(position -> position.map(PositionDTO::new))
                .toList();
        Position target = movePieceToMoveablePosition(selected, destinations, positionDTOS);
        outputView.printBoardStatus(mapToBoardDTO(), target.map(PositionDTO::new));
    }

    private Position movePieceToMoveablePosition(Position selected, Destinations destinations,
                                                 List<PositionDTO> positionDTOS) {
        return retry(() -> {
            outputView.printBoardStatus(mapToBoardDTO(), selected.map(PositionDTO::new), positionDTOS);
            Position target = selectTargetPosition();
            gameManager.movePiece(selected, target, destinations);
            return target;
        });
    }

    private Position selectTargetPosition() {
        outputView.printSelectTargetPosition();
        return readTargetPosition();
    }

    private void printBoard() {
        outputView.printBoardSettingNotice();
        outputView.printBoardStatus(mapToBoardDTO());
    }

    private <T> T retry(Supplier<T> supplier) {
        Optional<T> result = Optional.empty();

        while (result.isEmpty()) {
            result = attempt(supplier);
        }

        return result.get();
    }

    private <T> Optional<T> attempt(Supplier<T> supplier) {
        try {
            return Optional.of(supplier.get());
        } catch (IllegalArgumentException e) {
            outputView.printLine(e.getMessage());
            return Optional.empty();
        }
    }
}
