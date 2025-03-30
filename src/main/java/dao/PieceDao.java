package dao;

import domain.direction.PieceDirections;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Position;
import domain.piece.category.Cannon;
import domain.piece.category.Chariot;
import domain.piece.category.Elephant;
import domain.piece.category.General;
import domain.piece.category.Guard;
import domain.piece.category.Horse;
import domain.piece.category.Soldier;
import domain.player.Player;
import domain.player.Team;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PieceDao {

    private static final String SERVER = "localhost:13306"; // MySQL 서버 주소
    private static final String DATABASE = "janggi"; // MySQL DATABASE 이름
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "user"; //  MySQL 서버 아이디
    private static final String PASSWORD = "password"; // MySQL 서버 비밀번호

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void save(final Piece piece, final Player player) {
        final var query = "INSERT INTO piece(`row`, `column`, team, piece_type) VALUES(?, ?, ?, ?)";
        try (final var connection = this.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, piece.getPosition().getRow());
            preparedStatement.setInt(2, piece.getPosition().getColumn());
            preparedStatement.setString(3, player.team().name());
            preparedStatement.setString(4, piece.getType().name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Piece> findAllByTeam(final Team team) {
        List<Piece> pieces = new ArrayList<>();

        final var query = "SELECT * FROM piece WHERE team = ?";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, team.name());
            final var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int row = resultSet.getInt("row");
                int column = resultSet.getInt("column");
                PieceType type = PieceType.getValue(resultSet.getString("piece_type"));

                pieces.add(getPiece(Position.of(row, column), type, team));
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }

        return pieces;
    }

    public void updatePosition(final Piece piece, final Position targetPosition) {
        final var query = "UPDATE piece SET `row` = ?, `column` = ? "
                + "WHERE `row` = ? AND `column` = ?";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, targetPosition.getRow());
            preparedStatement.setInt(2, targetPosition.getColumn());
            preparedStatement.setInt(3, piece.getPosition().getRow());
            preparedStatement.setInt(4, piece.getPosition().getColumn());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(final Piece piece, final Team team) {
        final var query = "DELETE FROM piece WHERE `row` = ? AND `column` = ? AND piece_type = ? AND team = ?";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, piece.getPosition().getRow());
            preparedStatement.setInt(2, piece.getPosition().getColumn());
            preparedStatement.setString(3, piece.getType().name());
            preparedStatement.setString(4, team.name());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void clear() {
        final var query = "DELETE FROM Piece";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Piece getPiece(final Position position, final PieceType type, final Team team) {
        if (type == PieceType.CANNON) {
            return new Cannon(position, PieceDirections.CANNON.get());
        }
        if (type == PieceType.CHARIOT) {
            return new Chariot(position, PieceDirections.CHARIOT.get());
        }
        if (type == PieceType.ELEPHANT) {
            return new Elephant(position, PieceDirections.ELEPHANT.get());
        }
        if (type == PieceType.GENERAL) {
            return new General(position, PieceDirections.GENERAL.get());
        }
        if (type == PieceType.GUARD) {
            return new Guard(position, PieceDirections.GUARD.get());
        }
        if (type == PieceType.HORSE) {
            return new Horse(position, PieceDirections.HORSE.get());
        }
        if (type == PieceType.SOLDIER) {
            if (team == Team.HAN) {
                return new Soldier(position, PieceDirections.HAN_SOLDIER.get());
            }
            return new Soldier(position, PieceDirections.CHO_SOLDIER.get());
        }
        throw new IllegalArgumentException("[ERROR] 데이터베이스에 존재하지 않는 기물입니다.");
    }
}
