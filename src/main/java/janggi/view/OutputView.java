package janggi.view;

import janggi.domain.Camp;
import janggi.domain.Position;
import janggi.domain.piece.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
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

    private static final Map<Boolean, String> SOLDIER_SYMBOL = Map.of(
            true, "兵",
            false, "卒"
    );

    private static final Map<Class<?>, Function<Piece, String>> SYMBOL_MAP = Map.of(
            General.class, p -> "將",
            Advisor.class, p -> "士",
            Chariot.class, p -> "車",
            Cannon.class, p -> "包",
            Horse.class, p -> "馬",
            Elephant.class, p -> "象",
            Soldier.class, p -> SOLDIER_SYMBOL.get(p.isSameCamp(Camp.CHO))
    );

    public void printError(String message) {
        System.out.println(message);
    }

    public void printBoard(Map<Position, Piece> board, Camp currentCamp) {
        System.out.println();

        int rowStart = 9 - currentCamp.initRowPosition();
        int rowStep = -currentCamp.direction();

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
                .map(piece -> COLOR_MAP.get(piece.isSameCamp(Camp.CHO)) + SYMBOL_MAP.get(piece.getClass()).apply(piece) + RESET)
                .orElse("＋");
    }

    private String verticalRow() {
        return IntStream.range(0, COL_SIZE)
                .mapToObj(i -> "｜")
                .collect(Collectors.joining("　"));
    }
}
