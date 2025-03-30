package view;

import domain.CommandOption;
import domain.game.dto.JanggiGameResponseDto;
import domain.player.Player;
import domain.position.Position;
import java.util.List;
import java.util.Scanner;
import util.PositionConvertor;

public class InputView {

    private final Scanner scanner = new Scanner(System.in);

    public String getFirstPlayerName() {
        System.out.println("첫번째 플레이어의 이름을 입력해주세요.");
        return nextLine();
    }

    public String getSecondPlayerName() {
        System.out.println("두번째 플레이어의 이름을 입력해주세요.");
        return nextLine();
    }

    public String getStartPlayerName() {
        System.out.println("먼저 시작할 플레이어의 이름을 입력해주세요.");
        return nextLine();
    }

    public String getSetupNumber(String playerName) {
        System.out.printf("%s의 배치를 선택해주세요%n", playerName);
        System.out.println("1. Inner Elephant Setup");
        System.out.println("2. Outer Elephant Setup");
        System.out.println("3. Right Elephant Setup");
        System.out.println("4. Left Elephant Setup");

        return nextLine();
    }

    public CommandOption getOptionCommand(Player player) {
        System.out.printf("%s의 턴입니다. 번호를 선택하세요.%n", player.getName());
        for (CommandOption option : CommandOption.values()) {
            System.out.printf("%s. %s%n", option.getCommand(), option.getDescription());
        }

        return CommandOption.of(nextLine());
    }

    public Position getStartPosition(Player player) {
        System.out.printf("%s가 옮기고 싶은 장기의 좌표를 입력해주세요. 예시 -> a4%n", player.getName());
        return PositionConvertor.changeInputToPosition(nextLine());
    }

    public Position getEndPosition(Player player) {
        System.out.printf("%s가 옮길 위치의 좌표를 입력해주세요. 예시 -> a5%n", player.getName());
        return PositionConvertor.changeInputToPosition(nextLine());
    }

    public boolean askToPlayInProgressGame() {
        System.out.println("진행중인 게임이 있습니다. 계속 하시겠습니까? (Y/N)");
        return parseBoolean(nextLine());
    }

    public long getInProgressGameId(List<JanggiGameResponseDto> inProgressGames) {
        System.out.printf("진행중인 게임이 %d개 있습니다. 계속 진행할 게임의 번호를 입력해주세요.%n", inProgressGames.size());
        for (JanggiGameResponseDto inProgressGame : inProgressGames) {
            System.out.printf("%d. %s : %s%n", inProgressGame.gameId(), inProgressGame.choPlayerName(), inProgressGame.hanPlayerName());
        }

        int gameId = parseInt(nextLine());
        validateGameId(inProgressGames, gameId);
        return gameId;
    }

    private void validateGameId(List<JanggiGameResponseDto> inProgressGames, int inputGameId) {
        try {
            inProgressGames.stream()
                    .map(JanggiGameResponseDto::gameId)
                    .filter(gameId -> gameId == inputGameId)
                    .findAny()
                    .orElseThrow(() -> new IllegalArgumentException("해당하는 게임 번호가 없습니다. 다시 입력해주세요."));
        } catch (IllegalArgumentException e){
            validateGameId(inProgressGames, inputGameId);
        }

    }

    private boolean parseBoolean(String input){
        if(input.equalsIgnoreCase("Y")){
            return true;
        }
        if(input.equalsIgnoreCase("N")){
            return false;
        }
        throw new IllegalArgumentException("Y/N 중 하나만 입력해주세요.");
    }

    private int parseInt(String input){
        try{
            return Integer.parseInt(input);
        } catch (NumberFormatException e){
            throw new IllegalArgumentException("숫자만 입력해주세요.");
        }

    }

    private String nextLine() {
        return scanner.nextLine().trim();
    }
}
