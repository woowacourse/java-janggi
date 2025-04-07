package janggi.dao.piece;

import janggi.dao.BaseDao;
import janggi.domain.piece.Piece;
import janggi.domain.piece.position.Position;
import janggi.domain.players.Team;
import janggi.dto.PieceDto;
import janggi.dto.PieceMove;
import janggi.infrastructure.DefaultDatabaseProvider;
import janggi.infrastructure.DatabaseConnectionProvider;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TestPieceDaoImpl extends BaseDao implements PieceDao {

    private static final PieceDao pieceDao = new TestPieceDaoImpl(DefaultDatabaseProvider.getInstance());
    private static final int SOLDIER_START_INDEX = 4;

    private TestPieceDaoImpl(final DatabaseConnectionProvider databaseConnectionProvider) {
        super(databaseConnectionProvider);
    }

    public static PieceDao getPieceDao() {
        return pieceDao;
    }

    @Override
    public List<PieceDto> select(final Team givenTeam) {
        final var query = "SELECT * FROM piece WHERE team = ?";
        return findAll(query, (preparedStatement, connection) -> {
            final int givenTeamId = getTeamIdByName(connection, givenTeam.name());
            preparedStatement.setInt(1, givenTeamId);
        }, this::mapToPieceDto);
    }

    @Override
    public List<PieceDto> selectAll() {
        final var query = "SELECT * FROM piece";
        return findAll(query, this::mapToPieceDto);
    }

    @Override
    public void insert(final PieceDto pieceDto) {
        final var query = "INSERT INTO piece (y, x, team, piecetype) VALUES(?, ?, ?, ?)";
        executeUpdate(query, (preparedStatement, connection) -> {
            preparedStatement.setInt(1, pieceDto.y());
            preparedStatement.setInt(2, pieceDto.x());
            final int teamId = getTeamIdByName(connection, pieceDto.team().name());
            preparedStatement.setInt(3, teamId);
            final int pieceTypeId = getPieceTypeIdByName(pieceDto.piece());
            preparedStatement.setInt(SOLDIER_START_INDEX, pieceTypeId);
        });
    }

    @Override
    public void update(final PieceMove pieceMove) {
        final var query = "UPDATE piece SET y = ?, x= ? WHERE y = ? AND x=? AND team=? AND piecetype = ?";
        executeUpdate(query, (preparedStatement, connection) -> {
            final Position to = pieceMove.to();
            final Position from = pieceMove.from();
            preparedStatement.setInt(1, to.getY());
            preparedStatement.setInt(2, to.getX());
            preparedStatement.setInt(3, from.getY());
            preparedStatement.setInt(SOLDIER_START_INDEX, from.getX());

            final int teamId = getTeamIdByName(connection, pieceMove.team().name());
            preparedStatement.setInt(5, teamId);
            final int pieceTypeId = getPieceTypeIdByName(pieceMove.piece());
            preparedStatement.setInt(6, pieceTypeId);
        });
    }

    @Override
    public void delete(final PieceMove pieceMove) {
        if (pieceMove.isNotCaptured()) {
            return;
        }

        final var query = "DELETE FROM piece WHERE y = ? AND x = ? AND team = ? AND piecetype = ?";
        executeUpdate(query, (preparedStatement, connection) -> {
            final Position arrivalPosition = pieceMove.to();
            preparedStatement.setInt(1, arrivalPosition.getY());
            preparedStatement.setInt(2, arrivalPosition.getX());
            final Team opponentTeam = pieceMove.team().getOppositeTeam();
            preparedStatement.setInt(3, getTeamIdByName(connection, opponentTeam.name()));
            preparedStatement.setInt(SOLDIER_START_INDEX,
                    getPieceTypeIdByName(pieceMove.caughtPiece().get()));
        });
    }

    @Override
    public void deleteAll() {
        executeUpdate("DELETE FROM piece WHERE piece_id > 0");
    }

    private PieceDto mapToPieceDto(final ResultSet resultSet, final Connection connection) throws SQLException {
        final Team team = Team.from(getTeamNameById(connection, resultSet.getInt("team")));
        return new PieceDto(
                team,
                Piece.from(getPieceTypeById(resultSet.getInt("piecetype")), team),
                resultSet.getInt("y"),
                resultSet.getInt("x"));
    }

    private String getPieceTypeById(final int pieceTypeId) {
        final var query = "SELECT name FROM piecetype WHERE piecetype_id = ?";
        return findOne(query, (preparedStatement, connection) -> {
            preparedStatement.setInt(1, pieceTypeId);
        }, (resultSet, connection) -> resultSet.getString("name"));
    }

    private int getPieceTypeIdByName(final Piece piece) {
        final var query = "SELECT * FROM piecetype WHERE name = ?";
        final String pieceTypeName = makePieceTypeName(piece);
        return findOne(query, (preparedStatement, connection) -> {
            preparedStatement.setString(1, pieceTypeName);
        }, (resultSet, connection) -> resultSet.getInt("piecetype_id"));
    }

    private String makePieceTypeName(final Piece piece) {
        if (piece.isSoldier()) {
            return piece.name().substring(SOLDIER_START_INDEX);
        }
        return piece.name();
    }
}
