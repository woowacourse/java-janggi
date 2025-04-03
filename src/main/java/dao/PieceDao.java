package dao;

import piece.Country;
import piece.Piece;
import position.Position;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static db.ConnectionManager.getConnection;

public final class PieceDao {

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
            throw new IllegalArgumentException("기물을 저장하는 데에 오류가 생겼습니다.");
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
            throw new IllegalArgumentException("기물을 불러오는 데에 오류가 생겼습니다.");
        }

        return pieces;
    }

    public void clearPieces() {
        String sql = "DELETE FROM pieces";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new IllegalArgumentException("기물을 삭제하는 데에 오류가 생겼습니다.");
        }
    }
}
