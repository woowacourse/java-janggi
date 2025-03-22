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
        playerTurn(janggiBoard);
    }

    private void playerTurn(JanggiBoard janggiBoard) {
        boolean isGameOver = false;
        boolean choTurn = true;
        while (!isGameOver) {
            Team team = decideTeam(choTurn);
            try {
                List<Point> movePoints = movePointInput(team);
                if (janggiBoard.isNotMyTeamPoint(movePoints.getFirst(), team)) {
                    throw new IllegalArgumentException("아군 장기말만 움직일 수 있습니다.");
                }
                boolean isCriticalPoint = janggiBoard.isCriticalPoint(movePoints.getLast(), team);
                boolean moveSuccess = janggiBoard.movePiece(movePoints.getFirst(), movePoints.getLast());
                if (isCriticalPoint && moveSuccess) {
                    isGameOver = true;
                }
                choTurn = !choTurn;
            } catch (IllegalArgumentException e) {
                displayErrorMessage(e.getMessage());
            }
            displayJanggiBoard(janggiBoard);
        }
    }

    private Team decideTeam(boolean choTurn) {
        Team team = Team.RED;
        if (choTurn) {
            team = Team.BLUE;
        }
        return team;
    }
}
