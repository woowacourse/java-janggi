package view;

import domain.board.Board;
import domain.board.Position;
import domain.piece.Piece;
import domain.piece.Team;

import java.util.Optional;

public class OutputView {

    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";   // 한(HAN)나라
    private static final String BLUE = "\u001B[34m";  // 초(CHO)나라

    // 일반 '+' 기호(1칸) 대신 전각 문자 '＋'(2칸) 사용
    private static final String EMPTY_MARK = "＋";

    public void printBoard(Board board) {
        // 🌟 X축 알파벳도 전각 문자(Ａ, Ｂ, Ｃ...)를 사용하여 한자와 폭을 완벽하게 일치시킴!
        System.out.println("\n  Ａ Ｂ Ｃ Ｄ Ｅ Ｆ Ｇ Ｈ Ｉ");

        for (int y = 9; y >= 0; y--) {
            printRow(board, y);
        }
        System.out.println();
    }

    private void printRow(Board board, int y) {
        System.out.print(y + " ");
        for (int x = 0; x <= 8; x++) {
            Position position = new Position(x, y);
            Optional<Piece> piece = board.getPiece(position);

            // null 없이 우아하게 처리
            String symbol = piece.map(this::getPieceSymbol).orElse(EMPTY_MARK);
            System.out.print(symbol + " "); // 기호(2칸) + 간격용 공백(1칸)
        }
        System.out.println();
    }

    public void printError(Exception e) {
        System.out.println(RED + "[ERROR] " + e.getMessage() + RESET);
    }

    private String getPieceSymbol(Piece piece) {
        String teamColor = piece.getTeam() == Team.CHO ? BLUE : RED;
        String typeName = switch (piece.getPieceType()) {
            case GENERAL -> piece.getTeam() == Team.CHO ? "楚" : "漢";
            case CHARIOT -> "車";
            case CANNON -> "包";
            case HORSE -> "馬";
            case ELEPHANT -> "象";
            case GUARD -> "士";
            case SOLDIER -> piece.getTeam() == Team.CHO ? "卒" : "兵";
        };

        return teamColor + typeName + RESET;
    }
}
