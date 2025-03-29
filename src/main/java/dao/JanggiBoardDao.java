package dao;

import domain.janggiPiece.JanggiChessPiece;
import domain.janggiPiece.Piece;
import domain.position.JanggiPosition;
import domain.type.JanggiTeam;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

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

    private int findPieceTypeId(Piece type) {
        final String query = "SELECT id FROM pieceType WHERE name = ?";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, type.name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
            throw new RuntimeException(type + " 타입이 존재하지 않습니다.");
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int findTeamId(JanggiTeam team) {
        final String query = "SELECT id FROM team WHERE name = ?";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, team.name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
            throw new RuntimeException(team + " 팀이 존재하지 않습니다.");
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JanggiChessPiece findByPosition(JanggiPosition position) {
        final var query = "SELECT * FROM piece WHERE position_row = ? AND position_col = ?";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, position.getRow());
            preparedStatement.setInt(2, position.getCol());
            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                final int typeId = resultSet.getInt("type_id");
                final int teamId = resultSet.getInt("team_id");
                return createPiece(typeId, teamId);
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    private JanggiChessPiece createPiece(final int typeId, final int teamId) {
        Piece pieceType = findPieceType(typeId);
        JanggiTeam team = findTeam(teamId);
        return pieceType.create(team);
    }

    private Piece findPieceType(final int typeId) {
        final String query = "SELECT name FROM pieceType WHERE id = ?";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, typeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String typeName = resultSet.getString("name");
                return getPieceType(typeName);
            }
            throw new RuntimeException(String.format("기물 타입의 id(%d)가 올바르지 않습니다.", typeId));
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Piece getPieceType(String name) {
        return Arrays.stream(Piece.values())
                .filter(type -> type.name.equals(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(name + "은 올바르지 않은 기물 이름입니다."));
    }

    private JanggiTeam findTeam(final int teamId) {
        final String query = "SELECT name FROM team WHERE id = ?";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, teamId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String teamName = resultSet.getString("name");
                return getTeam(teamName);
            }
            throw new RuntimeException(String.format("기물 타입의 id(%d)가 올바르지 않습니다.", teamId));
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private JanggiTeam getTeam(String teamName) {
        return Arrays.stream(JanggiTeam.values())
                .filter(team -> team.name.equals(teamName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(teamName + "은 올바르지 않은 팀 이름입니다."));
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
