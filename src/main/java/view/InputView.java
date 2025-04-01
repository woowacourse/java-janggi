package view;

import domain.CommandOption;
import domain.SetupOption;
import domain.TeamType;
import domain.game.dto.JanggiGameDto;
import domain.piece.strategy.HorseElephantSetupStrategy;
import domain.player.Player;
import domain.player.Username;
import domain.position.Position;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import util.PositionConvertor;

public class InputView {

    private final Scanner scanner = new Scanner(System.in);
    private static final Map<String, CommandOption> COMMAND_NUMBER = new LinkedHashMap<>();
    private static final Map<String, SetupOption> SETUP_OPTION_NUMBER = new LinkedHashMap<>();

    static {
        COMMAND_NUMBER.put("1", CommandOption.MOVE);
        COMMAND_NUMBER.put("2", CommandOption.UNDO);

        SETUP_OPTION_NUMBER.put("1", SetupOption.INNER_ELEPHANT_SETUP);
        SETUP_OPTION_NUMBER.put("2", SetupOption.OUTER_ELEPHANT_SETUP);
        SETUP_OPTION_NUMBER.put("3", SetupOption.LEFT_ELEPHANT_SETUP);
        SETUP_OPTION_NUMBER.put("4", SetupOption.RIGHT_ELEPHANT_SETUP);
    }

    public Username getFirstPlayerName() {
        System.out.println("첫번째 플레이어의 이름을 입력해주세요.");
        return new Username(nextLine());
    }

    public Username getSecondPlayerName() {
        System.out.println("두번째 플레이어의 이름을 입력해주세요.");
        return new Username(nextLine());
    }

    public Username getStartPlayerName() {
        System.out.println("먼저 시작할 플레이어의 이름을 입력해주세요.");
        return new Username(nextLine());
    }

    public HorseElephantSetupStrategy getSetupStrategy(String playerName) {
        System.out.printf("%s의 배치를 선택해주세요%n", playerName);
        for (Entry<String, SetupOption> entry : SETUP_OPTION_NUMBER.entrySet()) {
            System.out.printf("%s. %s%n", entry.getKey(), entry.getValue().getDescription());
        }

        return getStrategyOf(nextLine());
    }

    public CommandOption getOptionCommand(Player player) {
        System.out.printf("%s의 턴입니다. 번호를 선택하세요.%n", player.getName());
        for (Entry<String, CommandOption> entry : COMMAND_NUMBER.entrySet()) {
            System.out.printf("%s. %s%n", entry.getKey(), entry.getValue().getDescription());
        }

        return getCommandOptionOf(nextLine());
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

    public long getInProgressGameId(List<JanggiGameDto> inProgressGames) {
        System.out.printf("진행중인 게임이 %d개 있습니다. 계속 진행할 게임의 번호를 입력해주세요.%n", inProgressGames.size());
        for (JanggiGameDto inProgressGame : inProgressGames) {
            System.out.printf("%d. %s : %s %n",
                    inProgressGame.gameId(),
                    getPlayerTeamDescription(inProgressGame.choPlayerName(), TeamType.CHO),
                    getPlayerTeamDescription(inProgressGame.hanPlayerName(), TeamType.HAN));
        }
        int gameId = parseInt(nextLine());
        validateGameId(inProgressGames, gameId);
        return gameId;
    }

    private String getPlayerTeamDescription(String playerName, TeamType teamType) {
        String format = "%s (%s나라)";
        if (teamType == TeamType.HAN) {
            return String.format(format, playerName, "한");
        }
        if (teamType == TeamType.CHO) {
            return String.format(format, playerName, "초");
        }
        throw new IllegalStateException("알 수 없는 팀입니다.");
    }

    private void validateGameId(List<JanggiGameDto> inProgressGames, int inputGameId) {
        try {
            inProgressGames.stream()
                    .map(JanggiGameDto::gameId)
                    .filter(gameId -> gameId == inputGameId)
                    .findAny()
                    .orElseThrow(() -> new IllegalArgumentException("해당하는 게임 번호가 없습니다. 다시 입력해주세요."));
        } catch (IllegalArgumentException e) {
            validateGameId(inProgressGames, inputGameId);
        }

    }

    private boolean parseBoolean(String input) {
        if (input.equalsIgnoreCase("Y")) {
            return true;
        }
        if (input.equalsIgnoreCase("N")) {
            return false;
        }
        throw new IllegalArgumentException("Y/N 중 하나만 입력해주세요.");
    }

    private int parseInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자만 입력해주세요.");
        }

    }

    private String nextLine() {
        return scanner.nextLine().trim();
    }

    private HorseElephantSetupStrategy getStrategyOf(String input) {
        if (SETUP_OPTION_NUMBER.containsKey(input)) {
            return SETUP_OPTION_NUMBER.get(input).getSetupStrategy();
        }
        throw new IllegalArgumentException("존재하지 않는 옵션 번호입니다.");
    }

    private CommandOption getCommandOptionOf(String input) {
        if (COMMAND_NUMBER.containsKey(input)) {
            return COMMAND_NUMBER.get(input);
        }
        throw new IllegalArgumentException("올바르지 않은 선택지 입니다.");
    }
}
