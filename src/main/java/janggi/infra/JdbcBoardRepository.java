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
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.h2.jdbcx.JdbcConnectionPool;

public class JdbcBoardRepository implements BoardRepository {

    private static final int POOL_SIZE = 10;

    private final JdbcConnectionPool connectionPool;
    private final GameRoomDao roomDao = new GameRoomDao();
    private final PiecesDao piecesDao = new PiecesDao();
    private final String url;
    private final String username;
    private final String password;

    public JdbcBoardRepository() {
        try {
            Properties properties = new Properties();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");
            properties.load(inputStream);
            url = properties.getProperty("db.url");
            username = properties.getProperty("db.username");
            password = properties.getProperty("db.password");
            connectionPool = JdbcConnectionPool.create(url, username, password);
            connectionPool.setMaxConnections(POOL_SIZE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long save(JanggiGame game) {
        return executeInTransaction(connection -> {
            long roomId = roomDao.save(GameRoomData.from(game), connection);
            List<List<Piece>> pieces = game.getBoardStatus();
            List<PieceData> data = new ArrayList<>();
            for (int i = 0; i < pieces.size(); i++) {
                addPieceData(pieces, i, data);
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
            return null;
        });
    }

    @Override
    public JanggiGame loadGame(Long gameRoomId) {
        return executeInTransaction(connection -> {
            GameRoomData roomData = roomDao.findRoomById(gameRoomId, connection);
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

    private Connection getConnection() throws SQLException {
        return connectionPool.getConnection();
    }

    private static void addPieceData(List<List<Piece>> pieces, int i, List<PieceData> data) {
        for (int j = 0; j < pieces.get(i).size(); j++) {
            Piece piece = pieces.get(i).get(j);
            if (piece == null) {
                continue;
            }
            data.add(new PieceData(piece.getType().name(),
                    piece.isSameTeam(Team.HAN) ? Team.HAN.name() : Team.CHO.name(), i, j));
        }
    }

    private <T> T executeInTransaction(TransactionCallback<T> action) {
        try (Connection connection = getConnection()) {
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
}
