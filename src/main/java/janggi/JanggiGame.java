package janggi;

import janggi.domain.Position;
import janggi.domain.Turn;
import janggi.domain.board.Board;
import janggi.domain.board.BoardInitializer;
import janggi.domain.board.ElephantSetting;
import janggi.domain.board.StandardBoardInitializer;
import janggi.domain.piece.Camp;
import janggi.view.InputView;
import janggi.view.OutputView;
import janggi.view.dto.CampDto;
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
        outputView.printBoard(board.getBoard());
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

    private void play(Board board) {
        Turn turn = new Turn();
        while (true) {
            retryOnInvalidInput(() -> playTurn(board, turn));
            outputView.printBoard(board.getBoard());
        }
    }

    private void playTurn(Board board, Turn turn) {
        Camp camp = turn.currentTurn();
        Position source = retryOnInvalidInput(() -> readSource(board, camp));
        Position destination = retryOnInvalidInput(inputView::readDestination);
        board.movePiece(source, destination, camp);
        turn.finishTurn();
    }

    private Position readSource(Board board, Camp camp) {
        Position source = inputView.readSource(CampDto.from(camp));
        board.validateCampTurn(source, camp);
        return source;
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
