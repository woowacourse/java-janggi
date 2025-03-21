package object.view;

import java.util.Scanner;
import object.piece.Team;

public class GameView {
    private final Scanner scanner;

    public GameView() {
        this.scanner = new Scanner(System.in);
    }

    public void printTurn(Team team) {
        System.out.printf("%s 차례입니다.%n", team.getType());
    }

    public void printChangePieceNotImplement() {
        System.out.println("상차림 기능은 아직 구현이 안되었습니다.");
    }
}
