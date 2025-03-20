package view;

import domain.Column;
import domain.Player;
import domain.Position;
import domain.Row;
import domain.TeamType;
import domain.piece.Piece;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OutputView {

    public void printBoard(List<Piece> pieces) {
        printColumnsInfo();
        printBoardInfo(pieces);
    }

    public void printWinner(Player player) {
        System.out.printf("%s가 승리했습니다!\n", player.getName());
    }

    private void printBoardInfo(List<Piece> pieces) {
        for (int row = Row.MAX_ROW; row >= Row.MIN_ROW; row--) {
            printRowInfo(row);
            for (int col = Column.MIN_COLUMN; col <= Column.MAX_COLUMN; col++) {
                Position position = Position.of(row, col);
                Optional<Piece> findPiece = getFindPiece(pieces, position);
                printPositionState(findPiece.orElse(null));
            }
            System.out.println();
        }
        System.out.println();
    }

    private void printRowInfo(int row) {
        System.out.printf("%2d  ", row);
    }

    private Optional<Piece> getFindPiece(List<Piece> pieces, Position position) {
        return pieces.stream().filter(piece -> piece.hasSamePosition(position))
                .findAny();
    }

    private void printPositionState(Piece findPiece) {
        if (findPiece == null) {
            System.out.print("- ");
            return;
        }
        System.out.print(getDescription(findPiece) + " ");
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
}
