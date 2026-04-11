package team.janggi.view;

import team.janggi.domain.BoardSize;
import team.janggi.domain.Position;
import team.janggi.domain.Team;
import team.janggi.domain.board.BoardStateReader;
import team.janggi.domain.piece.Piece;
import team.janggi.domain.piece.PieceType;

public class ConsoleOutputView {
    private static final String SPACE = " ";
    private static final String EMPTY_TEXT = "";
    private static final String EMPTY_SYMBOL = "．";
    private static final String UNKNOWN_SYMBOL = "？";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_RED = "\u001B[31m";

    public void print(BoardStateReader reader,
                      long gameRoomId,
                      double choScore,
                      double hanScore) {
        final int totalCellCount = BoardSize.X * BoardSize.Y;

        printSplitLine();
        printRoomId(gameRoomId);
        printScore(choScore, hanScore);
        printColumnHeader();
        for (int index = 0; index < totalCellCount; index++) {
            int y = index / BoardSize.X;
            int x = index % BoardSize.X;

            Piece piece = reader.getPiece(new Position(x, y));
            String cellText = toSymbol(piece);
            print(applyTeamColor(piece, cellText));
            print(cellSeparatorAfter(x));
            print(rowLineSuffix(x, y));
        }
    }

    private void printSplitLine() {
        printLn("-----------------------------");
    }

    public void printRoomId(long gameRoomId) {
        printLn("게임 방 번호: " + gameRoomId);
    }
    public void printWinner(Team winner) {
        if (winner == Team.CHO) {
            printWinner("초나라");
            return;
        }

        if (winner == Team.HAN) {
            printWinner("한나라");
            return;
        }

        throw new IllegalArgumentException("알 수 없는 팀입니다: " + winner);
    }

    public void printWinner(String teamName) {
        printLn(teamName + " 승리!");
    }

    private void printColumnHeader() {
        for (int x = 0; x < BoardSize.X; x++) {
            print(toFullWidthDigit(x) + SPACE);
        }
        printLn(headerRowIndexColumnPadding());
    }

    private void printScore(double choScore, double hanScore) {
        printLn("초: " + choScore + SPACE);
        printLn("한: " + hanScore);
    }

    private String toSymbol(Piece piece) {
        if (piece == null || piece.isPieceType(PieceType.EMPTY)) {
            return EMPTY_SYMBOL;
        }

        return pieceCode(piece);
    }

    private String pieceCode(Piece piece) {
        final boolean isCho = piece.isSameTeam(Team.CHO);

        if (piece.isPieceType(PieceType.KING) && isCho) {
            return "宮";
        }
        if (piece.isPieceType(PieceType.KING)) {
            return "將";
        }
        if (piece.isPieceType(PieceType.GUARD)) {
            return "士";
        }
        if (piece.isPieceType(PieceType.HORSE)) {
            return "馬";
        }
        if (piece.isPieceType(PieceType.ELEPHANT)) {
            return "象";
        }
        if (piece.isPieceType(PieceType.CHARIOT)) {
            return "車";
        }
        if (piece.isPieceType(PieceType.CANNON) && isCho) {
            return "包";
        }
        if (piece.isPieceType(PieceType.CANNON)) {
            return "砲";
        }
        if (piece.isPieceType(PieceType.SOLDIER) && isCho) {
            return "卒";
        }
        if (piece.isPieceType(PieceType.SOLDIER)) {
            return "兵";
        }

        return UNKNOWN_SYMBOL;
    }

    private String applyTeamColor(Piece piece, String value) {
        if (piece == null || piece.isPieceType(PieceType.EMPTY)) {
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

    private String cellSeparatorAfter(int x) {
        return x < BoardSize.X - 1 ? SPACE : EMPTY_TEXT;
    }

    private String rowLineSuffix(int x, int y) {
        if (x == BoardSize.X - 1) {
            return SPACE + y + System.lineSeparator();
        }

        return EMPTY_TEXT;
    }

    private String headerRowIndexColumnPadding() {
        return SPACE;
    }

    private void print(String text) {
        System.out.print(text);
    }

    private void printLn() {
        printLn("");
    }

    private void printLn(String text) {
        System.out.println(text);
    }

    public void printInvalidGameRoomIdMessage() {
        printLn("유효하지 않은 게임 방 번호입니다. 다시 입력해주세요.");
    }

    public void printExitMessage() {
        printLn("게임을 종료합니다.");
    }

    public void printWarningMessage(String message) {
        printLn("[경고]: " + message);
    }
}
