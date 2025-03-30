package domain.dao;

import domain.piece.Piece;
import domain.piece.Position;
import java.util.List;
import java.util.Optional;

public class MemoryPieceDao implements PieceDao {

    private final List<Piece> pieces;

    public MemoryPieceDao(List<Piece> pieces) {
        this.pieces = pieces;
    }

    @Override
    public void addAll(List<Piece> pieces) {
        this.pieces.addAll(pieces);
    }

    @Override
    public void add(Piece piece) {
        this.pieces.add(piece);
    }

    @Override
    public void removeByPosition(Position position) {
        pieces.removeAll(pieces.stream().filter(piece -> piece.getPosition().equals(position)).toList());
    }

    @Override
    public Optional<Piece> findByPosition(Position position) {
        return pieces.stream().filter(piece -> piece.getPosition().equals(position)).findFirst();
    }

    @Override
    public List<Piece> findAll() {
        return pieces;
    }

    @Override
    public void changePosition(Position position, Position newPosition) {
        findByPosition(position).get().changePosition(newPosition);
    }
}
