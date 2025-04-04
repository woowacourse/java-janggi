package janggi.domain.board.dao;

import janggi.domain.board.JanggiBoard;
import janggi.domain.board.PieceFactory;
import janggi.database.utils.DatabaseUtils;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.value.JanggiPosition;
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
        databaseUtils.executeQuery(INSERT_PIECES, stmt -> {
            try {
                List<Piece> choPieces = janggiBoard.getChoPieces();
                for (Piece piece : choPieces) {
                    stmt.setInt(INSERT_TEAM_ID, 1);
                    stmt.setString(INSERT_PIECE_TYPE, piece.getPieceType().getName());
                    stmt.setInt(INSERT_X, piece.getPosition().x());
                    stmt.setInt(INSERT_Y, piece.getPosition().y());
                    stmt.addBatch();
                }
                stmt.executeBatch();
                return null;
            } catch (SQLException e) {
                throw new RuntimeException("[ERROR] PIECES 테이블 INSERT 초나라 쿼리 에러 발생");
            }
        });
        databaseUtils.executeQuery(INSERT_PIECES, stmt -> {
            try {
                List<Piece> hanPieces = janggiBoard.getHanPieces();
                for (Piece piece : hanPieces) {
                    stmt.setInt(INSERT_TEAM_ID, 2);
                    stmt.setString(INSERT_PIECE_TYPE, piece.getPieceType().getName());
                    stmt.setInt(INSERT_X, piece.getPosition().x());
                    stmt.setInt(INSERT_Y, piece.getPosition().y());
                    stmt.addBatch();
                }
                stmt.executeBatch();
                return null;
            } catch (SQLException e) {
                throw new RuntimeException("[ERROR] PIECES 테이블 INSERT 한나라 쿼리 에러 발생");
            }
        });
    }

    @Override
    public void dropTables() {
        databaseUtils.executeQuery(DROP_PIECES_TABLE, stmt -> {
           try {
               stmt.executeUpdate();
               return null;
           } catch (SQLException e) {
               throw new RuntimeException("[ERROR] PIECES 테이블 DROP 쿼리 에러 발생");
           }
        });
    }

    @Override
    public void updateRecords(JanggiPosition current, JanggiPosition destination, int teamId) {
        databaseUtils.executeQuery(UPDATE_QUERY, stmt -> {
           try {
               stmt.setInt(UPDATE_X, destination.x());
               stmt.setInt(UPDATE_Y, destination.y());
               stmt.setInt(UPDATE_TEAM_ID, teamId);
               stmt.setInt(BEFORE_X, current.x());
               stmt.setInt(BEFORE_Y, current.y());

               stmt.executeUpdate();
               return null;
           } catch (SQLException e) {
               throw new RuntimeException("[ERROR] PIECES 테이블 UPDATE 쿼리 에러 발생");
           }
        });
    }

    @Override
    public void deleteRecords(JanggiPosition destination, int teamId) {
        databaseUtils.executeQuery(DELETE_QUERY, stmt -> {
           try {
               stmt.setInt(CURRENT_X, destination.x());
               stmt.setInt(CURRENT_Y, destination.y());
               stmt.setInt(CURRENT_TEAM_ID, teamId);
               stmt.executeUpdate();
               return null;
           } catch (SQLException e) {
               throw new RuntimeException("[ERROR] PIECES 테이블 DELETE 쿼리 에러 발생");
           }
        });
    }

    private List<Piece> selectRecords(int teamId) {
        return databaseUtils.executeQuery(SELECT_QUERY, stmt -> {
            try (ResultSet resultSet = stmt.executeQuery()) {
                List<Piece> pieces = new ArrayList<>();
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
                return pieces;
            } catch (SQLException e) {
                throw new IllegalArgumentException("[ERROR] Pieces 테이블 조회 중 에러 발생했습니다.");
            }
        }, teamId);
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
