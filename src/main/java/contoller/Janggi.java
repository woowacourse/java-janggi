package contoller;

import static view.InputView.choiceSetUp;
import static view.InputView.movePointInput;
import static view.OutputVIew.displayJanggiBoard;

import java.util.List;
import model.Point;
import model.Team;
import model.janggiboard.JanggiBoard;
import model.janggiboard.JanggiBoardSetUp;
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
                janggiBoard = new JanggiBoard(JanggiBoardSetUp.OUTER_SANG);
                break;
            case 3:
                janggiBoard = new JanggiBoard(JanggiBoardSetUp.LEFT_SANG);
                break;
            case 4:
                janggiBoard = new JanggiBoard(JanggiBoardSetUp.RIGHT_SANG);
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
                List<Point> movePoints = movePointInput(team);
                if(janggiBoard.isNotMyTeamPoint(movePoints.getFirst(), team)){
                    throw new IllegalArgumentException("아군 장기말만 움직일 수 있습니다.");
                }
                janggiBoard.move(movePoints.getFirst(), movePoints.getLast());
            } catch (Exception e) {
                OutputVIew.displayErrorMessage(e.getMessage());
                i--;
                continue;
            }
            displayJanggiBoard(janggiBoard);
        }
    }
}
