package janggi.view;

import janggi.domain.position.Position;
import janggi.util.Console;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class InputView {

    public static long askLoadGameId() {
        System.out.println("게임 ID를 입력해주세요. (새로운 게임을 시작하려면 0을 입력해주세요.) : ");
        String rawGameId = Console.readLine();
        try {
            return Long.parseLong(rawGameId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("게임 ID는 숫자로 입력해주세요.");
        }
    }

    public static Position askSelectPiece() {
        System.out.println("움직일 기물의 좌표를 입력해주세요.(e.g. 1,2) : ");
        return readPosition();
    }

    public static Position askTargetPosition() {
        System.out.println("목적지 좌표를 입력해주세요.(e.g. 1,2) : ");
        return readPosition();
    }

    public static boolean askSaveGame() {
        System.out.println("저장하시겠습니까? (y: 저장, n: 저장안함)");
        String answer = Console.readLine();
        if ("y".equals(answer)) {
            return true;
        }
        if ("n".equals(answer)) {
            return false;
        }
        throw new IllegalArgumentException("'y' 또는 'n'만 입력 가능합니다.");
    }

    public static Long askGenerateGameId() {
        System.out.println("저장할 게임 ID를 입력해주세요. : ");
        String rawGameId = Console.readLine();
        try {
            return Long.parseLong(rawGameId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("게임 ID는 숫자로 입력해주세요.");
        }
    }

    public static boolean askExitAfterSave() {
        System.out.println("게임을 종료하시겠습니까? (종료: exit, 계속: continue)");
        String answer = Console.readLine().trim().toLowerCase();
        if ("exit".equals(answer)) {
            return true;
        }
        if ("continue".equals(answer)) {
            return false;
        }
        throw new IllegalArgumentException("잘못된 입력입니다. 'exit' 또는 'continue'로 입력해주세요.");
    }

    public static boolean askOverwrite() {
        System.out.println("현재 ID에 덮어씌우시겠습니까? (y: 덮어쓰기, n: 새로운 ID로 저장)");
        String answer = Console.readLine().trim().toLowerCase();
        if ("y".equals(answer)) {
            return true;
        }
        if ("n".equals(answer)) {
            return false;
        }
        throw new IllegalArgumentException("'y' 또는 'n'만 입력 가능합니다.");
    }

    private static Position readPosition() {
        String rawPosition = Console.readLine();
        try {
            List<String> coordinate = Arrays.stream(rawPosition.split(","))
                    .map(String::trim)
                    .filter(Predicate.not(String::isBlank))
                    .toList();

            if (coordinate.size() != 2) {
                throw new IllegalArgumentException("좌표는 x,y 형식으로 입력해야 합니다.");
            }

            return Position.of(coordinate.get(0), coordinate.get(1));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("좌표는 정수로 입력해주세요.");
        }
    }
}
