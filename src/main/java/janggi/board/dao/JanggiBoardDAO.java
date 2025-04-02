package janggi.board.dao;

import janggi.board.JanggiBoard;
import janggi.board.PieceFactory;
import janggi.board.dao.utils.DatabaseUtils;
import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.value.JanggiPosition;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JanggiBoardDAO {
    private static final String INSERT_PIECES = "INSERT INTO pieces(team_id, piece_type, x, y) values(?, ?, ?, ?)";
    private static final String DROP_PIECES_TABLE = "DROP TABLE IF EXISTS pieces";
    private static final String UPDATE_QUERY = "UPDATE pieces SET x = ?, y = ? WHERE team_id = ? AND x = ? AND y = ?";
    private static final String DELETE_QUERY = "DELETE FROM pieces WHERE x = ? AND y = ? AND team_id = ?";
    private static final String SELECT_QUERY = "SELECT piece_type, x, y FROM pieces WHERE team_id = ?";
    private static final int INDEX_ONE = 1;
    private static final int INDEX_TWO = 2;
    private static final int INDEX_THREE = 3;
    private static final int INDEX_FOUR = 4;
    private static final int INDEX_FIVE = 5;

    private final DatabaseUtils databaseUtils;

    public JanggiBoardDAO(DatabaseUtils databaseUtils) {
        this.databaseUtils = databaseUtils;
    }

    public void insertPieces(final JanggiBoard janggiBoard) {
        try (final PreparedStatement preparedStatement = databaseUtils.prepareStatement(INSERT_PIECES)) {
            List<Piece> choPieces = janggiBoard.getChoPieces();
            for (Piece piece : choPieces) {
                preparedStatement.setInt(INDEX_ONE, 1);
                preparedStatement.setString(INDEX_TWO, piece.getPieceType().getName());
                preparedStatement.setInt(INDEX_THREE, piece.getPosition().x());
                preparedStatement.setInt(INDEX_FOUR, piece.getPosition().y());

                preparedStatement.addBatch();
            }

            List<Piece> hanPieces = janggiBoard.getHanPieces();
            for (Piece piece : hanPieces) {
                preparedStatement.setInt(INDEX_ONE, 2);
                preparedStatement.setString(INDEX_TWO, piece.getPieceType().getName());
                preparedStatement.setInt(INDEX_THREE, piece.getPosition().x());
                preparedStatement.setInt(INDEX_FOUR, piece.getPosition().y());

                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        } catch (final SQLException e) {
            throw new IllegalArgumentException("[ERROR] Pieces 테이블에 값 추가 중 에러 발생했습니다.");
        }
    }

    public void dropTables() {
        try (final PreparedStatement preparedStatement = databaseUtils.prepareStatement(DROP_PIECES_TABLE)) {
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new IllegalArgumentException("[ERROR] Pieces 테이블 삭제 중 에러 발생했습니다.");
        }
    }

    public void updateRecords(JanggiPosition current, JanggiPosition destination, int teamId) {
        try (final PreparedStatement preparedStatement = databaseUtils.prepareStatement(UPDATE_QUERY)) {
            preparedStatement.setInt(INDEX_ONE, destination.x());
            preparedStatement.setInt(INDEX_TWO, destination.y());
            preparedStatement.setInt(INDEX_THREE, teamId);
            preparedStatement.setInt(INDEX_FOUR, current.x());
            preparedStatement.setInt(INDEX_FIVE, current.y());

            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new IllegalArgumentException("[ERROR] Pieces 테이블에 값 업데이트 중 에러 발생했습니다.");
        }
    }

    public void deleteRecords(JanggiPosition destination, int teamId) {
        try (final PreparedStatement deleteStmt = databaseUtils.prepareStatement(DELETE_QUERY)) {
            deleteStmt.setInt(INDEX_ONE, destination.x());
            deleteStmt.setInt(INDEX_TWO, destination.y());
            deleteStmt.setInt(INDEX_THREE, teamId);
            deleteStmt.executeUpdate();
        } catch (final SQLException e) {
            throw new IllegalArgumentException("[ERROR] 레코드 삭제 중 에러가 발생했습니다.");
        }
    }

    private List<Piece> selectRecords(int teamId) {
        List<Piece> pieces = new ArrayList<>();

        try (final PreparedStatement preparedStatement = databaseUtils.prepareStatement(SELECT_QUERY)) {
            preparedStatement.setInt(1, teamId);

            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String typeName = resultSet.getString("piece_type");
                    int x = resultSet.getInt("x");
                    int y = resultSet.getInt("y");
                    PieceType pieceType = PieceType.findEqualPieceType(typeName);
                    JanggiPosition position = new JanggiPosition(x, y);

                    Piece piece = createPiece(pieceType, position);
                    if (piece != null) {
                        pieces.add(piece);
                    }
                }
            }
        } catch (final SQLException e) {
            throw new IllegalArgumentException("[ERROR] Pieces 테이블 조회 중 에러 발생했습니다.");
        }
        return pieces;
    }

    private Piece createPiece(PieceType pieceType, JanggiPosition position) {
        return PieceFactory.createPiece(pieceType, position);
    }

    public List<Piece> selectChoRecords() {
        return selectRecords(1);
    }

    public List<Piece> selectHanRecords() {
        return selectRecords(2);
    }

}
