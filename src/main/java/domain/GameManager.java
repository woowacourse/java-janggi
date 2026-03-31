package domain;

import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.Formation;
import domain.board.FormationCommand;
import domain.player.Name;
import domain.player.Players;
import java.util.List;
import java.util.function.Supplier;
import view.InputView;
import view.OutputView;

public class GameManager {
    private final InputView inputView;
    private final OutputView outputView;

    public GameManager(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void play() {
        Game game = initializeGame();
        outputView.printBoard(game.getBoard());
        while (!game.isOver()) {
            playTurn(game);
        }
        outputView.printWinner(game.getWinner());
    }

    private Game initializeGame() {
        Name choName = getPlayerName(Side.CHO);
        Players players = retry(() -> {
            Name hanName = getPlayerName(Side.HAN);
            return Players.createInitial(choName, hanName);
        });
        Board board = BoardFactory.create(getFormation(Side.CHO), getFormation(Side.HAN));
        return new Game(board, players);
    }

    private Name getPlayerName(Side side) {
        return retry(() -> InputParser.parseName(inputView.readPlayerName(side)));
    }

    private Formation getFormation(Side side) {
        return retry(() -> Formation.from(FormationCommand.from(inputView.readFormation(side))));
    }

    private void playTurn(Game game) {
        Position from = selectPiecePosition(game);
        retry(() -> {
            Position to = InputParser.parsePosition(inputView.readTargetPosition());
            game.move(from, to);
        });
        outputView.printBoard(game.getBoard());
    }

    private Position selectPiecePosition(Game game) {
        return retry(() -> {
            Position position = InputParser.parsePosition(inputView.readSourcePosition(game.getCurrentSide()));
            List<Position> destinations = game.selectSource(position).getPositions();
            outputView.printDestinations(destinations);
            return position;
        });
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
