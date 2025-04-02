package janggi.repository;

import janggi.database.dao.PieceDao;
import janggi.database.dao.PieceFactory;
import janggi.database.entity.PieceEntity;
import janggi.domain.piece.Piece;
import janggi.domain.piece.direction.Position;
import java.util.ArrayList;
import java.util.List;

public class DefaultPieceRepository implements PieceRepository {

    private final PieceDao pieceDao;

    public DefaultPieceRepository(final PieceDao pieceDao) {
        this.pieceDao = pieceDao;
    }

    @Override
    public Long add(final Piece piece) {
        return pieceDao.add(piece.getPieceType().name(), piece.getTeam().name(),
                piece.getPosition().x(),
                piece.getPosition().y());
    }

    @Override
    public void addAll(final List<Piece> pieces) {
        pieces.forEach(this::add);
    }

    @Override
    public List<Piece> findAll() {
        final List<PieceEntity> pieceEntities = pieceDao.findAll();
        final List<Piece> pieces = new ArrayList<>();
        for (final PieceEntity pieceEntity : pieceEntities) {
            pieces.add(PieceFactory.createPiece(pieceEntity));
        }
        return pieces;
    }

    @Override
    public void deleteAll() {
        pieceDao.deleteAll();
    }

    @Override
    public void delete(final Position position) {
        pieceDao.delete(position.x(), position.y());
    }

    @Override
    public boolean exist() {
        return pieceDao.exist();
    }
}
