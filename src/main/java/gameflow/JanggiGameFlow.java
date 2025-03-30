package gameflow;

import domain.Janggi;
import domain.position.Position;
import domain.position.Routes;
import domain.unit.Team;
import domain.unit.Unit;
import domain.unit.UnitType;
import domain.unit.Units;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import view.InputView;
import view.OutputView;

public class JanggiGameFlow {

    private static final String SURRENDER_COMMAND = "GG";
    private final Janggi janggi;
    private final InputView inputView;
    private final OutputView outputView;

    public JanggiGameFlow(InputView inputView, OutputView outputView) {
        this.janggi = initGame();
        this.inputView = inputView;
        this.outputView = outputView;
    }

    private Janggi initGame() {
        Map<Position, Unit> hanUnits = settingUnits(Team.HAN);
        Map<Position, Unit> choUnits = settingUnits(Team.CHO);
        Units totalUnits = Units.of(hanUnits, choUnits);
        return Janggi.of(totalUnits);
    }

    private Map<Position, Unit> settingUnits(Team team) {
        Map<Position, Unit> units = new HashMap<>();
        for (UnitType value : UnitType.values()) {
            units.putAll(UnitType.createDefaultUnits(value, team));
        }
        return units;
    }

    public void playGame() {
        outputView.printJanggiUnits(janggi.getUnits());
        while (isPlaying()) {
            String rawPosition = inputView.readUnitPosition(janggi.getTurn());
            if (rawPosition.equals(SURRENDER_COMMAND)) {
                janggi.surrender();
                break;
            }
            Position position = parsePosition(rawPosition);
            processTurn(position);
        }
    }

    private boolean isPlaying() {
        return !janggi.isEnd();
    }

    private void processTurn(Position pick) {
        Routes routes = janggi.findMovableRoutesFrom(pick);
        outputView.printAvailableRoute(pick, routes);

        Position destination = parsePosition(inputView.readDestinationPosition(janggi.getTurn()));

        janggi.doTurn(pick, destination);
        outputView.printJanggiUnits(janggi.getUnits());
    }

    public void endGame() {
        Team winner = janggi.getWinner();
        outputView.printWinner(winner, janggi.getScoreOf(Team.CHO), janggi.getScoreOf(Team.HAN));
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
