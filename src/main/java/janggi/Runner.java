package janggi;

import janggi.config.ConnectionManager;
import janggi.config.SchemaInitializer;
import janggi.dao.BoardPieceDao;
import janggi.dao.GameRoomDao;
import janggi.dao.JdbcBoardPieceDao;
import janggi.dao.JdbcGameRoomDao;
import janggi.domain.Arrangement;
import janggi.domain.Game;
import janggi.domain.PalaceTopology;
import janggi.domain.Position;
import janggi.dto.TurnDto;
import janggi.factory.PieceFactory;
import janggi.mapper.GamePersistenceMapper;
import janggi.repository.GameRepository;
import janggi.repository.JdbcGameRepository;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Runner {
    private static final long CREATE_NEW_GAME = -1L;

    private final ConnectionManager connectionManager;

    public Runner(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void run() {
        initializeSchema();
        GameRepository gameRepository = createGameRepository();
        startGame(gameRepository);
    }

    private void startGame(GameRepository gameRepository) {
        while (true) {
            try {
                long roomId = InputView.askRoomId();
                if (roomId == CREATE_NEW_GAME) {
                    runNewGame(gameRepository);
                    return;
                }

                Game game = gameRepository.enterGame(roomId);
                turnGame(gameRepository, roomId, game);
                return;
            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void runNewGame(GameRepository gameRepository) {
        String hanArrangementInput = InputView.askHanArrangement();
        Arrangement hanArrangement = Arrangement.from(hanArrangementInput);

        String choArrangementInput = InputView.askChoArrangement();
        Arrangement choArrangement = Arrangement.from(choArrangementInput);

        Game game = new Game(choArrangement, hanArrangement);
        long roomId = gameRepository.saveNewGame(game);
        OutputView.printNewGameRoom(roomId);

        turnGame(gameRepository, roomId, game);
    }

    private GameRepository createGameRepository() {
        PieceFactory pieceFactory = new PieceFactory(PalaceTopology.from());
        GamePersistenceMapper gamePersistenceMapper = new GamePersistenceMapper(pieceFactory);
        GameRoomDao gameRoomDao = new JdbcGameRoomDao();
        BoardPieceDao boardPieceDao = new JdbcBoardPieceDao();

        return new JdbcGameRepository(
                connectionManager,
                gameRoomDao,
                boardPieceDao,
                gamePersistenceMapper
        );
    }

    private void initializeSchema() {
        SchemaInitializer schemaInitializer = new SchemaInitializer(connectionManager);
        schemaInitializer.init();
    }

    private void turnGame(GameRepository gameRepository, long roomId, Game game) {
        while (!game.isFinished()) {
            playTurnGame(gameRepository, roomId, game);
        }
        printWinner(game);
    }

    private void playTurnGame(GameRepository gameRepository, long roomId, Game game) {
        try {
            printCurrentStatus(game, roomId);
            Position startPosition = Position.from(InputView.askStartPosition());
            Position endPosition = Position.from(InputView.askEndPosition());

            game.move(startPosition, endPosition);

            gameRepository.saveMove(
                    roomId,
                    game,
                    new TurnDto(
                            startPosition.x(),
                            startPosition.y(),
                            endPosition.x(),
                            endPosition.y()
                    )
            );
        } catch (IllegalArgumentException e) {
            OutputView.printErrorMessage(e.getMessage());
        } catch (IllegalStateException e) {
            OutputView.printErrorMessage(e.getMessage());
            throw e;
        }
    }

    private void printCurrentStatus(Game game, long roomId) {
        OutputView.printGameRoomNumber(roomId);
        OutputView.printBoard(game.getCurrentBoard());
        OutputView.printTurn(game.getCurrentSide());
        OutputView.printScoreStatus(game.getCurrentScoreStatus());
    }

    private void printWinner(Game game) {
        OutputView.printBoard(game.getCurrentBoard());
        OutputView.printWinner(game.getCurrentSide());
    }
}
