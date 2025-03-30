package janggi.data;

import janggi.data.dto.PiecePointDto;
import janggi.data.dto.PointDto;
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

public final class PieceDao {

    private static final String SERVER = "localhost:13306"; // MySQL 서버 주소
    private static final String DATABASE = "chess"; // MySQL DATABASE 이름
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

    public Map<Piece, Integer> getPieceKeys () {
        String query = "SELECT piece_id, piece_type, dynasty FROM piece";
        Map<Piece, Integer> pieceKeys = new HashMap<>();
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
}