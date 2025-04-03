package fake;

import domain.board.Board;
import domain.board.BoardPosition;
import domain.board.repository.BoardRepository;
import domain.piece.Piece;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
    public Optional<Board> load() {
        if (pieces.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new Board(pieces));
    }

    @Override
    public void deleteAll() {
        pieces.clear();
    }
}
