package view;

import domain.Team;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public final class InputView {

  private static final Scanner scanner = new Scanner(System.in);
  private static final int START_POINT_INDEX = 0;
  private static final int ARRIVAL_POINT_INDEX = 1;

  public Map<Team, Integer> readChoicesForSetup() {
    final Map<Team, Integer> elephantLocatorByTeam = new HashMap<>();
    for (final Team team : Team.getActualTeams()) {
      final int choice = readChoiceForElephantLocation();
      elephantLocatorByTeam.put(team, choice);
    }
    return elephantLocatorByTeam;
  }

  public List<List<Integer>> readMovementRequest() {
    System.out.println("출발점과 도착점의 위치를 알려주세요 ex.2,1 3,1");
    final String input = scanner.nextLine();
    final String[] splitInput = input.split(" ");
    final List<Integer> startPoint = formatToIntegerList(splitInput, START_POINT_INDEX);
    final List<Integer> arrivalPoint = formatToIntegerList(splitInput, ARRIVAL_POINT_INDEX);
    return List.of(startPoint, arrivalPoint);
  }

  private List<Integer> formatToIntegerList(String[] splitInput, int index) {
    return Arrays.stream(splitInput[index].split(","))
        .map(Integer::parseInt)
        .toList();
  }

  private int readChoiceForElephantLocation() {
    System.out.println("""
        마와 상의 배치를 선택해주세요.
        1. 바깥상 차림(상마마상)
        2. 안상 차림(마상상마)
        3. 왼상 차림(상마상마)
        4. 오른상 차림(마상마상)
        """);
    return Integer.parseInt(scanner.nextLine());
  }
}
