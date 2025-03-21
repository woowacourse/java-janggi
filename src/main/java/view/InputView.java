package view;

import domain.Player;
import domain.Team;
import java.util.List;
import java.util.Scanner;

public class InputView {

    private final Scanner scanner = new Scanner(System.in);

    public List<Player> readPlayerNames() {
        System.out.println("플레이어 두 명의 이름을 입력하시오. (플레이어1, 플레이어2)");
        String input = scanner.nextLine();
        return List.of(new Player(input.split(",")[0].trim(), Team.BLUE),
                new Player(input.split(",")[1].trim(), Team.RED));
    }

    public List<Integer> readMovePiecePosition(Player player) {
        System.out.printf("%s(%s)%s%s님이 움직일 말의 좌표를 입력하세요%n", specifyTeamColor(player), player.getTeam(), TextColor.exit,
                player.getName());
        return readPosition();
    }

    public List<Integer> readTargetPosition(Player player) {
        System.out.printf("%s(%s)%s%s님이 이동할 좌표를 입력하세요%n", specifyTeamColor(player), player.getTeam(), TextColor.exit,
                player.getName());
        return readPosition();
    }

    private List<Integer> readPosition() {
        String input = scanner.nextLine();
        String[] positions = input.split(",");
        int row = Integer.parseInt(positions[0].trim());
        int column = Integer.parseInt(positions[1].trim());
        return List.of(row, column);
    }

    private String specifyTeamColor(Player player) {
        if (player.getTeam() == Team.BLUE) {
            return TextColor.blue;
        }
        return TextColor.red;
    }
}
