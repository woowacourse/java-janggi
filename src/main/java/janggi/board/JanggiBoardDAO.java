package janggi.board;

import janggi.database.DBConnector;
import janggi.piece.Byeong;
import janggi.piece.Cha;
import janggi.piece.Gung;
import janggi.piece.Jol;
import janggi.piece.Ma;
import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.piece.Po;
import janggi.piece.Sa;
import janggi.piece.Sang;
import janggi.value.JanggiPosition;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class JanggiBoardDAO {
    private final DBConnector dbConnector;

    //ID는 auto_increment니까 넣어 줄 필요가 없군
    //외래키에 team을 넣을 때, team 테이블에 값이 있어야함!! 그래야 가능
    private static final String INSERT_PIECES = "INSERT INTO pieces(team_id, piece_type, x, y) values(?, ?, ?, ?)";
    private static final String DROP_PIECES_TABLE = "DROP TABLE IF EXISTS pieces";
    //UPDATE 사용시 where절을 생략하면 해당 테이블의 전체 행을 수정함
    private static final String UPDATE_QUERY = "UPDATE pieces SET x = ?, y = ? WHERE team_id = ? AND x = ? AND y = ?";
    private static final String DELETE_QUERY = "DELETE FROM pieces WHERE x = ? AND y = ? AND team_id = ?";
    public JanggiBoardDAO(DBConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    public void insertPieces(final JanggiBoard janggiBoard) {
        try (final PreparedStatement preparedStatement = dbConnector.getConnection().prepareStatement(INSERT_PIECES)) {
            List<Piece> choPieces = janggiBoard.getChoPieces();
            for (Piece piece : choPieces) {
                preparedStatement.setInt(1, 1); //1이 초나라
                preparedStatement.setString(2, piece.getPieceType().getName());
                preparedStatement.setInt(3, piece.getPosition().x());
                preparedStatement.setInt(4, piece.getPosition().y());

                preparedStatement.executeUpdate(); // 쿼리 실행!
            }

            List<Piece> hanPieces = janggiBoard.getHanPieces();
            for (Piece piece : hanPieces) {
                preparedStatement.setInt(1, 2); //2가 한나라
                preparedStatement.setString(2, piece.getPieceType().getName());
                preparedStatement.setInt(3, piece.getPosition().x());
                preparedStatement.setInt(4, piece.getPosition().y());

                preparedStatement.executeUpdate(); // 쿼리 실행!
            }

        } catch (final SQLException e) {
            throw new IllegalArgumentException("[ERROR] Pieces 테이블에 값 추가 중 에러 발생했습니다.");
        }
    }

    public void dropTables() {
        try (final PreparedStatement preparedStatement = dbConnector.getConnection().prepareStatement(DROP_PIECES_TABLE)) {
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new IllegalArgumentException("[ERROR] Pieces 테이블 삭제 중 에러 발생했습니다.");
        }
    }
    //TODO : 게임이 종료되기 전에 종료되었을 때 SELECT를 먼저 해와서 JanggiBoard 체인지 시켜주기.
    public void updateRecords(JanggiPosition current, JanggiPosition destination, int teamId) {
        try (final PreparedStatement preparedStatement = dbConnector.getConnection().prepareStatement(UPDATE_QUERY)) {
            preparedStatement.setInt(1, destination.x());
            preparedStatement.setInt(2, destination.y());
            preparedStatement.setInt(3, teamId);
            preparedStatement.setInt(4, current.x());
            preparedStatement.setInt(5, current.y());

            //한나라가 목적지로 이동을 했는데, 해당 목적지에 초나라 장기말이 있을 경우 해당 장기말은 사라지게됨.
            //그러면 DB에서 어떻게 처리를 해야할 것인가.
            //DB에서 한나라인데, 목적지의 x,y인 piece를 찾아서 삭제를 해야한다.

            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new IllegalArgumentException("[ERROR] Pieces 테이블에 값 업데이트 중 에러 발생했습니다.");
        }
    }

    public void deleteRecords(JanggiPosition destination, int teamId) {
        try (final PreparedStatement deleteStmt = dbConnector.getConnection().prepareStatement(DELETE_QUERY)) {
            deleteStmt.setInt(1, destination.x());
            deleteStmt.setInt(2, destination.y());
            deleteStmt.setInt(3, teamId);
            deleteStmt.executeUpdate();
        } catch (final SQLException e) {
            throw new IllegalArgumentException("[ERROR] 레코드 삭제 중 에러가 발생했습니다.");
        }
    }

    private List<Piece> selectRecords(int teamId) {
        List<Piece> pieces = new ArrayList<>();
        String query = "SELECT piece_type, x, y FROM pieces WHERE team_id = ?";

        try (final PreparedStatement preparedStatement = dbConnector.getConnection().prepareStatement(query)) {
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

    private static final Map<PieceType, Function<JanggiPosition, Piece>> PIECE_FACTORY = Map.of(
            PieceType.GUNG, Gung::from,
            PieceType.MA, Ma::from,
            PieceType.CHA, Cha::from,
            PieceType.PO, Po::from,
            PieceType.SANG, Sang::from,
            PieceType.SA, Sa::from,
            PieceType.JOL, Jol::from,
            PieceType.BYEONG, Byeong::from
    );

    private Piece createPiece(PieceType pieceType, JanggiPosition position) {
        return PIECE_FACTORY.getOrDefault(pieceType, pos -> null).apply(position);
    }

    public List<Piece> selectChoRecords() {
        return selectRecords(1);
    }

    public List<Piece> selectHanRecords() {
        return selectRecords(2);
    }

}
