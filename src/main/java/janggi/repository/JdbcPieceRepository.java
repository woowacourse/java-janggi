package janggi.repository;

import janggi.database.dao.PieceDao;
import janggi.database.dao.PieceFactory;
import janggi.database.entity.PieceEntity;
import janggi.domain.Team;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.direction.Position;
import java.util.ArrayList;
import java.util.List;

public class JdbcPieceRepository implements PieceRepository {

    private final PieceDao pieceDao;

    public JdbcPieceRepository(final PieceDao pieceDao) {
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
            pieces.add(PieceFactory.createPiece(new Position(pieceEntity.getX(), pieceEntity.getY()),
                    Team.valueOf(pieceEntity.getTeam()), PieceType.valueOf(pieceEntity.getType())));
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
}
