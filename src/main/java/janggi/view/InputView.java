package janggi.view;

import janggi.game.GameInformation;
import janggi.rule.CampType;
import janggi.rule.PieceAssignType;
import janggi.value.Position;
import janggi.view.answer.GameMenuAnswer;
import janggi.view.answer.PieceAssignTypeAnswer;
import janggi.view.answer.TurnMenuAnswer;
import java.util.List;
import java.util.Scanner;

public class InputView {

    private final static Scanner scanner = new Scanner(System.in);

    public GameMenuAnswer readGameMenuAnswer() {
        System.out.println("재밌는 장기를 해봐요!");
        System.out.println("1. 새로 하기");
        System.out.println("2. 이어서 하기");
        System.out.println("q. 종료하기");

        GameMenuAnswer gameMenuAnswer = GameMenuAnswer.parse(scanner.nextLine());
        System.out.println();
        return gameMenuAnswer;
    }

    public int selectGame(List<GameInformation> gameInformations) {
        System.out.println("이어서 할 게임을 선택해주세요.");
        int index;
        for (index = 0; index < gameInformations.size(); index++) {
            String content = String.format("%d. %s", index, gameInformations.get(index).gameTitle());
            System.out.println(content);
        }
        System.out.println(index + ". 뒤로가기");
        try {
            int input = Integer.parseInt(scanner.nextLine());
            System.out.println();
            return input;
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("[ERROR] 적절하지 않은 입력값입니다.");
        }
    }


    public String readNewGameTitle() {
        System.out.println("생성할 게임의 이름을 입력해주세요.(50자 이내)");
        String title = scanner.nextLine();
        if (title.length() >= 50) {
            throw new IllegalArgumentException("[ERROR] 게임제목이 너무 깁니다. 50자 이내로 작성해주세요!");
        }
        return title;
    }

    public PieceAssignType readPieceAssignType(CampType campType) {
        System.out.printf("%s의 초기 배치를 선택해주세요.", campType.getName());
        System.out.println();
        System.out.println("1. 왼상(상마상마)");
        System.out.println("2. 오른상(마상마상)");
        System.out.println("3. 안상(마상상마)");
        System.out.println("4. 바깥상(상마마상)");

        PieceAssignType pieceAssignType = PieceAssignTypeAnswer.parse(scanner.nextLine());
        System.out.println();
        return pieceAssignType;
    }

    public TurnMenuAnswer readTurnMenuAnswer(CampType campType) {
        System.out.println(campType.getName() + "의 턴입니다. 이번턴에 진행할 행동을 정해주세요!");
        System.out.println("1. 기물 움직이기");
        System.out.println("2. 한수 쉬어주기");
        System.out.println("3. 나중에 이어서 하기");
        System.out.println("4. 승패를 가르고 종료하기");

        TurnMenuAnswer turnMenuAnswer = TurnMenuAnswer.parse(scanner.nextLine());
        System.out.println();
        return turnMenuAnswer;
    }


    public Position readMovedPiecePosition() {
        System.out.println("이동할 장기말의 좌표 입력해주세요.");
        return readPosition();
    }

    public Position readDestinationPosition() {
        System.out.println("목적지 좌표를 입력해주세요.");
        return readPosition();
    }

    private Position readPosition() {
        String line = scanner.nextLine();
        List<String> positionInput = List.of(line.split(","));
        try {
            int x = Integer.parseInt(positionInput.getFirst());
            int y = Integer.parseInt(positionInput.getLast());
            return new Position(x, y);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("[ERROR] 적절하지 않은 입력값입니다.");
        }
    }
}
