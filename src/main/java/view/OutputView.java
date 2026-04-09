package view;

import domain.board.Board;
import domain.board.Intersection;
import domain.game.GameResult;
import domain.game.Side;
import domain.piece.Piece;
import domain.piece.PieceType;
import dto.GameMenu;
import dto.GameSummary;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class OutputView {

    private static final String ERROR_MESSAGE_PREFIX = "[ERROR] ";
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";

    private static final Map<PieceType, Map<Side, String>> SYMBOLS = Map.of(
            PieceType.GENERAL, Map.of(Side.HAN, "漢", Side.CHO, "楚"),
            PieceType.GUARD, Map.of(Side.HAN, "士", Side.CHO, "士"),
            PieceType.HORSE, Map.of(Side.HAN, "馬", Side.CHO, "馬"),
            PieceType.ELEPHANT, Map.of(Side.HAN, "象", Side.CHO, "象"),
            PieceType.CHARIOT, Map.of(Side.HAN, "車", Side.CHO, "車"),
            PieceType.CANNON, Map.of(Side.HAN, "包", Side.CHO, "包"),
            PieceType.SOLDIER, Map.of(Side.HAN, "兵", Side.CHO, "卒")
    );

    public void printError(String message) {
        System.out.println(ERROR_MESSAGE_PREFIX + message);
        System.out.println();
    }

    public void printWelcomeMessage() {
        System.out.println("안녕하세요. 루드비코의 장기 마을입니다!" + System.lineSeparator());
    }

    public void printExitMessage() {
        System.out.println("--- 프로그램을 종료합니다. ---" + System.lineSeparator());
    }

    public void printGameMenu() {
        System.out.println("선택 가능한 메뉴는 다음과 같습니다.");
        Arrays.stream(GameMenu.values())
                .map(GameMenu::toDisplayString)
                .forEach(System.out::println);
        System.out.println();
    }

    public void printGames(List<GameSummary> gameSummaries) {
        System.out.println("이전 게임을 조회합니다.");
        if (gameSummaries.isEmpty()) {
            System.out.println("--- 이전 게임이 존재하지 않습니다. ---");
            return;
        }

        System.out.println("------------- 저장된 게임 목록 -------------");
        System.out.printf("%-5s %-20s %-5s %n", "[ID]", "[STARTED_AT]", "[CURRENT_TURN]");
        gameSummaries.forEach(summary ->
                System.out.printf(" %-5d %-20s %-5s %n",
                        summary.id(), formatTime(summary.startedAt()), summary.currentTurn()
                )
        );
        System.out.println("----------------------------------------");
    }

    // TODO: 일단 해결은 완료. 좀 더 명확하게 바꿀 필요 있음. 지금 문제는 DB에서 받아 온 DATE의 ZONEID가 뭔지 자바 코드에서 결정하고 있다는 것임.
    private String formatTime(LocalDateTime ldt) {
        ZonedDateTime utcZdt = ldt.atZone(ZoneId.of("UTC"));
        ZonedDateTime asiaSeoulZdt = utcZdt.withZoneSameInstant(ZoneId.of("Asia/Seoul"));

        return asiaSeoulZdt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public void printGameStart() {
        System.out.println("장기 게임을 시작합니다." + System.lineSeparator());
    }

    public void printBoard(Board board) {
        printBoardWithMovable(board, Collections.emptyList());
    }

    public void printBoardWithMovable(Board board, List<Intersection> movableIntersections) {
        System.out.println("　 　　　１　　２　　３　　４　　５　　６　　７　　８　　９　");
        for (int row = 1; row <= 10; row++) {
            printRow(board, row, movableIntersections);
            System.out.println();
        }
        System.out.println();
    }

    public void printGameFinishedByCommand() {
        System.out.println("--- 게임을 종료합니다 ---" + System.lineSeparator());
    }

    public void printWinner(GameResult gameResult) {
        System.out.printf("--- 게임이 종료되었습니다 ---%n%s의 승리!%n", gameResult.winnerName());
        printWinningReason(gameResult);
    }

    private void printWinningReason(GameResult gameResult) {
        String winnerName = gameResult.winnerName();

        if (gameResult.generalCaptured()) {
            System.out.printf("%s가 상대방의 왕을 잡았습니다.%n", winnerName);
            return;
        }

        Map<Side, Double> totalPointBySide = gameResult.totalPointBySide();
        System.out.printf("%s의 점수가 상대방보다 높습니다.%n", winnerName);
        totalPointBySide.forEach((side, point) ->
                System.out.printf("%s의 점수: %.1f%n", side.name(), point)
        );
    }

    private void printRow(Board board, int row, List<Intersection> movableIntersections) {
        printRowHeader(row);
        for (int file = 1; file <= 9; file++) {
            Intersection current = new Intersection(row, file);
            boolean isMovable = movableIntersections.contains(current);
            printCell(board, current, isMovable);
        }
    }

    private void printRowHeader(int row) {
        if (row < 10) {
            System.out.print("　　" + getFullWidthNumber(row) + "　");
            return;
        }
        System.out.print("　１０　");
    }

    private void printCell(Board board, Intersection current, boolean isMovable) {
        if (board.isEmpty(current)) {
            printEmptyCell(isMovable);
            return;
        }
        printPieceCell(board, current, isMovable);
    }

    private void printEmptyCell(boolean isMovable) {
        if (isMovable) {
            System.out.print("［＊］");
            return;
        }
        System.out.print("　．　");
    }

    private void printPieceCell(Board board, Intersection current, boolean isMovable) {
        Piece piece = board.placedAt(current);
        String coloredSymbol = getColoredPieceSymbol(piece);
        if (isMovable) {
            System.out.print("［" + coloredSymbol + "］");
            return;
        }
        System.out.print("　" + coloredSymbol + "　");
    }

    private String getColoredPieceSymbol(Piece piece) {
        String color = GREEN;
        if (piece.isSameSide(Side.HAN)) {
            color = RED;
        }
        return color + getSymbol(piece) + RESET;
    }

    private String getSymbol(Piece piece) {
        return Arrays.stream(PieceType.values())
                .filter(piece::isSameType)
                .findFirst()
                .map(type -> getSideSymbol(type, piece))
                .orElse("？");
    }

    private String getSideSymbol(PieceType type, Piece piece) {
        return Arrays.stream(Side.values())
                .filter(piece::isSameSide)
                .findFirst()
                .map(side -> SYMBOLS.get(type).get(side))
                .orElse("？");
    }

    private String getFullWidthNumber(int number) {
        String[] fullWidth = {"０", "１", "２", "３", "４", "５", "６", "７", "８", "９"};
        return fullWidth[number];
    }
}
