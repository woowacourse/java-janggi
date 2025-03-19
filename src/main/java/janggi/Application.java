package janggi;

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
            //현재 장기판 출력
            Board board = Board.init();


            Team team = Team.CHO;
            while (true) {
                //TODO 우승자가 나오면 멈춘다.
                boardView.displayBoard(board);

                boardView.printTeam(team);

                team = team.reverse();

                break;
            }
        }

    }
}
