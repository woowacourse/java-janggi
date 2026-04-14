package controller;

import domain.board.Formation;
import domain.board.JanggiBoard;
import domain.board.JanggiGenerator;
import domain.game.Game;
import domain.game.MoveCommand;
import dto.InputMoveDto;
import entity.ResumableGameEntity;
import exception.InvalidMenuChoiceException;
import exception.PieceDbIdNotFoundException;
import java.util.List;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import mapper.BoardOutputMapper;
import mapper.MoveMapper;
import parser.MoveInputParser;
import repository.GameRepository;
import view.InputView;
import view.OutputView;

public class JanggiController {
    private final InputView inputView;
    private final OutputView outputView;
    private final BoardOutputMapper boardOutputMapper;
    private final GameRepository gameRepository;

    public JanggiController(
            InputView inputView,
            OutputView outputView,
            BoardOutputMapper boardOutputMapper,
            GameRepository gameRepository
    ) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.boardOutputMapper = boardOutputMapper;
        this.gameRepository = gameRepository;
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
        long gameId = gameRepository.saveNewGame(game);
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
        return gameRepository.findPieceIdAt(gameId, y, x)
                .orElseThrow(PieceDbIdNotFoundException::new);
    }

    private Long findCapturedPieceIdOrNull(Game game, long gameId, MoveCommand move) {
        if (!game.willCaptureOpponent(move.getTo())) {
            return null;
        }
        int y = move.getTo().y();
        int x = move.getTo().x();
        return gameRepository.findPieceIdAt(gameId, y, x)
                .orElseThrow(PieceDbIdNotFoundException::new);
    }

    private void saveMove(Game game, long gameId, MoveCommand move, long movedPieceId, Long capturedPieceId) {
        gameRepository.saveMove(game, gameId, move, movedPieceId, capturedPieceId);
    }

    private void resumeExistingGame() {
        List<ResumableGameEntity> games = gameRepository.findResumableGames();
        if (games.isEmpty()) {
            outputView.printNoResumableGames();
            startNewGame();
            return;
        }
        printResumableGames(games);
        long gameId = games.get(readResumeChoice(games.size()) - 1).id();
        Game game = gameRepository.findById(gameId);
        runGameLoop(game, gameId);
    }

    private void printResumableGames(List<ResumableGameEntity> games) {
        outputView.printResumableGamesHeader();

        int displayNumber = 1;
        for (ResumableGameEntity game : games) {
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

    private <T> T retry(Supplier<T> inputSupplier) {
        while (true) {
            try {
                return inputSupplier.get();
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }
}
