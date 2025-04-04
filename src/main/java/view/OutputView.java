package view;

import domain.position.Position;
import domain.position.Route;
import domain.position.Routes;
import domain.unit.Team;
import domain.unit.Unit;
import domain.unit.UnitType;
import java.util.Map;

public class OutputView {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";

    public void printJanggiUnits(Map<Position, Unit> units) {
        System.out.println("\n* |  0  1  2  3  4  5  6  7  8 ");
        System.out.println("--------------------------------");

        for (int y = 0; y <= Position.Y_MAX; y++) {
            System.out.print(y + " | ");

            for (int x = 0; x <= Position.X_MAX; x++) {
                Position position = Position.of(x, y);
                Unit unit = units.get(position);
                if (unit != null) {
                    String unitType = typeToName(unit.getType());
                    if (unit.getTeam() == Team.CHO) {
                        System.out.print(ANSI_GREEN + unitType + ANSI_RESET + " ");
                        continue;
                    }
                    System.out.print(ANSI_RED + unitType + ANSI_RESET + " ");
                    continue;
                }
                System.out.print(".. ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void printAvailableRoute(Position current, Routes routes) {
        System.out.println("\n이동 가능한 위치");
        for (Route route : routes.getRoutes()) {
            Position endPoint = route.searchDestination(current);
            System.out.printf("x = %d, y = %d\n"
                    , endPoint.getX(), endPoint.getY());
        }
    }

    public void printWinner(Team team, double choScore, double hanScore) {
        System.out.println(teamToName(team) + "가 승리하였습니다.");
        System.out.println("초나라 점수 : " + choScore + ", 한나라 점수 : " + hanScore);
    }

    private String typeToName(UnitType unitType) {
        if (unitType == UnitType.GENERAL) {
            return "GN";
        }
        if (unitType == UnitType.GUARD) {
            return "GD";
        }
        if (unitType == UnitType.CHARIOT) {
            return "CH";
        }
        if (unitType == UnitType.HORSE) {
            return "HR";
        }
        if (unitType == UnitType.ELEPHANT) {
            return "EL";
        }
        if (unitType == UnitType.CANNON) {
            return "CN";
        }
        if (unitType == UnitType.SOLDIER) {
            return "SD";
        }
        throw new IllegalStateException("예기치 못한 예외가 발생하였습니다.");
    }

    private String teamToName(Team team) {
        if (team == Team.HAN) {
            return "한나라";
        }
        if (team == Team.CHO) {
            return "초나라";
        }
        throw new IllegalStateException("예기치 못한 예외가 발생하였습니다.");
    }
}
