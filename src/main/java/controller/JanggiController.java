package controller;

import domain.board.Board;
import domain.board.ChoWings;
import domain.board.HanWings;
import domain.board.InitialPieces;
import domain.board.Intersection;
import domain.game.JanggiGame;
import domain.game.Side;
import domain.piece.AlivePieces;
import domain.piece.Piece;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import util.RetryUtil;
import view.ApplicationView;

public class JanggiController {

    private final ApplicationView view = new ApplicationView();

    public void run() {
        Board board = initBoard();
        JanggiGame game = new JanggiGame(board);

        while (game.isPlaying()) {
            retryOnIllegalArgument(() -> progressTurn(game));
        }

        view.printWinner(game.getWinner());
    }

    private Board initBoard() {
        ChoWings choWings = retryOnIllegalArgument(view::readChowings);
        HanWings hanWings = retryOnIllegalArgument(view::readHanWings);

        InitialPieces initialPieces = new InitialPieces(hanWings, choWings);
        AlivePieces alivePieces = initialPieces.get();

        return new Board(alivePieces);
    }

    private void progressTurn(JanggiGame game) {
        Map<Intersection, Piece> board = game.getBoard();
        Side currentTurn = game.getCurrentTurn();

        Intersection startIntersection = view.readSelectPieceToMove(board, currentTurn);
        List<Intersection> movableIntersections = game.getMovableIntersections(startIntersection);
        Intersection destination = view.readMovePiece(board, movableIntersections);

        game.movePiece(startIntersection, destination);
    }

    private <T> T retryOnIllegalArgument(Supplier<T> retryableAction) {
        return RetryUtil.retryOnInvalidInput(retryableAction, view::printError);
    }

    private void retryOnIllegalArgument(Runnable retryableAction) {
        RetryUtil.retryOnInvalidInput(retryableAction, view::printError);
    }
}
