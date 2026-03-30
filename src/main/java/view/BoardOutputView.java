package view;

import domain.board.File;
import domain.board.Intersection;
import domain.board.Row;
import domain.piece.Piece;
import java.util.List;
import java.util.Map;
import java.util.Set;
import view.label.PieceLabel;

public class BoardOutputView {

    private static final String FILE_PREFIX = "＋";
    private static final String EMPTY_INTERSECTION = "＋";
    private static final String MOVABLE_EMPTY = "Ｘ";
    private static final String MOVABLE_CAPTURE = "※";

    public void printBoard(Map<Intersection, Piece> board) {
        printBoard(board, List.of());
    }

    public void printBoard(
            Map<Intersection, Piece> board,
            List<Intersection> movableIntersections
    ) {
        Set<Intersection> movable = Set.copyOf(movableIntersections);
        printFileLine();
        for (Row row = Row.minimumRow(); row.isInBoard(); row = row.nextRow()) {
            printRow(board, row, movable);
        }
    }

    private void printFileLine() {
        StringBuilder fileBuilder = new StringBuilder(FILE_PREFIX);
        for (File file = File.minimumFile(); file.isInBoard(); file = file.nextFile()) {
            fileBuilder.append(' ')
                    .append(toFullWidthDigit(file.value()));
        }
        System.out.println(fileBuilder.toString().stripTrailing());
    }

    private void printRow(
            Map<Intersection, Piece> board,
            Row currentRow,
            Set<Intersection> movableIntersections
    ) {
        StringBuilder rowBuilder = new StringBuilder();

        rowBuilder.append(rowDisplayLabel(currentRow));
        for (File file = File.minimumFile(); file.isInBoard(); file = file.nextFile()) {
            rowBuilder.append(' ');
            Intersection intersection = new Intersection(currentRow, file);
            Piece piece = board.get(intersection);
            rowBuilder.append(cellLabel(piece, intersection, movableIntersections));
        }

        System.out.println(rowBuilder);
    }

    private String cellLabel(
            Piece piece,
            Intersection intersection,
            Set<Intersection> movableIntersections
    ) {
        if (!movableIntersections.contains(intersection)) {
            return parsePieceToLabel(piece);
        }
        if (piece == null) {
            return MOVABLE_EMPTY;
        }
        return MOVABLE_CAPTURE;
    }

    private String rowDisplayLabel(Row row) {
        if (row.value() == 10) {
            return "０";
        }
        return String.valueOf((char) ('０' + row.value()));
    }

    private String toFullWidthDigit(int digit) {
        return String.valueOf((char) ('０' + digit));
    }

    private String parsePieceToLabel(Piece piece) {
        if (piece == null) {
            return EMPTY_INTERSECTION;
        }

        return PieceLabel.getLabel(piece);
    }
}
