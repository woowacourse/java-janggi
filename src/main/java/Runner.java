import java.util.List;
import java.util.Map;

import domain.Board;
import domain.Piece;
import domain.Position;
import domain.Route;
import domain.TeamColor;
import domain.TurnManager;
import io.InputView;
import io.OutputView;
import strategy.formation.InitialFormationStrategy;
import strategy.formation.InnerFormationStrategy;
import strategy.formation.LeftFormationStrategy;
import strategy.formation.OuterFormationStrategy;
import strategy.formation.RightFormationStrategy;

public class Runner {

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
                int choice = inputView.readFormationChoice(teamColor);
                if (choice == 1) {
                    return new InnerFormationStrategy();
                }
                if (choice == 2) {
                    return new OuterFormationStrategy();
                }
                if (choice == 3) {
                    return new LeftFormationStrategy();
                }
                if (choice == 4) {
                    return new RightFormationStrategy();
                }
                throw new IllegalArgumentException("상차림 번호는 1~4 사이여야 합니다.");
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
                List<Map.Entry<Position, Piece>> pieces = board.findPiecesByTeam(currentTurn);
                outputView.printPieceOptions(pieces);

                int pieceChoice = inputView.readPieceChoice(currentTurn);
                Piece selectedPiece = getSelectedPiece(pieces, pieceChoice);

                List<Route> routes = board.findMovableRoutes(selectedPiece);
                if (routes.isEmpty()) {
                    throw new IllegalArgumentException("선택한 기물은 이동 가능한 경로가 없습니다.");
                }

                outputView.printRouteOptions(routes);
                int routeChoice = inputView.readRouteChoice();

                if (routeChoice == 0) {
                    continue;
                }

                Position destination = getSelectedRoute(routes, routeChoice).endPos();
                board.move(selectedPiece, destination);
                outputView.printMoveResult(selectedPiece, destination);
                turnManager.progressTurn();
                return;
            } catch (RuntimeException exception) {
                outputView.printError(exception.getMessage());
            }
        }
    }

    private Piece getSelectedPiece(List<Map.Entry<Position, Piece>> pieces, int pieceChoice) {
        if (pieceChoice < 1 || pieceChoice > pieces.size()) {
            throw new IllegalArgumentException("기물 번호가 범위를 벗어났습니다.");
        }
        return pieces.get(pieceChoice - 1).getValue();
    }

    private domain.Route getSelectedRoute(List<Route> routes, int routeChoice) {
        if (routeChoice < 1 || routeChoice > routes.size()) {
            throw new IllegalArgumentException("경로 번호가 범위를 벗어났습니다.");
        }
        return routes.get(routeChoice - 1);
    }
}
