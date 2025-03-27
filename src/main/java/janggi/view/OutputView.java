package janggi.view;

import janggi.unit.Team;
import janggi.unit.UnitType;
import janggi.position.Position;
import janggi.position.Route;
import janggi.unit.Unit;
import java.util.Arrays;
import java.util.List;

public class OutputView {
    private static final int MAX_ROW = 10;
    private static final int MAX_COLUM = 9;

    public void printAvailableRoute(List<Route> routes, Position startPoint) {
        int index = 1;
        for (Route route : routes) {
            Position endPoint = route.searchEndPoint(startPoint);
            System.out.printf("%d) x = %d, y = %d\n"
                    , index, endPoint.getX(), endPoint.getY());
            index++;
        }
    }

    private String teamToName(Team team) {
        if (team == Team.HAN) {
            return "한나라";
        }
        return "초나라";
    }

    public void printUnits(List<Unit> units) {
        String[][] board = initialBoard(units);
        String[][] teamInfo = initialTeamBoard(units);

        System.out.println("   | 0 1 2 3 4 5 6 7 8");
        System.out.println("----------------------");
        printBoard(board, teamInfo);
    }

    private void printBoard(String[][] board, String[][] teamInfo) {
        for (int i = 0; i < MAX_ROW; i++) {
            System.out.printf("%2d | ", i);
            for (int j = 0; j < MAX_COLUM; j++) {
                String cell = board[i][j];
                String team = teamInfo[i][j];
                if (!cell.equals(".")) {
                    String color = (team.equals("한나라")) ? ConsoleColors.HAN_COLOR : ConsoleColors.CHO_COLOR;
                    System.out.print(color + cell + ConsoleColors.RESET_COLOR + " ");
                    continue;
                }
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    private String[][] initialBoard(List<Unit> units) {
        String[][] board = new String[MAX_ROW][MAX_COLUM];

        for (int i = 0; i < MAX_ROW; i++) {
            Arrays.fill(board[i], ".");
        }
        for (Unit unit : units) {
            board[unit.getPosition().getY()][unit.getPosition().getX()] = typeToName(unit.getType());
        }
        return board;
    }

    private String[][] initialTeamBoard(List<Unit> units) {
        String[][] board = new String[MAX_ROW][MAX_COLUM];

        for (int i = 0; i < MAX_ROW; i++) {
            Arrays.fill(board[i], ".");
        }
        for (Unit unit : units) {
            board[unit.getPosition().getY()][unit.getPosition().getX()] = teamToName(unit.getTeam());
        }
        return board;
    }

    private String typeToName(UnitType unitType) {
        if (unitType == UnitType.KING) {
            return "K";
        }
        if (unitType == UnitType.SCHOLAR) {
            return "S";
        }
        if (unitType == UnitType.CAR) {
            return "C";
        }
        if (unitType == UnitType.HORSE) {
            return "H";
        }
        if (unitType == UnitType.ELEPHANT) {
            return "E";
        }
        if (unitType == UnitType.BOMB) {
            return "B";
        }
        if (unitType == UnitType.JOL) {
            return "J";
        }
        return "N";
    }

    public void printError(String message) {
        System.err.println("[ERROR] " + message);
    }
}
