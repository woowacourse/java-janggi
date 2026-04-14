package controller;

import dto.dao.InitialGamePersistDto;
import dto.dao.MovePersistDto;
import domain.board.Formation;
import domain.board.JanggiBoard;
import domain.board.JanggiGenerator;
import domain.board.SavedPiecesGenerator;
import domain.game.Game;
import domain.game.GameScore;
import domain.game.MoveCommand;
import domain.intersection.Intersection;
import domain.team.Team;
import dto.InputMoveDto;
import dto.dao.LoadedGameState;
import dto.dao.LoadedPiece;
import dto.dao.ResumableGame;
import dao.GameDao;
import exception.ErrorMessage;
import exception.InvalidMenuChoiceException;
import exception.PieceDbIdNotFoundException;
import java.util.List;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import transaction.TransactionTemplate;
import mapper.BoardOutputMapper;
import mapper.MoveMapper;
import parser.MoveInputParser;
import view.InputView;
import view.OutputView;

public class JanggiController {
    private final InputView inputView;
    private final OutputView outputView;
    private final BoardOutputMapper boardOutputMapper;
    private final GameDao gameDao;
    private final TransactionTemplate transactionTemplate;

    public JanggiController(
            InputView inputView,
            OutputView outputView,
            BoardOutputMapper boardOutputMapper,
            GameDao gameDao,
            TransactionTemplate transactionTemplate
    ) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.boardOutputMapper = boardOutputMapper;
        this.gameDao = gameDao;
        this.transactionTemplate = transactionTemplate;
    }

    public void run() {
        int choice = readValidMenuChoice();
        executeMenu(choice);
    }

    private int readValidMenuChoice() {
        return retry(() -> {
            int choice = inputView.inputNewOrResume();
            validateMenuChoice(choice);
            return choice;
        });
    }

    private <T> T retry(Supplier<T> inputSupplier) {
        while (true) {
            try {
                return inputSupplier.get();
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private static void validateMenuChoice(int choice) {
        if (choice != 1 && choice != 2) {
            throw new InvalidMenuChoiceException();
        }
    }

    private void executeMenu(int choice) {
        if (choice == 1) {
            startNewGame();
            return;
        }
        resumeExistingGame();
    }

    private void startNewGame() {
        Formation hanFormation = readFormation(() -> inputView.inputHanWingSetup());
        Formation choFormation = readFormation(() -> inputView.inputChoWingSetup());
        JanggiGenerator janggiGenerator = new JanggiGenerator(hanFormation, choFormation);

        final Game game = new Game(new JanggiBoard(janggiGenerator));
        long gameId = insertInitialGame(InitialGamePersistDto.from(game));
        runGameLoop(game, gameId);
    }

    private Formation readFormation(IntSupplier readChoice) {
        return retry(() -> {
            return getFormation(readChoice);
        });
    }

    private Formation getFormation(IntSupplier readChoice) {
        int choice = readChoice.getAsInt();
        try {
            return Formation.valueOf(choice);
        } catch (IllegalArgumentException e) {
            throw new exception.InvalidFormationChoiceException();
        }
    }

    private long insertInitialGame(InitialGamePersistDto dto) {
        return transactionTemplate.executeInTransaction(conn -> gameDao.insertInitialGameAndPieces(conn, dto));
    }

    private void runGameLoop(Game game, long gameId) {
        while (game.isRunning()) {
            processTurn(game, gameId);
        }
        outputView.printWinnerTeam(game.currentTurn().nextTurn());
    }

    private void processTurn(Game game, long gameId) {
        try {
            playOneTurn(game, gameId);
        } catch (IllegalArgumentException | IllegalStateException e) {
            outputView.printErrorMessage(e.getMessage());
        }
    }

    private void playOneTurn(Game game, long gameId) {
        presentTurnState(game);
        MoveCommand move = readMoveCommand();
        long movedPieceId = findMovedPieceId(gameId, move);
        Long capturedPieceId = findCapturedPieceIdOrNull(game, gameId, move);
        game.processTurn(move);
        saveMove(game, gameId, move, movedPieceId, capturedPieceId);
    }

    private void presentTurnState(Game game) {
        outputView.printCurrentBoardStatus(boardOutputMapper.toDto(game.getBoardState()));
        outputView.printCurrentTurn(game.currentTurn());
        outputView.printCurrentScore(game.currentScore());
    }

    private MoveCommand readMoveCommand() {
        return MoveMapper.toMove(getInputMove());
    }

    private InputMoveDto getInputMove() {
        String movePoint = inputView.inputMovePiecePoint();
        String destinationPoint = inputView.inputDestinationPoint();
        return MoveInputParser.parse(movePoint, destinationPoint);
    }

    private long findMovedPieceId(long gameId, MoveCommand move) {
        int y = move.getFrom().y();
        int x = move.getFrom().x();
        return gameDao.findPieceIdAt(gameId, y, x)
                .orElseThrow(PieceDbIdNotFoundException::new);
    }

    private Long findCapturedPieceIdOrNull(Game game, long gameId, MoveCommand move) {
        if (!game.willCaptureOpponent(move.getTo())) {
            return null;
        }
        int y = move.getTo().y();
        int x = move.getTo().x();
        return gameDao.findPieceIdAt(gameId, y, x)
                .orElseThrow(PieceDbIdNotFoundException::new);
    }

    private void saveMove(Game game, long gameId, MoveCommand move, long movedPieceId, Long capturedPieceId) {
        persistMove(MovePersistDto.afterTurn(game, gameId, move, movedPieceId, capturedPieceId));
    }

    private void persistMove(MovePersistDto dto) {
        transactionTemplate.executeInTransaction(conn -> {
            gameDao.persistMove(conn, dto);
            return null;
        });
    }

    private void resumeExistingGame() {
        List<ResumableGame> games = gameDao.findResumableGames();
        if (games.isEmpty()) {
            outputView.printNoResumableGames();
            startNewGame();
            return;
        }
        printResumableGames(games);
        long gameId = games.get(readResumeChoice(games.size()) - 1).id();
        LoadedGameState state = gameDao.loadGameForResume(gameId);
        runGameLoop(restoreGame(state), state.gameId());
    }

    private void printResumableGames(List<ResumableGame> games) {
        outputView.printResumableGamesHeader();

        int displayNumber = 1;
        for (ResumableGame game : games) {
            outputView.printResumableGameLine(
                    displayNumber++,
                    game.hanScore(),
                    game.choScore(),
                    game.updatedAt().toString()
            );
        }
    }

    private int readResumeChoice(int unfinishedGameCount) {
        return retry(() -> readValidatedChoice(unfinishedGameCount));
    }

    private int readValidatedChoice(int unfinishedGameCount) {
        int choice = inputView.inputResumeGameChoice(unfinishedGameCount);
        if (choice < 1 || choice > unfinishedGameCount) {
            throw new InvalidMenuChoiceException();
        }
        return choice;
    }

    private Game restoreGame(LoadedGameState state) {
        List<Intersection> intersections = state.pieces().stream()
                .map(LoadedPiece::toIntersection)
                .toList();
        JanggiBoard board = new JanggiBoard(new SavedPiecesGenerator(intersections));
        return Game.restored(
                board,
                new GameScore(state.choScore(), state.hanScore()),
                Team.valueOf(state.turnTeam()),
                board.isGameRunning()
        );
    }
}
