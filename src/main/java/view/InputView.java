package view;

import domain.Piece;
import domain.Position;
import domain.Side;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class InputView {
    private final Scanner scanner;

    public InputView(Scanner scanner) {
        this.scanner = scanner;
    }

    public String readPlayerName(Side side) {
        System.out.printf("%s나라 플레이어 이름 입력: ", side.getName());
        return scanner.nextLine();
    }

    public String readFormation(Side side) {
        System.out.printf("%s나라 플레이어 포메이션 입력%n", side.getName());
        printFormations();
        return scanner.nextLine();
    }

    private void printFormations() {
        System.out.println("1. 상마상마");
        System.out.println("2. 마상마상");
        System.out.println("3. 상마마상");
        System.out.println("4. 마상상마");
    }

    public String readPlayerPieceSelection(Side side) {
        System.out.println(side.getName() + "나라 플레이어 차례입니다. 이동 시킬 기물의 위치를 입력하세요.");
        return scanner.nextLine();
    }

    public String readDestination() {
        System.out.println("선택한 기물이 이동할 수 있는 위치입니다. 이동할 위치를 입력하세요.");
        return scanner.nextLine();
    }
}
