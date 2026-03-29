package domain.state;

import domain.command.Command;
import domain.game.Board;
import domain.game.JanggiGame;
import domain.game.Turn;
import io.OutputView;

public class PlayingState implements GameState {

    @Override
    public GameState handle(JanggiGame game, Command command) {
        game.move(command.toCoordinate());
        game.nextTurn();
        return this;
    }

    @Override
    public void display(JanggiGame game, OutputView outputView) {
        Board board = game.getBoard();
        Turn turn = game.getTurn();
        outputView.printBoard(board, turn);
        outputView.printPieceMovement(turn);
    }
}