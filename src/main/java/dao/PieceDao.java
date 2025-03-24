package dao;

import domain.Coordinate;
import domain.Team;
import domain.piece.Piece;
import domain.piece.noPathPiece.Byeong;
import domain.piece.noPathPiece.Goong;
import domain.piece.noPathPiece.Jol;
import domain.piece.noPathPiece.Sa;
import domain.piece.pathPiece.Cha;
import domain.piece.pathPiece.Ma;
import domain.piece.pathPiece.Po;
import domain.piece.pathPiece.Sang;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public final class PieceDao {

    private static final String SERVER = "localhost:13306"; // MySQL 서버 주소
    private static final String DATABASE = "janggi"; // MySQL DATABASE 이름
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root"; //  MySQL 서버 아이디
    private static final String PASSWORD = "root"; // MySQL 서버 비밀번호

    public Connection getConnection() {
        // 드라이버 연결
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void save(final Piece piece) {
        String query = "INSERT INTO piece (x_coordinate, y_coordinate, piece_type, team) VALUES (?, ?, ?, ?)";

        try (final var connection = getConnection()) {
            final var preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, piece.getCoordinate().x());
            preparedStatement.setInt(2, piece.getCoordinate().y());
            preparedStatement.setString(3, piece.getClass().getSimpleName());
            preparedStatement.setString(4, piece.getTeam().name());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Coordinate from, Coordinate to) {
        String query = "UPDATE piece SET x_coordinate = ?, y_coordinate = ? WHERE x_coordinate = ? AND y_coordinate = ?";

        try (final var connection = getConnection()) {
            final var preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, to.x());
            preparedStatement.setInt(2, to.y());
            preparedStatement.setInt(3, from.x());
            preparedStatement.setInt(4, from.y());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<Piece> findAll() {
        String query = "SELECT * FROM piece";
        try (final var connection = getConnection()) {
            final var preparedStatement = connection.prepareStatement(query);
            final var resultSet = preparedStatement.executeQuery();
            final Set<Piece> pieces = new HashSet<>();
            while (resultSet.next()) {
                final var x = resultSet.getInt("x_coordinate");
                final var y = resultSet.getInt("y_coordinate");
                final var type = resultSet.getString("piece_type");
                final var team = resultSet.getString("team");
                pieces.add(createPiece(x, y, type, team));
            }

            return pieces;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteByCoordinate(final Coordinate coordinate) {
        try (final var connection = getConnection()) {
            final String query = "DELETE FROM piece WHERE x_coordinate = ? AND y_coordinate = ?";
            final var preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, coordinate.x());
            preparedStatement.setInt(2, coordinate.y());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void clear() {
        try (final var connection = getConnection()) {
            final String query = "TRUNCATE TABLE piece";
            final var preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setTurn(Team team) {
        String query = "UPDATE turn SET team = ? WHERE team in ('HAN', 'CHO')";

        try (final var connection = getConnection()) {
            final var preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, team.name());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Team getTurn() {
        String query = "SELECT team FROM turn";

        try (final var connection = getConnection()) {
            final var preparedStatement = connection.prepareStatement(query);
            final var resultSet = preparedStatement.executeQuery();
            resultSet.next();
            final var team = resultSet.getString("team");
            return Team.valueOf(team);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Piece createPiece(int x, int y, String pieceType, String teamName) {
        final var coordinate = new Coordinate(x, y);
        final var team = Team.valueOf(teamName);
        return switch (pieceType) {
            case "Byeong" -> new Byeong(coordinate);
            case "Jol" -> new Jol(coordinate);
            case "Sa" -> new Sa(team, coordinate);
            case "Goong" -> new Goong(team, coordinate);
            case "Cha" -> new Cha(team, coordinate);
            case "Ma" -> new Ma(team, coordinate);
            case "Po" -> new Po(team, coordinate);
            case "Sang" -> new Sang(team, coordinate);
            default -> throw new IllegalArgumentException("존재하지 않는 기물 타입입니다 : " + pieceType);
        };
    }
}
