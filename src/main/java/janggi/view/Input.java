package janggi.view;

import janggi.board.TableOption;
import janggi.position.Position;
import janggi.team.Team;

import java.util.Map;
import java.util.Scanner;

import static janggi.board.TableOption.*;

public class Input {
    private static final Map<String,TableOption> tableOptions = Map.of(
            "상마상마",EHEH,
            "마상마상",HEHE,
            "마상상마",HEEH,
            "상마마상",EHHE
    );
    
    private static final Map<Team, String> teams = Map.of(
            Team.CHO,"초",
            Team.HAN,"한"
    );
    
    private final Scanner scanner = new Scanner(System.in);

    public TableOption readTableOption(Team team) {
        TableOption tableOption;
        do {
            System.out.printf("%s의 상차림을 선택해주세요. [입력 예시 : 상마상마, 마상마상, 마상상마, 상마마상]%n", teams.get(team));
            String inputTableOption = scanner.nextLine();
            tableOption = tableOptions.get(inputTableOption);
        } while(tableOption == null);
        return tableOption;
    }

    public Map.Entry<Position, Position> readMoveablePiece() {
        System.out.println("움직일 기물의 출발 위치 및 도착 위치를 입력해주세요.");
        System.out.println("ex) 1,1 1,2");
        String[] splitInput = scanner.nextLine().split(" ");
        Position startPosition = convertToPosition(splitInput[0].split(","));
        Position arrivedPosition = convertToPosition(splitInput[1].split(","));
        return Map.entry(startPosition, arrivedPosition);
    }

    private Position convertToPosition(String[] splitInputPosition) {
        int row = Integer.parseInt(splitInputPosition[0]);
        int column = Integer.parseInt(splitInputPosition[1]);
        return new Position(row, column);
    }
}
