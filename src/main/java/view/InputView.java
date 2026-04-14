package view;

import domain.board.Formation;
import domain.game.GameType;
import repository.dto.GameDto;

import java.util.List;
import java.util.Scanner;

public class InputView {

    private static final String POSITION_PATTERN = "^\\d+\\s+\\d+$";
    private final Scanner scanner = new Scanner(System.in);

    public GameType readGameType() {
        System.out.println("어떤 걸 선택하시겠습니다? (숫자만 입력)");
        System.out.println("1. 새로운 게임");
        System.out.println("2. 기존 게임");
        System.out.println("3. 게임 종료");

        String input = scanner.nextLine().trim();
        try {
            int parsedInput = Integer.parseInt(input);

            System.out.println();
            return switch (parsedInput) {
                case 1 -> GameType.NEW;
                case 2 -> GameType.LOAD;
                case 3 -> GameType.EXIT;
                default -> throw new IllegalArgumentException();
            };
        }
        catch (Exception e) {
            System.out.println("[ERROR] 잘못된 입력입니다.");
            System.out.println();
            return readGameType();
        }
    }

    public Long readGameNumber(List<GameDto> games) {
        System.out.println("어떤 게임을 이어서 하시겠어요? (숫자만 입력)");
        for (GameDto game : games) {
            System.out.println(game.getId() + "번 게임 마지막 수정 시간 : " + game.getUpdatedAt());
        }

        String input = scanner.nextLine().trim();
        try {
            Long parsedInput = Long.parseLong(input);
            games.stream()
                    .filter(g -> g.getId().equals(parsedInput))
                    .findFirst()
                    .orElseThrow(IllegalArgumentException::new);

            System.out.println();
            return parsedInput;
        }
        catch (Exception e) {
            System.out.println("[ERROR] 잘못된 입력입니다.");
            System.out.println();
            return readGameNumber(games);
        }
    }

    public Formation readHorseElephantFormation(String team) {
        System.out.println(team + "의 초기 진형을 선택하세요. (숫자만 입력)");
        System.out.println("1. 상마상마");
        System.out.println("2. 마상마상");
        System.out.println("3. 상마마상");
        System.out.println("4. 마상상마");

        String input = scanner.nextLine().trim();
        try {
            int parsedInput = Integer.parseInt(input);

            System.out.println();
            return switch (parsedInput) {
                case 1 -> Formation.LEFT_ELEPHANT_RIGHT_ELEPHANT;
                case 2 -> Formation.LEFT_HORSE_RIGHT_HORSE;
                case 3 -> Formation.LEFT_ELEPHANT_RIGHT_HORSE;
                case 4 -> Formation.LEFT_HORSE_RIGHT_ELEPHANT;
                default -> throw new IllegalArgumentException();
            };
        }
        catch (Exception e) {
            System.out.println("[ERROR] 잘못된 입력입니다.");
            System.out.println();
            return readHorseElephantFormation(team);
        }
    }
    
    public String readPosition(String turnName) {
        System.out.println(turnName + " 차례입니다.");
        System.out.println("움직일 기물의 위치를 입력해주세요. (예: 0 0) [게임 중단: n] [기권: r]");

        String input = scanner.nextLine();
        try {
            if (input.equals("n") || input.equals("r")) {
                return input;
            }
            validatePositionFormat(input);
            String[] tokens = input.split(" ");
            if (tokens.length != 2) {
                throw new IllegalArgumentException();
            }
            Integer.parseInt(tokens[0]);
            Integer.parseInt(tokens[1]);
            System.out.println();
            return input;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println();
            return readPosition(turnName);
        }
    }

    public String readTargetPosition() {
        System.out.println("기물을 움직일 위치를 입력해주세요. (예: 0 0) [게임 중단: n] [기권: r]");

        String input = scanner.nextLine();
        try {
            if (input.equals("n") || input.equals("r")) {
                return input;
            }
            validatePositionFormat(input);
            String[] tokens = input.split(" ");
            if (tokens.length != 2) {
                throw new IllegalArgumentException();
            }
            Integer.parseInt(tokens[0]);
            Integer.parseInt(tokens[1]);
            System.out.println();
            return input;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println();
            return readTargetPosition();
        }
    }

    private void validatePositionFormat(final String input) {
        if (!input.matches(POSITION_PATTERN)) {
            throw new IllegalArgumentException("[ERROR] 입력 형식이 잘못되었습니다. '숫자 공백 숫자' 형식이어야 합니다.");
        }
    }
}
