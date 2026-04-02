package janggi.controller;

import janggi.domain.Piece;
import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.board.BoardFactory;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class JanggiGame {

    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private boolean isChoTurn = true;

    public void run() {
        Board board = initializeBoard();
        outputView.printBoard(board.getBoard());
        while (true) {
            isChoTurn = playTurn(board);
        }
    }

    private boolean playTurn(Board board) {
        outputView.printTurnMessage(isChoTurn);
        Position movePiecePosition = choosePieceToMove(board);
        List<Position> availablePositions = board.findAvailablePositions(movePiecePosition);
        outputView.printAvailablePositions(board.getBoard(), availablePositions);
        Position targetPosition = chooseTargetPosition(board, movePiecePosition);
        board.movePiece(movePiecePosition, targetPosition);
        outputView.printBoard(board.getBoard());
        isChoTurn = !isChoTurn;
        return isChoTurn;
    }

    private Board initializeBoard() {
        Map<Position, Piece> initBoard = BoardFactory.settingUpBoard();
        return new Board(initBoard);
    }

    private Position chooseTargetPosition(Board board, Position movePiecePosition) {
        return retry(() -> {
            outputView.printMoveChoiceInfo();
            Position position = inputView.readPosition();
            board.validateDestination(movePiecePosition, position);
            return position;
        });
    }

    private Position choosePieceToMove(Board board) {
        return retry(() -> {
            outputView.printMoveInfo();
            Position position = inputView.readPosition();
            board.findAvailablePositions(position);
            return position;
        });
    }

    private <T> T retry(Supplier<T> inputFunction) {
        while (true) {
            try {
                return inputFunction.get();
            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }
}
