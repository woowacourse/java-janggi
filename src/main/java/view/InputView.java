package view;

import db.dao.JanggiGameDao.GameDto;
import janggiGame.piece.Dynasty;
import janggiGame.position.Position;
import java.util.List;
import java.util.Scanner;

public class InputView {

    public static final String ARRANGE_PROMPT = """            
            1. 내상 배치
            2. 외상 배치
            3. 좌상 배치
            4. 우상 배치""";
    private final Scanner scanner = new Scanner(System.in);

    public int readHanArrangement() {
        System.out.println("한나라는 배치 전략을 선택하세요.");
        System.out.println(ARRANGE_PROMPT);
        return Integer.parseInt(scanner.nextLine());
    }

    public int readChoArrangement() {
        System.out.println("초나라는 배치 전략을 선택하세요");
        System.out.println(ARRANGE_PROMPT);
        return Integer.parseInt(scanner.nextLine());
    }

    public int getTurnOption(Dynasty currentDynasty) {
        System.out.printf("""
                %n%s의 차례입니다.
                수행할 옵션을 입력해주세요
                1. 기물 이동
                2. 머무르기
                3. 한 수 물러주기
                4. 점수 확인
                """, getDynastyName(currentDynasty));
        return Integer.parseInt(scanner.nextLine());
    }

    private String getDynastyName(Dynasty currentDynasty) {
        if (currentDynasty == Dynasty.CHO) {
            return "초나라";
        }
        return "한나라";
    }

    public List<Position> readPieceMovement() {
        System.out.println("""
                말을 움직여주세요
                ex) 기물의 x좌표, 기물의 y좌표 > 이동할 x좌표, 이동할 y좌표""");
        String movementInput = scanner.nextLine();

        String[] originAndDestination = movementInput.split(">");
        String originInput = originAndDestination[0];
        String destinationInput = originAndDestination[1];

        String[] originXY = originInput.split(",");
        int originX = Integer.parseInt(originXY[0].trim());
        int originY = Integer.parseInt(originXY[1].trim());

        String[] destinationXY = destinationInput.split(",");
        int destinationX = Integer.parseInt(destinationXY[0].trim());
        int destinationY = Integer.parseInt(destinationXY[1].trim());

        Position origin = Position.getInstanceBy(originX, originY);
        Position destination = Position.getInstanceBy(destinationX, destinationY);

        return List.of(origin, destination);
    }

    public int readStartOption() {
        System.out.println("1. 이전 게임 불러오기");
        System.out.println("2. 새 게임 시작하기");
        int option = Integer.parseInt(scanner.nextLine());
        validateOption(option);
        return option;
    }

    public Long readSavedGameId(List<GameDto> games) {
        if (games.isEmpty()) {
            throw new IllegalStateException("[ERROR] 저장된 게임이 없습니다.");
        }
        for (GameDto game : games) {
            System.out.printf("게임 ID: %d | 마지막 턴: %s | 저장 시간: %s\n",
                    game.id(), game.currentDynasty(), game.updatedAt());
        }
        System.out.println("==========================\n");
        System.out.println("불러올 게임 ID를 입력하세요");

        return Long.parseLong(scanner.nextLine());
    }

    private void validateOption(int option) {
        if (option != 1 && option != 2) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 옵션입니다.");
        }
    }
}
