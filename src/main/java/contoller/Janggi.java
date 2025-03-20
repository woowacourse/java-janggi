package contoller;

import static view.InputView.choMoveInput;
import static view.InputView.choiceSetUp;
import static view.OutputVIew.displayJanggiBoard;

import model.Team;
import model.janggi_board.JanggiBoard;
import model.janggi_board.JanggiBoardSetUp;
import view.OutputVIew;

public class Janggi {
    public void play() {
        int setUpChoice = choiceSetUp();
        JanggiBoard janggiBoard;
        switch (setUpChoice) {
            case 1:
                janggiBoard = new JanggiBoard(JanggiBoardSetUp.INNER_ELEPHANT);
                break;
            case 2:
                janggiBoard = new JanggiBoard(JanggiBoardSetUp.OUTER_ELEPHANT);
                break;
            case 3:
                janggiBoard = new JanggiBoard(JanggiBoardSetUp.LEFT_ELEPHANT);
                break;
            case 4:
                janggiBoard = new JanggiBoard(JanggiBoardSetUp.RIGHT_ELEPHANT);
                break;
            default:
                throw new IllegalArgumentException("다시 입력하세요.");
        }
        displayJanggiBoard(janggiBoard);

        for (int i = 0; true; i++) {
            Team team = Team.RED;
            if (i % 2 == 0) {
                team = Team.BLUE;
            }
            try {
                choMoveInput(janggiBoard, team);
            } catch (Exception e) {
                OutputVIew.displayErrorMessage(e.getMessage());
                i--;
                continue;
            }
            displayJanggiBoard(janggiBoard);
        }
    }
}
