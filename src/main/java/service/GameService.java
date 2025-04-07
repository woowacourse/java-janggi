package service;

import dao.converter.BoardConverter;
import dao.converter.GameRoomDto;
import dao.gameroom.GameRoomDao;
import dao.init.ConnectionGenerator;
import dao.piece.PieceDao;
import domain.JanggiGame;
import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.pathfinder.DefaultPathFinderFactory;
import domain.piece.Piece;
import domain.piece.character.Team;
import domain.point.Point;
import java.sql.Connection;
import java.util.Map;
import java.util.Optional;
import queue.MessageQueue;
import view.SangMaOrderCommand;

public class GameService {

    private final PieceDao pieceDao;
    private final GameRoomDao gameRoomDao;
    private final ConnectionGenerator connectionGenerator;
    private final MessageQueue messageQueue;

    private JanggiGame janggiGame;

    public GameService(PieceDao pieceDao,
                       GameRoomDao gameRoomDao,
                       ConnectionGenerator connectionGenerator) {
        this.pieceDao = pieceDao;
        this.gameRoomDao = gameRoomDao;
        this.connectionGenerator = connectionGenerator;
        this.messageQueue = new MessageQueue(connectionGenerator);
    }

    public boolean existsGameRoom(final String gameRoomName) {
        try {
            final Optional<GameRoomDto> gameRoomDto = gameRoomDao.findByName(getConnection(), gameRoomName);
            return gameRoomDto.isPresent();
        } catch (RuntimeException e) {
            throw new RuntimeException("[ERROR] DB로부터 게임방 정보를 불러오는데 실패했습니다.");
        }
    }

    public void loadGame(String gameRoomName) {
        janggiGame = loadGameByGameRoomName(gameRoomName);
    }

    public void createNewGame(String gameRoomName,
                              BoardFactory boardFactory,
                              SangMaOrderCommand choSangMaOrderCommand,
                              SangMaOrderCommand hanSangMaOrderCommand) {
        final Team firstTurn = Team.CHO;
        final JanggiGame newGame = new JanggiGame(
                gameRoomName,
                boardFactory.createBoard(
                        DefaultPathFinderFactory.getInstance(), choSangMaOrderCommand, hanSangMaOrderCommand
                ),
                firstTurn
        );

        messageQueue.flushQueueAndExecuteTransaction((Connection connection) -> {
            gameRoomDao.insert(connection,
                    new GameRoomDto(null, gameRoomName, firstTurn));
            pieceDao.insertAll(connection,
                    BoardConverter.convertToPieceDtos(newGame.getPieceByPoint(), gameRoomName));
        });

        janggiGame = newGame;
    }

    public void movePiece(final Point source, final Point destination) {
        JanggiGame game = getGameOrThrow();
        game.movePiece(source, destination);

        final String gameRoomName = game.getGameRoomName();
        final Team turn = janggiGame.currentTurn();

        messageQueue.flushQueueAndExecuteTransaction((Connection connection) -> {
            pieceDao.deleteByGameRoomNameAndPoint(connection, gameRoomName, destination);
            pieceDao.updatePointByGameRoomNameAndPoint(connection, gameRoomName, source, destination);
            gameRoomDao.updateTurnByGameRoomName(connection, gameRoomName, turn.inverse());
        });
    }

    public void endGame() {
        JanggiGame game = getGameOrThrow();

        messageQueue.flushQueueAndExecuteTransaction((Connection connection) -> {
            gameRoomDao.deleteByGameRoomName(connection, game.getGameRoomName());
        });
    }

    public boolean isPlaying() {
        return isGameLoaded() && janggiGame.isPlaying();
    }

    public double calculateScore(final Team team) {
        JanggiGame game = getGameOrThrow();
        return game.calculateScore(team);
    }

    public Team findWinTeam() {
        JanggiGame game = getGameOrThrow();
        return game.findWinTeam();
    }

    public Map<Point, Piece> getPieceByPoint() {
        JanggiGame game = getGameOrThrow();
        return game.getPieceByPoint();
    }

    public Team currentTurn() {
        JanggiGame game = getGameOrThrow();
        return game.currentTurn();
    }

    private JanggiGame loadGameByGameRoomName(final String gameRoomName) {
        final GameRoomDto gameRoom = findGameRoomDtoByName(gameRoomName);
        return new JanggiGame(gameRoom.name(), loadBoardByGameRoomName(gameRoomName), gameRoom.turn());
    }

    private GameRoomDto findGameRoomDtoByName(final String name) {
        final Optional<GameRoomDto> maybeGameRoom = gameRoomDao.findByName(getConnection(), name);
        if (maybeGameRoom.isEmpty()) {
            throw new IllegalStateException("[ERROR] '" + name + "' 방이 존재하지 않습니다.");
        }
        return maybeGameRoom.get();
    }

    private Board loadBoardByGameRoomName(final String gameRoomName) {
        return BoardConverter.convertToBoard(
                pieceDao.findByGameRoomName(getConnection(), gameRoomName),
                DefaultPathFinderFactory.getInstance().createPathFinder()
        );
    }

    private JanggiGame getGameOrThrow() {
        if (!isGameLoaded()) {
            throw new IllegalStateException("[ERROR] 게임이 로드되지 않았습니다.");
        }
        return janggiGame;
    }

    private boolean isGameLoaded() {
        return janggiGame != null;
    }

    private Connection getConnection() {
        try {
            return connectionGenerator.createConnection();
        } catch (RuntimeException e) {
            throw new IllegalStateException("[ERROR] DB 연결에 실패했습니다. 게임이 저장/로드되지 않을 수 있습니다.");
        }
    }
}