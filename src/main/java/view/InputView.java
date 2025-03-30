package view;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import model.Point;
import model.Team;

public class InputView {
    private static Scanner sc;

    public static int choiceLoadOrNewGame() {
        sc = new Scanner(System.in);
        int loadOrNewGame;
        do {
            System.out.println("""
                    1. 이전 게임 불러오기
                    2. 새 게임 시작하기
                    """);
            loadOrNewGame = Integer.parseInt(sc.nextLine());
        } while (loadOrNewGame < 1 || loadOrNewGame > 2);

        return loadOrNewGame;
    }

    public static int inputGameId() {
        sc = new Scanner(System.in);
        int gameId = -1;
        boolean isValid;
        do {
            System.out.println("게임 번호를 선택하세요");
            try {
                gameId = Integer.parseInt(sc.nextLine());
                break;
            } catch (NumberFormatException numberFormatException) {
                System.out.println("[ERROR] 잘못된 번호 형식입니다." + numberFormatException.getMessage());
            }
            isValid = false;
        } while (!isValid);
        return gameId;
    }

    public static int choiceSetUp() {
        sc = new Scanner(System.in);
        int setUp = -1;
        boolean isValid;
        do {
            try {
                System.out.println("""
                        상차림을 선택하세요,
                        1. 안상차림 (마상상마)
                        2. 바깥상차림 (상마마상)
                        3. 왼상차림 (상마상마)
                        4. 오른상차림 (마상마상)
                        입력 형식 : 번호 ex)1
                        """);
                setUp = Integer.parseInt(sc.nextLine());
                break;
            } catch (NumberFormatException numberFormatException) {
                System.out.println("[ERROR] 잘못된 번호 형식입니다." + numberFormatException.getMessage());
            }
            isValid = false;
        } while (!isValid);
        return setUp;
    }

    public static String getBeforePointInput(Team team) {
        sc = new Scanner(System.in);
        String userInput = "";
        do {
            System.out.printf("%s 나라의 차례\n", team.getTeamName());
            System.out.println("이동할 기물의 위치를 선택하세요. 입력 형식 : 가로(공백)세로 ex)1 2");
            System.out.println("엔터 : 건너뛰기, wq : 저장 후 종료");
            userInput = sc.nextLine();
            if (!inValidBeforePointInput(userInput)) {
                return userInput;
            }
            System.out.println("[ERROR] 입력 형식이 올바르지 않습니다.");
        } while (inValidBeforePointInput(userInput));
        return userInput;
    }

    private static boolean inValidBeforePointInput(String userInput) {
        return !userInput.isEmpty() && !userInput.equals("wq") && !Pattern.matches("^\\d{1,2} \\d{1,2}$", userInput);
    }

    private static boolean inValidTargetPointInput(String userInput) {
        return !Pattern.matches("^\\d{1,2} \\d{1,2}$", userInput);
    }

    public static List<Point> getTargetPointInput(Team team, String beforePointInput) {
        Point beforePoint = getBeforePoint(team, beforePointInput);
        String targetPointInput;
        do {
            System.out.println("이동될 위치를 선택하세요. 입력 형식 : 가로(공백)세로 ex)1 2");
            System.out.println("wq : 저장 후 종료");
            targetPointInput = sc.nextLine();
            if (!inValidTargetPointInput(targetPointInput)) {
                Point targetPoint = getTargetPoint(targetPointInput);
                return List.of(beforePoint, targetPoint);
            }
            System.out.println("[ERROR] 입력 형식이 올바르지 않습니다.");
        } while (inValidTargetPointInput(targetPointInput));
        return null;
    }

    private static Point getBeforePoint(Team team, String userInput) {
        List<Integer> beforePointInput = Arrays.stream(userInput.split(" ")).map(Integer::parseInt).toList();
        Point beforePoint = Point.of(beforePointInput.getFirst() - 1, beforePointInput.getLast() - 1);
        return beforePoint;
    }

    private static Point getTargetPoint(String userInput) {
        List<Integer> targetPointInput = Arrays.stream(userInput.split(" ")).map(Integer::parseInt).toList();
        Point targetPoint = Point.of(targetPointInput.getFirst() - 1, targetPointInput.getLast() - 1);
        return targetPoint;
    }
}
