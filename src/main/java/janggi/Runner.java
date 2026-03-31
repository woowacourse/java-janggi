package janggi;

import janggi.domain.board.Board;
import janggi.domain.game.Players;
import janggi.domain.board.Position;
import janggi.domain.game.Side;
import janggi.dto.BoardDTO;
import janggi.dto.PlayerDTO;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.HashSet;
import java.util.List;
import java.util.function.Supplier;

public class Runner {

    private final OutputView outputView;
    private final InputView inputView;
    private final Board board;

    public Runner(OutputView outputView, InputView inputView) {
        this.outputView = outputView;
        this.inputView = inputView;
        this.board = Board.initialize();
    }

    public void run() {
        Players players = initialPlayers();
        printBoard();
        play(players);
    }

    private Players initialPlayers() {
        String choPlayerName = readPlayerName(Side.CHO);
        String hanPlayerName = readPlayerName(Side.HAN);

        return Players.of(choPlayerName, hanPlayerName);
    }

    private void printBoard() {
        outputView.printBoardSettingNotice();
        outputView.printBoardStatus(BoardDTO.from(board));
    }

    private void play(Players players) {
        while (true) {
            PlayerDTO currentPlayer = players.getCurrentPlayer();
            printPlayerTurnNotice(currentPlayer);
            playerTurn(currentPlayer);
            players.switchTurn();
        }
    }

    private void playerTurn(PlayerDTO currentPlayer) {
        Side currentSide = currentPlayer.side();
        Position selected = selectMovablePiece(currentSide);
        List<Position> destinations = board.calculateDestinations(selected, currentSide);
        movePiece(selected, destinations);
    }

    private Position selectMovablePiece(Side currentSide) {
        return retry(() -> {
            outputView.printSelectPiecePosition();
            Position position = readTargetPosition();
            board.calculateDestinations(position, currentSide);

            return position;
        });
    }

    private void movePiece(Position selected, List<Position> destinations) {
        outputView.printBoardStatus(BoardDTO.from(board), selected, new HashSet<>(destinations));
        Position target = selectValidTarget(destinations);
        board.movePiece(selected, target);
        outputView.printBoardStatus(BoardDTO.from(board), target);
    }

    private Position selectValidTarget(List<Position> destinations) {
        return retry(() -> {
            outputView.printSelectTargetPosition();
            Position inputTarget = readTargetPosition();

            if (!destinations.contains(inputTarget)) {
                throw new IllegalArgumentException("[ERROR] 해당 기물이 이동할 수 없는 위치입니다. 다시 선택하세요.");
            }

            return inputTarget;
        });
    }

    private Position readTargetPosition() {
        return retry(() -> {
            outputView.printMovePositionRowNotice();
            int row = inputView.readTargetRow();
            outputView.printMovePositionColumnNotice();
            int column = inputView.readTargetColumn();

            return new Position(row, column);
        });
    }

    private String readPlayerName(Side side) {
        return retry(() -> {
            outputView.printPlayerNameNotice(side.getDisplayName());
            return inputView.readPlayerName();
        });
    }

    private void printPlayerTurnNotice(PlayerDTO currentPlayer) {
        outputView.printPlayerTurnNotice(currentPlayer.name(), currentPlayer.side().getDisplayName());
    }

    private <T> T retry(Supplier<T> supplier) {
        T result = null;
        while (result == null) {
            result = tryOnce(supplier);
        }
        return result;
    }

    private <T> T tryOnce(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (IllegalArgumentException e) {
            outputView.printLine(e.getMessage());
            return null;
        }
    }
}
