package janggi.controller;

import janggi.domain.Game;
import janggi.domain.space.Position;
import janggi.domain.Side;
import janggi.domain.board.Board;
import janggi.domain.board.BoardFactory;
import janggi.domain.board.Formation;
import janggi.domain.player.Name;
import janggi.domain.player.Players;
import janggi.dto.BoardDto;
import janggi.dto.DestinationDto;
import java.util.function.Supplier;
import janggi.view.InputParser;
import janggi.view.InputView;
import janggi.view.OutputView;

public class ConsoleController {
    private final InputView inputView;
    private final OutputView outputView;

    public ConsoleController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void play() {
        Game game = initializeGame();
        outputView.printBoard(BoardDto.from(game.getBoard()));
        while (game.isPlaying()) {
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
        return retry(() -> Formation.from(InputParser.parseFormation(inputView.readFormation(side))));
    }

    private void playTurn(Game game) {
        Position source = selectPiecePosition(game);
        retry(() -> {
            Position target = InputParser.parsePosition(inputView.readTargetPosition());
            game.move(source, target);
        });
        outputView.printBoard(BoardDto.from(game.getBoard()));
    }

    private Position selectPiecePosition(Game game) {
        return retry(() -> {
            Position position = InputParser.parsePosition(inputView.readSourcePosition(game.getCurrentSide()));
            outputView.printDestinations(DestinationDto.from(game.selectSource(position)));
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
