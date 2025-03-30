package view;

import domain.janggiPiece.JanggiChessPiece;
import domain.janggiPiece.Piece;
import domain.position.JanggiPosition;
import domain.position.JanggiPositionFactory;
import domain.position.vo.Column;
import domain.position.vo.Row;
import domain.score.Score;
import domain.type.JanggiTeam;

import java.util.List;
import java.util.Map;

public class OutputView {
    private static final String RED = "\u001B[31m";
    private static final String BLUE = "\u001B[34m";
    public static final String YELLOW = "\u001B[33m";
    public static final String GREEN = "\u001B[32m";
    private static final String EXIT = "\u001B[0m";

    public static void printErrorMessage(String message) {
        printNewLine();
        System.out.print(YELLOW);
        System.out.printf("[ERROR] %s\n", message);
        System.out.print(EXIT);
        printNewLine();
    }

    public static void printBoard(Map<JanggiPosition, JanggiChessPiece> boardPositions) {
        printGridValue(" ");
        for (int col = Column.MIN_COL; col <= Column.MAX_COL; ++col) {
            printGridValue(String.valueOf(col));
        }
        printNewLine();
        for (int row = Row.MIN_ROW; row <= Row.MAX_ROW; ++row) {
            printGridValue(String.valueOf(row));
            for (int col = Column.MIN_COL; col <= Column.MAX_COL; ++col) {
                printChessPiece(JanggiPositionFactory.of(row, col), boardPositions);
            }
            printNewLine();
        }
        printNewLine();
    }

    public static void printCurrentTeam(JanggiTeam currentTeam) {
        String color = getTeamColor(currentTeam);
        System.out.print(color);
        System.out.printf("%s의 차례입니다.", getTeamText(currentTeam));
        System.out.print(EXIT);
        printNewLine();
    }

    public static void printNotExistPieceAt(JanggiPosition position) {
        System.out.print(YELLOW);
        System.out.printf("(%d, %d) 위치에는 기물이 존재하지 않습니다.\n", position.getRow(), position.getCol());
        System.out.print(EXIT);
        printNewLine();
    }

    public static void printNotExistPath() {
        System.out.print(YELLOW);
        System.out.println("해당 기물은 움직일 수 없습니다.");
        System.out.print(EXIT);
        printNewLine();
    }

    public static void printAvailableDestinations(List<JanggiPosition> destinations) {
        printNewLine();
        System.out.print(GREEN);
        System.out.println("해당 기물이 이동 가능한 위치는 다음과 같습니다.");
        String joined = String.join(", ", destinations.stream()
                .map(OutputView::getFormattedPosition)
                .toList());
        System.out.println(joined);
        System.out.print(EXIT);
        printNewLine();
    }

    public static void printInvalidDestination(JanggiPosition destinationPosition) {
        printNewLine();
        System.out.print(YELLOW);
        System.out.printf("%s 는 이동할 수 없는 위치입니다. 이동 가능한 위치 중에서 선택해주세요.\n", getFormattedPosition(destinationPosition));
        System.out.print(EXIT);
        printNewLine();
    }

    public static void printGameResult(JanggiTeam winner, Map<JanggiTeam, Score> scores) {
        printNewLine();
        System.out.print(YELLOW);
        System.out.println("경기가 종료되었습니다.");
        System.out.print(EXIT);
        printNewLine();
        System.out.println("=========== 게임 결과 ===========");
        System.out.printf("우승팀: %s\n", getTeamText(winner));
        System.out.println("=========== 기물 점수 ===========");
        for (JanggiTeam team : scores.keySet()) {
            System.out.printf("%s: %d점\n", getTeamText(team), scores.get(team).value());
        }
        printNewLine();
    }

    private static String getFormattedPosition(JanggiPosition position) {
        return String.format("(%d, %d)", position.getRow(), position.getCol());
    }

    private static void printGridValue(String value) {
        // 한글이 아닌 경우 전각으로 변환
        String fullWidthValue = toFullWidth(value);
        System.out.printf("%-3s", fullWidthValue); // 전각은 2칸 차지하므로 너비 약간 조정
    }

    private static String toFullWidth(String s) {
        StringBuilder result = new StringBuilder();
        for (char ch : s.toCharArray()) {
            // 숫자/알파벳/기호 -> 전각 변환
            if (ch >= 0x21 && ch <= 0x7E) {
                result.append((char) (ch - 0x20 + 0xFF00));
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    private static void printChessPiece(JanggiPosition currentPosition, Map<JanggiPosition, JanggiChessPiece> boardPositions) {
        if (!boardPositions.containsKey(currentPosition)) {
            printGridValue("ㅡ");
            return;
        }
        JanggiChessPiece piece = boardPositions.get(currentPosition);
        printPiece(piece);
    }

    private static void printPiece(JanggiChessPiece piece) {
        String symbol = getPieceSymbol(piece.getChessPieceType());
        String color = getTeamColor(piece.getTeam());
        System.out.print(color);
        printGridValue(symbol);
        System.out.print(EXIT);
    }

    private static String getTeamColor(JanggiTeam team) {
        return switch (team) {
            case BLUE -> BLUE;
            case RED -> RED;
        };
    }

    private static String getPieceSymbol(Piece type) {
        return switch (type) {
            case KING -> "왕";
            case PAWN -> "졸";
            case GUARD -> "사";
            case HORSE -> "마";
            case CANNON -> "포";
            case CHARIOT -> "차";
            case ELEPHANT -> "상";
        };
    }

    private static void printNewLine() {
        System.out.println();
    }

    private static String getTeamText(JanggiTeam team) {
        return switch (team) {
            case RED -> "한나라";
            case BLUE -> "초나라";
        };
    }
}
