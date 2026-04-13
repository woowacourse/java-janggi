package view;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import exception.Validator;
import util.InputParser;

public class InputView {
    private final Scanner scanner;
    private static final int CHOICE_START_NUMBER = 1;
    private static final int COORDINATE_INPUT_LENGTH = 2;
    public static final int CHOICE_START_NUMBER_WITH_QUIT = 0;
    public static final int CHOICE_QUIT_NUMBER = 0;

    public InputView(Scanner scanner) {
        this.scanner = scanner;
    }

    public int requestMaSangPosition() {
        System.out.println("[차 ( ) ( ) 사 ＋ 사 ( ) ( ) 차] ◀︎ 위치 선택");
        System.out.println("\n마,상의 위치를 번호로 입력해 주세요. (1~4 중에 입력)");
        System.out.println("""
                1. 마상상마
                2. 마상마상
                3. 상마상마
                4. 상마마상""");

        int choice = Validator.validateNumber(scanner.nextLine());
        Validator.validateNumberInRange(CHOICE_START_NUMBER, 4, choice);
        return choice;
    }

    public boolean askGameContinue() {
        System.out.println("\n게임을 계속 진행할까요? y/n ");
        return Validator.validateYesOrNo(scanner.nextLine());
    }

    public String requestPiece() {
        System.out.println("\n움작일 기물을 선택해주세요. (예: 졸, 차, 마, 등) : ");
        return scanner.nextLine();
    }

    public int requestStartPiecePosition(int size) {
        System.out.println("\n움직일 기물의 좌표의 번호를 선택해주세요. ");
        int choice = Validator.validateNumber(scanner.nextLine());
        Validator.validateNumberInRange(CHOICE_START_NUMBER_WITH_QUIT, size, choice);
        return choice;
    }

    public Optional<List<Integer>> requestMovePosition() {
        System.out.println("\n이동할 좌표를 입력해 주세요. (Q : 기물 선택으로 돌아가기)");
        String input = scanner.nextLine();
        if (input.toLowerCase().equals("q")){
            return Optional.empty();
        }
        List<Integer> coordinate = InputParser.splitBy(",", input);
        Validator.validateLength(COORDINATE_INPUT_LENGTH, coordinate.size());
        return Optional.of(coordinate);
    }

    public int chooseGameStartNewOrAgain() {
        System.out.println("1. 게임 새로 시작하기\n2. 게임 이어하기\n원하는 번호를 선택해주세요.");
        int choice = Validator.validateNumber(scanner.nextLine());
        Validator.validateNumberInRange(CHOICE_START_NUMBER_WITH_QUIT, 2, choice);
        return choice;
    }

    public int requestGameId() {
        System.out.println("게임 ID를 입력해주세요 (숫자)");
        return Validator.validateNumber(scanner.nextLine());
    }
}
