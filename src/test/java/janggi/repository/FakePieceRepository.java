package janggi.repository;

import janggi.database.dao.PieceFactory;
import janggi.database.entity.PieceEntity;
import janggi.domain.piece.Piece;
import janggi.domain.piece.direction.Position;
import java.util.ArrayList;
import java.util.List;

class FakePieceRepository implements PieceRepository {

    private final List<PieceEntity> pieceEntities = new ArrayList<>();
    private Long autoIncrement = 1L;

    @Override
    public Long add(final Piece piece) {
        final PieceEntity newPieceEntity = new PieceEntity(autoIncrement++, piece.getPieceType().name(),
                piece.getTeam().name(),
                piece.getPosition().x(),
                piece.getPosition().y());
        pieceEntities.add(newPieceEntity);
        return newPieceEntity.getId();
    }

    @Override
    public void addAll(final List<Piece> pieces) {
        pieces.forEach(this::add);
    }

    @Override
    public List<Piece> findAll() {
        return pieceEntities.stream()
                .map(PieceFactory::createPiece)
                .toList();
    }

    @Override
    public void deleteAll() {
        pieceEntities.clear();
    }

    @Override
    public void delete(final Position position) {
        pieceEntities.removeIf(pieceEntity -> new Position(pieceEntity.getX(), pieceEntity.getY()).equals(position));
    }

    @Override
    public boolean exist() {
        return !pieceEntities.isEmpty();
    }
}
