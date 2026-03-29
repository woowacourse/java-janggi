package janggi;

import janggi.domain.Position;
import janggi.domain.Turn;
import janggi.domain.board.Board;
import janggi.domain.board.BoardInitializer;
import janggi.domain.board.ElephantSetting;
import janggi.domain.board.StandardBoardInitializer;
import janggi.domain.piece.Camp;
import janggi.domain.piece.Piece;
import janggi.dto.CampDto;
import janggi.dto.PiecePositionDto;
import janggi.exception.ExceptionMessage;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class JanggiGame {

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiGame(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        Board board = createBoard();
        outputView.printBoard(toPiecePositions(board.getBoard()));
        play(board);
    }

    private Board createBoard() {
        ElephantSetting hanElephantSetting = retryOnInvalidInput(
                () -> ElephantSetting.findElephantSettingBy(
                        inputView.readElephantSettingCommand(CampDto.from(Camp.HAN))));

        ElephantSetting choElephantSetting = retryOnInvalidInput(
                () -> ElephantSetting.findElephantSettingBy(
                        inputView.readElephantSettingCommand(CampDto.from(Camp.CHO))));

        BoardInitializer initializer = new StandardBoardInitializer(hanElephantSetting, choElephantSetting);
        return new Board(initializer);
    }

    private List<PiecePositionDto> toPiecePositions(Map<Position, Piece> boardState) {
        return boardState.entrySet().stream()
                .map(entry -> PiecePositionDto.of(entry.getKey(), entry.getValue()))
                .toList();
    }

    private void play(Board board) {
        Turn turn = new Turn();
        while (true) {
            retryOnInvalidInput(() -> playTurn(board, turn));
            outputView.printBoard(toPiecePositions(board.getBoard()));
        }
    }

    private void playTurn(Board board, Turn turn) {
        Camp camp = turn.currentTurn();
        Position source = readSource(board, camp);
        Position destination = retryOnInvalidInput(() -> toPosition(inputView.readDestination()));
        board.movePiece(source, destination, camp);
        turn.finishTurn();
    }

    private Position readSource(Board board, Camp camp) {
        return retryOnInvalidInput(() -> {
            Position source = toPosition(inputView.readSource(CampDto.from(camp)));
            board.validateCampTurn(source, camp);
            return source;
        });
    }

    private Position toPosition(List<Integer> rawPosition) {
        validatePositionSize(rawPosition);
        return new Position(rawPosition.get(0), rawPosition.get(1));
    }

    private void validatePositionSize(List<Integer> rawPosition) {
        if (rawPosition.size() != 2) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_INPUT_FORMAT.getMessage());
        }
    }

    private <T> T retryOnInvalidInput(Supplier<T> input) {
        while (true) {
            try {
                return input.get();
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private void retryOnInvalidInput(Runnable input) {
        while (true) {
            try {
                input.run();
                return;
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }
}
