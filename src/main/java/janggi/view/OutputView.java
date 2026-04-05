package janggi.view;

import janggi.domain.board.BoardFormation;
import janggi.domain.common.Position;
import janggi.domain.common.Team;
import janggi.domain.piece.Piece;
import java.util.List;
import java.util.Map;

public class OutputView {

    private static final String HAN_COLOR = "\u001B[31m";
    private static final String CHO_COLOR = "\u001B[34m";
    private static final String AVAILABLE_COLOR = "\u001B[32m";
    private static final String RESET = "\u001B[0m";
    private static final String[] X_VALUES = {"１", "２", "３", "４", "５", "６", "７", "８", "９"};

    public void printErrorMessage(String message) {
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

            printRow(board, y);
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
            printAvailableRow(board, availablePositions, y);
        }
    }

    public void printTurnMessage(boolean isChoTurn) {
        if (isChoTurn) {
            System.out.println(OutputView.CHO_COLOR + "\n현재 초나라 차례입니다" + OutputView.RESET);
            return;
        }
        System.out.println(OutputView.HAN_COLOR + "\n한나라 차례입니다" + OutputView.RESET);
    }

    public void printMoveInfo() {
        System.out.println("이동하고 싶은 기물의 좌표를 입력하세요.");
    }

    public void printMoveChoiceInfo() {
        System.out.println("이동하고자 하는 목표 지점의 좌표를 입력하세요.");
    }

    public void printBoardFormation(Team team) {
        System.out.printf("%s 상차림을 선택하세요.%n", team.getName());
        for (BoardFormation boardFormation : BoardFormation.values()) {
            System.out.printf("%d. %s%n", boardFormation.getChoice(), boardFormation.getName());
        }
    }

    private String getColorByTeam(Piece piece) {
        if (piece.getTeam() == Team.CHO) {
            return CHO_COLOR;
        }
        return HAN_COLOR;
    }

    private void printRow(Map<Position, Piece> board, int y) {
        for (int x = 1; x <= 9; x++) {
            Position currentPos = new Position(x, y);
            printCell(board, currentPos);
        }
        System.out.println();
    }

    private void printCell(Map<Position, Piece> board, Position currentPos) {
        if (board.containsKey(currentPos)) {
            Piece piece = board.get(currentPos);
            String color = getColorByTeam(piece);
            System.out.print(color + piece.getPieceTypeName() + RESET + " ");
            return;
        }
        System.out.print("． ");
    }

    private void printAvailableRow(Map<Position, Piece> board, List<Position> availablePositions, int y) {
        for (int x = 1; x <= 9; x++) {
            Position currentPos = new Position(x, y);
            printAvailableCell(board, availablePositions, currentPos);
        }
        System.out.println();
    }

    private void printAvailableCell(Map<Position, Piece> board, List<Position> availablePositions,
                                    Position currentPos) {
        if (availablePositions.contains(currentPos)) {
            printAvailableMarker(board, currentPos);
            return;
        }
        printCell(board, currentPos);
    }

    private void printAvailableMarker(Map<Position, Piece> board, Position currentPos) {
        if (board.containsKey(currentPos)) {
            System.out.print(AVAILABLE_COLOR + board.get(currentPos).getPieceTypeName() + RESET + " ");
            return;
        }
        System.out.print(AVAILABLE_COLOR + "Ｏ" + RESET + " ");
    }
}
