package janggi;

import janggi.domain.board.Destinations;
import janggi.domain.board.Position;
import janggi.domain.game.GameManager;
import janggi.domain.game.Player;
import janggi.dto.BoardDTO;
import janggi.dto.PlayerDTO;
import janggi.dto.PositionDTO;
import janggi.persistence.ActiveGameSession;
import janggi.service.JanggiService;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class Runner {

    private final OutputView outputView;
    private final InputView inputView;
    private final JanggiService janggiService;

    public Runner(InputView inputView, OutputView outputView, JanggiService janggiService) {
        this.outputView = outputView;
        this.inputView = inputView;
        this.janggiService = janggiService;
    }

    public void run(Connection connection, long gameId) throws SQLException {
        ActiveGameSession session = reloadSession(connection, gameId);
        printBoard(session.gameManager());
        play(connection, session);
    }

    private void play(Connection connection, ActiveGameSession session) throws SQLException {
        while (!session.gameManager().isFinished()) {
            session = processTurn(connection, session);
        }
        if (session.gameManager().isFinished()) {
            janggiService.saveFinished(connection, session.gameId(), session.gameManager().isFinished());
        }
    }

    private ActiveGameSession processTurn(Connection connection, ActiveGameSession session) {
        try {
            return playAndSave(connection, session);
        } catch (SQLException exception) {
            outputView.printLine("[ERROR] 저장 실패. 이전 상태로 복구합니다.");
            return reloadSession(connection, session.gameId());
        }
    }

    private ActiveGameSession playAndSave(Connection connection, ActiveGameSession session) throws SQLException {
        GameManager gameManager = session.gameManager();
        long gameId = session.gameId();
        printCurrentTurnNotice(gameManager);
        playerTurn(gameManager);
        gameManager.switchTurn();
        janggiService.saveGameState(connection, gameId, gameManager);
        return session;
    }

    private ActiveGameSession reloadSession(Connection connection, long gameId) {
        try {
            return janggiService.loadGameSession(connection, gameId);
        } catch (SQLException exception) {
            throw new IllegalStateException("[ERROR] 게임 복구에 실패하여 종료합니다.", exception);
        }
    }

    private void printCurrentTurnNotice(GameManager gameManager) {
        Player currentPlayer = gameManager.currentPlayer();
        PlayerDTO currentPlayerDTO = PlayerDTO.from(currentPlayer);
        double currentPlayerScore = gameManager.currentPlayerScore();
        printPlayerTurnNotice(currentPlayerDTO, currentPlayerScore);
    }

    private void printPlayerTurnNotice(PlayerDTO currentPlayer, double currentPlayerScore) {
        outputView.printPlayerTurnNotice(currentPlayer.name(), currentPlayer.sideName(), currentPlayerScore);
    }

    private void playerTurn(GameManager gameManager) {
        retry(() -> attemptPlayerTurn(gameManager));
    }

    private boolean attemptPlayerTurn(GameManager gameManager) {
        Position selected = selectPiecePosition(gameManager);
        validateMoveablePiece(gameManager, selected);
        Destinations destinations = gameManager.findDestinations(selected);
        movePiece(selected, destinations, gameManager);
        return true;
    }

    private Position selectPiecePosition(GameManager gameManager) {
        outputView.printSelectPiecePosition();
        Position position = readTargetPosition();
        if (!gameManager.isPieceExist(position)) {
            throw new IllegalArgumentException("[ERROR] 해당 위치에 기물이 존재하지 않습니다.");
        }
        return position;
    }

    private void validateMoveablePiece(GameManager gameManager, Position selected) {
        if (!gameManager.isThereMoveablePiece(selected)) {
            throw new IllegalArgumentException("[ERROR] 이동할 수 있는 본인의 기물이 아닙니다.");
        }
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

    private BoardDTO mapToBoardDTO(GameManager gameManager) {
        return BoardDTO.from(gameManager.getPiecePositions());
    }

    private void movePiece(Position selected, Destinations destinations, GameManager gameManager) {
        Position target = movePieceToMoveablePosition(selected, destinations, gameManager);
        outputView.printBoardStatus(mapToBoardDTO(gameManager), PositionDTO.from(target));
    }

    private Position movePieceToMoveablePosition(Position selected, Destinations destinations,
                                                 GameManager gameManager) {
        return retry(() -> executeMoveLogic(selected, destinations, gameManager));
    }

    private Position executeMoveLogic(Position selected, Destinations destinations, GameManager gameManager) {
        printMoveableStatus(selected, destinations, gameManager);
        Position target = selectTargetPosition();
        gameManager.movePiece(selected, target, destinations);
        return target;
    }

    private void printMoveableStatus(Position selected, Destinations destinations, GameManager gameManager) {
        List<PositionDTO> dtos = destinations.getDestinations().stream()
                .map(PositionDTO::from)
                .toList();
        BoardDTO boardDTO = mapToBoardDTO(gameManager);
        outputView.printBoardStatus(boardDTO, PositionDTO.from(selected), dtos);
    }

    private Position selectTargetPosition() {
        outputView.printSelectTargetPosition();
        return readTargetPosition();
    }

    private void printBoard(GameManager gameManager) {
        outputView.printBoardSettingNotice();
        outputView.printBoardStatus(mapToBoardDTO(gameManager));
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
