package service;

import dao.converter.BoardConverter;
import dao.converter.GameRoomDto;
import dao.gameroom.GameRoomCommandDao;
import dao.gameroom.GameRoomQueryDao;
import dao.init.ConnectionGenerator;
import dao.piece.PieceCommandDao;
import dao.piece.PieceQueryDao;
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
import queue.Transaction;
import view.SangMaOrderCommand;

public class GameService {

    private final PieceQueryDao pieceQueryDao;
    private final PieceCommandDao pieceCommandDao;
    private final GameRoomQueryDao gameRoomQueryDao;
    private final GameRoomCommandDao gameRoomCommandDao;

    private final ConnectionGenerator connectionGenerator;
    private final MessageQueue messageQueue;

    private JanggiGame janggiGame;

    public GameService(PieceQueryDao pieceQueryDao, PieceCommandDao pieceCommandDao,
                       GameRoomQueryDao gameRoomQueryDao, GameRoomCommandDao gameRoomCommandDao,
                       ConnectionGenerator connectionGenerator) {
        this.pieceQueryDao = pieceQueryDao;
        this.pieceCommandDao = pieceCommandDao;
        this.gameRoomQueryDao = gameRoomQueryDao;
        this.gameRoomCommandDao = gameRoomCommandDao;
        this.connectionGenerator = connectionGenerator;
        this.messageQueue = new MessageQueue(connectionGenerator);
    }

    public void executeDelayedQueries() {
        messageQueue.executeTransactions();
    }

    public boolean existsGameRoom(final String gameRoomName) {
        try {
            final Optional<GameRoomDto> gameRoomDto = gameRoomQueryDao.findByName(getConnection(), gameRoomName);
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

        final Transaction transaction = new Transaction();
        gameRoomCommandDao.insert(transaction, new GameRoomDto(null, gameRoomName, firstTurn));
        pieceCommandDao.insertAll(transaction,
                BoardConverter.convertToPieceDtos(newGame.getPieceByPoint(), gameRoomName));
        messageQueue.addLast(transaction);

        executeDelayedQueries();
        janggiGame = newGame;
    }

    public void movePiece(final Point source, final Point destination) {
        JanggiGame game = getGameOrThrow();
        game.movePiece(source, destination);

        final String gameRoomName = game.getGameRoomName();
        final Team turn = janggiGame.currentTurn();

        final Transaction transaction = new Transaction();
        pieceCommandDao.deleteByGameRoomNameAndPoint(transaction, gameRoomName, destination);
        pieceCommandDao.updatePointByGameRoomNameAndPoint(transaction, gameRoomName, source, destination);
        gameRoomCommandDao.updateTurnByGameRoomName(transaction, gameRoomName, turn.inverse());
        messageQueue.addLast(transaction);

        executeDelayedQueries();
    }

    public void endGame() {
        JanggiGame game = getGameOrThrow();

        final Transaction transaction = new Transaction();
        gameRoomCommandDao.deleteByGameRoomName(transaction, game.getGameRoomName());
        messageQueue.addLast(transaction);

        executeDelayedQueries();
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

    public Map<Point, Piece> findPieceByPoint() {
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
        final Optional<GameRoomDto> maybeGameRoom = gameRoomQueryDao.findByName(getConnection(), name);
        if (maybeGameRoom.isEmpty()) {
            throw new IllegalStateException("[ERROR] '" + name + "' 방이 존재하지 않습니다.");
        }
        return maybeGameRoom.get();
    }

    private Board loadBoardByGameRoomName(final String gameRoomName) {
        return BoardConverter.convertToBoard(
                pieceQueryDao.findByGameRoomName(getConnection(), gameRoomName),
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
