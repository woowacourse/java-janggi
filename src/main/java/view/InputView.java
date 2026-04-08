package view;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class InputView {
    private static final String DELIMITER = ",";

    static Scanner sc = new Scanner(System.in);

    public static int readGameMode() {
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 올바른 번호를 입력해 주세요.");
        }
    }

    public static String readRoomName() {
        return sc.nextLine();
    }

    public static int readArrangement() {
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 올바른 번호를 입력해 주세요.");
        }
    }

    public static List<Integer> readStartPosition() {
        System.out.println("움직일 기물를 입력해주세요. ex) \"1,3\"");
        String input = sc.nextLine();
        return Arrays.stream(input.split(DELIMITER))
                .map(s -> Integer.parseInt(s.trim()))
                .collect(Collectors.toList());
    }

    public static List<Integer> readEndPosition() {
        System.out.println("기물의 도착지를 입력해주세요. ex) \"2,3\"");
        String input = sc.nextLine();
        return Arrays.stream(input.split(DELIMITER))
                .map(s -> Integer.parseInt(s.trim()))
                .collect(Collectors.toList());
    }
}
