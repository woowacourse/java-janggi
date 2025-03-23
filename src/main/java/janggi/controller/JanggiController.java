package janggi.controller;

import janggi.domain.Board;
import janggi.domain.Route;
import janggi.domain.Team;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Position;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {

        Board board = new Board();
        List<Piece> pieces = board.getPieces();
        outputView.printBoard(pieces);

        while (true) {
            Team currentTurn = board.getTurn();
            outputView.printTurn(currentTurn);

            // 말 고르기 --> 해당 위치에 말이 없으면 재입력
            Piece selectedPiece = retryUntilSuccess(() -> selectPiece(board));

            // 해당 말이 갈 수 있는 위치 계산
            Set<Route> possibleRoutes = board.findPossibleRoutes(selectedPiece);
            outputView.printPossibleRoutes(possibleRoutes);

            // 목적지 입력받기 --> 해당 위치가 잘못된 위치라면 재입력
            Position destination = retryUntilSuccess(inputView::inputDestination);

            board.movePiece(destination, selectedPiece);
            outputView.printBoard(pieces);
            board.changeTurn();
        }
    }

    private Piece selectPiece(Board board) {
        Position position = inputView.inputPiecePosition();
        return board.selectPiece(position);
    }

    private <T> T retryUntilSuccess(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
