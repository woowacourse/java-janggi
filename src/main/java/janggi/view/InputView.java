package janggi.view;

import java.util.Scanner;

public class InputView {

    private final Scanner scanner = new Scanner(System.in);

    public String readPlayerName() {
        String playerName = scanner.nextLine();
        validateNotBlank(playerName);
        return playerName;
    }

    private void validateNotBlank(String nickname) {
        if (nickname.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 플레이어의 이름은 공백이 될 수 없습니다.");
        }
    }
}
