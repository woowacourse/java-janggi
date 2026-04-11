package janggi.view;

import janggi.domain.Camp;
import janggi.view.dto.GameResult;
import janggi.view.dto.PieceStatus;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OutputView {
    private static final String RESET = "\u001B[0m";
    private static final String CHO_COLOR = "\u001B[1;34m";
    private static final String HAN_COLOR = "\u001B[1;31m";

    private static final int COL_SIZE = 9;

    private static final Map<Boolean, String> COLOR_MAP = Map.of(
            true, CHO_COLOR,
            false, HAN_COLOR
    );

    public void printError(String message) {
        System.out.println(message);
    }

    public void printEnterGameRoom(Long roomNumber) {
        System.out.println(roomNumber + "번방에 입장하셨습니다.");
    }

    public void printEndGame(Long roomNumber) {
        System.out.println(roomNumber + "번방은 종료되었습니다.");
    }

    public void printDeleteGame(Long roomNumber) {
        System.out.println(roomNumber + "번방을 삭제하였습니다.");
    }

    public void printCantDeleteGame(Long roomNumber) {
        System.out.println(roomNumber + "번방은 진행 중이여서 삭제할 수 없습니다.");
    }

    public void printGameResult(GameResult gameResult) {
        System.out.println();
        int choScore = gameResult.getChoScore();
        int hanScore = gameResult.getHanScore();
        String description = gameResult.getDescription();
        if (choScore >= 0 && hanScore >= 0) {
            System.out.printf("초나라 점수: %d, 한나라 점수: %d / %s!%n", choScore, hanScore, description);
            return;
        }
        System.out.printf("%s!%n", description);
    }

    public void printBoard(List<PieceStatus> piecesStatus, List<Integer> rows) {
        System.out.println();

        Map<String, PieceStatus> boardMap = piecesStatus.stream()
                .collect(Collectors.toMap(
                        p -> p.getRow() + "," + p.getColumn(),
                        p -> p
                ));

        String verticalSeparator = "\n   " + verticalRow() + "\n";

        String boardStr = rows.stream()
                .map(row -> row + "  " + intersectionRow(row, boardMap))
                .collect(Collectors.joining(verticalSeparator));

        System.out.println(boardStr);
        System.out.println("   ０　１　２　３　４　５　６　７　８");
        System.out.println();
    }

    private String intersectionRow(int row,  Map<String, PieceStatus> boardMap) {
        return IntStream.range(0, COL_SIZE)
                .mapToObj(col -> renderCell(row, col, boardMap))
                .collect(Collectors.joining("－"));
    }

    private String renderCell(int row, int col, Map<String, PieceStatus> boardMap) {
        PieceStatus pieceStatus = boardMap.get(row + "," + col);
        if (pieceStatus == null) {
            return "＋";
        }
        String color = COLOR_MAP.get(pieceStatus.isCho());
        return color + pieceStatus.getDisplayName() + RESET;
    }

    private String verticalRow() {
        return IntStream.range(0, COL_SIZE)
                .mapToObj(i -> "｜")
                .collect(Collectors.joining("　"));
    }
}
