package dao;

import direction.Point;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import piece.Cannon;
import piece.Chariot;
import piece.Elephant;
import piece.General;
import piece.Guard;
import piece.Horse;
import piece.Piece;
import piece.PieceType;
import piece.Soldier;
import team.Team;

public class PieceDao {

    public void savePiece(int playerId, PieceType type, int x, int y) {
        String sql = "INSERT INTO piece (player_id, type, x, y) VALUES (?, ?, ?, ?)";

        try (Connection connection = JdbcConnection.getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setInt(1, playerId);
            pstmt.setString(2, type.name());
            pstmt.setInt(3, x);
            pstmt.setInt(4, y);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 기물들을 저장하는데 실패했습니다.");
        }
    }

    public void updatePiece(Connection connection, int pieceId, int column, int row) {
        String sql = "UPDATE piece SET x = ?, y = ? WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, column);
            preparedStatement.setInt(2, row);
            preparedStatement.setInt(3, pieceId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 기물 정보 업데이트에 실패했습니다.");
        }
    }

    public List<Piece> findPieces(int playerId, Team team, Connection connection) {
        String sql = "SELECT type, x, y FROM Piece WHERE player_id = ?";

        List<Piece> pieces = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, playerId);

            ResultSet pieceResults = preparedStatement.executeQuery();
            while (pieceResults.next()) {
                int column = pieceResults.getInt("x");
                int row = pieceResults.getInt("y");
                PieceType pieceType  = PieceType.valueOf(pieceResults.getString("type"));
                Piece piece = pieceToTypePiece(pieceType, column, row, team);

                pieces.add(piece);
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 기물 정보들을 읽어올 수 없습니다.");
        }

        return pieces;
    }

    public void removeAll() {
        String sql = "DELETE FROM piece";

        try (Connection connection = JdbcConnection.getConnection()){
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 모든 기물을 삭제하는 데 실패했습니다.");
        }
    }

    public int getPieceIdByPoint(Connection connection, int column, int row) {
        String sql = "SELECT id FROM piece WHERE x = ? AND y = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setInt(1, column);
            pstmt.setInt(2, row);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 데이터베이스 조회 중 예외가 발생했습니다.");
        }

        throw new IllegalArgumentException("[ERROR] 위치에서 기물을 찾을 수 없습니다.");
    }

    private Piece pieceToTypePiece(PieceType pieceType, int column, int row, Team team) {
        if (pieceType.equals(PieceType.CANNON)) {
            return new Cannon(new Point(column, row));
        }

        if (pieceType.equals(PieceType.CHARIOT)) {
            return new Chariot(new Point(column, row));
        }

        if (pieceType.equals(PieceType.ELEPHANT)) {
            return new Elephant(new Point(column, row));
        }

        if (pieceType.equals(PieceType.GENERAL)) {
            return new General(new Point(column, row));
        }

        if (pieceType.equals(PieceType.GUARD)) {
            return new Guard(new Point(column, row));
        }

        if (pieceType.equals(PieceType.HORSE)) {
            return new Horse(new Point(column, row));
        }

        return new Soldier(new Point(column, row), team);
    }
}
