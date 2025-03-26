package repository;

import dao.PieceDao;
import domain.Coordinate;
import domain.Piece;
import domain.Team;
import java.util.Set;

public class DockerRepository implements JanggiRepository {

    private final PieceDao pieceDao;

    public DockerRepository(final PieceDao pieceDao) {
        this.pieceDao = pieceDao;
    }

    @Override
    public void save(final Piece piece) {
        pieceDao.save(piece);
    }

    @Override
    public void update(final Coordinate from, final Coordinate to) {
        pieceDao.update(from, to);
    }

    @Override
    public Set<Piece> findAll() {
        return pieceDao.findAll();
    }

    @Override
    public void deleteByCoordinate(final Coordinate coordinate) {
        pieceDao.deleteByCoordinate(coordinate);
    }

    @Override
    public void clear() {
        pieceDao.clear();
    }

    @Override
    public void setTurn(final Team team) {
        pieceDao.setTurn(team);
    }

    @Override
    public Team getTurn() {
        return pieceDao.getTurn();
    }
}
