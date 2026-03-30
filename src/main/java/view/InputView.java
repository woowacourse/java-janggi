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

    public String readChoPlayerName() {
        System.out.print("초나라 플레이어 이름 입력: ");
        return scanner.nextLine();
    }

    public String readHanPlayerName() {
        System.out.print("한나라 플레이어 이름 입력: ");
        return scanner.nextLine();
    }

    public String readChoFormation() {
        System.out.println("초나라 플레이어 포메이션 입력");
        printFormations();
        return scanner.nextLine();
    }

    public String readHanFormation() {
        System.out.println("한나라 플레이어 포메이션 입력");
        printFormations();
        return scanner.nextLine();
    }

    private void printFormations() {
        System.out.println("1. 상마상마");
        System.out.println("2. 마상마상");
        System.out.println("3. 상마마상");
        System.out.println("4. 마상상마");
    }

    public String readPlayerPieceSelection(Side side, Map<Position, Piece> board) {
        System.out.println(side.getName() + "나라 플레이어 차례입니다. 이동 시킬 기물의 위치를 입력하세요.");
        /*System.out.printf("궁: %s\n", String.join(", ",
                board.keySet().stream()
                        .filter(position -> board.get(position) instanceof General)
                        .map(Object::toString)
                        .toList()));
        System.out.printf("차: %s\n", String.join(", ",
                board.keySet().stream()
                        .filter(position -> board.get(position) instanceof Chariot)
                        .map(Object::toString)
                        .toList()));
        System.out.printf("포: %s\n", String.join(", ",
                board.keySet().stream()
                        .filter(position -> board.get(position) instanceof Cannon)
                        .map(Object::toString)
                        .toList()));
        System.out.printf("마: %s\n", String.join(", ",
                board.keySet().stream()
                        .filter(position -> board.get(position) instanceof Horse)
                        .map(Object::toString)
                        .toList()));
        System.out.printf("상: %s\n", String.join(", ",
                board.keySet().stream()
                        .filter(position -> board.get(position) instanceof Elephant)
                        .map(Object::toString)
                        .toList()));
        System.out.printf("사: %s\n", String.join(", ",
                board.keySet().stream()
                        .filter(position -> board.get(position) instanceof Guard)
                        .map(Object::toString)
                        .toList()));
        System.out.printf("졸: %s\n", String.join(", ",
                board.keySet().stream()
                        .filter(position -> board.get(position) instanceof Soldier)
                        .map(Object::toString)
                        .toList()));*/
        return scanner.nextLine();
    }

    public String readDestination(List<Position> destinations) {
        System.out.println("선택한 기물이 이동할 수 있는 위치입니다. 이동할 위치를 입력하세요.");
        System.out.println(destinations);
        return scanner.nextLine();
    }
}
