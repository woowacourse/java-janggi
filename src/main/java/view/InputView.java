package view;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import model.janggi_board.JanggiBoard;
import model.Point;
import model.Team;

public class InputView {
    public static void choMoveInput(Scanner sc, JanggiBoard janggiBoard, Team team) {
        System.out.printf("%s 나라의 차례\n", team.getTeam());
        System.out.println("이동할 기물의 위치를 선택하세요.");
        List<Integer> beforePointInput = Arrays.stream(sc.nextLine().split(" ")).map(Integer::parseInt).toList();
        Point beforePoint = Point.of(beforePointInput.get(0)-1, beforePointInput.get(1)-1);
        System.out.println("이동될 위치를 선택하세요.");
        List<Integer> targetPointInput = Arrays.stream(sc.nextLine().split(" ")).map(Integer::parseInt).toList();
        Point targetPoint = Point.of(targetPointInput.get(0)-1, targetPointInput.get(1)-1);
        janggiBoard.move(beforePoint, targetPoint);
    }
}
