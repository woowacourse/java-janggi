package view;

import java.util.Scanner;

public class InputView {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private final Scanner scanner = new Scanner(System.in);

    public String readTableSetting(String countryName) {
        System.out.printf("%s의 상차림을 입력하세요. (ex - 상마상마(왼상차림), 마상마상(오른상차림), 마상상마(안상차림), 상마마상(바깥상차림))" + LINE_SEPARATOR, countryName);
        return scanner.nextLine();
    }
}
