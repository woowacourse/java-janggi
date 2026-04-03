import board.SangSetupType;
import core.JanggiGame;
import pieces.Side;
import position.Position;
import util.Retry;
import view.DisplayBoard;
import view.JanggiView;

public class Application {

    public static void main(String[] args) {
        JanggiView view = new JanggiView();
        SangSetupType choSangSetupType = view.askSangSetupUntilSuccess(Side.CHO);
        SangSetupType hanSangSetupType = view.askSangSetupUntilSuccess(Side.HAN);
        JanggiGame game = JanggiGame.of(choSangSetupType, hanSangSetupType);

        new Application(game, view).run();
    }

    private JanggiGame game;
    private final JanggiView view;

    public Application(JanggiGame game, JanggiView view) {
        this.game = game;
        this.view = view;
    }

    public void run() {
        while (!game.isOver()) {
            moveUntilSuccess();
        }
        view.printGameIsOver(game.getWinnerSide());
    }

    private void moveUntilSuccess() {
        game = Retry.untilSuccess(() -> {
            printGameStatus();

            Position departure = view.askDeparture();
            Position destination = view.askDestination();
            return game.move(departure, destination);
        });
    }

    private void printGameStatus() {
        view.printBoard(DisplayBoard.of(game.getBoard()));

        final Side turnSide = game.getTurnSide();
        final Side otherTurnSide = turnSide.other();

        view.printTurnSide(turnSide);
        view.printScore(turnSide, game.calculateScoreOf(turnSide));
        view.printScore(otherTurnSide, game.calculateScoreOf(otherTurnSide));
    }
}
