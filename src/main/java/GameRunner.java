import domain.board.Board;
import domain.board.MoveResult;
import domain.piece.Piece;
import domain.board.PiecePosition;
import domain.board.Position;
import domain.board.Route;
import domain.game.Game;
import domain.game.FormationType;
import domain.piece.TeamColor;
import io.InputView;
import io.OutputView;
import java.util.List;
import java.util.Optional;

public class GameRunner {
    private static final int FIRST_OPTION_NUMBER = 1;
    private static final int BACK_OPTION_NUMBER = 0;
    private static final int ZERO_BASE_INDEX_OFFSET = 1;
    private static final List<FormationType> FORMATIONS = List.of(
            FormationType.INNER,
            FormationType.OUTER,
            FormationType.LEFT,
            FormationType.RIGHT
    );

    private final InputView inputView;
    private final OutputView outputView;
    private final NewGameFactory newGameFactory;

    public GameRunner() {
        this(new InputView(), new OutputView(), new NewGameFactory());
    }

    public GameRunner(InputView inputView, OutputView outputView, NewGameFactory newGameFactory) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.newGameFactory = newGameFactory;
    }

    public void run() {
        outputView.printGameStart();
        Game game = createNewGame();
        outputView.printBoard(game.board());

        while (game.isInProgress()) {
            playTurn(game);
        }
    }

    private Game createNewGame() {
        FormationType choFormation = chooseFormation(TeamColor.CHO);
        FormationType hanFormation = chooseFormation(TeamColor.HAN);
        return newGameFactory.create(choFormation, hanFormation);
    }

    private FormationType chooseFormation(TeamColor teamColor) {
        while (true) {
            outputView.printFormationSelectionPrompt(teamColor);
            try {
                return createFormation(inputView.readFormationChoice(teamColor));
            } catch (IllegalArgumentException exception) {
                outputView.printError("상차림 입력이 올바르지 않습니다.");
            }
        }
    }

    private void playTurn(Game game) {
        final Board board = game.board();
        final TeamColor currentTurn = game.currentTurn();
        outputView.printCurrentTurn(currentTurn);
        outputView.printBoard(board);

        while (true) {
            try {
                final Piece selectedPiece = choosePiece(board, currentTurn);
                final Optional<Route> selectedRoute = chooseRoute(board, selectedPiece);
                if (selectedRoute.isEmpty()) {
                    continue;
                }

                final Position destination = selectedRoute.get().endPos();
                final MoveResult moveResult = board.move(selectedPiece, destination);
                outputView.printMoveResult(selectedPiece, destination);
                if (moveResult.capturedKing()) {
                    game.finish();
                    outputView.printWinner(currentTurn);
                    return;
                }
                game.advanceTurn();
                return;
            } catch (IllegalArgumentException exception) {
                outputView.printError(exception.getMessage());
            }
        }
    }

    private FormationType createFormation(int choice) {
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
        List<PiecePosition> pieces = board.findPiecesByTeam(currentTurn);
        outputView.printPieceOptions(pieces);

        final int pieceChoice = inputView.readPieceChoice(currentTurn);
        return getSelectedPiece(pieces, pieceChoice);
    }

    private Optional<Route> chooseRoute(Board board, Piece selectedPiece) {
        final List<Route> routes = board.findMovableRoutes(selectedPiece);
        validateMovableRoutes(routes);
        outputView.printRouteOptions(routes);

        final int routeChoice = inputView.readRouteChoice();
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

    private Piece getSelectedPiece(List<PiecePosition> pieces, int pieceChoice) {
        if (pieceChoice < FIRST_OPTION_NUMBER || pieceChoice > pieces.size()) {
            throw new IllegalArgumentException("기물 번호가 범위를 벗어났습니다.");
        }
        return pieces.get(pieceChoice - ZERO_BASE_INDEX_OFFSET).piece();
    }

    private Route getSelectedRoute(List<Route> routes, int routeChoice) {
        if (routeChoice < FIRST_OPTION_NUMBER || routeChoice > routes.size()) {
            throw new IllegalArgumentException("경로 번호가 범위를 벗어났습니다.");
        }
        return routes.get(routeChoice - ZERO_BASE_INDEX_OFFSET);
    }
}
