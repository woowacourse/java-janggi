package infra.repository;

import domain.board.Board;
import domain.board.BoardPosition;
import domain.board.repository.BoardRepository;
import domain.piece.Piece;
import java.util.HashMap;
import java.util.Map;

public class InMemoryBoardRepository implements BoardRepository {

    private Map<BoardPosition, Piece> pieces = new HashMap<>();

    @Override
    public void save(final Board board) {
        this.pieces = new HashMap<>(board.getPieces());
    }

    @Override
    public boolean hasAnyPiece() {
        return !pieces.isEmpty();
    }

    @Override
    public Board load() {
        return new Board(new HashMap<>(pieces));
    }

    @Override
    public void deleteAll() {
        pieces.clear();
    }
}
