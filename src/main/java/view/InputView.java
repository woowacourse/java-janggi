package view;

import game.StartSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import piece.Country;

public class InputView {

    private final Scanner scanner = new Scanner(System.in);

    private static final String moveCommandPattern = "move [A-I][0-9] [A-I][0-9]";


    public StartSet getStartingPosition(Country country) {
        System.out.print(country.name());
        System.out.println("상차림을 입력하세요.\n"
                + "1 : 마상마상\n"
                + "2 : 상마상마\n"
                + "3 : 상마마상\n"
                + "4 : 마상상마");
        final String pattern = "[1-4]";
        String input = scanner.nextLine().trim();
        if (!input.matches(pattern)) {
            throw new IllegalArgumentException("올바른 형식으로 입력해주세요. (예: 1)");
        }
        return StartSet.fromOption(Integer.parseInt(input));
    }

    public List<String> readMoveCommand() {
        System.out.println("이동할 말을 입력하세요 (예: move A1 I8) 또는 종료하려면 'quit' 입력:");
        final String input = scanner.nextLine().trim();
        if (input.equalsIgnoreCase("quit")) {
            List<String> quitCommand = new ArrayList<>();
            quitCommand.add("quit");
            return quitCommand;
        }

        if (!input.matches(moveCommandPattern)) {
            throw new IllegalArgumentException("올바른 형식으로 입력해주세요. (예: move A1 C2)");
        }

        List<String> moveInfo = new ArrayList<>();
        moveInfo.add(String.valueOf(input.charAt(5)));
        moveInfo.add(convertRankChar(input.charAt(6)));
        moveInfo.add(String.valueOf(input.charAt(8)));
        moveInfo.add(convertRankChar(input.charAt(9)));
        return moveInfo;
    }


    private String convertRankChar(char ch) {
        if (ch == '0') {
            return "10";
        }
        return String.valueOf(ch);
    }

    public boolean askLoadSavedGame() {
        System.out.print("이전 저장된 게임을 불러오시겠습니까? (y/n): ");
        String input = scanner.nextLine().trim().toLowerCase();
        return input.equals("y");
    }
}