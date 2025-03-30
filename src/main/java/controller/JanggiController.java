package controller;

import dao.BoardDao;
import domain.JanggiGame;
import domain.Player;
import domain.board.Board;
import domain.board.Score;
import dto.MovementRequestDto;
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

        final Board board = boardDao.getBoard();
        final List<Player> players = boardDao.getPlayers();

        final JanggiGame game = new JanggiGame(board, players);

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
