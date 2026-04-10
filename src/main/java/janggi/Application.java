package janggi;

import janggi.db.ConnectionFactory;
import janggi.db.SchemaInitializer;
import janggi.domain.board.Board;
import janggi.domain.board.BoardInitializer;
import janggi.domain.board.Position;
import janggi.domain.game.JanggiGame;
import janggi.domain.game.MoveResult;
import janggi.domain.piece.Team;
import janggi.dto.BoardDto;
import janggi.dto.OpeningFormationChoices;
import janggi.repository.GameRepository;
import janggi.repository.JdbcGameRepository;
import janggi.repository.SavedGame;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.List;

public class Application {
    private final InputView inputView;
    private final OutputView outputView;

    public Application() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
    }

    public static void main(String[] args) {
        new Application().run();
    }

    private void run() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        initializeSchema(connectionFactory);
        GameRepository gameRepository = new JdbcGameRepository(connectionFactory);
        SavedGame savedGame = findSavedGameOrCreateNewGame(gameRepository);
        JanggiGame janggiGame = savedGame.janggiGame();
        long savedGameId = savedGame.id();

        while (janggiGame.isPlaying()) {
            outputView.printBoardMap(BoardDto.from(janggiGame.board()));
            Position startPiecePosition = readStartPositionUntilValid();
            Position endPiecePosition = readEndPositionUntilValid();
            tryMove(gameRepository, savedGameId, janggiGame, startPiecePosition, endPiecePosition);
        }
        outputView.printBoardMap(BoardDto.from(janggiGame.board()));
        outputView.printScore(janggiGame.calculateScore(Team.HAN), janggiGame.calculateScore(Team.CHO));
    }

    private void initializeSchema(ConnectionFactory connectionFactory) {
        SchemaInitializer schemaInitializer = new SchemaInitializer(connectionFactory);
        schemaInitializer.initialize();
    }

    private SavedGame findSavedGameOrCreateNewGame(GameRepository gameRepository) {
        return gameRepository.findPlayingGame()
                .orElseGet(() -> createNewGame(gameRepository));
    }

    private SavedGame createNewGame(GameRepository gameRepository) {
        OpeningFormationChoices openingFormationChoices = readOpeningFormationChoiceUntilValid();
        Board board = BoardInitializer.initializeBoard(openingFormationChoices.hanChoice(),
                openingFormationChoices.choChoice());
        JanggiGame janggiGame = JanggiGame.start(board);
        long savedGameId = gameRepository.saveNewGame(janggiGame);
        return new SavedGame(savedGameId, janggiGame);
    }

    private void tryMove(GameRepository gameRepository, long savedGameId, JanggiGame janggiGame,
                         Position startPiecePosition, Position endPiecePosition) {
        try {
            MoveResult moveResult = janggiGame.move(startPiecePosition, endPiecePosition);
            gameRepository.applyMoveResult(savedGameId, moveResult);
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
        }
    }

    private OpeningFormationChoices readOpeningFormationChoiceUntilValid() {
        while (true) {
            try {
                return inputView.readOpeningFormationChoice();
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private Position readStartPositionUntilValid() {
        while (true) {
            try {
                Position startPiecePosition = getStartPiecePosition();
                return startPiecePosition;
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private Position readEndPositionUntilValid() {
        while (true) {
            try {
                return getEndPiecePosition();
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private Position getEndPiecePosition() {
        List<Integer> endPosition = inputView.readEndPiecePosition();
        return toPosition(endPosition);
    }

    private Position getStartPiecePosition() {
        List<Integer> startPosition = inputView.readStartPiecePosition();
        return toPosition(startPosition);
    }

    private Position toPosition(List<Integer> values) {
        int x = values.get(0);
        int y = values.get(1);
        return new Position(x, y);
    }
}
