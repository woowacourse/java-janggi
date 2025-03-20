package janggi.view;

import janggi.board.Position;
import janggi.view.util.PositionFormatter;

import java.util.Scanner;

public class InputView {

    private static final Scanner scanner = new Scanner(System.in);

    public Position selectPiece() {
        System.out.println("이동할 기물의 위치를 선택해 주세요. (예: a1)");
        String selectedPiece = scanner.nextLine();

        return PositionFormatter.formatStringToPosition(selectedPiece);
    }


    public Position askMovableDestination() {
        System.out.println("기물을 어느 위치로 이동시키겠습니까?");
        String selectedDestination = scanner.nextLine();

        return PositionFormatter.formatStringToPosition(selectedDestination);
    }

}
