import domain.Board;
import domain.Piece;
import domain.Position;
import domain.Route;
import domain.TeamColor;
import domain.TurnManager;
import io.InputView;
import io.OutputView;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import strategy.formation.InitialFormationStrategy;
import strategy.formation.InnerFormationStrategy;
import strategy.formation.LeftFormationStrategy;
import strategy.formation.OuterFormationStrategy;
import strategy.formation.RightFormationStrategy;

public class Runner {
    private static final int FIRST_OPTION_NUMBER = 1;
    private static final int BACK_OPTION_NUMBER = 0;
    private static final int ZERO_BASE_INDEX_OFFSET = 1;
    private static final List<InitialFormationStrategy> FORMATIONS = List.of(
            new InnerFormationStrategy(),
            new OuterFormationStrategy(),
            new LeftFormationStrategy(),
            new RightFormationStrategy()
    );

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
        return FORMATIONS.get(choice - ZERO_BASE_INDEX_OFFSET);
    }

    private void validateFormationChoice(int choice) {
        if (choice >= FIRST_OPTION_NUMBER && choice <= FORMATIONS.size()) {
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
        if (routeChoice == BACK_OPTION_NUMBER) {
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
        if (pieceChoice < FIRST_OPTION_NUMBER || pieceChoice > pieces.size()) {
            throw new IllegalArgumentException("기물 번호가 범위를 벗어났습니다.");
        }
        return pieces.get(pieceChoice - ZERO_BASE_INDEX_OFFSET).getValue();
    }

    private Route getSelectedRoute(List<Route> routes, int routeChoice) {
        if (routeChoice < FIRST_OPTION_NUMBER || routeChoice > routes.size()) {
            throw new IllegalArgumentException("경로 번호가 범위를 벗어났습니다.");
        }
        return routes.get(routeChoice - ZERO_BASE_INDEX_OFFSET);
    }
}
