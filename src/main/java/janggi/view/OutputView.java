package janggi.view;

import janggi.domain.Piece;
import janggi.domain.PieceType;
import janggi.domain.Position;
import janggi.domain.Team;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class OutputView {

    public static final String AVAILABLE_COLOR = "\u001B[32m";
    public static final String RESET = "\u001B[0m";
    private static final List<String> COLUMN_VALUES = List.of("１", "２", "３", "４", "５", "６", "７", "８", "９");
    private static final String EMPTY_MARK = "． ";
    private static final String AVAILABLE_MARK = "Ｏ ";

    public static void printErrorMessage(String message) {
        System.out.println(message);
    }

    public void printBoard(Map<Position, Piece> board) {
        printGrid(board, Collections.emptyList());
    }

    public void printAvailablePositions(Map<Position, Piece> board, List<Position> availablePositions) {
        printGrid(board, availablePositions);
    }

    public void printTurnMessage(Team team) {
        String color = TEAM_COLORS.get(team);
        String name = TEAM_NAMES.get(team);
        System.out.println(color + "\n" + name + "나라 차례입니다" + RESET);
    }

    public void printWinner(Team team) {
        String color = TEAM_COLORS.get(team);
        String name = TEAM_NAMES.get(team);
        System.out.println();
        System.out.println(color + name + "나라 승리! 게임이 종료되었습니다!" + RESET);
    }

    public void printMoveInfo() {
        System.out.println("이동하고 싶은 기물의 좌표를 입력하세요. (예시: 1 7)");
    }

    public void printMoveChoiceInfo() {
        System.out.println("이동하고자 하는 목표 지점의 좌표를 입력하세요. (예시: 1 7)");
    }

    private static final Map<PieceType, String> PIECE_NAMES = Map.of(
            PieceType.KING, "왕", PieceType.SA, "사", PieceType.SANG, "상",
            PieceType.MA, "마", PieceType.CHA, "차", PieceType.PO, "포", PieceType.ZOL, "졸"
    );

    private static final Map<Team, String> TEAM_COLORS = Map.of(
            Team.CHO, "\u001B[34m",
            Team.HAN, "\u001B[31m"
    );

    private static final Map<Team, String> TEAM_NAMES = Map.of(
            Team.CHO, "초",
            Team.HAN, "한"
    );

    private void printGrid(Map<Position, Piece> board, List<Position> availablePositions) {
        printColumnAxis();
        for (int column = 1; column <= 10; column++) {
            printRow(board, availablePositions, column);
        }
    }

    private void printColumnAxis() {
        System.out.print("   ");
        for (String columnValue : COLUMN_VALUES) {
            System.out.print(columnValue + " ");
        }
        System.out.println();
    }

    private void printRow(Map<Position, Piece> board, List<Position> availablePositions, int column) {
        System.out.printf("%2d ", column);
        for (int row = 1; row <= 9; row++) {
            Position currentPos = new Position(row, column);
            System.out.print(generateCellString(board, availablePositions, currentPos));
        }
        System.out.println();
    }

    private String generateCellString(Map<Position, Piece> board, List<Position> availablePositions,
                                      Position position) {
        boolean isAvailable = availablePositions.contains(position);
        boolean hasPiece = board.containsKey(position);

        if (isAvailable && hasPiece) {
            return formatAvailablePiece(board.get(position));
        }
        if (isAvailable) {
            return AVAILABLE_COLOR + AVAILABLE_MARK + RESET;
        }
        if (hasPiece) {
            return formatPiece(board.get(position));
        }
        return EMPTY_MARK;
    }

    private String formatAvailablePiece(Piece piece) {
        String name = PIECE_NAMES.get(piece.getPieceType());
        return AVAILABLE_COLOR + name + RESET + " ";
    }

    private String formatPiece(Piece piece) {
        String color = TEAM_COLORS.get(piece.getTeam());
        String name = PIECE_NAMES.get(piece.getPieceType());
        return color + name + RESET + " ";
    }

    public void printInitialNotice() {
        System.out.println("\u001B[35m" + "\n\"점수계산\"을 입력하면 점수합계 및 승자 출력 후 게임이 종료됩니다.");
    }
}
