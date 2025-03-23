package contoller;

import static model.Team.BLUE;
import static model.Team.RED;
import static model.janggiboard.JanggiBoardSetUp.INNER_ELEPHANT;
import static model.janggiboard.JanggiBoardSetUp.LEFT_ELEPHANT;
import static model.janggiboard.JanggiBoardSetUp.OUTER_ELEPHANT;
import static model.janggiboard.JanggiBoardSetUp.RIGHT_ELEPHANT;
import static view.InputView.choiceSetUp;
import static view.InputView.movePointInput;
import static view.OutputVIew.displayErrorMessage;
import static view.OutputVIew.displayJanggiBoard;

import java.util.List;
import model.Point;
import model.Team;
import model.janggiboard.JanggiBoard;

public class Janggi {

    public void play() {
        int setUpChoice = choiceSetUp();
        JanggiBoard janggiBoard = switch (setUpChoice) {
            case 1 -> new JanggiBoard(INNER_ELEPHANT);
            case 2 -> new JanggiBoard(OUTER_ELEPHANT);
            case 3 -> new JanggiBoard(LEFT_ELEPHANT);
            case 4 -> new JanggiBoard(RIGHT_ELEPHANT);
            default -> throw new IllegalArgumentException("다시 입력하세요.");
        };

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
        if (choTurn) {
            return BLUE;
        }
        return RED;
    }
}
