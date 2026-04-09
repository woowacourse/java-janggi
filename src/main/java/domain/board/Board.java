package domain.board;

import domain.CellSnapshot;
import domain.coordinate.Position;
import domain.piece.EmptyPiece;
import domain.piece.Piece;
import domain.piece.Pieces;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board implements Pieces {

    private static final BoardBounds BOUNDS = BoardBounds.JANGGI;

    private final Map<Position, Piece> board;

    public Board(Map<Position, Piece> initialize) {
        board = new HashMap<>();
        for (Position position : BOUNDS.allPositions()) {
            board.put(position, initialize.getOrDefault(position, EmptyPiece.getInstance()));
        }
    }

    public void move(Position start, Position destination) {
        board.put(destination, board.get(start));
        board.put(start, EmptyPiece.getInstance());
    }

    @Override
    public Piece getPiece(Position position) {
        return board.get(position);
    }

    public List<Position> getPossibleMoves(Position start) {
        Piece piece = getPiece(start);
        return piece.getPossibleMoves(start, this);
    }

    public CellSnapshot[][] toSnapshot() {
        CellSnapshot[][] snapshot = new CellSnapshot[BOUNDS.rowSize()][BOUNDS.colSize()];
        for (int i = 0; i < BOUNDS.rowSize(); i++) {
            for (int j = 0; j < BOUNDS.colSize(); j++) {
                Piece piece = board.get(new Position(i, j));
                snapshot[i][j] = new CellSnapshot(piece.getType(), piece.getSide());
            }
        }
        return snapshot;
    }

    public Map<Position, CellSnapshot> toSnapshotMap() {
        Map<Position, CellSnapshot> result = new HashMap<>();
        board.forEach((position, piece) -> result.put(position, new CellSnapshot(piece.getType(), piece.getSide())));
        return result;
    }
}
