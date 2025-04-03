package janggi.controller;

import janggi.db.DBConnection;
import janggi.db.DBInitializer;
import janggi.model.Color;
import janggi.model.JanggiGame;
import janggi.model.Position;
import janggi.view.InputView;
import janggi.view.OutputView;
import janggi.view.Parser;
import java.util.List;

public class JanggiController {
    private final InputView inputView;
    private final OutputView outputView;
    private final DBInitializer dbInitializer = new DBInitializer(new DBConnection());

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        JanggiGame janggiGame = new JanggiGame();
        retry(() -> playGame(janggiGame));
    }

    private void playGame(final JanggiGame janggiGame) {
        double redTeamScore = janggiGame.calculateScore(Color.RED);
        double blueTeamScore = janggiGame.calculateScore(Color.BLUE);
        outputView.printBoard(janggiGame.getBoard());
        outputView.printRedTeamScore(redTeamScore);
        outputView.printBlueTeamScore(blueTeamScore);
        Color currentTurnColor = janggiGame.getCurrentTurn();
        String command = inputView.inputMovePositions(currentTurnColor);
        if (command.equals("Q")) {
            return;
        }
        if (command.equals("C")) {
            dbInitializer.init();
            playGame(janggiGame);
        }
        List<Position> positions = Parser.parsePositions(command);
        Position startPosition = positions.get(0);
        Position endPosition = positions.get(1);
        janggiGame.playTurn(startPosition, endPosition);
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
