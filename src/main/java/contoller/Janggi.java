package contoller;

import static view.InputView.choiceSetUp;
import static view.InputView.movePointInput;
import static view.OutputVIew.displayErrorMessage;
import static view.OutputVIew.displayJanggiBoard;

import java.util.List;
import model.Point;
import model.Team;
import model.janggiboard.JanggiBoard;
import model.janggiboard.JanggiBoardSetUp;

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

        boolean continueGame = true;

        for (int i = 0; continueGame; i++) {
            Team team = Team.RED;
            if (i % 2 == 0) {
                team = Team.BLUE;
            }
            try {
                List<Point> movePoints = movePointInput(janggiBoard, team);
                if(janggiBoard.isNotMyTeamPoint(movePoints.getFirst(), team)){
                    throw new IllegalArgumentException("아군 장기말만 움직일 수 있습니다.");
                }
                janggiBoard.move(movePoints.getFirst(), movePoints.getLast());
            } catch (Exception e) {
                displayErrorMessage(e.getMessage());
                i--;
                continue;
            }
            displayJanggiBoard(janggiBoard);
        }
    }
}
