package janggi.view;

import janggi.exception.ErrorException;
import janggi.piece.Camp;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InputView {

    private final Scanner scanner = new Scanner(System.in);

    public boolean readStartGame() {
        String response = prompt("게임을 시작하시겠습니까? (y/n)");
        return parseYesOrNo(response);
    }

    private boolean parseYesOrNo(String response) {
        if (response.equalsIgnoreCase("y")) {
            return true;
        }
        if (response.equalsIgnoreCase("n")) {
            return false;
        }
        throw new ErrorException("y 또는 n을 입력해야 합니다.");
    }

    public List<String> readMovement(Camp camp) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(System.lineSeparator())
                .append(String.format("[%s의 차례입니다.]", camp.getName()))
                .append(System.lineSeparator())
                .append("이동시킬 기물의 좌표와 도착 지점의 좌표를 입력해 주세요. 예) 03,13");
        String response = prompt(stringBuilder.toString());
        return parseMovement(response);
    }

    private List<String> parseMovement(String response) {
        String[] split = response.split(",", -1);
        if (split.length != 2) {
            throw new ErrorException("출발 좌표와 도착 좌표, 2개의 좌표를 입력해야 합니다.");
        }
        return Arrays.stream(split)
                .map(String::trim)
                .toList();
    }

    private String prompt(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }
}
