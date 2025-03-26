package janggi.repository;

import janggi.service.PlayingTurn;
import janggi.dao.PieceDao;
import janggi.domain.Coordinate;
import janggi.domain.Piece;
import java.util.Set;

public class DockerRepository implements Repository {

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
    public void updateTurn(final PlayingTurn playingTurn) {
        pieceDao.setTurn(playingTurn.currentTeam());
        pieceDao.setRound(playingTurn.currentRound());
    }

    @Override
    public PlayingTurn getTurn() {
        final var team = pieceDao.getTurn();
        final var round = pieceDao.getRound();
        return new PlayingTurn(team, round);
    }
}
