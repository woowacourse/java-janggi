import java.util.List;
import java.util.Map;
import java.util.Optional;

import domain.JanggiGame;
import domain.MovableRoutes;
import domain.Piece;
import domain.Position;
import domain.Route;
import domain.TurnOutcome;
import io.InputView;
import io.OutputView;

public class TurnExecutor {

    private final InputView inputView;
    private final OutputView outputView;

    public TurnExecutor(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public TurnOutcome execute(JanggiGame game) {
        outputView.printCurrentTurn(game.currentTurn());
        outputView.printBoard(game.board());
        while (true) {
            TurnOutcome outcome = trySingleTurnAction(game);
            if (outcome != TurnOutcome.RETRY) {
                return outcome;
            }
        }
    }

    private TurnOutcome trySingleTurnAction(JanggiGame game) {
        try {
            return processPieceSelection(game);
        } catch (RuntimeException exception) {
            outputView.printError(exception.getMessage());
            return TurnOutcome.RETRY;
        }
    }

    private TurnOutcome processPieceSelection(JanggiGame game) {
        List<Map.Entry<Position, Piece>> pieces = game.currentTurnPieces();
        outputView.printPieceOptions(pieces);
        int pieceChoice = inputView.readPieceChoice(game.currentTurn());
        Piece selectedPiece = getSelectedPiece(pieces, pieceChoice);
        return followRoutes(game, selectedPiece);
    }

    private TurnOutcome followRoutes(JanggiGame game, Piece selectedPiece) {
        MovableRoutes movable = game.findMovableRoutes(selectedPiece);
        List<Route> routes = movable.routes();
        if (routes.isEmpty()) {
            throw new IllegalArgumentException("선택한 기물은 이동 가능한 경로가 없습니다.");
        }
        printKingNoticeIfApplicable(movable);
        outputView.printRouteOptions(routes);
        int routeChoice = inputView.readRouteChoice();
        return applyRouteChoice(game, selectedPiece, routes, routeChoice);
    }

    private void printKingNoticeIfApplicable(MovableRoutes movable) {
        if (!movable.hasKingDestination()) {
            return;
        }
        outputView.printCheck();
    }

    private TurnOutcome applyRouteChoice(
            JanggiGame game, Piece selectedPiece, List<Route> routes, int routeChoice) {
        if (routeChoice == 0) {
            return TurnOutcome.RETRY;
        }
        return completeMove(game, selectedPiece, routes, routeChoice);
    }

    private TurnOutcome completeMove(JanggiGame game, Piece piece, List<Route> routes, int routeChoice) {
        Route selectedRoute = getSelectedRoute(routes, routeChoice);
        Optional<Piece> captured = game.move(piece, selectedRoute.endPos());
        outputView.printMoveResult(piece, selectedRoute.endPos());
        return resolveOutcome(game, captured);
    }

    private TurnOutcome resolveOutcome(JanggiGame game, Optional<Piece> captured) {
        if (isKingCapture(captured)) {
            outputView.printGameEnd(game.currentTurn());
            return TurnOutcome.GAME_OVER;
        }
        return TurnOutcome.TURN_DONE;
    }

    private static boolean isKingCapture(Optional<Piece> captured) {
        return captured.filter(Piece::isKing).isPresent();
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
