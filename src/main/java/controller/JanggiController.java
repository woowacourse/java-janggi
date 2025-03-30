package controller;

import dao.BoardDao;
import domain.JanggiGame;
import domain.board.BoardPoint;
import domain.board.Score;
import java.util.List;
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
        BoardDao boardDao = new BoardDao();

        final JanggiGame game = new JanggiGame(boardDao.getBoard());
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
        final List<BoardPoint> movementRequest = inputView.readMovementRequest();
        final BoardPoint startBoardPoint = movementRequest.getFirst();
        final BoardPoint arrivalBoardPoint = movementRequest.getLast();

        game.move(startBoardPoint, arrivalBoardPoint);

        outputView.printBoard(game.getBoard());
    }
}
