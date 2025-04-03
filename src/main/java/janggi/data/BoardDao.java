package janggi.data;

import static janggi.data.DataConfig.DATABASE;
import static janggi.data.DataConfig.OPTION;
import static janggi.data.DataConfig.PASSWORD;
import static janggi.data.DataConfig.SERVER;
import static janggi.data.DataConfig.USERNAME;

import janggi.data.dto.BoardDto;
import janggi.data.dto.PiecePointDto;
import janggi.domain.board.Dynasty;
import janggi.domain.board.Point;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class BoardDao {

    public void initializePiecePoints() {
        withConnection(connection -> {
            String query = "INSERT INTO board (piece_id, x, y) VALUES (?, ?, ?)";
            try (final var preparedStatement = connection.prepareStatement(query)) {
                for (BoardDto boardDto : InitialBoardData.INITIAL_BOARD) {
                    preparedStatement.setInt(1, boardDto.pieceId());
                    preparedStatement.setInt(2, boardDto.x());
                    preparedStatement.setInt(3, boardDto.y());
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
            }
        });
    }

    public void updatePiecePoints(Map<Point, Piece> boardPieces) {
        withConnection(connection -> {
            Map<Piece, Integer> pieceKeys = getPieceKeys();
            String query = "INSERT board SET x = ?, y = ?, piece_id = ?";
            try (final var preparedStatement = connection.prepareStatement(query)) {
                for (Entry<Point, Piece> entry : boardPieces.entrySet()) {
                    int pieceId = pieceKeys.get(entry.getValue());
                    Point point = entry.getKey();
                    preparedStatement.setInt(1, point.x());
                    preparedStatement.setInt(2, point.y());
                    preparedStatement.setInt(3, pieceId);
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
            }
        });
    }

    public Map<Point, Piece> getPiecePoints() {
        List<PiecePointDto> result = new ArrayList<>();
        withConnection(connection -> {
            String query = """
                        SELECT b.piece_type, b.dynasty, pt.x, pt.y
                        FROM board b
                        JOIN point pt ON p.piece_id = pt.piece_id
                    """;
            try (final var preparedStatement = connection.prepareStatement(query);
                 ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    PieceType pieceType = PieceType.valueOf(rs.getString("piece_type"));
                    Dynasty dynasty = Dynasty.valueOf(rs.getString("dynasty"));
                    int x = rs.getInt("x");
                    int y = rs.getInt("y");
                    result.add(new PiecePointDto(pieceType, dynasty, x, y));
                }
            }
        });
        return convertToMap(result);
    }

    private Map<Point, Piece> convertToMap(List<PiecePointDto> result) {
        Map<Point, Piece> pieces = new HashMap<>();
        for (PiecePointDto piecePointDto : result) {
            Point point = new Point(piecePointDto.x(), piecePointDto.y());
            Piece piece = piecePointDto.pieceType().from(piecePointDto.dynasty());
            pieces.put(point, piece);
        }
        return pieces;
    }

    public void deleteAll() {
        withConnection(connection -> {
            String query = "DELETE FROM board";
            try (final var preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.executeUpdate();
            }
        });
    }

    public Map<Piece, Integer> getPieceKeys () {
        Map<Piece, Integer> pieceKeys = new HashMap<>();
        String query = "SELECT piece_id, piece_type, dynasty FROM piece";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while(resultSet.next()) {
                PieceType type = PieceType.valueOf(resultSet.getString("piece_type"));
                Dynasty dynasty = Dynasty.valueOf(resultSet.getString("dynasty"));
                int key = resultSet.getInt("piece_id");
                pieceKeys.put(type.from(dynasty), key);
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        return pieceKeys;
    }

    private void withConnection(SqlConsumer<Connection> consumer) {
        try (Connection connection = getConnection()) {
            consumer.accept(connection);
        } catch (SQLException ex) {
            throw new RuntimeException("DB 연결 오류: ", ex);
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
    }
}