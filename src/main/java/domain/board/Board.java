package domain.board;

import domain.CellSnapshot;
import domain.coordinate.Position;
import domain.piece.EmptyPiece;
import domain.piece.Piece;
import domain.piece.Pieces;

import java.util.List;
import java.util.Map;

public class Board implements Pieces {

    private static final BoardBounds BOUNDS = BoardBounds.JANGGI;

    private final Piece[][] board = new Piece[BOUNDS.colsize()][BOUNDS.rowSize()];

    public Board(Map<Position, Piece> initialize) {
        for (Position position : BOUNDS.allPositions()) {
            board[position.col()][position.row()] = initialize.getOrDefault(position, EmptyPiece.getInstance());
        }
    }

    public void move(Position start, Position destination) {
        board[destination.col()][destination.row()] = board[start.col()][start.row()];
        board[start.col()][start.row()] = EmptyPiece.getInstance();
    }

    @Override
    public Piece getPiece(Position position) {
        return board[position.col()][position.row()];
    }

    public List<Position> getPossibleMoves(Position start) {
        Piece piece = getPiece(start);
        return piece.getPossibleMoves(start, this);
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
