package janggi.view;

import janggi.domain.piece.Position;
import java.util.Scanner;

public class InputView {

    private Scanner scanner = new Scanner(System.in);

    public Position inputPiecePosition() {
        System.out.println("선택할 말의 위치를 골라주세요. 예시) 1 5");
        String input = scanner.nextLine();
        String[] splittedInput = input.split(" ");
        return new Position(Integer.parseInt(splittedInput[0]), Integer.parseInt(splittedInput[1]));
    }

    public Position inputDestination() {
        System.out.println("움직일 장소를 입력해주세요");
        String input = scanner.nextLine();
        String[] splittedInput = input.split(" ");
        return new Position(Integer.parseInt(splittedInput[0]), Integer.parseInt(splittedInput[1]));
    }
}
