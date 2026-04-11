package team.janggi.view;

import java.util.List;
import java.util.Map;
import team.janggi.domain.Position;
import team.janggi.domain.Team;
import team.janggi.domain.board.Board;
import team.janggi.domain.piece.Piece;
import team.janggi.domain.piece.PieceType;
import team.janggi.entity.Game;

public class ConsoleOutputView {
    private static final int Y_SIZE = 10;
    private static final int X_SIZE = 9;
    private static final String SPACE = " ";
    private static final String HEADER_LEFT_PADDING = "   ";
    private static final String EMPTY_SYMBOL = "．";
    private static final String UNKNOWN_SYMBOL = "？";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_RED = "\u001B[31m";

    public void print(Board board) {
        final Map<Position, Piece> status = board.getStatus();

        printLine();
        printColumnHeader();

        for (int y = 0; y < Y_SIZE; y++) {
            printRow(status, y);
        }
        printLine();
    }

    public void printWinner(Team team) {
        switch (team) {
            case CHO -> printText("한나라 승리! 게임을 종료합니다.");
            case HAN -> printText("초나라 승리! 게임을 종료합니다.");
            default -> {
            }
        }
    }

    public void printScore(Team team, double teamScore) {
        switch (team) {
            case CHO -> printTextLine("초나라 기물 점수 : " + teamScore);
            case HAN -> printTextLine("한나라 기물 점수 : " + teamScore);
            default -> {
            }
        }
    }

    public void printGames(List<Game> games) {
        if (games.isEmpty()) {
            System.out.println("저장된 게임이 없습니다.");
            return;
        }
        games.forEach(game ->
                System.out.println(game.getId() + ". " + game.getGameName())
        );
        System.out.println((games.size() + 1) + ". 새 게임 시작하기");
    }

    public void printNoSavedGames() {
        System.out.println("저장된 게임이 없어 새 게임을 시작합니다.");
    }

    public void printSavedGame(String gameName) {
        printText(gameName + " 을 저장했습니다. 게임이 종료됩니다.");
    }

    private void printColumnHeader() {
        printText(HEADER_LEFT_PADDING);
        for (int col = 0; col < X_SIZE; col++) {
            printText(toFullWidthDigit(col) + SPACE);
        }
        printLine();
    }

    private void printRow(Map<Position, Piece> status, int y) {
        printText(String.format("%2d%s", y, SPACE));

        for (int x = 0; x < X_SIZE; x++) {
            Piece piece = status.get(new Position(x, y));
            String symbol = toSymbol(piece);
            printText(applyTeamColor(piece, symbol) + SPACE);
        }
        printLine();
    }

    private String toSymbol(Piece piece) {
        if (piece == null || piece.isSamePieceType(PieceType.EMPTY)) {
            return EMPTY_SYMBOL;
        }
        return pieceCode(piece);
    }

    private String pieceCode(Piece piece) {
        final boolean isCho = piece.isSameTeam(Team.CHO);

        return switch (piece.getPieceType()) {
            case KING -> isCho ? "宮" : "將";
            case GUARD -> "士";
            case HORSE -> "馬";
            case ELEPHANT -> "象";
            case CHARIOT -> "車";
            case CANNON -> isCho ? "包" : "砲";
            case SOLDIER -> isCho ? "卒" : "兵";
            default -> UNKNOWN_SYMBOL;
        };
    }

    private String applyTeamColor(Piece piece, String value) {
        if (piece == null || piece.isSamePieceType(PieceType.EMPTY)) {
            return value;
        }

        if (piece.isSameTeam(Team.CHO)) {
            return ANSI_BLUE + value + ANSI_RESET;
        }
        if (piece.isSameTeam(Team.HAN)) {
            return ANSI_RED + value + ANSI_RESET;
        }
        return value;
    }

    private String toFullWidthDigit(int value) {
        return String.valueOf((char) ('０' + value));
    }

    private void printText(String text) {
        System.out.print(text);
    }

    private void printTextLine(String text) {
        System.out.println(text);
    }

    private void printLine() {
        System.out.println();
    }
}