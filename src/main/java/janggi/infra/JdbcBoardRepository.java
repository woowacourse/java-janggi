package janggi.infra;

import janggi.domain.board.Board;
import janggi.domain.board.BoardRepository;
import janggi.domain.JanggiGame;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceFactory;
import janggi.domain.piece.PieceType;
import janggi.domain.point.Point;
import janggi.domain.status.GameStatusFactory;
import janggi.domain.status.Team;
import janggi.infra.dao.GameRoomDao;
import janggi.infra.dao.PiecesDao;
import janggi.infra.dto.GameRoomData;
import janggi.infra.dto.PieceData;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcBoardRepository implements BoardRepository {

    private final DataConnectionManager manager;
    private final GameRoomDao roomDao = new GameRoomDao();
    private final PiecesDao piecesDao = new PiecesDao();


    public JdbcBoardRepository(DataConnectionManager manager) {
        this.manager = manager;
    }

    @Override
    public long save(JanggiGame game) {
        return executeInTransaction(connection -> {
            long roomId = roomDao.save(GameRoomData.from(game), connection);
            Map<Point, Piece> pieces = game.getBoardStatus();
            List<PieceData> data = new ArrayList<>();
            for (Point point : pieces.keySet()) {
                Piece piece = pieces.get(point);
                data.add(new PieceData(piece.getType().name(), piece.getTeam().name(), point.getRow(), point.getColumn()));
            }
            piecesDao.save(roomId, data, connection);
            return roomId;
        });
    }

    @Override
    public void update(Long roomId, Point from, Point to, JanggiGame game) {
        executeInTransaction(connection -> {
            roomDao.update(roomId, GameRoomData.from(game), connection);
            piecesDao.delete(roomId, to.getRow(), to.getColumn(), connection);
            piecesDao.update(roomId, from.getRow(), from.getColumn(), to.getRow(), to.getColumn(), connection);
            return Optional.empty();
        });
    }

    @Override
    public JanggiGame loadGame(Long gameRoomId) {
        validateIsNull(gameRoomId);
        return executeInTransaction(connection -> {
            GameRoomData roomData = roomDao.findRoomById(gameRoomId, connection)
                    .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 게임방 입니다."));
            List<PieceData> pieceDatas = piecesDao.findAllByRoomId(gameRoomId, connection);
            Map<Point, Piece> pieces = new LinkedHashMap<>();
            pieceDatas.forEach(pieceData -> {
                PieceType type = PieceType.valueOf(pieceData.pieceName());
                Team team = Team.valueOf(pieceData.teamName());
                Point point = Point.of(pieceData.column(), pieceData.row());
                Piece piece = PieceFactory.createPiece(team, type);
                pieces.put(point, piece);
            });
            Board board = new Board(pieces);
            connection.commit();
            return new JanggiGame(board, GameStatusFactory.create(Team.valueOf(roomData.currentTurn())));
        });
    }

    private <T> T executeInTransaction(TransactionCallback<T> action) {
        try (Connection connection = manager.getConnection()) {
            return processTransaction(connection, action);
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] DB 커넥션 에러", e);
        }
    }

    private <T> T processTransaction(Connection connection, TransactionCallback<T> action) throws SQLException {
        try {
            connection.setAutoCommit(false);
            T result = action.doInTransaction(connection);
            connection.commit();
            return result;
        } catch (SQLException e) {
            connection.rollback();
            throw new RuntimeException("[ERROR] 게임 저장 중 트랜잭션 롤백됨", e);
        } finally {
            connection.setAutoCommit(true);
        }
    }

    private void validateIsNull(Long roomId) {
        if(roomId == null) {
            throw new IllegalArgumentException("[ERROR] 잘못된 게임방 ID 입력입니다.");
        }
    }
}
