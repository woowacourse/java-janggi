package janggi.controller;

import janggi.dao.BoardDao;
import janggi.dao.TurnDao;
import janggi.model.Color;
import janggi.model.JanggiGame;
import janggi.view.Parser;
import janggi.model.Position;
import janggi.model.Turn;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.List;

public class JanggiController {
    private final InputView inputView;
    private final OutputView outputView;
    private final BoardDao boardDao = new BoardDao();
    private final TurnDao turnDao = new TurnDao();

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        Turn turn = new Turn(turnDao.findCurrentTurn());
        JanggiGame janggiGame = new JanggiGame(boardDao.findBoard(), turn);
        outputView.printBoard(janggiGame.getBoard());
        retry(() -> playGame(janggiGame));
    }

    private void playGame(final JanggiGame janggiGame) {
        String command = inputView.inputMovePositions(janggiGame.getCurrentTurn().name());
        if (command.equals("Q")) {
            boardDao.updateBoard(janggiGame.getBoard().generateOccupiedPositions());
            turnDao.updateCurrentTurn(janggiGame.getCurrentTurn());
            return;
        }
        List<Position> positions = Parser.parsePositions(command);
        Position startPosition = positions.get(0);
        Position endPosition = positions.get(1);
        janggiGame.move(startPosition, endPosition);
        outputView.printBoard(janggiGame.getBoard());
        outputView.printRedTeamScore(janggiGame.calculateScore(Color.RED));
        outputView.printBlueTeamScore(janggiGame.calculateScore(Color.BLUE));
        playGame(janggiGame);
    }

    private void retry(final Runnable runnable) {
        while (true) {
            try {
                runnable.run();
                return;
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            } catch (Exception e) {
                outputView.printError("예상치 못한 예외가 발생했습니다.");
            }
        }
    }
}
