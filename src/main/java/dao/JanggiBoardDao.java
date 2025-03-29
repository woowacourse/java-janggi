package dao;

import domain.janggiPiece.JanggiChessPiece;
import domain.position.JanggiPosition;
import domain.type.JanggiPieceType;
import domain.type.JanggiTeam;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JanggiBoardDao implements BoardDao {
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

    @Override
    public void addPiece(JanggiPosition position, JanggiChessPiece piece) {
        final String query = "INSERT INTO piece(position_row, position_col, type_id, team_id) VALUES(?, ?, ?, ?)";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, position.getRow());
            preparedStatement.setInt(2, position.getCol());
            preparedStatement.setInt(3, findPieceTypeId(piece.getChessPieceType()));
            preparedStatement.setInt(4, findTeamId(piece.getTeam()));
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int findPieceTypeId(JanggiPieceType type) {
        final String query = "SELECT id FROM pieceType WHERE name = ?";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, getTypeName(type));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
            throw new RuntimeException(type + " 타입이 존재하지 않습니다.");
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String getTypeName(JanggiPieceType type) {
        return switch (type) {
            case ELEPHANT -> "ELEPHANT";
            case CHARIOT -> "CHARIOT";
            case CANNON -> "CANNON";
            case HORSE -> "HORSE";
            case GUARD -> "GUARD";
            case PAWN -> "PAWN";
            case KING -> "KING";
        };
    }

    private int findTeamId(JanggiTeam team) {
        final String query = "SELECT id FROM team WHERE name = ?";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, getTeamName(team));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
            throw new RuntimeException(team + " 팀이 존재하지 않습니다.");
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String getTeamName(JanggiTeam team) {
        return switch (team) {
            case BLUE -> "BLUE";
            case RED -> "RED";
        };
    }

    @Override
    public JanggiChessPiece findByPosition(JanggiPosition position) {
        return null;
    }

    @Override
    public void save(JanggiPosition position, JanggiChessPiece piece) {

    }

    @Override
    public void delete(JanggiPosition position) {

    }

    @Override
    public void updatePosition(JanggiPosition before, JanggiPosition after) {

    }
}
