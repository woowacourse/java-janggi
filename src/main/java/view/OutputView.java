package view;

import domain.board.Board;
import domain.board.Position;
import domain.piece.Piece;
import domain.piece.Team;

import java.util.Optional;

public class OutputView {

    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String BLUE = "\u001B[34m";

    private static final String EMPTY_MARK = "＋";

    public void printBoard(Board board) {
        System.out.println("\n  Ａ Ｂ Ｃ Ｄ Ｅ Ｆ Ｇ Ｈ Ｉ");

        for (int y = 9; y >= 0; y--) {
            printRow(board, y);
        }
    }

    private void printRow(Board board, int y) {
        System.out.print(y + " ");
        for (int x = 0; x <= 8; x++) {
            Position position = new Position(x, y);
            Optional<Piece> piece = board.getPiece(position);

            String symbol = piece.map(this::getPieceSymbol).orElse(EMPTY_MARK);
            System.out.print(symbol + " ");
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

    public void printChoScore(double choScore) {
        System.out.printf("\n초(CHO): %.1f\n", choScore);
    }

    public void printHanScore(double hanScore) {
        System.out.printf("\n한(HAN): %.1f\n", hanScore);
    }

    public void printCurrentTurn(Team turn) {
        String teamName = turn == Team.CHO ? "초(CHO)" : "한(HAN)";
        String color = turn == Team.CHO ? BLUE : RED;
        System.out.printf(color + "\n▶ [" + teamName + "의 차례입니다]  " + RESET);
    }

    public void printGameResult(Team winner) {
        String winnerName = winner == Team.CHO ? "초(CHO)" : "한(HAN)";
        String color = winner == Team.CHO ? BLUE : RED;
        System.out.println(color + "\n🎉 " + winnerName + "가 승리했습니다! 게임을 종료합니다." + RESET);
    }
}
