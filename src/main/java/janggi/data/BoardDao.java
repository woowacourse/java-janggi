package janggi.data;

import janggi.data.dto.PiecePointDto;
import janggi.data.dto.BoardDto;
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

    private static final String SERVER = "localhost:13306"; // MySQL 서버 주소
    private static final String DATABASE = "chess"; // MySQL DATABASE 이름
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root"; //  MySQL 서버 아이디
    private static final String PASSWORD = "root"; // MySQL 서버 비밀번호

    private static final List<BoardDto> INITIAL_BOARD_DTOS = new ArrayList<>() {{
        add(new BoardDto(1, 2, 5));
        add(new BoardDto(2, 1, 1));
        add(new BoardDto(2, 1, 9));
        add(new BoardDto(3, 1, 4));
        add(new BoardDto(3, 1, 6));
        add(new BoardDto(4, 3, 2));
        add(new BoardDto(4, 3, 8));
        add(new BoardDto(5, 4, 1));
        add(new BoardDto(5, 4, 3));
        add(new BoardDto(5, 4, 5));
        add(new BoardDto(5, 4, 7));
        add(new BoardDto(5, 4, 9));
        add(new BoardDto(6, 9, 5));
        add(new BoardDto(7, 10, 1));
        add(new BoardDto(7, 10, 9));
        add(new BoardDto(8, 10, 4));
        add(new BoardDto(8, 10, 6));
        add(new BoardDto(9, 8, 2));
        add(new BoardDto(9, 8, 8));
        add(new BoardDto(10, 7, 1));
        add(new BoardDto(10, 7, 3));
        add(new BoardDto(10, 7, 5));
        add(new BoardDto(10, 7, 7));
        add(new BoardDto(10, 7, 9));
    }};

    private final PieceDao pieceDao;

    public BoardDao(PieceDao pieceDao) {
        this.pieceDao = pieceDao;
    }

    private Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            return null;
        }
    }

    private void run(String query) {
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            for (BoardDto boardDto : INITIAL_BOARD_DTOS) {
                preparedStatement.setInt(1, boardDto.pieceId());
                preparedStatement.setInt(2, boardDto.x());
                preparedStatement.setInt(3, boardDto.y());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void initializePiecePoints() {
        String query = "INSERT INTO board (piece_id, x, y) VALUES (?, ?, ?)";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            for (BoardDto boardDto : INITIAL_BOARD_DTOS) {
                preparedStatement.setInt(1, boardDto.pieceId());
                preparedStatement.setInt(2, boardDto.x());
                preparedStatement.setInt(3, boardDto.y());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updatePiecePoints(Map<Point, Piece> boardPieces) {
        Map<Piece, Integer> pieceKeys = pieceDao.getPieceKeys();
        String query = "INSERT board SET x = ?, y = ?, piece_id = ?";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            for (Entry<Point, Piece> entry : boardPieces.entrySet()) {
                int pieceId = pieceKeys.get(entry.getValue());
                Point point = entry.getKey();
                preparedStatement.setInt(1, point.x());
                preparedStatement.setInt(2, point.y());
                preparedStatement.setInt(3, pieceId);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<Point, Piece> getPiecePoints() {
        String query = """
                    SELECT b.piece_type, b.dynasty, pt.x, pt.y
                    FROM board b
                    JOIN point pt ON p.piece_id = pt.piece_id
                """;
        List<PiecePointDto> result = new ArrayList<>();
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query);
             ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                PieceType pieceType = PieceType.valueOf(rs.getString("piece_type"));
                Dynasty dynasty = Dynasty.valueOf(rs.getString("dynasty"));
                int x = rs.getInt("x");
                int y = rs.getInt("y");
                result.add(new PiecePointDto(pieceType, dynasty, x, y));
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
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
        String query = "DELETE FROM board";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}