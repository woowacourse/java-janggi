package janggi.presentation.ui;

import janggi.domain.status.Team;
import janggi.presentation.dto.GameCommand;
import janggi.presentation.dto.MoveCommand;
import janggi.presentation.util.Console;
import janggi.presentation.util.Parser;
import java.util.Map;

public class InputView {

    private static final Map<Team, String> DISPLAY_NAME = Map.of(
            Team.HAN, "한",
            Team.CHO, "초"
    );

    public GameCommand chooseNewGame() {
        System.out.println("새로운 장기 게임을 시작하시겠습니까?(y,n)");
        return GameCommand.from(Console.readLine());
    }

    public Long chooseExistsGame() {
        System.out.println("기존 장기 게임방ID를 입력해주세요.");
        return Long.valueOf(Console.readLine());
    }

    public MoveCommand readPoints(Team team) {
        System.out.println("현재 " + DISPLAY_NAME.get(team) + "나라의 차례입니다.");
        return new MoveCommand(Parser.parsePoint(readFromPoint()),
                Parser.parsePoint(readToPoint())
        );
    }

    private String readFromPoint() {
        System.out.print("움직일 기물의 출발지 좌표 입력해 주세요.(쉼표 기준으로 분리, ex. 0,0) : ");
        return Console.readLine();
    }

    private String readToPoint() {
        System.out.print("움직일 기물의 도착지 좌표 입력해 주세요.(쉼표 기준으로 분리, ex. 0,1) : ");
        return Console.readLine();
    }
}
