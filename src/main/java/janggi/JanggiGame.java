package janggi;

import janggi.dao.JanggiDAO;
import janggi.domain.Position;
import janggi.domain.Team;
import janggi.domain.board.Board;
import janggi.domain.turn.GameOverTurn;
import janggi.domain.turn.Turn;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.List;
import java.util.function.Supplier;

public class JanggiGame {

    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private final Board board;
    private final JanggiDAO janggiDAO = new JanggiDAO();

    public JanggiGame(Board board) {
        this.board = board;
    }

    public void start(Turn currentTurn) {
        outputView.printBoard(board.getBoard());
        outputView.printInitialNotice();
        while (!currentTurn.isFinished()) {
            currentTurn = playTurn(currentTurn);
        }
        outputView.printWinner(currentTurn.getTeam());
    }

    private Turn playTurn(Turn currentTurn) {
        outputView.printTurnMessage(currentTurn.getTeam());

        Position sourcePosition = choosePieceToMove(currentTurn);

        if (sourcePosition == null) {
            return decideWinnerByScore();
        }

        List<Position> availablePositions = board.findAvailablePositions(sourcePosition);
        outputView.printAvailablePositions(board.getBoard(), availablePositions);

        Position targetPosition = chooseTargetPosition(sourcePosition);
        Turn nextTurn = currentTurn.move(sourcePosition, targetPosition, board);
        outputView.printBoard(board.getBoard());
        janggiDAO.saveGame(nextTurn, board);

        return nextTurn;
    }

    private Turn decideWinnerByScore() {
        double choScore = board.calculateScore(Team.CHO);
        double hanScore = board.calculateScore(Team.HAN) + 1.5;

        if (choScore > hanScore) {
            return new GameOverTurn(Team.CHO);
        }
        return new GameOverTurn(Team.HAN);
    }


    private Position chooseTargetPosition(Position movePiecePosition) {
        return retry(() -> {
            outputView.printMoveChoiceInfo();
            String input = inputView.readString();
            Position position = inputView.parsePosition(input);

            board.validateDestination(movePiecePosition, position);
            return position;
        });
    }

    private Position choosePieceToMove(Turn turn) {
        return retry(() -> {
            outputView.printMoveInfo();
            String input = inputView.readString();

            if (input.equals("점수계산")) {
                return null;
            }

            Position position = inputView.parsePosition(input);

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
