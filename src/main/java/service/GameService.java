package service;

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
import java.util.Map;
import java.util.Optional;
import queue.MessageQueue;
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

    public void executeDelayedQueries() {
        MessageQueue.executeDelayedQueries(getConnection());
    }

    public boolean existsGameRoom(final String gameRoomName) {
        try {
            Optional<GameRoomEntity> gameRoomEntity = gameRoomDao.findByName(getConnection(), gameRoomName);
            return gameRoomEntity.isPresent();
        } catch (RuntimeException e) {
            return false;
        }
    }

    public void loadGame(String gameRoomName) {
        janggiGame = loadGameByGameRoomName(gameRoomName);
    }

    public void setNewGame(String gameRoomName,
                           BoardGenerator boardGenerator,
                           SangMaOrderCommand choSangMaOrderCommand,
                           SangMaOrderCommand hanSangMaOrderCommand) {
        final Team firstTurn = Team.CHO;
        JanggiGame newGame = new JanggiGame(
                gameRoomName,
                boardGenerator.generateInitialBoard(choSangMaOrderCommand, hanSangMaOrderCommand),
                firstTurn
        );
        gameRoomDao.insert(new GameRoomEntity(gameRoomName, firstTurn));
        pieceDao.insertAll(BoardConverter.convertToPieceEntities(newGame.getPieceByPoint(), gameRoomName));

        executeDelayedQueries();
        janggiGame = newGame;
    }

    public void movePiece(final Point source, final Point destination) {
        getGameOrThrow().movePiece(source, destination);

        final String gameRoomName = getGameOrThrow().getGameRoomName();
        final Team turn = janggiGame.currentTurn();
        pieceDao.deleteByGameRoomNameAndPoint(gameRoomName, destination);
        pieceDao.updatePointByGameRoomNameAndPoint(gameRoomName, source, destination);
        gameRoomDao.updateTurnByGameRoomName(gameRoomName, turn.inverse());

        executeDelayedQueries();
    }

    public void endGame() {
        gameRoomDao.deleteByGameRoomName(getConnection(), getGameOrThrow().getGameRoomName());

        executeDelayedQueries();
    }

    public boolean isPlaying() {
        return isGameLoaded() && janggiGame.isPlaying();
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

    private JanggiGame loadGameByGameRoomName(String gameRoomName) {
        GameRoomEntity gameRoom = findGameRoomEntityByName(gameRoomName);
        return new JanggiGame(gameRoom.name(), loadBoardByGameRoomName(gameRoomName), gameRoom.turn());
    }

    private GameRoomEntity findGameRoomEntityByName(String name) {
        Optional<GameRoomEntity> maybeGameRoom = gameRoomDao.findByName(getConnection(), name);
        if (maybeGameRoom.isEmpty()) {
            throw new IllegalStateException("[ERROR] '" + name + "' 방이 존재 하지 않습니다.");
        }
        return maybeGameRoom.get();
    }

    private Board loadBoardByGameRoomName(String gameRoomName) {
        return BoardConverter.convertToBoard(pieceDao.findByGameRoomName(getConnection(), gameRoomName));
    }

    private Connection getConnection() {
        try {
            return connectionFactory.createConnection();
        } catch (RuntimeException e) {
            throw new IllegalStateException("[ERROR] DB 연결에 실패했습니다. 게임이 저장/로드되지 않을 수 있습니다.");
        }
    }

    private JanggiGame getGameOrThrow() {
        if (janggiGame == null) {
            throw new IllegalStateException("[ERROR] 게임이 로드되지 않았습니다.");
        }
        return janggiGame;
    }

    private boolean isGameLoaded() {
        return janggiGame != null;
    }
}
