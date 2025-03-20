package view;

import model.Piece;
import model.Position;

import java.util.Scanner;

public class InputView {

    private final Scanner scanner = new Scanner(System.in);

    public String printMovePiece() {
        System.out.println("이동할 말을 선택해주세요. ex) 1,4");
        return scanner.nextLine();
    }

    public String printMovePosition(Piece piece) {
        System.out.println(piece +"를 선택했습니다. 이동할 위치를 선택해주세요.");
        return scanner.nextLine();
    }
}
