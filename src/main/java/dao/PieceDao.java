package dao;

import piece.Country;
import piece.Piece;
import position.Position;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class PieceDao {

    private static final String SERVER = "localhost:13306";
    private static final String DATABASE = "janggi";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void savePieces(List<Piece> pieces) {
        for (Piece piece : pieces) {
            savePiece(piece);
        }
    }

    public void savePiece(Piece piece) {
        String sql = "INSERT INTO pieces (type, country, x, y) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, piece.getClass().getSimpleName());
            pstmt.setString(2, piece.getCountry().name());
            pstmt.setInt(3, piece.getPosition().x());
            pstmt.setInt(4, piece.getPosition().y());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Piece> loadPieces() {
        String sql = "SELECT * FROM pieces";
        List<Piece> pieces = new ArrayList<>();

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String type = rs.getString("type");
                String countryText = rs.getString("country");
                int x = rs.getInt("x");
                int y = rs.getInt("y");

                Position position = new Position(x, y);
                Country c = Country.valueOf(countryText);

                Piece piece = PieceFactory.create(type, c, position);
                pieces.add(piece);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pieces;
    }

    public void clearPieces() {
        String sql = "DELETE FROM pieces";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
