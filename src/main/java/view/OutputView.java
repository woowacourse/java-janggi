package view;

import domain.TeamType;
import domain.piece.Piece;
import domain.player.Player;
import domain.position.Column;
import domain.position.Position;
import domain.position.Row;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OutputView {

    private static final String ERROR_PREFIX = "[ERROR] ";

    public void printBoard(Map<Position, Piece> pieces) {
        printColumnsInfo();
        printBoardInfo(pieces);
    }

    public void printWinner(Player player) {
        System.out.printf("%s가 승리했습니다!\n", player.getName());
    }

    public void printScoreWinner(Player winner, Map<String, Double> teamScore) {
        printWinner(winner);
        for (Entry<String, Double> entry : teamScore.entrySet()) {
            System.out.printf("%s: %.1f점\n", entry.getKey(), entry.getValue());
        }
    }

    private void printBoardInfo(Map<Position, Piece> pieces) {
        for (int row = Row.MAX_ROW; row >= Row.MIN_ROW; row--) {
            printRowInfo(row);
            for (int col = Column.MIN_COLUMN; col <= Column.MAX_COLUMN; col++) {
                Position position = Position.of(row, col);
                printPositionState(pieces, position);
            }
            System.out.println();
        }
        System.out.println();
    }

    private void printRowInfo(int row) {
        System.out.printf("%2d  ", row);
    }

    private void printPositionState(Map<Position, Piece> pieces, Position position) {
        if (!pieces.containsKey(position)) {
            System.out.print("- ");
            return;
        }
        System.out.print(getDescription(pieces.get(position)) + " ");
    }

    private void printColumnsInfo() {
        String columnInfo = IntStream.range(Column.MIN_COLUMN, Column.MAX_COLUMN + 1)
                .mapToObj(value -> (char) ('a' + value))
                .map(Objects::toString)
                .collect(Collectors.joining(" "));

        System.out.println("    " + columnInfo);
    }

    private String getDescription(Piece piece) {
        if (piece.isSameTeam(TeamType.CHO)) {
            return piece.getType().getDescription().toLowerCase();
        }
        return piece.getType().getDescription();
    }

    public void printError(String errorMessage) {
        System.out.println(ERROR_PREFIX + errorMessage);
    }
}
