package domain.state;

import domain.board.Board;
import domain.game.JanggiGame;
import domain.game.Turn;
import domain.piece.Team;
import domain.rule.BikjangDetector;
import domain.rule.CheckDetector;
import domain.rule.CheckmateDetector;
import domain.setup.Command;
import io.OutputView;

public class PlayingState implements GameState {

    private final CheckDetector checkDetector = new CheckDetector();
    private final CheckmateDetector checkmateDetector = new CheckmateDetector();
    private final BikjangDetector bikjangDetector = new BikjangDetector();

    @Override
    public GameState handle(JanggiGame game, Command command) {
        game.move(command.toCoordinate());
        Team opponent = game.getEnemy();
        if (checkmateDetector.isCheckmate(game.getBoard(), opponent)) {
            return new EndGameState(GameResult.winOf(game.getCurrentTeam()));
        }
        if (bikjangDetector.isBikjang(game.getBoard())) {
            return new BikjangState();
        }
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
