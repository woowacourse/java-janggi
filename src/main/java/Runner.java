import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import domain.Board;
import domain.GameDeadline;
import domain.GameStatus;
import domain.JanggiGame;
import domain.MovableRoutes;
import domain.Piece;
import domain.Position;
import domain.Route;
import domain.ScoreCalculator;
import domain.TeamColor;
import domain.TeamScores;
import domain.TurnManager;
import domain.TurnOutcome;
import io.InputView;
import io.OutputView;
import persistence.GameStateRepository;
import persistence.SaveGameStateRequest;
import persistence.SavedGameState;
import strategy.formation.InitialFormationStrategy;
import strategy.formation.InitialFormationStrategyFactory;

public class Runner {

    private record GameContext(JanggiGame game, GameDeadline deadline) {}

    private final InputView inputView;
    private final OutputView outputView;
    private final GameStateRepository gameStateRepository;
    private final ScoreCalculator scoreCalculator;
    private final Clock clock;

    public Runner(
            InputView inputView, OutputView outputView, GameStateRepository gameStateRepository, Clock clock) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.gameStateRepository = gameStateRepository;
        this.scoreCalculator = new ScoreCalculator();
        this.clock = clock;
    }

    public void run() {
        outputView.printGameStart();
        GameContext context = resolveInitialSession();
        outputView.printBoard(context.game().board());
        persistInProgress(context);
        runGameLoop(context);
    }

    private GameContext resolveInitialSession() {
        Optional<SavedGameState> saved = gameStateRepository.load();
        if (saved.isEmpty()) {
            return startFreshSession();
        }
        return resumeOrNewSession(saved.get());
    }

    private GameContext resumeOrNewSession(SavedGameState saved) {
        if (saved.gameStatus() == GameStatus.ENDED) {
            TeamColor winner = saved.winner();
            if (winner == null) {
                outputView.printSavedGameEndedWithoutWinner();
                return startFreshSession();
            }
            outputView.printSavedGameEndedWithWinner(winner);
            return startFreshSession();
        }
        int choice = readResumeChoiceWithRetry();
        if (choice == 1) {
            return resumeSession(saved);
        }
        return startFreshSession();
    }

    private GameContext resumeSession(SavedGameState saved) {
        Board board = new Board(saved.snapshot().pieces());
        TurnManager turnManager = new TurnManager(saved.currentTurn());
        JanggiGame game = new JanggiGame(board, turnManager);
        GameDeadline deadline = resolveDeadlineForResume(saved);
        return new GameContext(game, deadline);
    }

    private GameContext startFreshSession() {
        Board board = initializeNewBoard();
        GameDeadline deadline = readNewDeadline();
        TurnManager turnManager = new TurnManager();
        turnManager.start();
        JanggiGame game = new JanggiGame(board, turnManager);
        return new GameContext(game, deadline);
    }

    private GameDeadline resolveDeadlineForResume(SavedGameState saved) {
        if (saved.deadline() != null) {
            return saved.deadline();
        }
        GameDeadline deadline = readNewDeadline();
        gameStateRepository.save(
                new SaveGameStateRequest(
                        saved.snapshot(),
                        saved.currentTurn(),
                        saved.gameStatus(),
                        saved.winner(),
                        deadline));
        return deadline;
    }

    private GameDeadline readNewDeadline() {
        int seconds = readTimeLimitSecondsWithRetry();
        Instant deadlineInstant = Instant.now(clock).plusSeconds(seconds);
        return GameDeadline.of(deadlineInstant);
    }

    private int readTimeLimitSecondsWithRetry() {
        while (true) {
            try {
                return parseTimeLimitSeconds();
            } catch (RuntimeException exception) {
                outputView.printError(exception.getMessage());
            }
        }
    }

    private int parseTimeLimitSeconds() {
        outputView.printTimeLimitPrompt();
        int seconds = inputView.readTimeLimitSeconds();
        return validatedSeconds(seconds);
    }

    private static int validatedSeconds(int seconds) {
        if (seconds <= 0) {
            throw new IllegalArgumentException("제한 시간은 1초 이상이어야 합니다.");
        }
        return seconds;
    }

    private int readResumeChoiceWithRetry() {
        while (true) {
            try {
                return parseResumeChoice();
            } catch (RuntimeException exception) {
                outputView.printError(exception.getMessage());
            }
        }
    }

    private int parseResumeChoice() {
        outputView.printResumePrompt();
        int choice = inputView.readResumeOrNewChoice();
        return validatedResumeChoice(choice);
    }

    private static int validatedResumeChoice(int choice) {
        if (choice == 1) {
            return 1;
        }
        if (choice == 2) {
            return 2;
        }
        throw new IllegalArgumentException("1 또는 2만 입력하세요.");
    }

    private Board initializeNewBoard() {
        InitialFormationStrategy choStrategy = chooseFormationStrategy(TeamColor.CHO);
        InitialFormationStrategy hanStrategy = chooseFormationStrategy(TeamColor.HAN);
        Initializer initializer = new Initializer(choStrategy, hanStrategy);
        return initializer.initialize();
    }

    private void persistInProgress(GameContext context) {
        gameStateRepository.save(
                new SaveGameStateRequest(
                        context.game().captureSnapshot(),
                        context.game().currentTurn(),
                        GameStatus.IN_PROGRESS,
                        null,
                        context.deadline()));
    }

    private void runGameLoop(GameContext context) {
        while (playTurn(context.game(), context.deadline())) {
        }
    }

    private InitialFormationStrategy chooseFormationStrategy(TeamColor teamColor) {
        while (true) {
            Optional<InitialFormationStrategy> picked = tryPickFormation(teamColor);
            if (picked.isPresent()) {
                return picked.get();
            }
        }
    }

    private Optional<InitialFormationStrategy> tryPickFormation(TeamColor teamColor) {
        outputView.printFormationSelectionPrompt(teamColor);
        try {
            return Optional.of(InitialFormationStrategyFactory.from(inputView.readFormationChoice(teamColor)));
        } catch (RuntimeException exception) {
            outputView.printError("상차림 입력이 올바르지 않습니다.");
            return Optional.empty();
        }
    }

    private boolean playTurn(JanggiGame game, GameDeadline deadline) {
        if (deadline.isExpired(clock)) {
            endByScore(game, deadline);
            return false;
        }
        outputView.printCurrentTurn(game.currentTurn());
        outputView.printBoard(game.board());
        return runTurnInputLoop(game, deadline);
    }

    private void endByScore(JanggiGame game, GameDeadline deadline) {
        TeamScores scores =
                scoreCalculator.calculate(game.piecesOfTeam(TeamColor.CHO), game.piecesOfTeam(TeamColor.HAN));
        Optional<TeamColor> winner = scores.winner();
        if (winner.isEmpty()) {
            outputView.printTimeOverDraw(scores);
            saveEndedState(game, null, deadline);
            return;
        }
        TeamColor winnerColor = winner.get();
        outputView.printTimeOverWinnerByScore(scores, winnerColor);
        saveEndedState(game, winnerColor, deadline);
    }

    private boolean runTurnInputLoop(JanggiGame game, GameDeadline deadline) {
        while (true) {
            TurnOutcome outcome = trySingleTurnAction(game, deadline);
            Boolean gameContinues = interpretOutcome(outcome);
            if (gameContinues != null) {
                return gameContinues;
            }
        }
    }

    private static Boolean interpretOutcome(TurnOutcome outcome) {
        if (outcome == TurnOutcome.RETRY) {
            return null;
        }
        return outcome != TurnOutcome.GAME_OVER;
    }

    private TurnOutcome trySingleTurnAction(JanggiGame game, GameDeadline deadline) {
        try {
            return processPieceSelection(game, deadline);
        } catch (RuntimeException exception) {
            outputView.printError(exception.getMessage());
            return TurnOutcome.RETRY;
        }
    }

    private TurnOutcome processPieceSelection(JanggiGame game, GameDeadline deadline) {
        List<Map.Entry<Position, Piece>> pieces = game.currentTurnPieces();
        outputView.printPieceOptions(pieces);
        int pieceChoice = inputView.readPieceChoice(game.currentTurn());
        Piece selectedPiece = getSelectedPiece(pieces, pieceChoice);
        return followRoutes(game, selectedPiece, deadline);
    }

    private TurnOutcome followRoutes(JanggiGame game, Piece selectedPiece, GameDeadline deadline) {
        MovableRoutes movable = game.findMovableRoutes(selectedPiece);
        List<Route> routes = movable.routes();
        if (routes.isEmpty()) {
            throw new IllegalArgumentException("선택한 기물은 이동 가능한 경로가 없습니다.");
        }
        printKingNoticeIfApplicable(movable);
        outputView.printRouteOptions(routes);
        int routeChoice = inputView.readRouteChoice();
        return applyRouteChoice(game, selectedPiece, routes, routeChoice, deadline);
    }

    private void printKingNoticeIfApplicable(MovableRoutes movable) {
        if (!movable.hasKingDestination()) {
            return;
        }
        outputView.printCheck();
    }

    private TurnOutcome applyRouteChoice(
            JanggiGame game, Piece selectedPiece, List<Route> routes, int routeChoice, GameDeadline deadline) {
        if (routeChoice == 0) {
            return TurnOutcome.RETRY;
        }
        return completeMove(game, selectedPiece, routes, routeChoice, deadline);
    }

    private TurnOutcome completeMove(
            JanggiGame game, Piece piece, List<Route> routes, int routeChoice, GameDeadline deadline) {
        Position destination = getSelectedRoute(routes, routeChoice).endPos();
        Optional<Piece> captured = game.move(piece, destination);
        outputView.printMoveResult(piece, destination);
        return afterMove(game, captured, deadline);
    }

    private TurnOutcome afterMove(JanggiGame game, Optional<Piece> captured, GameDeadline deadline) {
        if (isKingCapture(captured)) {
            outputView.printGameEnd(game.currentTurn());
            saveEndedState(game, game.currentTurn(), deadline);
            return TurnOutcome.GAME_OVER;
        }
        progressTurnAndPersist(game, deadline);
        return TurnOutcome.TURN_DONE;
    }

    private static boolean isKingCapture(Optional<Piece> captured) {
        return captured.filter(Piece::isKing).isPresent();
    }

    private void saveEndedState(JanggiGame game, TeamColor winner, GameDeadline deadline) {
        gameStateRepository.save(
                new SaveGameStateRequest(
                        game.captureSnapshot(),
                        game.currentTurn(),
                        GameStatus.ENDED,
                        winner,
                        deadline));
    }

    private void progressTurnAndPersist(JanggiGame game, GameDeadline deadline) {
        game.progressTurn();
        gameStateRepository.save(
                new SaveGameStateRequest(
                        game.captureSnapshot(),
                        game.currentTurn(),
                        GameStatus.IN_PROGRESS,
                        null,
                        deadline));
    }

    private Piece getSelectedPiece(List<Map.Entry<Position, Piece>> pieces, int pieceChoice) {
        if (pieceChoice < 1 || pieceChoice > pieces.size()) {
            throw new IllegalArgumentException("기물 번호가 범위를 벗어났습니다.");
        }
        return pieces.get(pieceChoice - 1).getValue();
    }

    private Route getSelectedRoute(List<Route> routes, int routeChoice) {
        if (routeChoice < 1 || routeChoice > routes.size()) {
            throw new IllegalArgumentException("경로 번호가 범위를 벗어났습니다.");
        }
        return routes.get(routeChoice - 1);
    }
}
