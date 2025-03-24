package domain;

import domain.position.DefaultUnitPosition;
import domain.position.Position;
import domain.position.Route;
import domain.unit.Team;
import domain.unit.Unit;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import view.InputView;
import view.OutputView;

public class Game {

    private final Janggi janggi;
    private final InputView inputView;
    private final OutputView outputView;

    public Game(InputView inputView, OutputView outputView) {
        this.janggi = createJanggi();
        this.inputView = inputView;
        this.outputView = outputView;
    }

    private Janggi createJanggi() {
        Map<Position, Unit> hanUnits = settingUnits(Team.HAN);
        Map<Position, Unit> choUnits = settingUnits(Team.CHO);
        return Janggi.of(hanUnits, choUnits, Team.CHO);
    }

    private Map<Position, Unit> settingUnits(Team team) {
        Map<Position, Unit> units = new HashMap<>();
        for (DefaultUnitPosition value : DefaultUnitPosition.values()) {
            units.putAll(DefaultUnitPosition.createDefaultUnits(value, team));
        }
        return units;
    }

    public void doTurn() {
        outputView.printJanggiUnits(janggi.getUnits());
        Position pick = parsePosition(inputView.readUnitPosition(janggi.getTurn()));

        List<Route> routes = janggi.findMovableRoutesFrom(pick);
        outputView.printAvailableRoute(pick, routes);

        Position destination = parsePosition(inputView.readDestinationPosition(janggi.getTurn()));

        janggi.doTurn(pick, destination);
        outputView.printJanggiUnits(janggi.getUnits());
    }

    public boolean isEnd() {
        return janggi.isOneOfTeamNonExist();
    }

    private List<Integer> parseInteger(String rawPosition) {
        return Arrays.stream(rawPosition.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .toList();
    }

    private Position parsePosition(String rawPosition) {
        List<Integer> positionValue = parseInteger(rawPosition);
        return Position.of(positionValue.get(0), positionValue.get(1));
    }
}
