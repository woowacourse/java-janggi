package view;

import domain.piece.PieceType;
import domain.piece.Team;
import dto.GameDto;
import dto.PieceDto;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class OutputView {

    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String BLUE = "\u001B[34m";
    private static final String EMPTY_MARK = "＋";

    private static final Map<Team, String> TEAM_NAMES = Map.of(
            Team.CHO, "초(CHO)",
            Team.HAN, "한(HAN)"
    );

    private static final Map<Team, String> TEAM_COLORS = Map.of(
            Team.CHO, BLUE,
            Team.HAN, RED
    );

    private static final Map<PieceType, Map<Team, String>> SYMBOLS = Map.of(
            PieceType.GENERAL, Map.of(Team.CHO, "楚", Team.HAN, "漢"),
            PieceType.CHARIOT, Map.of(Team.CHO, "車", Team.HAN, "車"),
            PieceType.CANNON, Map.of(Team.CHO, "包", Team.HAN, "包"),
            PieceType.HORSE, Map.of(Team.CHO, "馬", Team.HAN, "馬"),
            PieceType.ELEPHANT, Map.of(Team.CHO, "象", Team.HAN, "象"),
            PieceType.GUARD, Map.of(Team.CHO, "士", Team.HAN, "士"),
            PieceType.SOLDIER, Map.of(Team.CHO, "卒", Team.HAN, "兵")
    );

    private record Cell(int x, int y) {
    }

    public void printGame(GameDto game) {
        printScore(Team.HAN, game.hanScore());
        printBoard(game.pieces());
        printScore(Team.CHO, game.choScore());
        printCurrentTurn(game.turn());
    }

    private void printBoard(List<PieceDto> pieces) {
        Map<Cell, PieceDto> pieceMap = pieces.stream()
                .collect(Collectors.toMap(piece -> new Cell(piece.x(), piece.y()), piece -> piece));

        System.out.println("\n  Ａ Ｂ Ｃ Ｄ Ｅ Ｆ Ｇ Ｈ Ｉ");
        for (int y = 9; y >= 0; y--) {
            printRow(pieceMap, y);
        }
    }

    private void printRow(Map<Cell, PieceDto> pieceMap, int y) {
        System.out.print(y + " ");
        for (int x = 0; x <= 8; x++) {
            String symbol = Optional.ofNullable(pieceMap.get(new Cell(x, y)))
                    .map(this::getPieceSymbol)
                    .orElse(EMPTY_MARK);
            System.out.print(symbol + " ");
        }
        System.out.println();
    }

    private String getPieceSymbol(PieceDto piece) {
        return TEAM_COLORS.get(piece.team())
                + SYMBOLS.get(piece.pieceType()).get(piece.team())
                + RESET;
    }

    public void printError(Exception e) {
        System.out.println(RED + "[ERROR] " + e.getMessage() + RESET);
    }

    private void printScore(Team team, double score) {
        System.out.printf("\n%s: %.1f\n", TEAM_NAMES.get(team), score);
    }

    private void printCurrentTurn(Team turn) {
        System.out.printf(TEAM_COLORS.get(turn) + "\n▶ [" + TEAM_NAMES.get(turn) + "의 차례입니다]  " + RESET);
    }

    public void printGameResult(Team winner) {
        System.out.println(TEAM_COLORS.get(winner) + "\n🎉 " + TEAM_NAMES.get(winner) + "가 승리했습니다! 게임을 종료합니다." + RESET);
    }

    public void printSavedGames(Map<Long, String> games) {
        System.out.println("=== 💾 저장된 게임 목록 ===");
        if (games.isEmpty()) {
            System.out.println("저장된 게임이 없습니다.");
            System.out.println("==========================");
            return;
        }

        for (Map.Entry<Long, String> entry : games.entrySet()) {
            long id = entry.getKey();
            String turn = TEAM_NAMES.get(Team.valueOf(entry.getValue()));
            System.out.printf("- %d번 방 (현재 차례: %s)\n", id, turn);
        }
        System.out.println("==========================");
    }

    public void printGameSaved(long gameId) {
        System.out.println("게임을 중단합니다. 현재 상태는 " + gameId + "번 방에 안전하게 저장되어 있습니다.");
    }

    public void printStartNewGame() {
        System.out.println("새 게임을 시작합니다.");
    }

    public void printGameLoaded(long gameId) {
        System.out.println("\n[" + gameId + "번 방 게임을 성공적으로 불러왔습니다!]");
    }
}
