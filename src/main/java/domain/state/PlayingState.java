package domain.state;

import domain.command.Command;
import domain.game.JanggiGame;
import domain.vo.Coordinate;
import io.OutputView;

public class PlayingState implements GameState {

    @Override
    public GameState handle(JanggiGame game, Command command) {
        game.move(Coordinate.toCoordinate(command.getValue()));
        game.nextTurn();
        return this;
    }

    @Override
    public void display(JanggiGame game, OutputView outputView) {
        outputView.printBoard(game.getBoard(), game.getTurn());
        outputView.printPieceMovement(game.getTurn());
    }
}