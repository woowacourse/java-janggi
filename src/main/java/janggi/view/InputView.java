package janggi.view;

import janggi.domain.position.Position;
import janggi.util.Console;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class InputView {

    public long askGameId() {
        System.out.println("게임 ID를 입력해주세요. (새로운 게임을 시작하려면 0을 입력해주세요.) : ");
        String rawGameId = Console.readLine();
        try {
            return Long.parseLong(rawGameId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("게임 ID는 숫자로 입력해주세요.");
        }
    }

    public Position askSelectPiece() {
        System.out.println("움직일 기물의 좌표를 입력해주세요.(e.g. 1,2) : ");
        return readPosition();
    }

    public Position askTargetPosition() {
        System.out.println("목적지 좌표를 입력해주세요.(e.g. 1,2) : ");
        return readPosition();
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
