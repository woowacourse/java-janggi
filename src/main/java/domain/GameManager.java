package domain;

import java.util.List;
import java.util.function.Supplier;
import view.InputView;
import view.OutputView;

public class GameManager {
    private final InputView inputView;
    private final OutputView outputView;
    private Side side;
    private Position from;

    public GameManager(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start() {
        Player choPlayer = retry(() -> InputParser.parsePlayer(inputView.readChoPlayerName()));
        Player hanPlayer = retry(() -> InputParser.parsePlayer(inputView.readChoPlayerName()));

        Formation choFormation = Formation.from(Selection.from(inputView.readChoFormation()));
        Formation hanFormation = Formation.from(Selection.from(inputView.readHanFormation()));
        Board board = new InitialBoardFactory().create(choFormation, hanFormation);
        Game game = new Game(board, choPlayer, hanPlayer);

        side = Side.CHO;
        while (!game.isOver()) {
            outputView.printBoard(game.getBoard());

            List<Position> destinations = retry(() -> {
                from = InputParser.parsePosition(inputView.readPlayerPieceSelection(side, game.getBoard()));
                return game.getPossibleDestinations(from);
            });

            Position to = retry(() -> {
                Position position = InputParser.parsePosition(inputView.readDestination(destinations));
                if (!destinations.contains(position)) {
                    throw new IllegalArgumentException("선택할 수 없는 위치입니다.");
                }
                return position;
            });
            game.movePiece(from, to);

            side = nextTurn();
        }
    }

    public Side nextTurn() {
        if (side.isCho()) return Side.HAN;
        return Side.CHO;
    }

    private <T> T retry(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private void retry(Runnable action) {
        while (true) {
            try {
                action.run();
                return;
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }
}
