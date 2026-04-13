import database.ConnectionManager;
import database.DatabaseConfig;
import database.H2GameRepository;
import domain.board.Board;
import domain.piece.Piece;
import domain.board.PiecePosition;
import domain.board.Position;
import domain.board.Route;
import domain.game.Game;
import domain.game.FormationType;
import domain.game.GameRepository;
import domain.game.SavedGame;
import domain.game.TurnResult;
import domain.piece.TeamColor;
import domain.score.PieceScoreCalculator;
import io.InputView;
import io.OutputView;
import java.util.List;
import java.util.Optional;

public class GameRunner {
    private static final int FIRST_OPTION_NUMBER = 1;
    private static final int RESUME_GAME_OPTION_NUMBER = 1;
    private static final int NEW_GAME_OPTION_NUMBER = 2;
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
    private final GameRepository gameRepository;
    private final NewGameFactory newGameFactory;
    private final PieceScoreCalculator pieceScoreCalculator;

    public GameRunner() {
        this(new H2GameRepository(new ConnectionManager(new DatabaseConfig())));
    }

    public GameRunner(GameRepository gameRepository) {
        this(new InputView(), new OutputView(), gameRepository, new NewGameFactory(), new PieceScoreCalculator());
    }

    public GameRunner(InputView inputView, OutputView outputView, GameRepository gameRepository, NewGameFactory newGameFactory) {
        this(inputView, outputView, gameRepository, newGameFactory, new PieceScoreCalculator());
    }

    public GameRunner(InputView inputView, OutputView outputView, GameRepository gameRepository,
                      NewGameFactory newGameFactory, PieceScoreCalculator pieceScoreCalculator) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.gameRepository = gameRepository;
        this.newGameFactory = newGameFactory;
        this.pieceScoreCalculator = pieceScoreCalculator;
    }

    public void run() {
        outputView.printGameStart();
        SavedGame savedGame = loadOrCreateGame();
        Game game = savedGame.game();
        printBoard(game.board());

        while (game.isInProgress()) {
            savedGame = playTurn(savedGame);
            game = savedGame.game();
        }
    }

    private SavedGame loadOrCreateGame() {
        return gameRepository.findInProgress()
                .map(this::chooseGameToStart)
                .orElseGet(this::createNewGame);
    }

    private SavedGame chooseGameToStart(SavedGame inProgressGame) {
        while (true) {
            outputView.printGameStartOptions();
            try {
                int gameStartChoice = inputView.readGameStartChoice();
                validateGameStartChoice(gameStartChoice);
                if (gameStartChoice == RESUME_GAME_OPTION_NUMBER) {
                    return inProgressGame;
                }
                gameRepository.deleteInProgress();
                return createNewGame();
            } catch (IllegalArgumentException exception) {
                outputView.printError("시작 옵션 입력이 올바르지 않습니다.");
            }
        }
    }

    private SavedGame createNewGame() {
        FormationType choFormation = chooseFormation(TeamColor.CHO);
        FormationType hanFormation = chooseFormation(TeamColor.HAN);
        return gameRepository.save(newGameFactory.create(choFormation, hanFormation));
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

    private SavedGame playTurn(SavedGame savedGame) {
        final Game game = savedGame.game();
        final Board board = game.board();
        final TeamColor currentTurn = game.currentTurn();
        outputView.printCurrentTurn(currentTurn);
        printBoard(board);

        while (true) {
            try {
                final Piece selectedPiece = choosePiece(board, currentTurn);
                final Optional<Route> selectedRoute = chooseRoute(board, selectedPiece);
                if (selectedRoute.isEmpty()) {
                    continue;
                }

                final Position destination = selectedRoute.get().endPos();
                final TurnResult turnResult = game.move(selectedPiece, destination);
                outputView.printMoveResult(selectedPiece, destination);
                final SavedGame updatedGame = gameRepository.save(savedGame);
                turnResult.winner().ifPresent(outputView::printWinner);
                return updatedGame;
            } catch (IllegalArgumentException exception) {
                outputView.printError(exception.getMessage());
            }
        }
    }

    private FormationType createFormation(int choice) {
        validateFormationChoice(choice);
        return FORMATIONS.get(choice - ZERO_BASE_INDEX_OFFSET);
    }

    private void validateGameStartChoice(int choice) {
        if (choice == RESUME_GAME_OPTION_NUMBER || choice == NEW_GAME_OPTION_NUMBER) {
            return;
        }
        throw new IllegalArgumentException("시작 옵션 번호는 1 또는 2여야 합니다.");
    }

    private void validateFormationChoice(int choice) {
        if (choice >= FIRST_OPTION_NUMBER && choice <= FORMATIONS.size()) {
            return;
        }
        throw new IllegalArgumentException("상차림 번호는 1~4 사이여야 합니다.");
    }

    private void printBoard(Board board) {
        outputView.printBoard(board, pieceScoreCalculator.calculate(board));
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
