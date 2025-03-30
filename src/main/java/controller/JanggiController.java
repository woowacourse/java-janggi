package controller;

import domain.JanggiGame;
import domain.board.Score;
import dto.MovementRequestDto;
import view.InputView;
import view.OutputView;

public class JanggiController {
    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        final JanggiGame game = new JanggiGame();

        outputView.printBoard(game.getBoard());
        while (true) {
            if (game.isGeneralDied()) {
                Score score = game.calculateScore();
                OutputView.printGameEndMessage(score);
                break;
            }
            processMove(game);

        }
    }

    private void processMove(final JanggiGame game) {
        final MovementRequestDto movementRequestDto = inputView.readMovementRequest();

        game.move(movementRequestDto.startPoint(), movementRequestDto.arrivalPoint());

        outputView.printBoard(game.getBoard());
    }
}
