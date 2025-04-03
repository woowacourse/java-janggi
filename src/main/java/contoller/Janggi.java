package contoller;

import static view.InputView.choiceSetUp;
import static view.InputView.movePointInput;
import static view.InputView.selectGameVersion;
import static view.OutputVIew.displayGameIsOver;
import static view.OutputVIew.displayJanggiBoard;
import static view.OutputVIew.displayJanggiScore;

import dao.JanggiDao;
import java.util.List;
import model.Point;
import model.Team;
import model.janggiboard.JanggiBoard;
import model.janggiboard.JanggiBoardSetUp;
import view.OutputVIew;

public class Janggi {

    JanggiDao janggiDao = new JanggiDao();
    JanggiBoard janggiBoard;
    public void play() {
        int gameVersion = selectGameVersion();
        int turn = 0;
        if (gameVersion == 1) {
        int setUpChoice = choiceSetUp();
            janggiBoard = switch (setUpChoice) {
                case 1 -> new JanggiBoard(JanggiBoardSetUp.INNER_SANG, janggiDao);
                case 2 -> new JanggiBoard(JanggiBoardSetUp.OUTER_SANG, janggiDao);
                case 3 -> new JanggiBoard(JanggiBoardSetUp.LEFT_SANG, janggiDao);
                case 4 -> new JanggiBoard(JanggiBoardSetUp.RIGHT_SANG, janggiDao);
            default -> throw new IllegalArgumentException("다시 입력하세요.");
            };
        }
        if (gameVersion == 2) {
            janggiBoard = new JanggiBoard(janggiDao);
            turn = janggiDao.getGameTurn();
        }

        displayJanggiBoard(janggiBoard);
        displayJanggiScore(janggiBoard.getTeamScore(Team.BLUE), janggiBoard.getTeamScore(Team.RED));
        for (int i = turn; true; i++) {
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
                if (janggiBoard.isKingDead()) {
                    displayGameIsOver(janggiBoard.getWinner());
                    break;
                }
            } catch (Exception e) {
                OutputVIew.displayErrorMessage(e.getMessage());
                i--;
                continue;
            }
            displayJanggiBoard(janggiBoard);
            displayJanggiScore(janggiBoard.getTeamScore(Team.BLUE), janggiBoard.getTeamScore(Team.RED));
        }
    }
}
