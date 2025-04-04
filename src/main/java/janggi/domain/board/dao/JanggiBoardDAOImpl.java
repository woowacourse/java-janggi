package janggi.domain.board.dao;

import janggi.domain.board.JanggiBoard;
import janggi.domain.board.PieceFactory;
import janggi.database.utils.DatabaseUtils;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.value.JanggiPosition;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JanggiBoardDAOImpl implements JanggiBoardDAO{
    private static final String INSERT_PIECES = "INSERT INTO pieces(team_id, piece_type, x, y) values(?, ?, ?, ?)";
    private static final String DROP_PIECES_TABLE = "DROP TABLE IF EXISTS pieces";
    private static final String UPDATE_QUERY = "UPDATE pieces SET x = ?, y = ? WHERE team_id = ? AND x = ? AND y = ?";
    private static final String DELETE_QUERY = "DELETE FROM pieces WHERE x = ? AND y = ? AND team_id = ?";
    private static final String SELECT_QUERY = "SELECT piece_type, x, y FROM pieces WHERE team_id = ?";
    private static final int INSERT_TEAM_ID = 1;
    private static final int INSERT_PIECE_TYPE = 2;
    private static final int INSERT_X = 3;
    private static final int INSERT_Y = 4;
    private static final int UPDATE_X = 1;
    private static final int UPDATE_Y = 2;
    private static final int UPDATE_TEAM_ID = 3;
    private static final int BEFORE_X = 4;
    private static final int BEFORE_Y = 5;
    private static final int CURRENT_X = 1;
    private static final int CURRENT_Y = 2;
    private static final int CURRENT_TEAM_ID = 3;
    private static final int SELECT_TEAM_ID = 1;

    private final DatabaseUtils databaseUtils;

    public JanggiBoardDAOImpl(DatabaseUtils databaseUtils) {
        this.databaseUtils = databaseUtils;
    }

    @Override
    public void insertPieces(final JanggiBoard janggiBoard) {
        try (final PreparedStatement preparedStatement = databaseUtils.prepareStatement(INSERT_PIECES)) {
            List<Piece> choPieces = janggiBoard.getChoPieces();
            for (Piece piece : choPieces) {
                preparedStatement.setInt(INSERT_TEAM_ID, 1);
                preparedStatement.setString(INSERT_PIECE_TYPE, piece.getPieceType().getName());
                preparedStatement.setInt(INSERT_X, piece.getPosition().x());
                preparedStatement.setInt(INSERT_Y, piece.getPosition().y());

                preparedStatement.addBatch();
            }

            List<Piece> hanPieces = janggiBoard.getHanPieces();
            for (Piece piece : hanPieces) {
                preparedStatement.setInt(INSERT_TEAM_ID, 2);
                preparedStatement.setString(INSERT_PIECE_TYPE, piece.getPieceType().getName());
                preparedStatement.setInt(INSERT_X, piece.getPosition().x());
                preparedStatement.setInt(INSERT_Y, piece.getPosition().y());

                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        } catch (final SQLException e) {
            throw new IllegalArgumentException("[ERROR] Pieces 테이블에 값 추가 중 에러 발생했습니다.");
        }
    }

    @Override
    public void dropTables() {
        try (final PreparedStatement preparedStatement = databaseUtils.prepareStatement(DROP_PIECES_TABLE)) {
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new IllegalArgumentException("[ERROR] Pieces 테이블 삭제 중 에러 발생했습니다.");
        }
    }

    @Override
    public void updateRecords(JanggiPosition current, JanggiPosition destination, int teamId) {
        try (final PreparedStatement preparedStatement = databaseUtils.prepareStatement(UPDATE_QUERY)) {
            preparedStatement.setInt(UPDATE_X, destination.x());
            preparedStatement.setInt(UPDATE_Y, destination.y());
            preparedStatement.setInt(UPDATE_TEAM_ID, teamId);
            preparedStatement.setInt(BEFORE_X, current.x());
            preparedStatement.setInt(BEFORE_Y, current.y());

            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new IllegalArgumentException("[ERROR] Pieces 테이블에 값 업데이트 중 에러 발생했습니다.");
        }
    }

    @Override
    public void deleteRecords(JanggiPosition destination, int teamId) {
        try (final PreparedStatement deleteStmt = databaseUtils.prepareStatement(DELETE_QUERY)) {
            deleteStmt.setInt(CURRENT_X, destination.x());
            deleteStmt.setInt(CURRENT_Y, destination.y());
            deleteStmt.setInt(CURRENT_TEAM_ID, teamId);
            deleteStmt.executeUpdate();
        } catch (final SQLException e) {
            throw new IllegalArgumentException("[ERROR] 레코드 삭제 중 에러가 발생했습니다.");
        }
    }

    private List<Piece> selectRecords(int teamId) {
        List<Piece> pieces = new ArrayList<>();

        try (final PreparedStatement preparedStatement = databaseUtils.prepareStatement(SELECT_QUERY)) {
            preparedStatement.setInt(SELECT_TEAM_ID, teamId);

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

    @Override
    public List<Piece> selectChoRecords() {
        return selectRecords(1);
    }

    @Override
    public List<Piece> selectHanRecords() {
        return selectRecords(2);
    }

}
