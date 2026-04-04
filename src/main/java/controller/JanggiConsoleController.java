package controller;

import domain.Game;
import domain.MoveCandidate;
import domain.Position;
import domain.Side;
import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.Formation;
import domain.board.FormationCommand;
import domain.player.Name;
import domain.player.Players;
import dto.BoardDto;
import dto.DestinationDto;
import dto.PositionDto;
import java.util.List;
import java.util.function.Supplier;
import view.InputParser;
import view.InputView;
import view.OutputView;

public class JanggiConsoleController {
    private final InputView inputView;
    private final OutputView outputView;

    public JanggiConsoleController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void play() {
        Game game = initializeGame();
        outputView.printBoard(BoardDto.from(game.getBoard()));
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
        return retry(() -> Formation.from(InputParser.parseFormation(inputView.readFormation(side))));
    }

    private void playTurn(Game game) {
        MoveCandidate moveCandidate = selectPiecePosition(game);
        retry(() -> {
            Position target = InputParser.parsePosition(inputView.readTargetPosition());
            game.move(moveCandidate, target);
        });
        outputView.printBoard(BoardDto.from(game.getBoard()));
    }

    private MoveCandidate selectPiecePosition(Game game) {
        return retry(() -> {
            Position position = InputParser.parsePosition(inputView.readSourcePosition(game.getCurrentSide()));
            MoveCandidate moveCandidate = game.selectSource(position);
            outputView.printDestinations(DestinationDto.from(moveCandidate));
            return moveCandidate;
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
