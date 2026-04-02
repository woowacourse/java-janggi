package janggi.view;

import janggi.domain.Piece;
import janggi.domain.PieceType;
import janggi.domain.Position;
import janggi.domain.Team;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class OutputView {

    public static final String HAN_COLOR = "\u001B[31m";
    public static final String CHO_COLOR = "\u001B[34m";
    public static final String AVAILABLE_COLOR = "\u001B[32m";
    public static final String RESET = "\u001B[0m";

    private static final String[] X_VALUES = {"１", "２", "３", "４", "５", "６", "７", "８", "９"};
    private static final String EMPTY_MARK = "． ";
    private static final String AVAILABLE_MARK = "Ｏ ";

    public static void printErrorMessage(String message) {
        System.out.printf("%s%n", message);
    }

    public void printBoard(Map<Position, Piece> board) {
        printGrid(board, Collections.emptyList());
    }

    public void printAvailablePositions(Map<Position, Piece> board, List<Position> availablePositions) {
        printGrid(board, availablePositions);
    }

    private void printGrid(Map<Position, Piece> board, List<Position> availablePositions) {
        printXAxis();

        for (int y = 1; y <= 10; y++) {
            System.out.printf("%2d ", y);
            for (int x = 1; x <= 9; x++) {
                Position currentPos = new Position(x, y);
                printCell(board, availablePositions, currentPos);
            }
            System.out.println();
        }
    }

    private void printXAxis() {
        System.out.print("   ");
        for (String xValue : X_VALUES) {
            System.out.print(xValue + " ");
        }
        System.out.println();
    }

    private void printCell(Map<Position, Piece> board, List<Position> availablePositions, Position position) {
        boolean isAvailable = availablePositions.contains(position);
        boolean hasPiece = board.containsKey(position);

        if (isAvailable && hasPiece) {
            Piece piece = board.get(position);
            String pieceName = resolvePieceName(piece.getPieceType());
            System.out.print(AVAILABLE_COLOR + pieceName + RESET + " ");
        } else if (isAvailable) {
            System.out.print(AVAILABLE_COLOR + AVAILABLE_MARK + RESET);
        } else if (hasPiece) {
            Piece piece = board.get(position);
            String pieceName = resolvePieceName(piece.getPieceType());
            String color = getColorByTeam(piece.getTeam());
            System.out.print(color + pieceName + RESET + " ");
        } else {
            System.out.print(EMPTY_MARK);
        }
    }

    private String resolvePieceName(PieceType pieceType) {
        return switch (pieceType) {
            case KING -> "왕";
            case SA -> "사";
            case SANG -> "상";
            case MA -> "마";
            case CHA -> "차";
            case PO -> "포";
            case ZOL -> "졸";
        };
    }

    private String getColorByTeam(Team team) {
        if (team == Team.CHO) {
            return CHO_COLOR;
        }
        return HAN_COLOR;
    }

    public void printTurnMessage(boolean isChoTurn) {
        if (isChoTurn) {
            System.out.println(CHO_COLOR + "\n현재 초나라 차례입니다" + RESET);
        } else {
            System.out.println(HAN_COLOR + "\n한나라 차례입니다" + RESET);
        }
    }

    public void printMoveInfo() {
        System.out.println("이동하고 싶은 기물의 좌표를 입력하세요.");
    }

    public void printMoveChoiceInfo() {
        System.out.println("이동하고자 하는 목표 지점의 좌표를 입력하세요.");
    }
}
