package view;

import domain.chessPiece.ChessPiece;
import domain.position.ChessPosition;
import domain.type.ChessPieceType;
import domain.type.ChessTeam;

import java.util.List;
import java.util.Map;

public class OutputView {

    private static final String RED = "\u001B[31m";
    private static final String BLUE = "\u001B[34m";
    public static final String YELLOW = "\u001B[33m";
    public static final String GREEN = "\u001B[32m";
    private static final String EXIT = "\u001B[0m";

    public void printErrorMessage(String message) {
        printNewLine();
        System.out.print(YELLOW);
        System.out.printf("[ERROR] %s\n", message);
        System.out.print(EXIT);
        printNewLine();
    }

    public void printBoard(Map<ChessPosition, ChessPiece> boardPositions) {
        printGridValue(" ");
        for (int col = ChessPosition.MIN_COL; col <= ChessPosition.MAX_COL; ++col) {
            printGridValue(String.valueOf(col));
        }
        printNewLine();
        for (int row = ChessPosition.MIN_ROW; row <= ChessPosition.MAX_ROW; ++row) {
            printGridValue(String.valueOf(row));
            for (int col = ChessPosition.MIN_COL; col <= ChessPosition.MAX_COL; ++col) {
                printChessPiece(new ChessPosition(row, col), boardPositions);
            }
            printNewLine();
        }
        printNewLine();
    }

    public void printCurrentTeam(ChessTeam currentTeam) {
        String color = getTeamColor(currentTeam);
        System.out.print(color);
        System.out.printf("%s의 차례입니다.", getTeamText(currentTeam));
        System.out.print(EXIT);
        printNewLine();
    }

    public void printNotExistPieceAt(ChessPosition position) {
        System.out.print(YELLOW);
        System.out.printf("(%d, %d) 위치에는 기물이 존재하지 않습니다.\n", position.row(), position.column());
        System.out.print(EXIT);
        printNewLine();
    }

    public void printNotExistPath() {
        System.out.print(YELLOW);
        System.out.println("해당 기물은 움직일 수 없습니다.");
        System.out.print(EXIT);
        printNewLine();
    }

    public void printAvailableDestinations(List<ChessPosition> destinations) {
        printNewLine();
        System.out.print(GREEN);
        System.out.println("해당 기물이 이동 가능한 위치는 다음과 같습니다.");
        String joined = String.join(", ", destinations.stream()
                .map(this::getFormattedPosition)
                .toList());
        System.out.println(joined);
        System.out.print(EXIT);
        printNewLine();
    }

    public void printInvalidDestination(ChessPosition destinationPosition) {
        printNewLine();
        System.out.print(YELLOW);
        System.out.printf("%s 는 이동할 수 없는 위치입니다. 이동 가능한 위치 중에서 선택해주세요.\n", getFormattedPosition(destinationPosition));
        System.out.print(EXIT);
        printNewLine();
    }

    private String getFormattedPosition(ChessPosition position) {
        return String.format("(%d, %d)", position.row(), position.column());
    }

    private void printGridValue(String value) {
        System.out.printf("%-3s", value);
    }

    private void printChessPiece(ChessPosition currentPosition, Map<ChessPosition, ChessPiece> boardPositions) {
        if (!boardPositions.containsKey(currentPosition)) {
            printGridValue("＿");
            return;
        }
        ChessPiece piece = boardPositions.get(currentPosition);
        printPiece(piece);
    }

    private void printPiece(ChessPiece piece) {
        String symbol = getPieceSymbol(piece.getChessPieceType());
        String color = getTeamColor(piece.getTeam());
        System.out.print(color);
        printGridValue(symbol);
        System.out.print(EXIT);
    }

    private String getTeamColor(ChessTeam team) {
        return switch (team) {
            case BLUE -> BLUE;
            case RED -> RED;
        };
    }

    private String getPieceSymbol(ChessPieceType type) {
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

    private void printNewLine() {
        System.out.println();
    }

    private String getTeamText(ChessTeam team) {
        return switch (team) {
            case RED -> "한나라";
            case BLUE -> "초나라";
        };
    }
}
