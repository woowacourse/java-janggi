package janggi;

import janggi.point.Point;
import janggi.view.BoardView;
import janggi.view.InputView;

public class Application {

    private final InputView inputView;
    private final BoardView boardView;

    private Application() {
        inputView = new InputView();
        boardView = new BoardView();
    }

    public static void main(String[] args) {
        Application application = new Application();
        application.run();
    }

    private void run() {
        if (inputView.readGameStart()) {
            Board board = Board.init();

            Team team = Team.CHO;
            while (true) { //TODO 우승자가 나오면 멈춘다.
                boardView.displayBoard(board);

                boardView.printTeam(team);
                handleMoveException(() -> {
                    Point startPoint = inputView.readStartPoint();
                    Point targetPoint = inputView.readTargetPoint();
                    board.move(startPoint, targetPoint);
                });

                team = team.reverse();
            }
        }

    }

    private void handleMoveException(Runnable action) {
        while (true) {
            try {
                action.run();
                return;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
