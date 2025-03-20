package object.view;

import java.util.Scanner;
import object.piece.Team;

public class GameView {
    private final Scanner scanner;

    public GameView() {
        this.scanner = new Scanner(System.in);
    }

    /*
    1. 청 차례인지 홍 차례인지
    2. 상차림 구현안됨 알림
    3.
     */

    public void printTurn(Team team) {
        System.out.printf("%s 차례입니다.%n", team.getType());
    }

    public void printChangePieceNotImplement() {
        System.out.println("상차림 기능은 아직 구현이 안되었습니다.");
    }
}
