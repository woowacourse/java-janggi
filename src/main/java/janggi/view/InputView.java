package janggi.view;

import janggi.board.Position;
import janggi.view.util.PositionFormatter;

import java.util.Scanner;
import java.util.regex.Pattern;

public class InputView {

    private static final String POSITION_FORMAT_REGEX = "^[a-i][0-9]$";
    private static final Scanner scanner = new Scanner(System.in);

    public Position selectPiece() {
        System.out.println("이동할 기물의 위치를 선택해 주세요. (예: a1)");
        String selectedPiece = scanner.nextLine();

        validateInputPosition(selectedPiece);
        return PositionFormatter.formatStringToPosition(selectedPiece);
    }


    public Position askMovableDestination() {
        System.out.println("기물을 어느 위치로 이동시키겠습니까?");
        String selectedDestination = scanner.nextLine();

        validateInputPosition(selectedDestination);
        return PositionFormatter.formatStringToPosition(selectedDestination);
    }

    private void validateInputPosition(String inputPosition) {
        if (!Pattern.matches(POSITION_FORMAT_REGEX, inputPosition)) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않는 좌표 입력 형식입니다.");
        }
    }

}
