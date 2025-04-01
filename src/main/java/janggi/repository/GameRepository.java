package janggi.repository;

import janggi.domain.Coordinate;
import janggi.domain.Piece;
import janggi.domain.PieceType;
import janggi.domain.Team;
import janggi.domain.game.PlayingTurn;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class GameRepository implements Repository {

    private static final String SERVER = "localhost:13306"; // MySQL 서버 주소
    private static final String DATABASE = "janggi"; // MySQL DATABASE 이름
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root"; //  MySQL 서버 아이디
    private static final String PASSWORD = "root"; // MySQL 서버 비밀번호

    public Connection getConnection() {
        // 드라이버 연결
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION,
                USERNAME, PASSWORD);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isConnectable() {
        try {
            if (getConnection() != null) {
                return true;
            }
        } catch (RuntimeException e) {
        }
        return false;
    }

    public void saveAll(final Collection<Piece> pieces) {
        String query = "INSERT INTO piece (x_coordinate, y_coordinate, piece_type, team) VALUES (?, ?, ?, ?)";

        try (final var connection = getConnection()) {
            final var preparedStatement = connection.prepareStatement(query);
            for (final var piece : pieces) {
                preparedStatement.setInt(1, piece.coordinate().x());
                preparedStatement.setInt(2, piece.coordinate().y());
                preparedStatement.setString(3, piece.pieceType().name());
                preparedStatement.setString(4, piece.team().name());
                preparedStatement.addBatch();
                preparedStatement.clearParameters();
            }

            preparedStatement.executeBatch();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Coordinate from, Coordinate to, final PlayingTurn playingTurn) {
        try (final var connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM piece WHERE x_coordinate = ? AND y_coordinate = ?"
            );
            preparedStatement.setInt(1, to.x());
            preparedStatement.setInt(2, to.y());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(
                "UPDATE piece SET x_coordinate = ?, y_coordinate = ? WHERE x_coordinate = ? AND y_coordinate = ?"
            );
            preparedStatement.setInt(1, to.x());
            preparedStatement.setInt(2, to.y());
            preparedStatement.setInt(3, from.x());
            preparedStatement.setInt(4, from.y());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(
                "UPDATE turn SET team = ?, round = ? WHERE team in ('HAN', 'CHO')"
            );
            preparedStatement.setString(1, playingTurn.currentTeam().name());
            preparedStatement.setInt(2, playingTurn.currentRound());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<Piece> allPieces() {
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

    public void clear() {
        try (final var connection = getConnection()) {
            connection.createStatement().executeUpdate("TRUNCATE TABLE piece");
            connection.createStatement().executeUpdate(
                "UPDATE turn SET team = 'CHO', round = 1 WHERE team in ('HAN', 'CHO')"
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public PlayingTurn getPlayingTurn() {
        String query = "SELECT team, round FROM turn";

        try (final var connection = getConnection()) {
            final var preparedStatement = connection.prepareStatement(query);
            final var resultSet = preparedStatement.executeQuery();
            resultSet.next();
            final var team = resultSet.getString("team");
            final var round = resultSet.getInt("round");
            return new PlayingTurn(Team.valueOf(team), round);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Piece createPiece(int x, int y, String pieceType, String teamName) {
        final var coordinate = new Coordinate(x, y);
        final var team = Team.valueOf(teamName);
        return switch (pieceType.toUpperCase()) {
            case "BYEONG" -> new Piece(team, coordinate, PieceType.BYEONG);
            case "JOL" -> new Piece(team, coordinate, PieceType.JOL);
            case "SA" -> new Piece(team, coordinate, PieceType.SA);
            case "GOONG" -> new Piece(team, coordinate, PieceType.GOONG);
            case "CHA" -> new Piece(team, coordinate, PieceType.CHA);
            case "MA" -> new Piece(team, coordinate, PieceType.MA);
            case "PO" -> new Piece(team, coordinate, PieceType.PO);
            case "SANG" -> new Piece(team, coordinate, PieceType.SANG);
            default -> throw new IllegalArgumentException("존재하지 않는 기물 타입입니다 : " + pieceType);
        };
    }
}
