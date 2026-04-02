package janggi.ui;

import janggi.dto.GameSummary;
import janggi.domain.status.Team;
import janggi.dto.GameStatusInfo;
import janggi.dto.PieceInfo;
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
        status.pieces().stream()
                .map(row -> row.stream()
                        .map(OutputView::formatPiece)
                        .toList())
                .forEach(System.out::println);
        System.out.println();
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
