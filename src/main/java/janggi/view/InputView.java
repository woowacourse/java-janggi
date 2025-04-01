package janggi.view;

import static janggi.controller.JanggiFunction.GIVE_UP;
import static janggi.controller.JanggiFunction.MOVE;
import static janggi.controller.JanggiFunction.STOP;
import static janggi.domain.BoardSetup.INNER_ELEPHANT_SETUP;
import static janggi.domain.BoardSetup.LEFT_ELEPHANT_SETUP;
import static janggi.domain.BoardSetup.OUTER_ELEPHANT_SETUP;
import static janggi.domain.BoardSetup.RIGHT_ELEPHANT_SETUP;
import static janggi.domain.Input.N;
import static janggi.domain.Input.Y;
import static janggi.domain.Team.RED;

import janggi.controller.JanggiFunction;
import janggi.domain.BoardSetup;
import janggi.domain.Input;
import janggi.domain.Team;
import janggi.domain.piece.direction.Position;
import java.util.Scanner;

public class InputView {

    private final Scanner scanner = new Scanner(System.in);

    public BoardSetup inputBoardSetup(final Team team) {
        System.out.println("안상차림, 바깥상차림, 왼상차림, 오른상차림 중 골라주세요.");
        if (team == RED) {
            System.out.println("한나라의 상차림을 입력해 주세요.");
            return getBoardSetup(scanner.nextLine());
        }
        System.out.println("초나라의 상차림을 입력해 주세요.");
        return getBoardSetup(scanner.nextLine());
    }

    private BoardSetup getBoardSetup(final String input) {
        return switch (input) {
            case "오른상차림" -> RIGHT_ELEPHANT_SETUP;
            case "왼상차림" -> LEFT_ELEPHANT_SETUP;
            case "안상차림" -> INNER_ELEPHANT_SETUP;
            case "바깥상차림" -> OUTER_ELEPHANT_SETUP;
            default -> throw new IllegalArgumentException("존재하지 않는 상차림입니다.");
        };
    }

    public Position inputPiecePosition() {
        System.out.println("선택할 말의 위치를 골라주세요. 예시) 1 5");
        final String input = scanner.nextLine();
        final String[] splittedInput = input.split(" ");
        return new Position(Integer.parseInt(splittedInput[0]), Integer.parseInt(splittedInput[1]));
    }

    public Position inputDestination() {
        System.out.println("움직일 장소를 입력해주세요");
        final String input = scanner.nextLine();
        final String[] splittedInput = input.split(" ");
        return new Position(Integer.parseInt(splittedInput[0]), Integer.parseInt(splittedInput[1]));
    }

    public Input inputStopGame() {
        System.out.println("게임을 중단하시겠습니까? Y/N");
        return getInput(scanner.nextLine());
    }

    private Input getInput(final String input) {
        return switch (input) {
            case "Y" -> Y;
            case "N" -> N;
            default -> throw new IllegalArgumentException("입력이 올바르지 않습니다.");
        };
    }

    public JanggiFunction inputSelectFunction() {
        System.out.println("1. 기물 움직이기");
        System.out.println("2. 게임 포기하기");
        System.out.println("3. 게임 종료하기(이후 가능)");
        return getFunction(scanner.nextLine());
    }

    private JanggiFunction getFunction(final String input) {
        return switch (input) {
            case "1" -> MOVE;
            case "2" -> GIVE_UP;
            case "3" -> STOP;
            default -> throw new IllegalArgumentException("입력이 올바르지 않습니다.");
        };
    }

    public Input inputNewGame() {
        System.out.println("새로운 게임 생성 Y/N");
        return getInput(scanner.nextLine());
    }
}
