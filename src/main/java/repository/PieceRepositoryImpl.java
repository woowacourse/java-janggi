package repository;

import domain.Team;
import domain.direction.Directions;
import domain.direction.PieceDirection;
import domain.piece.Piece;
import domain.piece.Pieces;
import domain.piece.category.Cannon;
import domain.piece.category.Chariot;
import domain.piece.category.Elephant;
import domain.piece.category.Guard;
import domain.piece.category.Horse;
import domain.piece.category.King;
import domain.piece.category.PieceCategory;
import domain.piece.category.Soldier;
import domain.spatial.Position;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PieceRepositoryImpl implements PieceRepository {

    private static final String SERVER = "localhost:13306"; // MySQL 서버 주소
    private static final String DATABASE = "janggi"; // MySQL DATABASE 이름
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root"; //  MySQL 서버 아이디
    private static final String PASSWORD = "root"; // MySQL 서버 비밀번호

    @Override
    public void saveAll(final String gameName, final Team team, final Pieces pieces) {
        for (final Piece piece : pieces.pieces()) {
            save(gameName, team, piece);
        }
    }

    @Override
    public void save(final String gameName, final Team team, final Piece piece) {
        final String query = "INSERT INTO piece (game_name, player_team, piece_type, row_value, column_value) VALUES (?, ?, ?, ?, ?)";
        try (final Connection connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, gameName);
            preparedStatement.setString(2, team.name());
            preparedStatement.setString(3, piece.getCategory().name());
            preparedStatement.setInt(4, piece.getPosition().row());
            preparedStatement.setInt(5, piece.getPosition().column());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Pieces findAllByGameNameAndTeam(final String gameName, final Team team) {
        final String query = "SELECT piece_type, row_value, column_value FROM piece WHERE game_name = ? AND player_team = ?";
        try (final Connection connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, gameName);
            preparedStatement.setString(2, team.name());
            final var resultSet = preparedStatement.executeQuery();
            return mapResultSetToPieces(resultSet, team);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteByPosition(final String gameName, final Team team, final Position position) {
        final String query = "DELETE FROM piece WHERE game_name = ? AND player_team = ? AND row_value = ? AND column_value = ?";
        try (final Connection connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, gameName);
            preparedStatement.setString(2, team.name());
            preparedStatement.setInt(3, position.row());
            preparedStatement.setInt(4, position.column());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Pieces mapResultSetToPieces(final ResultSet resultSet, final Team team) {
        try {
            final List<Piece> pieces = new ArrayList<>();
            while (resultSet.next()) {
                final PieceCategory category = PieceCategory.valueOf(resultSet.getString("piece_type"));
                final int rowValue = resultSet.getInt("row_value");
                final int columnValue = resultSet.getInt("column_value");

                final Piece piece = createPiece(category, rowValue, columnValue, team);
                pieces.add(piece);
            }
            return new Pieces(pieces);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Piece createPiece(final PieceCategory category, final int rowValue, final int columnValue,
                              final Team team) {
        Position position = new Position(rowValue, columnValue);
        Directions directions = getDirectionsForCategoryAndTeam(category, team);
        return switch (category) {
            case CANNON -> new Cannon(position, directions);
            case CHARIOT -> new Chariot(position, directions);
            case ELEPHANT -> new Elephant(position, directions);
            case GUARD -> new Guard(position, directions);
            case HORSE -> new Horse(position, directions);
            case KING -> new King(position, directions);
            case SOLDIER -> new Soldier(position, directions);
            default -> throw new IllegalArgumentException("잘못된 카테고리 : " + category);
        };
    }

    private Directions getDirectionsForCategoryAndTeam(final PieceCategory category, final Team team) {
        if (category == PieceCategory.SOLDIER) {
            return team == Team.HAN ? PieceDirection.HAN_SOLDIER.get() : PieceDirection.CHO_SOLDIER.get();
        }
        return PieceDirection.valueOf(category.name()).get();
    }

    private Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            return null;
        }
    }
}
