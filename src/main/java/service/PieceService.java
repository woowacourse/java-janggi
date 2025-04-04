package service;

import dao.PieceDao;
import domain.Team;
import domain.piece.Piece;
import domain.spatial.Position;

public class PieceService {

    private final PieceDao pieceRepository;

    public PieceService(final PieceDao pieceRepository) {
        this.pieceRepository = pieceRepository;
    }

    public void delete(final String gameName, final Team team, final Position position) {
        pieceRepository.deleteByPosition(gameName, team, position);
    }

    public void update(final String gameName, final Team team, final Position start, final Piece moved) {
        pieceRepository.deleteByPosition(gameName, team, start);
        pieceRepository.save(gameName, team, moved);
    }
}
