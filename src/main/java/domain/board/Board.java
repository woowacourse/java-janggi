package domain.board;

import domain.CellSnapshot;
import domain.coordinate.Position;
import domain.coordinate.Topology;
import domain.piece.EmptyPiece;
import domain.piece.Piece;
import domain.piece.Pieces;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board implements Pieces {

    private static final BoardBounds BOUNDS = BoardBounds.JANGGI;

    private final Map<Position, Piece> board;
    private final Topology topology;

    public Board(Map<Position, Piece> initialize, Topology topology) {
        this.topology = topology;
        board = new HashMap<>();
        for (Position position : BOUNDS.allPositions()) {
            board.put(position, initialize.getOrDefault(position, EmptyPiece.getInstance()));
        }
    }

    @Override
    public Topology getTopology() {
        return topology;
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

    public Map<Position, CellSnapshot> getBoardPiecesPosition() {
        Map<Position, CellSnapshot> result = new HashMap<>();
        board.entrySet().stream()
                .filter(entry -> !entry.getValue().isEmpty())
                .forEach(entry ->
                        result.put(entry.getKey(), new CellSnapshot(entry.getValue().getType(), entry.getValue().getSide())));
        return result;
    }
}
