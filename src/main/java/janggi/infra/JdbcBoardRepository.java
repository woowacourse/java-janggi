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
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class JdbcBoardRepository implements BoardRepository {

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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long save(JanggiGame game) {
        try (Connection connection = getConnection()) {
            Long roomId = roomDao.save(GameRoomData.from(game), connection);
            List<List<Piece>> pieces = game.getBoardStatus();
            List<PieceData> data = new ArrayList<>();
            for (int i = 0; i < pieces.size(); i++) {
                addPieceData(pieces, i, data);
            }
            piecesDao.save(roomId, data, connection);
            return roomId;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Long roomId, Point from, Point to, JanggiGame game) {
        try (Connection connection = getConnection()) {
            roomDao.update(roomId, GameRoomData.from(game), connection);
            piecesDao.delete(roomId, to.getRow(), to.getColumn(), connection);
            piecesDao.update(roomId, from.getRow(), from.getColumn(), to.getRow(), to.getColumn(), connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JanggiGame loadGame(Long gameRoomId) {
        try (Connection connection = getConnection()) {
            GameRoomData roomData = roomDao.findRoomById(gameRoomId, connection);
            List<PieceData> pieceDatas = piecesDao.findAllByRoomId(gameRoomId, connection);
            Map<Point, Piece> pieces = new LinkedHashMap<>();
            pieceDatas.forEach(pieceData -> {
                PieceType type = PieceType.valueOf(pieceData.pieceName());
                Team team = Team.valueOf(pieceData.teamName());
                Point point = Point.of(pieceData.col(), pieceData.row());
                Piece piece = PieceFactory.createPiece(team, type);
                pieces.put(point, piece);
            });
            Board board = new Board(pieces);
            return new JanggiGame(board, GameStatusFactory.create(Team.valueOf(roomData.currentTurn())));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
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
}
