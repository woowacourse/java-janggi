package view;

import java.util.Scanner;

public class InputView {
    private final Scanner sc = new Scanner(System.in);

    public String inputMovePiece() {
        System.out.println("어떤 기물을 옮기시겠습니까?");
        return sc.nextLine();
    }

    public String inputTargetPosition() {
        System.out.println("어디로 옮기시겠습니까?");
        return sc.nextLine();
    }
}
