package domain.state;

import domain.board.Board;
import domain.game.JanggiGame;
import domain.game.Turn;
import domain.piece.Team;
import domain.rule.CheckDetector;
import domain.setup.Command;
import io.OutputView;

public class PlayingState implements GameState {

    private final CheckDetector checkDetector = new CheckDetector();

    @Override
    public GameState handle(JanggiGame game, Command command) {
        game.move(command.toCoordinate());
        return this;
    }

    @Override
    public void display(JanggiGame game, OutputView outputView) {
        Board board = game.getBoard();
        Turn turn = game.getTurn();
        Team currentTeam = turn.team();

        if (checkDetector.isInCheck(board, currentTeam)) {
            outputView.printCheckMessage(currentTeam);
        }

        outputView.printBoard(board, turn);
        outputView.printPieceMovement(turn);
    }
}
