package janggi.view;

import janggi.position.Column;
import janggi.position.Position;
import janggi.position.Row;
import janggi.team.Team;

import java.util.Map;
import java.util.Scanner;

public class Input {

    private final Scanner scanner = new Scanner(System.in);

    public String readTableOption(Team team) {
        System.out.printf("%s의 상차림을 선택해주세요. [입력 예시 : 상마상마, 마상마상, 마상상마, 상마마상]%n", team.getValue());
        return scanner.nextLine();
    }

    public Map.Entry<Position, Position> readMoveablePiece() {
        System.out.println("움직일 기물의 출발 위치 및 도착 위치를 입력해주세요.");
        System.out.println("ex) 1,1 1,2"); //출발 좌표가 해당 기물의 종류가 맞는지 검증 필요
        String[] splitInput = scanner.nextLine().split(" ");
        Position startPosition = convertToPosition(splitInput[0].split(","));
        Position arrivedPosition = convertToPosition(splitInput[1].split(","));

        return Map.entry(startPosition, arrivedPosition);
    }

    private Position convertToPosition(String[] splitInputPosition) {
        int row = Integer.parseInt(splitInputPosition[0]);
        int column = Integer.parseInt(splitInputPosition[1]);
        return new Position(new Row(row), new Column(column));
    }
}
