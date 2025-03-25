package janggi.dao;

import janggi.domain.board.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.TeamColor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public final class BoardDao {

    private static final String SERVER = "localhost:13306"; // MySQL 서버 주소
    private static final String DATABASE = "chess"; // MySQL DATABASE 이름
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

    public void saveBoard(Map<Position, Piece> board) {
        for (Map.Entry<Position, Piece> entry : board.entrySet()) {
            Position position = entry.getKey();
            Piece piece = entry.getValue();
            savePiece(position, piece);
        }
    }

    public void savePiece(Position position, Piece piece) {
        final String query = "INSERT INTO Board (position_row, position_col, piece_type, piece_color) VALUES (?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, position.rowValue());
            preparedStatement.setInt(2, position.columnValue());
            preparedStatement.setString(3, piece.getType().name());
            preparedStatement.setString(4, piece.getColor().name());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error while saving piece to the database", e);
        }
    }

    public void updatePiecePosition(Position source, Position destination, PieceType pieceType, TeamColor teamColor) {
        String deleteSourceQuery = "DELETE FROM Board WHERE position_row = ? AND position_col = ?";
        String insertDestinationQuery = "INSERT INTO Board (position_row, position_col, piece_type, piece_color) VALUES (?, ?, ?, ?)";

        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);  // 트랜잭션 시작

            // 1. destination 위치에서 기물 삭제
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSourceQuery)) {
                preparedStatement.setInt(1, destination.rowValue());
                preparedStatement.setInt(2, destination.columnValue());
                preparedStatement.executeUpdate();
            }

            // 2. destination 위치에 새로운 기물 추가
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertDestinationQuery)) {
                preparedStatement.setInt(1, destination.rowValue());
                preparedStatement.setInt(2, destination.columnValue());
                preparedStatement.setString(3, pieceType.name());  // 예시: 기물 타입
                preparedStatement.setString(4, teamColor.name()); // 예시: 기물 색상
                preparedStatement.executeUpdate();
            }

            // 3. source 위치에서 기물 삭제
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSourceQuery)) {
                preparedStatement.setInt(1, source.rowValue());
                preparedStatement.setInt(2, source.columnValue());
                preparedStatement.executeUpdate();
            }

            connection.commit();  // 트랜잭션 커밋
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update piece positions", e);
        }
    }
}
