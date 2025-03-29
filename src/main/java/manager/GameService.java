package manager;

import dao.GameRoomDao;
import dao.GameRoomEntity;
import dao.PieceDao;
import dao.converter.BoardConverter;
import dao.init.ConnectionFactory;
import domain.JanggiGame;
import domain.board.Board;
import domain.board.BoardGenerator;
import domain.piece.Piece;
import domain.piece.character.Team;
import domain.point.Point;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;
import view.SangMaOrderCommand;

public class GameService {

    private final GameRoomDao gameRoomDao;
    private final PieceDao pieceDao;
    private final ConnectionFactory connectionFactory;

    private JanggiGame janggiGame;

    public GameService(GameRoomDao gameRoomDao, PieceDao pieceDao, ConnectionFactory connectionFactory) {
        this.gameRoomDao = gameRoomDao;
        this.pieceDao = pieceDao;
        this.connectionFactory = connectionFactory;
    }

    public boolean existsGameRoom(final String gameRoomName) {
        Optional<GameRoomEntity> gameRoomEntity = gameRoomDao.findByName(getConnection(), gameRoomName);
        return gameRoomEntity.isPresent();
    }

    public void loadGame(String gameRoomName) {
        janggiGame = loadGameByGameRoomName(gameRoomName);
    }

    private JanggiGame loadGameByGameRoomName(String gameRoomName) {
        GameRoomEntity gameRoom = loadGameRoomByName(gameRoomName);
        return new JanggiGame(gameRoom.name(), loadBoardByGameRoomName(gameRoomName), gameRoom.turn());
    }

    private GameRoomEntity loadGameRoomByName(String name) {
        Optional<GameRoomEntity> maybeGameRoom = gameRoomDao.findByName(getConnection(), name);
        if (maybeGameRoom.isEmpty()) {
            throw new IllegalStateException("해당 게임방이 존재 하지 않습니다.");
        }
        return maybeGameRoom.get();
    }

    private Board loadBoardByGameRoomName(String gameRoomName) {
        return BoardConverter.convertToBoard(pieceDao.findByGameRoomName(getConnection(), gameRoomName));
    }

    public void setNewGame(String gameRoomName,
                           BoardGenerator boardGenerator,
                           SangMaOrderCommand choSangMaOrderCommand,
                           SangMaOrderCommand hanSangMaOrderCommand) {
        try (final Connection connection = getConnection()) {
            connection.setAutoCommit(false);

            final Team firstTurn = Team.CHO;
            JanggiGame newGame = new JanggiGame(
                    gameRoomName,
                    boardGenerator.generateInitialBoard(choSangMaOrderCommand, hanSangMaOrderCommand),
                    firstTurn
            );
            gameRoomDao.insert(connection, new GameRoomEntity(gameRoomName, firstTurn));
            pieceDao.insertAll(connection,
                    BoardConverter.convertToPieceEntities(newGame.getPieceByPoint(), gameRoomName));

            connection.commit();
            janggiGame = newGame;
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 새로운 게임방 '" + gameRoomName + "' 생성에 실패했습니다.");
        }
    }

    public void movePiece(final Point source, final Point destination) {
        try (final Connection connection = getConnection()) {
            connection.setAutoCommit(false);

            final String gameRoomName = getGameOrThrow().getName();
            final Team turn = janggiGame.currentTurn();

            pieceDao.deleteByGameRoomNameAndPoint(connection, gameRoomName, destination);
            pieceDao.updatePointByGameRoomNameAndPoint(connection, gameRoomName, source, destination);
            gameRoomDao.updateTurnByGameRoomName(connection, gameRoomName, turn.inverse());

            connection.commit();
            getGameOrThrow().movePiece(source, destination);
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 기물의 위치를 옮기는 데 실패했습니다.");
        }
    }

    public void endGame() {
        gameRoomDao.deleteByGameRoomName(getConnection(), getGameOrThrow().getName());
    }

    private JanggiGame getGameOrThrow() {
        if (janggiGame == null) {
            throw new IllegalStateException("게임이 로드되지 않았습니다.");
        }
        return janggiGame;
    }

    private Connection getConnection() {
        return connectionFactory.createConnection();
    }

    public boolean isPlaying() {
        return getGameOrThrow().isPlaying();
    }

    public double calculateScore(Team team) {
        return getGameOrThrow().calculateScore(team);
    }

    public Team findWinTeam() {
        return getGameOrThrow().findWinTeam();
    }

    public Map<Point, Piece> findPieceByPoint() {
        return getGameOrThrow().getPieceByPoint();
    }

    public Team currentTurn() {
        return getGameOrThrow().currentTurn();
    }
}
