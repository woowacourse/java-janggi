package io;

import domain.setup.Command;
import java.util.Scanner;

public class InputView {
    private final Scanner scanner = new Scanner(System.in);

    public Command readCommand() {
        String input = validate(scanner.nextLine().trim());
        return new Command(input);
    }

    public int readRoomMenuChoice() {
        String input = validate(scanner.nextLine().trim());
        try {
            int choice = Integer.parseInt(input);
            if (choice < 1 || choice > 2) {
                throw new IllegalArgumentException("[ERROR] 1 또는 2를 입력해주세요.");
            }
            return choice;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 숫자를 입력해주세요.");
        }
    }

    public int readRoomNumber(int maxRoomNumber) {
        String input = validate(scanner.nextLine().trim());
        try {
            int number = Integer.parseInt(input);
            if (number < 1 || number > maxRoomNumber) {
                throw new IllegalArgumentException("[ERROR] 1~" + maxRoomNumber + " 사이의 번호를 입력해주세요.");
            }
            return number;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 숫자를 입력해주세요.");
        }
    }

    public String readRoomName() {
        return validate(scanner.nextLine().trim());
    }

    private String validate(String input) {
        if (input.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 빈 입력은 허용되지 않습니다.");
        }
        return input;
    }
}
