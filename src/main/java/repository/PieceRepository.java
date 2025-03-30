package repository;

import domain.Team;
import domain.piece.Piece;
import domain.piece.Pieces;

public interface PieceRepository {
    void saveAll(final String gameName, final Team team, final Pieces pieces);

    void save(final String gameName, final Team team, final Piece piece);

    Pieces findAllByGameNameAndTeam(final String gameName, final Team team);
}
