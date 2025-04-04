package dao;

import domain.Team;
import domain.piece.Piece;
import domain.piece.Pieces;
import domain.spatial.Position;

public interface PieceDao {
    void saveAll(final String gameName, final Team team, final Pieces pieces);

    void save(final String gameName, final Team team, final Piece piece);

    Pieces findAllByGameNameAndTeam(final String gameName, final Team team);

    void deleteByPosition(final String gameName, final Team team, final Position position);
}
