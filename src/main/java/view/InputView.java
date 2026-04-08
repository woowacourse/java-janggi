package view;

import dto.SavedGameDto;
import java.util.List;
import java.util.Scanner;

import exception.Validator;
import util.InputParser;

public class InputView {
    private static final int CHOICE_START_NUMBER = 1;

    private final Scanner scanner;
    private final ViewFormatter formatter;

    public InputView(Scanner scanner, ViewFormatter formatter) {
        this.scanner = scanner;
        this.formatter = formatter;
    }

    public int requestGameMenu() {
        System.out.println("""
                게임을 시작합니다.
                1: 새 게임
                2: 이어하기
                3: 기록 조회""");
        return Validator.validateNumber(scanner.nextLine());
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

    public String requestPiece() {
        System.out.println("\n움작일 기물을 선택해주세요. (예: 졸, 차, 마, 등) : ");
        return scanner.nextLine();
    }

    public int requestStartPiecePosition(int size) {
        System.out.println("\n움직일 기물의 좌표의 번호를 선택해주세요. ");
        int choice = Validator.validateNumber(scanner.nextLine());
        Validator.validateNumberInRange(CHOICE_START_NUMBER, size, choice);
        return choice;
    }

    public List<Integer> requestMovePosition() {
        System.out.println("\n이동할 좌표의 행과 열을 입력해 주세요. (행,열) ");
        return InputParser.splitBy(",", scanner.nextLine());
    }

    public int requestGameId(List<SavedGameDto> savedGames) {
        printSavedGames(savedGames);
        return requestValidGameId(savedGames);
    }

    private void printSavedGames(List<SavedGameDto> savedGames) {
        System.out.println("\n진행 중인 게임 목록입니다. 게임 방의 숫자를 입력해주세요.");
        for (SavedGameDto savedGameDto : savedGames) {
            System.out.print(formatter.formatSavedGames(savedGameDto));
        }
    }

    private int requestValidGameId(List<SavedGameDto> savedGames) {
        int input = Validator.validateNumber(scanner.nextLine());
        List<Integer> numbers = savedGames.stream()
                .map(SavedGameDto::gameId)
                .toList();
        Validator.validateContainsNumber(input, numbers);
        return input;
    }
}
