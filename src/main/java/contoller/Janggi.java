package contoller;

import static view.InputView.choMoveInput;
import static view.OutputVIew.displayJanggiBoard;

import java.util.Scanner;
import model.JanggiBoard;
import model.JanggiBoardSetUp;
import model.Team;
import view.OutputVIew;

public class Janggi {
    public void play() {
        System.out.println("""
                상차림을 선택하세요,
                1. 안상차림 (마상상마)
                2. 바깥상차림 (상마마상)
                3. 왼상차림 (상마상마)
                4. 오른상차림 (마상마상)
                """);
        Scanner sc = new Scanner(System.in);
        int setUpChoice = Integer.parseInt(sc.nextLine());
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
                choMoveInput(sc, janggiBoard, team);
            } catch (Exception e) {
                OutputVIew.displayErrorMessage(e.getMessage());
                i--;
                continue;
            }
            displayJanggiBoard(janggiBoard);
        }
    }
}
