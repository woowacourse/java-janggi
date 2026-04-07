package janggi.ui;

import janggi.dto.GameStatusInfo;
import janggi.dto.GameSummary;
import janggi.dto.PieceInfo;
import janggi.domain.status.Team;
import java.util.List;

public class OutputView {

    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";

    public static void printSavedGames(List<GameSummary> gameSummaries) {
        System.out.println();
        System.out.println("저장된 게임 목록입니다.");
        gameSummaries.forEach(OutputView::printGameSummary);
        System.out.println();
    }

    public static void printCurrentTurn(String teamName) {
        System.out.println("현재 턴 : " + teamName);
    }

    public static void printWinner(Team winner) {
        System.out.println("승자는 " + winner.getName());
    }

    public static void printGameStatus(GameStatusInfo status) {
        System.out.println();
        printHeader();
        printLinesByY(status.pieces());
        System.out.println();
    }

    public static void printScore(int choScore, int hanScore) {
        System.out.println("최종 점수");
        System.out.println("초 : " + choScore);
        System.out.println("한 : " + hanScore);
    }

    private static void printHeader() {
        System.out.println("   " + List.of(0, 1, 2, 3, 4, 5, 6, 7, 8));
    }

    private static void printLinesByY(List<List<PieceInfo>> pieces) {
        for (int y = pieces.size() - 1; y >= 0; y--) {
            System.out.println(y + " " + formatLineAtY(pieces.get(y)));
        }
    }

    private static String formatLineAtY(List<PieceInfo> piecesAtY) {
        return piecesAtY.stream()
                .map(OutputView::formatPiece)
                .toList()
                .toString();
    }

    private static void printGameSummary(GameSummary gameSummary) {
        System.out.println(
                gameSummary.id() + "번 게임 - " + gameStatus(gameSummary)
        );
    }

    private static String gameStatus(GameSummary gameSummary) {
        if (gameSummary.finished()) {
            return "종료";
        }
        return "진행 중";
    }

    private static String formatPiece(PieceInfo piece) {
        if (piece.team() == null) {
            return piece.name();
        }
        if (piece.team() == Team.CHO) {
            return GREEN + piece.name() + RESET;
        }
        return RED + piece.name() + RESET;
    }
}
