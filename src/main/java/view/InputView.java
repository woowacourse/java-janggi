package view;

import domain.Coordinate;
import java.util.Scanner;

public class InputView {

    private final static Scanner scanner = new Scanner(System.in);

    public Coordinate readMovePiece(String countryName) {
        System.out.println(countryName + "의 옮길 기물의 좌표를 입력해주세요.");
        String coordinate = scanner.nextLine();
        return new Coordinate(
                Integer.parseInt(coordinate.split(",")[0]),
                Integer.parseInt(coordinate.split(",")[1]));
    }

    public Coordinate readMoveDestination() {
        System.out.println("기물을 옮길 좌표를 입력해주세요.");
        String coordinate = scanner.nextLine();
        return new Coordinate(
                Integer.parseInt(coordinate.split(",")[0]),
                Integer.parseInt(coordinate.split(",")[1]));
    }
}
