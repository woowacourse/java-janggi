package domain;

import domain.position.Position;
import domain.position.Route;
import java.util.Arrays;
import java.util.List;
import view.InputView;
import view.OutputView;

public class Game {
    private final Janggi janggi;
    private final InputView inputView;
    private final OutputView outputView;

    public Game(Janggi janggi, InputView inputView, OutputView outputView) {
        this.janggi = janggi;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void helloWorld() {
        outputView.printUnits(janggi.getUnits());
        String rawPosition = inputView.readPosition(janggi.getTurn());
        List<Integer> positionValue = Arrays.stream(rawPosition.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .toList();
        Position position = new Position(positionValue.get(0), positionValue.get(1));

        List<Route> routes = janggi.searchAvailableRoutes(position);
        outputView.printAvailableRoute(routes);

        // TODO: 장기 움직임 호출

        janggi.changeTurn();
    }
}
