package janggi;

import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.turn.ChoTurn;
import janggi.domain.turn.Turn;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.List;
import java.util.function.Supplier;

public class JanggiGame {

    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private final Board board;

    public JanggiGame(Board board) {
        this.board = board;
    }

    public void run() {
        Turn currentTurn = new ChoTurn();
        outputView.printBoard(board.getBoard());
        while (true) {
            currentTurn = playTurn(currentTurn);
        }
    }

    private Turn playTurn(Turn currentTurn) {
        outputView.printTurnMessage(currentTurn.getTeam());

        Position sourcePosition = choosePieceToMove(currentTurn);
        List<Position> availablePositions = board.findAvailablePositions(sourcePosition);
        outputView.printAvailablePositions(board.getBoard(), availablePositions);

        Position targetPosition = chooseTargetPosition(sourcePosition);
        Turn nextTurn = currentTurn.move(sourcePosition, targetPosition, board);
        outputView.printBoard(board.getBoard());

        return nextTurn;
    }

    private Position chooseTargetPosition(Position movePiecePosition) {
        return retry(() -> {
            outputView.printMoveChoiceInfo();
            Position position = inputView.readPosition();
            board.validateDestination(movePiecePosition, position);
            return position;
        });
    }

    private Position choosePieceToMove(Turn turn) {
        return retry(() -> {
            outputView.printMoveInfo();
            Position position = inputView.readPosition();
            turn.validateIsNull(board.getPiece(position));
            turn.validateTurn(board.getPiece(position));
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
