package janggi.domain.movestorage;

import janggi.domain.BoardView;
import janggi.domain.Column;
import janggi.domain.Piece;
import janggi.domain.Position;
import janggi.domain.Row;
import java.util.List;
import java.util.stream.IntStream;

public class PoMoveStorage implements MoveStorage {

    @Override
    public boolean canMove(Position from, Position to, BoardView boardView) {
        if (isNotStraight(from, to)) {
            return false;
        }

        List<Piece> pathPieces = getPathPieces(from, to, boardView);

        if (isInvalidPath(pathPieces)) {
            return false;
        }

        return isValidTarget(to, boardView);
    }

    private boolean isNotStraight(Position from, Position to) {
        return from.getRowValue() != to.getRowValue() && from.getColumnValue() != to.getColumnValue();
    }

    private List<Piece> getPathPieces(Position from, Position to, BoardView boardView) {
        return getPathPositions(from, to).stream()
                .filter(boardView::hasPieceAt)
                .map(boardView::getPieceAt)
                .toList();
    }

    private List<Position> getPathPositions(Position from, Position to) {
        if (from.getRowValue() == to.getRowValue()) {
            int row = from.getRowValue();
            return IntStream.range(Math.min(from.getColumnValue(), to.getColumnValue()) + 1,
                            Math.max(from.getColumnValue(), to.getColumnValue()))
                    .mapToObj(column -> Position.of(Row.of(row), Column.of(column)))
                    .toList();
        }

        int column = from.getColumnValue();
        return IntStream.range(Math.min(from.getRowValue(), to.getRowValue()) + 1,
                        Math.max(from.getRowValue(), to.getRowValue()))
                .mapToObj(row -> Position.of(Row.of(row), Column.of(column)))
                .toList();
    }

    private boolean isInvalidPath(List<Piece> pathPieces) {
        if (pathPieces.size() != 1) {
            return true;
        }
        return isPo(pathPieces.get(0));
    }

    private boolean isValidTarget(Position to, BoardView boardView) {
        if (!boardView.hasPieceAt(to)) {
            return true;
        }

        return !isPo(boardView.getPieceAt(to));
    }

    private boolean isPo(Piece piece) {
        return piece.getName().equals("包");
    }
}
