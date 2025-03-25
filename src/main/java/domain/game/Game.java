package domain.game;

import domain.position.Position;
import domain.position.Route;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import view.InputView;
import view.OutputView;

public class Game {
    public static final int QUIT = 0;
    public static final int PLAY = 1;

    private final Janggi janggi;
    private final InputView inputView;
    private final OutputView outputView;

    public Game(Janggi janggi, InputView inputView, OutputView outputView) {
        this.janggi = janggi;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void play() {
        int gameState = PLAY;
        while (gameState == PLAY) {
            try {
                gameState = controlGame();
            } catch (IllegalArgumentException exception) {
                outputView.printError(exception.getMessage());
            }
        }
    }

    private int controlGame() {
        outputView.printUnits(janggi.getUnits());

        Position position = getPosition();
        janggi.judgeUnitTurn(position);

        List<Route> routes = janggi.searchAvailableRoutes(position);
        outputView.printAvailableRoute(routes, position);

        moveAndCaptureIfEnemyExists(routes, position);
        if (janggi.isNoneEnemyUnit()) {
            return QUIT;
        }
        janggi.changeTurn();
        return PLAY;
    }

    private void moveAndCaptureIfEnemyExists(List<Route> routes, Position startPoint) {
        int selectedRouteNumber = handleException(inputView::readRoute, Integer::parseInt) - 1;
        Route route = routes.get(selectedRouteNumber);
        janggi.moveAndCaptureIfEnemyExists(route, startPoint);
    }

    private Position getPosition() {
        List<Integer> positionValue = handleException(() -> inputView.readPosition(janggi.getTurn()),
                Game::getPosition);
        return new Position(positionValue.get(0), positionValue.get(1));
    }

    private static List<Integer> getPosition(String rawPosition) {
        return Arrays.stream(rawPosition.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .toList();
    }

    private <T> T handleException(Supplier<String> input, Function<String, T> converter) {
        while (true) {
            try {
                String inputValue = input.get();
                return converter.apply(inputValue);
            } catch (IllegalArgumentException exception) {
                outputView.printError(exception.getMessage());
            }
        }
    }
}
