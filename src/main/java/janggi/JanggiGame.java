package janggi;

import janggi.domain.Position;
import janggi.domain.Turn;
import janggi.domain.board.Board;
import janggi.domain.board.initializer.BoardInitializer;
import janggi.domain.board.initializer.ElephantSetUp;
import janggi.domain.board.initializer.StandardBoardInitializer;
import janggi.domain.board.initializer.dto.ElephantSetUpDto;
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
        ElephantSetUpDto hanElephantSetUp = readElephantSetUp(Camp.HAN);
        ElephantSetUpDto choElephantSetUp = readElephantSetUp(Camp.CHO);

        BoardInitializer initializer
                = new StandardBoardInitializer(hanElephantSetUp, choElephantSetUp);
        return new Board(initializer);
    }

    private ElephantSetUpDto readElephantSetUp(Camp camp) {
        ElephantSetUp elephantSetUp = retryOnInvalidInput(
                () -> inputView.readElephantSetting(CampDto.from(camp))
        );
        return new ElephantSetUpDto(camp, elephantSetUp);
    }

    private void play(Board board) {
        Turn turn = new Turn();
        boolean continueGame = true;
        while (continueGame) {
            outputView.printScore(board.calculateScore());
            continueGame = retryOnInvalidInput(() -> playTurn(board, turn));
            outputView.printBoard(board.getBoard());
        }
        outputView.printWinner(turn.currentTurn());
    }

    private boolean playTurn(Board board, Turn turn) {
        Camp camp = turn.currentTurn();

        Position source = retryOnInvalidInput(() -> readSource(board, camp));
        Position destination = retryOnInvalidInput(inputView::readDestination);

        boolean gameEnded = board.movePiece(source, destination, camp);
        if (gameEnded) {
            return false;
        }
        turn.finishTurn();
        return true;
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
}
