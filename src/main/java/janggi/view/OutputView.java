package janggi.view;

import janggi.domain.Camp;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OutputView {
    private static final String RESET = "\u001B[0m";
    private static final String CHO_COLOR = "\u001B[1;34m";
    private static final String HAN_COLOR = "\u001B[1;31m";

    private static final Map<Boolean, String> COLOR_MAP = Map.of(
            true, CHO_COLOR,
            false, HAN_COLOR
    );

    private static final int COL_SIZE = 9;

    public void printBoard(Map<Position, Piece> board, Camp currentCamp) {
        System.out.println();

        int rowStart = currentCamp.isCho() ? 9 : 0;
        int rowStep = currentCamp.isCho() ? -1 : 1;

        List<Integer> rows = IntStream.iterate(rowStart, r -> r + rowStep)
                .limit(10)
                .boxed()
                .toList();

        String verticalSeparator = "\n   " + verticalRow() + "\n";

        String boardStr = rows.stream()
                .map(row -> row + "  " + intersectionRow(row, board))
                .collect(Collectors.joining(verticalSeparator));

        System.out.println(boardStr);
        System.out.println("   ０　１　２　３　４　５　６　７　８");
        System.out.println();
    }

    private String intersectionRow(int row, Map<Position, Piece> board) {
        return IntStream.range(0, COL_SIZE)
                .mapToObj(col -> renderCell(row, col, board))
                .collect(Collectors.joining("－"));
    }

    private String renderCell(int row, int col, Map<Position, Piece> board) {
        return Optional.ofNullable(board.get(Position.of(row, col)))
                .map(piece -> COLOR_MAP.get(piece.isSameCamp(Camp.CHO)) + piece.pieceName() + RESET)
                .orElse("＋");
    }

    private String verticalRow() {
        return IntStream.range(0, COL_SIZE)
                .mapToObj(i -> "｜")
                .collect(Collectors.joining("　"));
    }

    public void printGameResult(Camp camp) {
        if (camp.isCho()) {
            System.out.println("초나라가 승리하였습니다.");
            return;
        }
        System.out.println("한나라가 승리하였습니다.");
    }

    public void printErrorMessage(String message) {
        System.out.println(message);
    }


}
