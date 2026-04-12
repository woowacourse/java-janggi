package view;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import model.board.Country;

public class InputView {
    private static final String DELIMITER = ",";

    static Scanner sc = new Scanner(System.in);

    public static String readArrangement(Country country) {
        System.out.println(country.color() + "1. 마상상마" + Country.RESET);
        System.out.println(country.color() + "2. 상마마상" + Country.RESET);
        System.out.println(country.color() + "3. 마상마상" + Country.RESET);
        System.out.println(country.color() + "4. 상마상마" + Country.RESET);
        return sc.nextLine();
    }

    public static List<Integer> readStartPosition() {
        System.out.println("움직일 기물의 위치를 입력해주세요. ex) \"1,3\"");
        String input = sc.nextLine();
        return Arrays.stream(input.split(","))
                .map(s -> Integer.parseInt(s.trim()))
                .collect(Collectors.toList());
    }

    public static List<Integer> readEndPosition() {
        System.out.println("기물의 도착지를 입력해주세요. ex) \"1,3\"");
        String input = sc.nextLine();
        return Arrays.stream(input.split(","))
                .map(s -> Integer.parseInt(s.trim()))
                .collect(Collectors.toList());
    }

    public static String readStartOption(){
        return sc.nextLine();
    }
}
