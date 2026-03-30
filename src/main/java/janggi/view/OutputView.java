package janggi.view;

import janggi.domain.Piece;
import janggi.domain.Position;
import janggi.domain.Team;
import java.util.List;
import java.util.Map;

public class OutputView {

    public static final String HAN_COLOR = "\u001B[31m";
    public static final String CHO_COLOR = "\u001B[34m";
    public static final String AVAILABLE_COLOR = "\u001B[32m";
    public static final String RESET = "\u001B[0m";
    private static final String[] X_VALUES = {"１", "２", "３", "４", "５", "６", "７", "８", "９"};

    public static void printErrorMessage(String message) {
        System.out.printf("%s%n", message);
    }

    public void printBoard(Map<Position, Piece> board) {
        System.out.print("   ");
        for (int x = 1; x <= 9; x++) {
            System.out.print(X_VALUES[x - 1] + " ");
        }
        System.out.println();

        for (int y = 1; y <= 10; y++) {
            System.out.print(String.format("%2d ", y));

            for (int x = 1; x <= 9; x++) {
                Position currentPos = new Position(x, y);
                if (board.containsKey(currentPos)) {
                    Piece piece = board.get(currentPos);
                    String color = getColorByTeam(piece);

                    System.out.print(color + piece.getPieceTypeName() + RESET + " ");
                } else {
                    System.out.print("． ");
                }
            }
            System.out.println();
        }
    }

    public void printAvailablePositions(Map<Position, Piece> board, List<Position> availablePositions) {
        System.out.print("   ");
        for (int x = 1; x <= 9; x++) {
            System.out.print(X_VALUES[x - 1] + " ");
        }
        System.out.println();

        for (int y = 1; y <= 10; y++) {
            System.out.print(String.format("%2d ", y));

            for (int x = 1; x <= 9; x++) {
                Position currentPos = new Position(x, y);

                if (availablePositions.contains(currentPos)) {
                    if (board.containsKey(currentPos)) {
                        System.out.print(AVAILABLE_COLOR + board.get(currentPos).getPieceTypeName() + RESET + " ");
                    } else {
                        System.out.print(AVAILABLE_COLOR + "Ｏ" + RESET + " ");
                    }
                    continue;
                }

                if (board.containsKey(currentPos)) {
                    Piece piece = board.get(currentPos);
                    String color = getColorByTeam(piece);
                    System.out.print(color + piece.getPieceTypeName() + RESET + " ");
                } else {
                    System.out.print("． ");
                }
            }
            System.out.println();
        }
    }

    private String getColorByTeam(Piece piece) {
        if(piece.getTeam() == Team.CHO) return CHO_COLOR;
        return HAN_COLOR;
    }

    public void printTurnMessage(boolean isChoTurn) {
        if(isChoTurn) {
            System.out.println(OutputView.CHO_COLOR + "\n현재 초나라 차례입니다" + OutputView.RESET);
        } else {
            System.out.println(OutputView.HAN_COLOR + "\n한나라 차례입니다" + OutputView.RESET);
        }
    }

    public void printMoveInfo() {
        System.out.println("이동하고 싶은 기물의 좌표를 입력하세요.");
    }

    public void printMoveChoiceInfo() {
        System.out.println("이동하고자 하는 목표 지점의 좌표를 입력하세요.");
    }
}
