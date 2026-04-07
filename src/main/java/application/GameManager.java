package application;

import domain.Game;
import domain.Position;
import domain.Side;
import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.Formation;
import domain.player.Name;
import domain.player.Players;
import domain.score.RemainingPieceScorePolicy;
import java.util.List;
import java.util.function.Supplier;
import parser.InputParser;
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
        printScore(game);
        while (!game.isOver()) {
            playTurn(game);
        }
        printFinalScore(game);
        outputView.printWinner(game.getWinner());
    }

    private Game initializeGame() {
        Name choName = getPlayerName(Side.CHO);
        Players players = retry(() -> {
            Name hanName = getPlayerName(Side.HAN);
            return Players.createInitial(choName, hanName);
        });
        Board board = BoardFactory.create(getFormation(Side.CHO), getFormation(Side.HAN));
        return new Game(board, players, new RemainingPieceScorePolicy());
    }

    private Name getPlayerName(Side side) {
        return retry(() -> InputParser.parseName(inputView.readPlayerName(side)));
    }

    private Formation getFormation(Side side) {
        return retry(() -> InputParser.parseFormation(inputView.readFormation(side)));
    }

    private void playTurn(Game game) {
        Position source = selectPiecePosition(game);
        retry(() -> {
            Position target = InputParser.parsePosition(inputView.readTargetPosition());
            game.move(source, target);
        });
        outputView.printBoard(game.getBoard());
        printScore(game);
    }

    private void printScore(Game game) {
        outputView.printScore(game.getScore(Side.CHO), game.getScore(Side.HAN));
    }

    private void printFinalScore(Game game) {
        outputView.printFinalScore(game.getScore(Side.CHO), game.getScore(Side.HAN));
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
