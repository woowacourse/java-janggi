package service;

import dao.GameRoomDao;
import dao.GameRoomDto;
import dao.PieceDao;
import dao.converter.BoardConverter;
import dao.init.ConnectionGenerator;
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

    private final GameRoomDao gameRoomDao;
    private final PieceDao pieceDao;
    private final ConnectionGenerator connectionGenerator;

    private JanggiGame janggiGame;

    public GameService(GameRoomDao gameRoomDao, PieceDao pieceDao, ConnectionGenerator connectionGenerator) {
        this.gameRoomDao = gameRoomDao;
        this.pieceDao = pieceDao;
        this.connectionGenerator = connectionGenerator;
    }

    public void executeDelayedQueries() {
        MessageQueue.getInstance().executeDelayedQueries(getConnection());
    }

    public boolean existsGameRoom(final String gameRoomName) {
        try {
            final Optional<GameRoomDto> gameRoomDto = gameRoomDao.findByName(getConnection(), gameRoomName);
            return gameRoomDto.isPresent();
        } catch (RuntimeException e) {
            return false;
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
        gameRoomDao.insert(new GameRoomDto(gameRoomName, firstTurn));
        pieceDao.insertAll(BoardConverter.convertToPieceDtos(newGame.getPieceByPoint(), gameRoomName));

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
        gameRoomDao.deleteByGameRoomName(getGameOrThrow().getGameRoomName());

        executeDelayedQueries();
    }

    public boolean isPlaying() {
        return isGameLoaded() && janggiGame.isPlaying();
    }

    public double calculateScore(final Team team) {
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

    private JanggiGame loadGameByGameRoomName(final String gameRoomName) {
        final GameRoomDto gameRoom = findGameRoomDtoByName(gameRoomName);
        return new JanggiGame(gameRoom.name(), loadBoardByGameRoomName(gameRoomName), gameRoom.turn());
    }

    private GameRoomDto findGameRoomDtoByName(final String name) {
        final Optional<GameRoomDto> maybeGameRoom = gameRoomDao.findByName(getConnection(), name);
        if (maybeGameRoom.isEmpty()) {
            throw new IllegalStateException("[ERROR] '" + name + "' 방이 존재 하지 않습니다.");
        }
        return maybeGameRoom.get();
    }

    private Board loadBoardByGameRoomName(final String gameRoomName) {
        return BoardConverter.convertToBoard(
                pieceDao.findByGameRoomName(getConnection(), gameRoomName),
                DefaultPathFinderFactory.getInstance()
        );
    }

    private Connection getConnection() {
        try {
            return connectionGenerator.createConnection();
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
