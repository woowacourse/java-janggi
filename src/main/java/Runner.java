import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import domain.Board;
import domain.GameDeadline;
import domain.GameStatus;
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
import strategy.formation.InnerFormationStrategy;
import strategy.formation.LeftFormationStrategy;
import strategy.formation.OuterFormationStrategy;
import strategy.formation.RightFormationStrategy;

public class Runner {

    private record GameSession(Board board, TurnManager turnManager, GameDeadline deadline) {}

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
        GameSession session = resolveInitialSession();
        outputView.printBoard(session.board());
        persistSession(session);
        runGameLoop(session);
    }

    private GameSession resolveInitialSession() {
        Optional<SavedGameState> saved = gameStateRepository.load();
        if (saved.isEmpty()) {
            return startFreshSession();
        }
        return resumeOrNewSession(saved.get());
    }

    private GameSession resumeOrNewSession(SavedGameState saved) {
        if (saved.gameStatus() == GameStatus.ENDED) {
            outputView.printSavedGameEnded(saved.winner());
            return startFreshSession();
        }
        int choice = readResumeChoiceWithRetry();
        if (choice == 1) {
            return resumeSession(saved);
        }
        return startFreshSession();
    }

    private GameSession resumeSession(SavedGameState saved) {
        Board board = new Board(saved.snapshot().pieces());
        TurnManager turnManager = new TurnManager(saved.currentTurn());
        GameDeadline deadline = resolveDeadlineForResume(saved);
        return new GameSession(board, turnManager, deadline);
    }

    private GameSession startFreshSession() {
        Board board = initializeNewBoard();
        GameDeadline deadline = readNewDeadline();
        TurnManager turnManager = new TurnManager();
        turnManager.start();
        return new GameSession(board, turnManager, deadline);
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

    private void persistSession(GameSession session) {
        gameStateRepository.save(
                new SaveGameStateRequest(
                        session.board().capture(),
                        session.turnManager().getCurrentTurn(),
                        GameStatus.IN_PROGRESS,
                        null,
                        session.deadline()));
    }

    private void runGameLoop(GameSession session) {
        while (playTurn(session.board(), session.turnManager(), session.deadline())) {
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
            return Optional.of(strategyForChoice(inputView.readFormationChoice(teamColor)));
        } catch (RuntimeException exception) {
            outputView.printError("상차림 입력이 올바르지 않습니다.");
            return Optional.empty();
        }
    }

    private static InitialFormationStrategy strategyForChoice(int choice) {
        if (choice == 1) {
            return new InnerFormationStrategy();
        }
        return strategyForChoiceFromTwo(choice);
    }

    private static InitialFormationStrategy strategyForChoiceFromTwo(int choice) {
        if (choice == 2) {
            return new OuterFormationStrategy();
        }
        return strategyForChoiceFromThree(choice);
    }

    private static InitialFormationStrategy strategyForChoiceFromThree(int choice) {
        if (choice == 3) {
            return new LeftFormationStrategy();
        }
        return strategyForChoiceFromFour(choice);
    }

    private static InitialFormationStrategy strategyForChoiceFromFour(int choice) {
        if (choice == 4) {
            return new RightFormationStrategy();
        }
        throw new IllegalArgumentException("상차림 번호는 1~4 사이여야 합니다.");
    }

    private boolean playTurn(Board board, TurnManager turnManager, GameDeadline deadline) {
        if (deadline.isExpired(clock)) {
            endByScore(board, turnManager, deadline);
            return false;
        }
        TeamColor currentTurn = turnManager.getCurrentTurn();
        outputView.printCurrentTurn(currentTurn);
        outputView.printBoard(board);
        return runTurnInputLoop(board, turnManager, currentTurn, deadline);
    }

    private void endByScore(Board board, TurnManager turnManager, GameDeadline deadline) {
        TeamScores scores = scoreCalculator.calculate(board.capture());
        Optional<TeamColor> winner = scores.winner();
        outputView.printTimeOverByScore(scores, winner);
        gameStateRepository.save(
                new SaveGameStateRequest(
                        board.capture(),
                        turnManager.getCurrentTurn(),
                        GameStatus.ENDED,
                        winner.orElse(null),
                        deadline));
    }

    private boolean runTurnInputLoop(
            Board board, TurnManager turnManager, TeamColor currentTurn, GameDeadline deadline) {
        while (true) {
            TurnOutcome outcome = trySingleTurnAction(board, turnManager, currentTurn, deadline);
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

    private TurnOutcome trySingleTurnAction(
            Board board, TurnManager turnManager, TeamColor currentTurn, GameDeadline deadline) {
        try {
            return processPieceSelection(board, turnManager, currentTurn, deadline);
        } catch (RuntimeException exception) {
            outputView.printError(exception.getMessage());
            return TurnOutcome.RETRY;
        }
    }

    private TurnOutcome processPieceSelection(
            Board board, TurnManager turnManager, TeamColor currentTurn, GameDeadline deadline) {
        List<Map.Entry<Position, Piece>> pieces = board.findPiecesByTeam(currentTurn);
        outputView.printPieceOptions(pieces);
        int pieceChoice = inputView.readPieceChoice(currentTurn);
        Piece selectedPiece = getSelectedPiece(pieces, pieceChoice);
        return followRoutes(board, turnManager, selectedPiece, deadline);
    }

    private TurnOutcome followRoutes(
            Board board, TurnManager turnManager, Piece selectedPiece, GameDeadline deadline) {
        MovableRoutes movable = board.findMovableRoutes(selectedPiece);
        List<Route> routes = movable.routes();
        if (routes.isEmpty()) {
            throw new IllegalArgumentException("선택한 기물은 이동 가능한 경로가 없습니다.");
        }
        printKingNoticeIfApplicable(movable);
        outputView.printRouteOptions(routes);
        int routeChoice = inputView.readRouteChoice();
        return applyRouteChoice(board, turnManager, selectedPiece, routes, routeChoice, deadline);
    }

    private void printKingNoticeIfApplicable(MovableRoutes movable) {
        if (!movable.hasKingDestination()) {
            return;
        }
        outputView.printCheck();
    }

    private TurnOutcome applyRouteChoice(
            Board board,
            TurnManager turnManager,
            Piece selectedPiece,
            List<Route> routes,
            int routeChoice,
            GameDeadline deadline) {
        if (routeChoice == 0) {
            return TurnOutcome.RETRY;
        }
        return completeMove(board, turnManager, selectedPiece, routes, routeChoice, deadline);
    }

    private TurnOutcome completeMove(
            Board board,
            TurnManager turnManager,
            Piece piece,
            List<Route> routes,
            int routeChoice,
            GameDeadline deadline) {
        Position destination = getSelectedRoute(routes, routeChoice).endPos();
        Optional<Piece> captured = board.move(piece, destination);
        outputView.printMoveResult(piece, destination);
        return afterMove(board, turnManager, captured, deadline);
    }

    private TurnOutcome afterMove(
            Board board, TurnManager turnManager, Optional<Piece> captured, GameDeadline deadline) {
        if (isKingCapture(captured)) {
            persistFinalState(board, turnManager, deadline);
            return TurnOutcome.GAME_OVER;
        }
        progressTurnAndPersist(board, turnManager, deadline);
        return TurnOutcome.TURN_DONE;
    }

    private static boolean isKingCapture(Optional<Piece> captured) {
        return captured.filter(Piece::isKing).isPresent();
    }

    private void persistFinalState(Board board, TurnManager turnManager, GameDeadline deadline) {
        outputView.printGameEnd(turnManager.getCurrentTurn());
        gameStateRepository.save(
                new SaveGameStateRequest(
                        board.capture(),
                        turnManager.getCurrentTurn(),
                        GameStatus.ENDED,
                        turnManager.getCurrentTurn(),
                        deadline));
    }

    private void progressTurnAndPersist(Board board, TurnManager turnManager, GameDeadline deadline) {
        turnManager.progressTurn();
        gameStateRepository.save(
                new SaveGameStateRequest(
                        board.capture(),
                        turnManager.getCurrentTurn(),
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
