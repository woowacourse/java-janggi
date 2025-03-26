package controller;

import janggiGame.JanggiGame;
import janggiGame.arrangement.ArrangementOption;
import janggiGame.arrangement.ArrangementStrategy;
import janggiGame.Dot;
import janggiGame.state.GameResult;
import janggiGame.state.GameScore;
import view.InputView;
import view.OutputView;
import java.util.List;
import java.util.Map;

public class JanggiController {

    private final JanggiGame janggiGame;
    private final InputView inputView;
    private final OutputView outputView;

    private final Map<Integer, Runnable> options;

    public JanggiController(JanggiGame janggiGame, InputView inputView, OutputView outputView) {
        this.janggiGame = janggiGame;
        this.inputView = inputView;
        this.outputView = outputView;

        this.options = Map.of(
                1, this::takeTurn,
                2, janggiGame::skipTurn,
                3, janggiGame::undoTurn,
                4, this::printGameScore
        );
    }

    public void arrangePieces() {
        int hanOption = inputView.readHanArrangement();
        ArrangementStrategy hanStrategy = ArrangementOption.findBy(hanOption).getArrangementStrategy();

        int choOption = inputView.readChoArrangement();
        ArrangementStrategy choStrategy = ArrangementOption.findBy(choOption).getArrangementStrategy();

        janggiGame.arrangePieces(hanStrategy, choStrategy);
    }

    public void selectOption(int option) {
        Runnable action = options.get(option);
        if (action == null) {
            throw new IllegalArgumentException("[ERROR] 알맞은 옵션이 아닙니다.");
        }
        action.run();
    }

    public void takeTurn() {
        List<Dot> movement = inputView.readPieceMovement();
        janggiGame.takeTurn(movement.getFirst(), movement.getLast());
    }

    public void printGameScore() {
        GameScore score = janggiGame.getGameScore();
        outputView.printGameScore(score);
    }

    public void printGameResult() {
        GameResult result = janggiGame.getGameResult();
        outputView.printGameResult(result);
    }
}
