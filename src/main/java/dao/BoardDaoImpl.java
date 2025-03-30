package dao;

import domain.board.Point;
import domain.piece.Byeong;
import domain.piece.Cha;
import domain.piece.Ma;
import domain.piece.Piece;
import domain.piece.Po;
import domain.piece.Sa;
import domain.piece.Sang;
import domain.piece.Team;
import domain.piece.Wang;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class BoardDaoImpl implements BoardDao {

    private final Connection connection;

    public BoardDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Map<Point, Piece> load() {
        Map<Point, Piece> board = new HashMap<>();
        final String query = "SELECT * FROM board";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int row = resultSet.getInt("pointRow");
                int column = resultSet.getInt("pointColumn");
                String team = resultSet.getString("team");
                String pieceType = resultSet.getString("pieceType");
                board.put(createPoint(row, column), createPiece(team, pieceType));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return board;
    }

    private Point createPoint(final int row, final int column) {
        return Point.of(row, column);
    }

    private Piece createPiece(final String team, final String pieceType) {
        final Team pieceTeam = Team.valueOf(team);
        return switch (pieceType) {
            case "wang" -> new Wang(pieceTeam);
            case "sa" -> new Sa(pieceTeam);
            case "sang" -> new Sang(pieceTeam);
            case "ma" -> new Ma(pieceTeam);
            case "cha" -> new Cha(pieceTeam);
            case "po" -> new Po(pieceTeam);
            default -> new Byeong(pieceTeam);
        };
    }

    @Override
    public void save(final Point point, final Piece piece) {
        final String query = "INSERT INTO board VALUES(?, ?, ?, ?)";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, point.row());
            preparedStatement.setInt(2, point.column());
            preparedStatement.setString(3, piece.team().name());
            preparedStatement.setString(4, piece.type().name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeAll() {
        final String query = "DELETE FROM board";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
