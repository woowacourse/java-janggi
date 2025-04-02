package controller;

import janggiGame.position.Position;
import janggiGame.state.GameResult;
import janggiGame.state.GameScore;
import java.util.List;
import java.util.Map;
import service.JanggiGameProgressService;
import view.InputView;
import view.OutputView;

public class JanggiController {

    private final JanggiGameProgressService gameService;
    private final InputView inputView;
    private final OutputView outputView;

    private final Map<Integer, Runnable> options;

    public JanggiController(JanggiGameProgressService gameService, InputView inputView, OutputView outputView) {
        this.gameService = gameService;
        this.inputView = inputView;
        this.outputView = outputView;

        this.options = Map.of(
                1, this::takeTurn,
                2, gameService::skipTurn,
                3, gameService::undoTurn,
                4, this::printGameScore
        );
    }

    public void selectOption() {
        int option = inputView.getTurnOption(gameService.getGame().getCurrentDynasty());
        Runnable action = options.get(option);
        if (action == null) {
            throw new IllegalArgumentException("[ERROR] 알맞은 옵션이 아닙니다.");
        }
        action.run();
    }

    public void takeTurn() {
        List<Position> movement = inputView.readPieceMovement();
        Position origin = movement.getFirst();
        Position destination = movement.getLast();

        gameService.takeTurn(origin, destination);
    }

    public void printGameScore() {
        GameScore score = gameService.getGame().getGameScore();
        outputView.printGameScore(score);
    }

    public void printGameResult() {
        GameResult result = gameService.getGame().getGameResult();
        outputView.printGameResult(result);
    }

    public void printBoard() {
        outputView.printBoard(gameService.getGame().getPieces());
    }

    public boolean isGameFinished() {
        return gameService.isFinished();
    }
}
