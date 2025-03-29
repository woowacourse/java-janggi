package janggi.view;

import janggi.piece.PieceType;
import janggi.piece.Team;
import janggi.piece.pieces.Piece;
import janggi.position.Position;
import janggi.position.Route;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OutputView {
    private static final int MAX_ROW = 10;
    private static final int MAX_COLUM = 9;

    public void printError(String message) {
        System.err.println("[ERROR] " + message);
    }

    public void printAvailableRoute(List<Route> routes, Position startPoint) {
        int index = 1;
        for (Route route : routes) {
            Position endPoint = route.searchEndPoint(startPoint);
            System.out.printf("%d) x = %d, y = %d%n"
                    , index, endPoint.getColumn(), endPoint.getRow());
            index++;
        }
    }

    public void printPieces(HashMap<Position, Piece> pieces) {
        String[][] board = initialBoard(pieces);
        String[][] teamInfo = initialTeamBoard(pieces);

        System.out.println("   | 0 1 2 3 4 5 6 7 8");
        System.out.println("----------------------");
        printBoard(board, teamInfo);
    }

    private String[][] initialBoard(Map<Position, Piece> units) {
        String[][] board = new String[MAX_ROW][MAX_COLUM];

        for (int i = 0; i < MAX_ROW; i++) {
            Arrays.fill(board[i], ".");
        }
        units.forEach((position, piece) ->
                board[position.getRow()][position.getColumn()] = typeToName(piece.getType()));

        return board;
    }

    private String typeToName(PieceType type) {
        if (type == PieceType.KING) {
            return "K";
        }
        if (type == PieceType.SCHOLAR) {
            return "S";
        }
        if (type == PieceType.CHARIOT) {
            return "C";
        }
        if (type == PieceType.HORSE) {
            return "H";
        }
        if (type == PieceType.ELEPHANT) {
            return "E";
        }
        if (type == PieceType.CANNON) {
            return "B";
        }
        if (type == PieceType.SOLDIER) {
            return "J";
        }
        return "N";
    }

    private String[][] initialTeamBoard(Map<Position, Piece> pieces) {
        String[][] board = new String[MAX_ROW][MAX_COLUM];

        for (int i = 0; i < MAX_ROW; i++) {
            Arrays.fill(board[i], ".");
        }
        pieces.forEach(
                (position, piece) -> board[position.getRow()][position.getColumn()] = teamToName(piece.getTeam()));
        return board;
    }

    private String teamToName(Team team) {
        if (team == Team.HAN) {
            return "한나라";
        }
        return "초나라";
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
}
