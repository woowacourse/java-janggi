package contoller;

import static model.Team.BLUE;
import static model.Team.RED;
import static model.janggiboard.JanggiBoardSetUp.INNER_ELEPHANT;
import static model.janggiboard.JanggiBoardSetUp.LEFT_ELEPHANT;
import static model.janggiboard.JanggiBoardSetUp.OUTER_ELEPHANT;
import static model.janggiboard.JanggiBoardSetUp.RIGHT_ELEPHANT;
import static view.InputView.choiceGameId;
import static view.InputView.choiceLoadOrNewGame;
import static view.InputView.choiceSetUp;
import static view.InputView.movePointInput;
import static view.OutputVIew.displayJanggiBoard;
import static view.OutputVIew.displayTotalScore;
import static view.OutputVIew.printErrorMessage;

import java.util.List;
import model.Point;
import model.Team;
import model.dao.JanggiBoardDao;
import model.janggiboard.JanggiBoard;

public class Janggi {
    JanggiBoardDao janggiBoardDao = new JanggiBoardDao();

    public void start() {
        int loadOrNewGameChoice = choiceLoadOrNewGame();
        if (loadOrNewGameChoice == 1) {
            loadGame();
        }
        if (loadOrNewGameChoice == 2) {
            startNewGame();
        }
    }

    private void loadGame() {
        int gameId;
        do {
            gameId = choiceGameId();
        } while (janggiBoardDao.existJanggiGame(gameId));

        // dao에서 이미 db에 존재하는 id인지 체크
        // 없으면 재입력 받기

    }

    private void startNewGame() {
        int gameId = choiceGameId();
        // dao에서 이미 db에 존재하는 id인지 체크
        // 이미 존재하면 다시 입력 받기
        int setUpChoice = choiceSetUp();
        JanggiBoard janggiBoard = switch (setUpChoice) {
            case 1 -> new JanggiBoard(INNER_ELEPHANT);
            case 2 -> new JanggiBoard(OUTER_ELEPHANT);
            case 3 -> new JanggiBoard(LEFT_ELEPHANT);
            case 4 -> new JanggiBoard(RIGHT_ELEPHANT);
            default -> throw new IllegalArgumentException("다시 입력하세요.");
        };
        playGame(gameId, janggiBoard, true);
    }


    private void playGame(int gameId, JanggiBoard janggiBoard, boolean choTurn) {
        boolean isGameOver = false;
        do {
            displayTotalScore(janggiBoard.getTotalScore(BLUE), janggiBoard.getTotalScore(RED));
            displayJanggiBoard(janggiBoard);
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
                printErrorMessage(e.getMessage());
            }
        } while (!isGameOver);

    }


    private Team decideTeam(boolean choTurn) {
        if (choTurn) {
            return BLUE;
        }
        return RED;
    }
}
