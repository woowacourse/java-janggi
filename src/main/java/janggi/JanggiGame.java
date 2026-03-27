package janggi;

import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.board.BoardInitializer;
import janggi.domain.board.ElephantSetting;
import janggi.domain.board.StandardBoardInitializer;
import janggi.domain.piece.Camp;
import janggi.domain.piece.Piece;
import janggi.dto.PiecePositionDto;
import janggi.exception.ExceptionMessage;
import janggi.util.RetryHandler;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class JanggiGame {

    private final Queue<Camp> turns = new ArrayDeque<>(List.of(Camp.CHO, Camp.HAN));

    public void run() {
        Board board = createBoard();
        OutputView.printBoard(toPiecePositions(board.getBoard()));
        play(board);
    }

    private void play(Board board) {
        int playCount = 100;
        while (playCount-- > 0) {
            Camp turn = turns.poll();
            OutputView.printBoard(RetryHandler.retryOnInvalidInput(() -> playTurn(board, turn)));
            turns.offer(turn);
        }
    }

    private Board createBoard() {
        ElephantSetting hanElephantSetting = RetryHandler.retryOnInvalidInput(
                () -> ElephantSetting.findElephantSettingBy(InputView.readElephantSettingCommand(Camp.HAN)));

        ElephantSetting choElephantSetting = RetryHandler.retryOnInvalidInput(
                () -> ElephantSetting.findElephantSettingBy(InputView.readElephantSettingCommand(Camp.CHO)));

        BoardInitializer initializer = new StandardBoardInitializer(hanElephantSetting, choElephantSetting);
        return new Board(initializer);
    }

    private List<PiecePositionDto> playTurn(Board board, Camp camp) {
        Position source = readSource(board, camp);
        Position destination = RetryHandler.retryOnInvalidInput(() -> toPosition(InputView.readDestination()));
        Map<Position, Piece> boardState = board.movePiece(source, destination, camp);
        return toPiecePositions(boardState);
    }

    private Position readSource(Board board, Camp camp) {
        return RetryHandler.retryOnInvalidInput(() -> {
            Position source = toPosition(InputView.readSource(camp));
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

    private List<PiecePositionDto> toPiecePositions(Map<Position, Piece> boardState) {
        return boardState.entrySet().stream()
                .map(entry -> PiecePositionDto.of(entry.getKey(), entry.getValue()))
                .toList();
    }
}
