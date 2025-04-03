package domain.dao;

import domain.piece.Piece;
import domain.piece.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MemoryPieceDao implements PieceDao {

    private final Map<Long, List<Piece>> pieces;

    public MemoryPieceDao(Map<Long, List<Piece>> pieces) {
        this.pieces = pieces;
    }

    @Override
    public void addAll(Long gameId, List<Piece> pieces) {
        if (this.pieces.get(gameId) == null) {
            return;
        }
        if (this.pieces.containsKey(gameId)) {
            this.pieces.get(gameId).addAll(pieces);
        }
        this.pieces.get(gameId).addAll(pieces);
    }

    @Override
    public void add(Long gameId, Piece piece) {
        if (pieces.get(gameId) == null) {
            return;
        }
        if (this.pieces.containsKey(gameId)) {
            this.pieces.get(gameId).add(piece);
        }
    }

    @Override
    public void removeByPosition(Long gameId, Position position) {
        if (pieces.get(gameId) == null) {
            return;
        }
        pieces.get(gameId)
                .removeAll(pieces.get(gameId).stream().filter(piece -> piece.getPosition().equals(position)).toList());
    }

    @Override
    public Optional<Piece> findByPosition(Long gameId, Position position) {
        if (pieces.get(gameId) == null) {
            return Optional.empty();
        }
        return pieces.get(gameId).stream().filter(piece -> piece.getPosition().equals(position)).findFirst();
    }

    @Override
    public List<Piece> findAll(Long gameId) {
        if (pieces.get(gameId) == null) {
            return new ArrayList<>();
        }
        return pieces.get(gameId);
    }

    @Override
    public void removeAll(Long gameId) {
        pieces.get(gameId).removeAll(pieces.get(gameId));
    }

    @Override
    public void changePosition(Long gameId, Position position, Position newPosition) {
        findByPosition(gameId, position).get().changePosition(newPosition);
    }
}
