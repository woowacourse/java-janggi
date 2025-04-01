package dao;

import config.Connector;
import domain.chessPiece.Cannon;
import domain.chessPiece.Chariot;
import domain.chessPiece.ChessPiece;
import domain.chessPiece.Elephant;
import domain.chessPiece.Guard;
import domain.chessPiece.Horse;
import domain.chessPiece.King;
import domain.chessPiece.Pawn;
import domain.position.ChessPosition;
import domain.type.ChessTeam;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PieceDaoImpl implements PieceDao {

    private final Connector connector;

    public PieceDaoImpl(final Connector connector) {
        this.connector = connector;
    }

    @Override
    public void saveAll(final List<ChessPiece> pieces) {
        if (pieces == null || pieces.isEmpty()) {
            return;
        }
        String sql = "INSERT INTO piece (y_axis, x_axis, team, piece_type) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connector.getConnection().prepareStatement(sql)) {
            for (ChessPiece piece : pieces) {
                pstmt.setInt(1, piece.getPosition().row());
                pstmt.setInt(2, piece.getPosition().column());
                pstmt.setString(3, piece.getTeam().name());
                pstmt.setString(4, piece.getChessPieceType().name());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException("배치 삽입 중 오류 발생", e);
        }
    }

    @Override
    public List<ChessPiece> findAll() {
        final String sql = "SELECT * FROM piece";
        try (PreparedStatement pstmt = connector.getConnection().prepareStatement(sql)) {
            final ResultSet resultSet = pstmt.executeQuery();
            final List<ChessPiece> pieces = new ArrayList<>();
            while (resultSet.next()) {
                pieces.add(createPiece(resultSet));
            }
            return pieces;
        } catch (SQLException e) {
            throw new RuntimeException("조회 오류",e);
        }
    }

    @Override
    public void clear() {
        final String sql = "DELETE FROM piece";
        try (PreparedStatement pstmt = connector.getConnection().prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("테이블 클리어 오류",e);
        }
    }

    private ChessPiece createPiece(final ResultSet resultSet) throws SQLException {
        ChessPosition position = new ChessPosition(
                resultSet.getInt("y_axis"),
                resultSet.getInt("x_axis")
        );
        ChessTeam team = ChessTeam.valueOf(resultSet.getString("team"));

        String pieceType = resultSet.getString("piece_type");

        if ("CANNON".equals(pieceType)) {
            return new Cannon(team, position);
        }
        if ("ELEPHANT".equals(pieceType)) {
            return new Elephant(position, team);
        }
        if ("GUARD".equals(pieceType)) {
            return new Guard(position, team);
        }
        if ("HORSE".equals(pieceType)) {
            return new Horse(position, team);
        }
        if ("KING".equals(pieceType)) {
            return new King(position, team);
        }
        if ("PAWN".equals(pieceType)) {
            return new Pawn(position, team);
        }
        if ("CHARIOT".equals(pieceType)) {
            return new Chariot(team, position);
        }

        throw new IllegalArgumentException("유효하지 않은 타입입니다.");
    }

}
