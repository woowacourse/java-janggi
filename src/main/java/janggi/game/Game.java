package janggi.game;

import janggi.position.Position;
import janggi.position.Route;
import janggi.piece.Team;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Game {
    private static final int POSITION_INPUT_SIZE = 2;
    private static final int INPUT_COLUMN_INDEX = 0;
    private static final int INPUT_ROW_INDEX = 1;

    private final InputView inputView;
    private final OutputView outputView;

    public Game(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void play() {
        GameState gameState = GameState.PLAY;
        Janggi janggi = new Janggi(new Pieces(), Team.CHO);

        while (gameState == GameState.PLAY) {
            gameState = handleGameState(() -> controlGame(janggi));
        }
        inputView.close();
    }

    private GameState handleGameState(Supplier<GameState> game) {
        try {
            return game.get();
        } catch (IllegalArgumentException exception) {
            outputView.printError(exception.getMessage());
            return GameState.PLAY;
        }
    }

    private GameState controlGame(Janggi janggi) {
        outputView.printPieces(janggi.getPieces());

        Position position = getPosition(janggi);
        janggi.judgeUnitTurn(position);

        List<Route> routes = janggi.searchAvailableRoutes(position);
        outputView.printAvailableRoute(routes, position);

        moveAndCaptureIfEnemyExists(janggi, routes, position);
        if (janggi.isNoneEnemyUnit()) {
            return GameState.QUIT;
        }
        janggi.changeTurn();
        return GameState.PLAY;
    }

    private Position getPosition(Janggi janggi) {
        List<Integer> positionValue = handleInputException(() ->
                inputView.readPosition(janggi.getTurn()), Game::getPosition);
        if (positionValue.size() != POSITION_INPUT_SIZE) {
            throw new IllegalArgumentException("column, row 형태로 입력해주세요.");
        }
        return new Position(positionValue.get(INPUT_COLUMN_INDEX), positionValue.get(INPUT_ROW_INDEX));
    }

    private static List<Integer> getPosition(String rawPosition) {
        try {
            return Arrays.stream(rawPosition.split(","))
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .toList();
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("좌표 내의 정수 숫자를 입력해주세요.");
        }
    }

    private void moveAndCaptureIfEnemyExists(Janggi janggi, List<Route> routes, Position startPoint) {
        int selectedRouteNumber = handleInputException(inputView::readRoute,
                (inputValue) -> parseSelectNumber(inputValue, routes.size()));
        Route route = routes.get(selectedRouteNumber - 1);
        janggi.moveAndCaptureIfEnemyExists(route, startPoint);
    }

    private int parseSelectNumber(String input, int selectBoxMaxSize) {
        try {
            return parseAndValidateNumber(input, selectBoxMaxSize);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("올바른 번호를 입력해주세요.");
        }
    }

    private int parseAndValidateNumber(String input, int selectBoxMaxSize) {
        int selectedNumber = Integer.parseInt(input);
        if (selectedNumber < 1 || selectedNumber > selectBoxMaxSize) {
            throw new IllegalArgumentException("범위 내의 번호를 입력해주세요.");
        }
        return selectedNumber;
    }

    private <T> T handleInputException(Supplier<String> input, Function<String, T> converter) {
        try {
            String inputValue = input.get();
            return converter.apply(inputValue);
        } catch (IllegalArgumentException exception) {
            outputView.printError(exception.getMessage());
            return handleInputException(input, converter);
        }
    }
}
