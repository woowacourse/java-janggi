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
import piece.Pieces;
import piece.Soldier;
import team.Team;

public class PieceDao {

    public void addAllPieces(Pieces pieces) {
        String sql = "INSERT INTO piece (type, team, x, y) VALUES (?, ?, ?, ?)";

        try (Connection connection = JdbcConnection.getConnection();
            PreparedStatement pieceStmt = connection.prepareStatement(sql)) {
                for (Piece piece : pieces.getPieces()) {
                    pieceStmt.setString(1, piece.type().name());
                    pieceStmt.setString(2, piece.team().name());
                    pieceStmt.setInt(3, piece.column());
                    pieceStmt.setInt(4, piece.row());

                    pieceStmt.addBatch();
                    pieceStmt.clearParameters();
                }

                pieceStmt.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 기물들을 저장하는데 실패했습니다.");
        }
    }

    public List<Piece> findAllPieces() {
        String sql = "SELECT type, team, x, y FROM piece";

        List<Piece> pieces = new ArrayList<>();
        try (Connection connection = JdbcConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet pieceResults = preparedStatement.executeQuery()) {
                while (pieceResults.next()) {
                    PieceType pieceType = PieceType.valueOf(pieceResults.getString("type"));
                    Team team = Team.valueOf(pieceResults.getString("team"));
                    int column = pieceResults.getInt("x");
                    int row = pieceResults.getInt("y");

                    Piece piece = pieceToTypePiece(pieceType, team, column, row);

                    pieces.add(piece);
                }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 기물 정보들을 읽어올 수 없습니다.");
        }

        return pieces;
    }

    public void removeAll() {
        String sql = "DELETE FROM piece";

        try (Connection connection = JdbcConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 모든 기물을 삭제하는 데 실패했습니다.");
        }
    }

    public boolean existPiece() {
        String query = """ 
                        SELECT EXISTS (
                              SELECT 1 FROM piece
                            )
                """;

        try (Connection connection = JdbcConnection.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getBoolean(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 플레이어 목록을 읽어올 수 없습니다.");
        }

        return false;
    }

    private Piece pieceToTypePiece(PieceType pieceType, Team team, int column, int row) {
        if (pieceType.equals(PieceType.CANNON)) {
            return new Cannon(team, new Point(column, row));
        }

        if (pieceType.equals(PieceType.CHARIOT)) {
            return new Chariot(team, new Point(column, row));
        }

        if (pieceType.equals(PieceType.ELEPHANT)) {
            return new Elephant(team, new Point(column, row));
        }

        if (pieceType.equals(PieceType.GENERAL)) {
            return new General(team, new Point(column, row));
        }

        if (pieceType.equals(PieceType.GUARD)) {
            return new Guard(team, new Point(column, row));
        }

        if (pieceType.equals(PieceType.HORSE)) {
            return new Horse(team, new Point(column, row));
        }

        return new Soldier(team, new Point(column, row));
    }
}
