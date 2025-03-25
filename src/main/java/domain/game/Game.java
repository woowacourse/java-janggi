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
    public static final int POSITION_INPUT_SIZE = 2;
    public static final int COLUMN = 0;
    public static final int ROW = 1;

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
            gameState = handleGameState(this::controlGame);
        }
        inputView.close();
    }

    private int handleGameState(Supplier<Integer> game) {
        try {
            return game.get();
        } catch (IllegalArgumentException exception) {
            outputView.printError(exception.getMessage());
            return PLAY;
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
        int selectedRouteNumber = handleInputException(inputView::readRoute, Integer::parseInt) - 1;
        Route route = routes.get(selectedRouteNumber);
        janggi.moveAndCaptureIfEnemyExists(route, startPoint);
    }

    private Position getPosition() {
        List<Integer> positionValue = handleInputException(() ->
                inputView.readPosition(janggi.getTurn()), Game::getPosition);
        if (positionValue.size() != POSITION_INPUT_SIZE) {
            throw new IllegalArgumentException("column, row 형태로 입력해주세요.");
        }
        return new Position(positionValue.get(COLUMN), positionValue.get(ROW));
    }

    private static List<Integer> getPosition(String rawPosition) {
        return Arrays.stream(rawPosition.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .toList();
    }

    private <T> T handleInputException(Supplier<String> input, Function<String, T> converter) {
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
