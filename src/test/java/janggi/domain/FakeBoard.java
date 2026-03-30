package janggi.domain;

import janggi.domain.board.BoardView;
import janggi.domain.piece.EmptyPosition;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Team;
import janggi.domain.vo.Position;

import java.util.HashMap;
import java.util.Map;

public class FakeBoard implements BoardView {
    private final Map<Position, Piece> map;

    public FakeBoard() {
        map = new HashMap<>();
    }

    public FakeBoard(Map<Position, Piece> map) {
        this.map = map;
    }

    public void place(Position position, Piece piece) {
        map.put(position, piece);
    }

    @Override
    public Piece findByPosition(Position position) {
        return map.getOrDefault(position, new EmptyPosition(Team.NONE));
    }

    @Override
    public boolean isEmptyPosition(Position position) {
        return findByPosition(position).pieceType() == PieceType.EMPTY;
    }
}
