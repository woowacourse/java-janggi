import domain.Board;
import domain.Piece;
import domain.Position;
import domain.Route;
import domain.TeamColor;
import domain.TurnManager;
import io.InputView;
import io.OutputView;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import strategy.formation.InitialFormationStrategy;
import strategy.formation.InnerFormationStrategy;
import strategy.formation.LeftFormationStrategy;
import strategy.formation.OuterFormationStrategy;
import strategy.formation.RightFormationStrategy;

public class Runner {
    private static final List<FormationFactory> FORMATION_FACTORIES = createFormationFactories();

    private final InputView inputView;
    private final OutputView outputView;
    private final TurnManager turnManager;

    public Runner() {
        this(new InputView(), new OutputView(), new TurnManager());
    }

    public Runner(InputView inputView, OutputView outputView, TurnManager turnManager) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.turnManager = turnManager;
    }

    public void run() {
        outputView.printGameStart();

        InitialFormationStrategy choStrategy = chooseFormationStrategy(TeamColor.CHO);
        InitialFormationStrategy hanStrategy = chooseFormationStrategy(TeamColor.HAN);

        Initializer initializer = new Initializer(choStrategy, hanStrategy);
        Board board = initializer.initialize();

        outputView.printBoard(board);

        while (true) {
            playTurn(board);
        }
    }

    private InitialFormationStrategy chooseFormationStrategy(TeamColor teamColor) {
        while (true) {
            outputView.printFormationSelectionPrompt(teamColor);
            try {
                return createFormationStrategy(inputView.readFormationChoice(teamColor));
            } catch (RuntimeException exception) {
                outputView.printError("상차림 입력이 올바르지 않습니다.");
            }
        }
    }

    private void playTurn(Board board) {
        TeamColor currentTurn = turnManager.getCurrentTurn();
        outputView.printCurrentTurn(currentTurn);
        outputView.printBoard(board);

        while (true) {
            try {
                Piece selectedPiece = choosePiece(board, currentTurn);
                Optional<Route> selectedRoute = chooseRoute(board, selectedPiece);
                if (selectedRoute.isEmpty()) {
                    continue;
                }

                Position destination = selectedRoute.get().endPos();
                board.move(selectedPiece, destination);
                outputView.printMoveResult(selectedPiece, destination);
                turnManager.progressTurn();
                return;
            } catch (RuntimeException exception) {
                outputView.printError(exception.getMessage());
            }
        }
    }

    private InitialFormationStrategy createFormationStrategy(int choice) {
        validateFormationChoice(choice);
        return FORMATION_FACTORIES.get(choice - 1).create();
    }

    private void validateFormationChoice(int choice) {
        if (choice >= 1 && choice <= FORMATION_FACTORIES.size()) {
            return;
        }
        throw new IllegalArgumentException("상차림 번호는 1~4 사이여야 합니다.");
    }

    private Piece choosePiece(Board board, TeamColor currentTurn) {
        List<Map.Entry<Position, Piece>> pieces = board.findPiecesByTeam(currentTurn);
        outputView.printPieceOptions(pieces);

        int pieceChoice = inputView.readPieceChoice(currentTurn);
        return getSelectedPiece(pieces, pieceChoice);
    }

    private Optional<Route> chooseRoute(Board board, Piece selectedPiece) {
        List<Route> routes = board.findMovableRoutes(selectedPiece);
        validateMovableRoutes(routes);
        outputView.printRouteOptions(routes);

        int routeChoice = inputView.readRouteChoice();
        if (routeChoice == 0) {
            return Optional.empty();
        }
        return Optional.of(getSelectedRoute(routes, routeChoice));
    }

    private void validateMovableRoutes(List<Route> routes) {
        if (routes.isEmpty()) {
            throw new IllegalArgumentException("선택한 기물은 이동 가능한 경로가 없습니다.");
        }
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

    private static List<FormationFactory> createFormationFactories() {
        List<FormationFactory> formationFactories = new ArrayList<>();
        formationFactories.add(InnerFormationStrategy::new);
        formationFactories.add(OuterFormationStrategy::new);
        formationFactories.add(LeftFormationStrategy::new);
        formationFactories.add(RightFormationStrategy::new);
        return List.copyOf(formationFactories);
    }

    @FunctionalInterface
    private interface FormationFactory {
        InitialFormationStrategy create();
    }
}
