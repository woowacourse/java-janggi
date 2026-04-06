import java.util.List;
import java.util.Map;
import java.util.Optional;

import domain.Board;
import domain.Piece;
import domain.Position;
import domain.Route;
import domain.TeamColor;
import domain.TurnManager;
import io.InputView;
import io.OutputView;
import persistence.GameStateRepository;
import persistence.SavedGameState;
import strategy.formation.InitialFormationStrategy;
import strategy.formation.InnerFormationStrategy;
import strategy.formation.LeftFormationStrategy;
import strategy.formation.OuterFormationStrategy;
import strategy.formation.RightFormationStrategy;

public class Runner {

    private record GameSession(Board board, TurnManager turnManager) {}

    private final InputView inputView;
    private final OutputView outputView;
    private final GameStateRepository gameStateRepository;

    public Runner(
            InputView inputView, OutputView outputView, GameStateRepository gameStateRepository) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.gameStateRepository = gameStateRepository;
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
        int choice = readResumeChoiceWithRetry();
        if (choice == 1) {
            return resumeSession(saved);
        }
        return startFreshSession();
    }

    private GameSession resumeSession(SavedGameState saved) {
        Board board = new Board(saved.snapshot().pieces());
        TurnManager turnManager = new TurnManager(saved.currentTurn());
        return new GameSession(board, turnManager);
    }

    private GameSession startFreshSession() {
        Board board = initializeNewBoard();
        return new GameSession(board, new TurnManager());
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
        gameStateRepository.save(session.board().capture(), session.turnManager().getCurrentTurn());
    }

    private void runGameLoop(GameSession session) {
        while (playTurn(session.board(), session.turnManager())) {
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

    private boolean playTurn(Board board, TurnManager turnManager) {
        TeamColor currentTurn = turnManager.getCurrentTurn();
        outputView.printCurrentTurn(currentTurn);
        outputView.printBoard(board);
        return runTurnInputLoop(board, turnManager, currentTurn);
    }

    private boolean runTurnInputLoop(Board board, TurnManager turnManager, TeamColor currentTurn) {
        while (true) {
            TurnOutcome outcome = trySingleTurnAction(board, turnManager, currentTurn);
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
        if (outcome == TurnOutcome.GAME_OVER) {
            return false;
        }
        return true;
    }

    private TurnOutcome trySingleTurnAction(Board board, TurnManager turnManager, TeamColor currentTurn) {
        try {
            return processPieceSelection(board, turnManager, currentTurn);
        } catch (RuntimeException exception) {
            outputView.printError(exception.getMessage());
            return TurnOutcome.RETRY;
        }
    }

    private TurnOutcome processPieceSelection(Board board, TurnManager turnManager, TeamColor currentTurn) {
        List<Map.Entry<Position, Piece>> pieces = board.findPiecesByTeam(currentTurn);
        outputView.printPieceOptions(pieces);
        int pieceChoice = inputView.readPieceChoice(currentTurn);
        Piece selectedPiece = getSelectedPiece(pieces, pieceChoice);
        return followRoutes(board, turnManager, selectedPiece);
    }

    private TurnOutcome followRoutes(Board board, TurnManager turnManager, Piece selectedPiece) {
        List<Route> routes = board.findMovableRoutes(selectedPiece);
        if (routes.isEmpty()) {
            throw new IllegalArgumentException("선택한 기물은 이동 가능한 경로가 없습니다.");
        }
        outputView.printRouteOptions(routes);
        int routeChoice = inputView.readRouteChoice();
        return applyRouteChoice(board, turnManager, selectedPiece, routes, routeChoice);
    }

    private TurnOutcome applyRouteChoice(
            Board board,
            TurnManager turnManager,
            Piece selectedPiece,
            List<Route> routes,
            int routeChoice) {
        if (routeChoice == 0) {
            return TurnOutcome.RETRY;
        }
        return completeMove(board, turnManager, selectedPiece, routes, routeChoice);
    }

    private TurnOutcome completeMove(
            Board board, TurnManager turnManager, Piece piece, List<Route> routes, int routeChoice) {
        Position destination = getSelectedRoute(routes, routeChoice).endPos();
        Optional<Piece> captured = board.move(piece, destination);
        outputView.printMoveResult(piece, destination);
        return afterMove(board, turnManager, captured);
    }

    private TurnOutcome afterMove(Board board, TurnManager turnManager, Optional<Piece> captured) {
        if (isKingCapture(captured)) {
            persistFinalState(board, turnManager);
            return TurnOutcome.GAME_OVER;
        }
        progressTurnAndPersist(board, turnManager);
        return TurnOutcome.TURN_DONE;
    }

    private static boolean isKingCapture(Optional<Piece> captured) {
        return captured.filter(Piece::isKing).isPresent();
    }

    private void persistFinalState(Board board, TurnManager turnManager) {
        gameStateRepository.save(board.capture(), turnManager.getCurrentTurn());
        outputView.printGameEnd(turnManager.getCurrentTurn());
    }

    private void progressTurnAndPersist(Board board, TurnManager turnManager) {
        turnManager.progressTurn();
        gameStateRepository.save(board.capture(), turnManager.getCurrentTurn());
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
