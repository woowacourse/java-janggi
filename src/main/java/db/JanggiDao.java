package db;

import domain.GameState;
import domain.Team;
import domain.piece.PieceType;
import domain.position.Point;
import domain.position.PointValue;
import domain.position.Position;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JanggiDao {
    private static final String SERVER = "localhost:13307"; // MySQL 서버 주소
    private static final String DATABASE = "janggi"; // MySQL DATABASE 이름
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root"; //  MySQL 서버 아이디
    private static final String PASSWORD = "root"; // MySQL 서버 비밀번호

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void savePosition(final Position position) {
        Team team = Team.RED;
        if (position.isGreenTeam()) {
            team = Team.GREEN;
        }

        final var insertPointSql = "INSERT INTO point(x, y) VALUES(?, ?)";
        final var insertPieceSql = "INSERT INTO piece(pieceType, team, pointId) VALUES(?, ?, ?)";

        try (final var connection = getConnection();
             final var pointStmt = connection.prepareStatement(insertPointSql, Statement.RETURN_GENERATED_KEYS);
             final var pieceStmt = connection.prepareStatement(insertPieceSql)) {

            pointStmt.setInt(1, position.getPointValue().x());
            pointStmt.setInt(2, position.getPointValue().y());
            pointStmt.executeUpdate();

            try (final ResultSet generatedKeys = pointStmt.getGeneratedKeys()) {
                if (!generatedKeys.next()) {
                    throw new SQLException("point ID 생성 실패");
                }
                final int pointId = generatedKeys.getInt(1);

                pieceStmt.setString(1, position.getPieceType().name());
                pieceStmt.setString(2, team.name());
                pieceStmt.setInt(3, pointId);
                pieceStmt.executeUpdate();
            }

        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updatePoint(final PointValue fromPoint, final PointValue toPoint) {
        final var findPointSql = "SELECT id FROM point WHERE x = ? AND y = ?";
        final var updatePointSql = "update point set x = ?, y = ? where id = ?";

        try (final var connection = getConnection();
             final var findPointStmt = connection.prepareStatement(findPointSql);
             final var updatePointStmt = connection.prepareStatement(updatePointSql)) {

            findPointStmt.setInt(1, fromPoint.x());
            findPointStmt.setInt(2, fromPoint.y());
            final ResultSet resultSet = findPointStmt.executeQuery();

            if (resultSet.next()) {
                final int pointId = resultSet.getInt("id");
                updatePointStmt.setInt(1, toPoint.x());
                updatePointStmt.setInt(2, toPoint.y());
                updatePointStmt.setInt(3, pointId);
                updatePointStmt.executeUpdate();
            }
        } catch (final SQLException | IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    public void deletePosition(final PointValue pointValue) {

        final var findPointSql = "select id from point where x = ? and y = ?";
        final var deletePieceSql = "delete from piece where pointId = ?";
        final var deletePointSql = "delete from point where id = ?";

        try (final var connection = getConnection();
             final var findPointStmt = connection.prepareStatement(findPointSql);
             final var deletePieceStmt = connection.prepareStatement(deletePieceSql);
             final var deletePointStmt = connection.prepareStatement(deletePointSql)) {

            findPointStmt.setInt(1, pointValue.x());
            findPointStmt.setInt(2, pointValue.y());
            final ResultSet resultSet = findPointStmt.executeQuery();
            if (resultSet.next()) {
                final int pointId = resultSet.getInt("id");
                deletePieceStmt.setInt(1, pointId);
                deletePieceStmt.executeUpdate();
                deletePointStmt.setInt(1, pointId);
                deletePointStmt.executeUpdate();
            }
        } catch (final SQLException | IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    public void changeTurn(final Team team) {
        final var updateTurnSql = "update turn set team = ?";

        try (final var connection = getConnection();
             final var turnStmt = connection.prepareStatement(updateTurnSql)) {
            turnStmt.setString(1, team.name());
            turnStmt.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Team getTurn() {
        final var getTurnSql = "select team from turn";

        try (final var connection = getConnection();
             final var turnStmt = connection.prepareStatement(getTurnSql);
             final ResultSet resultSet = turnStmt.executeQuery()) {

            if (resultSet.next()) {
                final String team = resultSet.getString("team");
                return Team.valueOf(team.toUpperCase());
            } else {
                throw new SQLException("턴 정보가 존재하지 않습니다.");
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Position> getPositions() {
        final List<Position> positions = new ArrayList<>();
        final var positionSql = "select p.pieceType, p.team, pt.x, pt.y "
                + "from piece p "
                + "join point pt on p.pointId = pt.id";

        try (final var connection = getConnection();
             final var positionStmt = connection.prepareStatement(positionSql);
             final ResultSet rs = positionStmt.executeQuery()) {

            while (rs.next()) {
                final PieceType pieceType = PieceType.valueOf(rs.getString("pieceType"));
                final int x = rs.getInt("x");
                final int y = rs.getInt("y");
                final Team team = Team.valueOf(rs.getString("team").toUpperCase());
                final Position position = Position.newInstance(Point.newInstance(x, y),
                        PieceType.find(pieceType, team));
                positions.add(position);
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        return positions;
    }

    public GameState getGameState() {

        final var gameStateSql = "select state from game_state where id = 1";

        try (final var connection = getConnection();
             final var gameState = connection.prepareStatement(gameStateSql);
             final ResultSet resultSet = gameState.executeQuery()) {

            if (resultSet.next()) {
                final String state = resultSet.getString("state");
                return GameState.valueOf(state);
            }
        } catch (final SQLException | IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalStateException("데이터를 가져오는 과정에서 에러가 발생했습니다.");
    }

    public void updateGameState(final GameState gameState) {

        final var gameStateSql = "update game_state SET state = ? where id = 1";

        try (final var connection = getConnection();
             final var gameStateStmt = connection.prepareStatement(gameStateSql)) {
            gameStateStmt.setString(1, gameState.name());
            gameStateStmt.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAllPosition() {
        final var deletePieceSql = "delete from piece";
        final var deletePointSql = "delete from point";

        try (final var connection = getConnection();
             final var deletePieceStmt = connection.prepareStatement(deletePieceSql);
             final var deletePointStmt = connection.prepareStatement(deletePointSql)) {
            deletePieceStmt.executeUpdate();
            deletePointStmt.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
