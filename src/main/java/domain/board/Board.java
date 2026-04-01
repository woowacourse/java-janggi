package domain.board;

import domain.CellSnapshot;
import domain.coordinate.Path;
import domain.coordinate.Position;
import domain.piece.Cannon;
import domain.piece.EmptyPiece;
import domain.piece.Piece;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private static final BoardBounds BOUNDS = BoardBounds.JANGGI;

    private final Piece[][] board = new Piece[BOUNDS.colsize()][BOUNDS.rowSize()];

    public Board(Map<Position, Piece> initialize) {
        for (Position position : BOUNDS.allPositions()) {
            board[position.col()][position.row()] = initialize.getOrDefault(position, EmptyPiece.getInstance());
        }
    }

    public boolean isEmpty(Position position) {
        return !isInvalidRange(position) && getPiece(position).isEmpty();
    }

    public boolean isCannon(Position position) {
        return board[position.col()][position.row()] instanceof Cannon;
    }

    public void move(Position start, Position destination) {
        validateRange(start);
        validateRange(destination);
        board[destination.col()][destination.row()] = board[start.col()][start.row()];
        board[start.col()][start.row()] = EmptyPiece.getInstance();
    }

    public Piece getPiece(Position position) {
        validateRange(position);
        return board[position.col()][position.row()];
    }

    public boolean isInvalidRange(Position position) {
        return !BOUNDS.contains(position);
    }

    public void validateRange(Position position) {
        BOUNDS.validateContains(position);
    }

    public Map<Position, Piece> collectPieces(List<Path> paths) {
        Map<Position, Piece> result = new HashMap<>();
        for (Path path : paths) {
            for (Position position : path.getPositions()) {
                result.put(position, board[position.col()][position.row()]);
            }
        }
        return result;
    }

    public List<Position> getPossibleMoves(Position start) {
        Piece piece = board[start.col()][start.row()];
        List<Path> paths = piece.getPaths(start, BOUNDS);
        Map<Position, Piece> pathPieces = collectPieces(paths);
        return piece.getPossiblePositions(pathPieces, paths);
    }

    public CellSnapshot[][] toSnapshot() {
        CellSnapshot[][] snapshot = new CellSnapshot[BOUNDS.colsize()][BOUNDS.rowSize()];
        for (int i = 0; i < BOUNDS.colsize(); i++) {
            for (int j = 0; j < BOUNDS.rowSize(); j++) {
                Piece piece = board[i][j];
                snapshot[i][j] = new CellSnapshot(piece.getType(), piece.getSide());
            }
        }
        return snapshot;
    }
}
