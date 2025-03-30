package janggi.console;

import janggi.domain.game.Game;
import janggi.domain.position.Column;
import janggi.domain.position.Position;
import janggi.domain.position.Row;
import janggi.view.BoardView;
import janggi.view.InputView;
import janggi.view.SystemView;
import java.util.ArrayList;
import java.util.List;

public final class GamePlayConsole {

    private final InputView inputView;
    private final SystemView systemView;
    private final BoardView boardView;

    public GamePlayConsole(final InputView inputView, final SystemView systemView, final BoardView boardView) {
        this.inputView = inputView;
        this.systemView = systemView;
        this.boardView = boardView;
    }

    public GameStatus playTurn(final Game game) {
        String command = inputView.readCommand();
        if (command.equals("end")) {
            return GameStatus.ENDED;
        }
        move(game, command);
        systemView.displayPoints(game);
        boardView.displayBoard(game);
        return game.getStatus();
    }

    private void move(final Game game, final String input) {
        try {
            final List<Integer> moveCommand = parseMoveCommand(input);
            final Position start = new Position(Column.of(moveCommand.get(0)), Row.of(moveCommand.get(1)));
            final Position end = new Position(Column.of(moveCommand.get(2)), Row.of(moveCommand.get(3)));
            game.move(start, end);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private List<Integer> parseMoveCommand(final String input) {
        List<Integer> moveInfo = new ArrayList<>();
        moveInfo.add(Integer.parseInt(String.valueOf(input.charAt(5))));
        moveInfo.add(Integer.parseInt(String.valueOf(input.charAt(6))));
        moveInfo.add(Integer.parseInt(String.valueOf(input.charAt(8))));
        moveInfo.add(Integer.parseInt(String.valueOf(input.charAt(9))));
        return moveInfo;
    }
}
