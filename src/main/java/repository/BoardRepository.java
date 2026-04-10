package repository;

import domain.board.Board;
import domain.board.BoardAssembler;
import domain.piece.Piece;
import domain.piece.PieceSnapshot;
import domain.piece.PieceType;
import domain.piece.Side;
import domain.position.Position;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BoardRepository {
    private final DataSource dataSource;

    public BoardRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Optional<Board> findByGameId(Long gameId) {
        String sql = "SELECT * FROM board WHERE game_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, gameId);

            try (ResultSet resultSet = statement.executeQuery()) {
                List<PieceSnapshot> pieces = new ArrayList<>();

                while (resultSet.next()) {
                    PieceSnapshot pieceSnapshot = PieceSnapshot.of(
                            PieceType.valueOf(resultSet.getString("piece_type")),
                            Side.valueOf(resultSet.getString("side")),
                            resultSet.getInt("position_x"),
                            resultSet.getInt("position_y")
                    );
                    pieces.add(pieceSnapshot);
                }
                return Optional.of(BoardAssembler.assemble(pieces));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("게임에 해당하는 기물 조회에 실패했습니다.", e);
        }
    }

    public void savePlacementByGameId(Board board, Long gameId, Side side) {
        String sql = "INSERT INTO board (piece_type, side, position_x, position_y, game_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            for (Map.Entry<Position, Piece> entry : board.getState().entrySet()) {
                Position position = entry.getKey();
                Piece piece = entry.getValue();

                if (!piece.isSameSide(side)){
                    continue;
                }

                statement.setString(1, piece.getPieceType().name());
                statement.setString(2, piece.getSide().name());
                statement.setInt(3, position.getRow());
                statement.setInt(4, position.getColumn());
                statement.setLong(5, gameId);
                int affectedRows = statement.executeUpdate();

                if (affectedRows != 1) {
                    throw new IllegalStateException("기물 저장에 실패했습니다.");
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("상차림 저장에 실패했습니다.", e);
        }
    }

    public void updatePiecePositionByGameId(Position from, Position to, Long gameId) {
        String sql = "UPDATE board SET position_x = ?, position_y = ? WHERE game_id = ? AND position_x = ? AND position_y = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, to.getRow());
            statement.setInt(2, to.getColumn());
            statement.setLong(3, gameId);
            statement.setInt(4, from.getRow());
            statement.setInt(5, from.getColumn());
            int affectedRows = statement.executeUpdate();

            if (affectedRows != 1) {
                throw new IllegalStateException("수정된 기물 수가 올바르지 않습니다.");
            }
        } catch (SQLException e) {
            throw new IllegalStateException("기물 이동 수정에 실패했습니다.", e);
        }
    }

    public void deletePiecePositionByGameId(Position from, Long gameId) {
        String sql = "DELETE FROM board WHERE position_x = ? AND position_y = ? AND game_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, from.getRow());
            statement.setInt(2, from.getColumn());
            statement.setLong(3, gameId);
            int affectedRows = statement.executeUpdate();

            if (affectedRows != 1) {
                throw new IllegalStateException("삭제된 기물 수가 올바르지 않습니다.");
            }
        } catch (SQLException e) {
            throw new IllegalStateException("해당 기물 삭제를 실패하였습니다.", e);
        }
    }
}
