package janggi.view;

import janggi.game.Game;
import janggi.game.Team;
import java.util.EnumMap;

public final class SystemView {

    public void display() {
        System.out.println("=== 기물표기법 ===\n"
                + "  한 초\n"
                + "궁 k K\n"
                + "차 c C\n"
                + "포 p P\n"
                + "마 h H\n"
                + "상 e E\n"
                + "사 g G\n"
                + "병 s S");
        System.out.println("=== 명령어 안내 ===\n"
                + "end: 게임을 종료합니다.\n"
                + "move [시작위치] [종료위치]` : 시작위치에서 종료위치로 기물을 이동합니다.\n"
                + "    `[위치]` : 열번호, 행번호 순으로 입력합니다.");
        System.out.println("=== 게임 규칙 ===\n"
                + "- 초나라가 선공이며 번갈아가면서 공격을 할 수 있습니다.\n"
                + "- 상대의 궁을 잡으면 게임이 종료됩니다.\n"
                + "- 보드에 남아있는 기물의 점수를 합산하여 점수를 게산합니다..\n"
                + "    - 차(C): 13점\n"
                + "    - 포(P): 7점\n"
                + "    - 마(H): 5점\n"
                + "    - 상(E): 3점\n"
                + "    - 사(G): 3점\n"
                + "    - 병(S): 2점"
                + "- 한은 후공이기 때문에 추가 점수 1.5점을 받습니다.\n"
                + "================");
        System.out.println("\n상차림을 입력하세요.\n"
                + "1. 안상\n"
                + "2. 바깥상\n"
                + "3. 오른상\n"
                + "4. 왼상");
    }

    public void displayPoints(final Game game) {
        EnumMap<Team, Double> teamPoints = game.getTeamPoints();
        System.out.println();
        System.out.printf("한나라: %.1f점%n", teamPoints.get(Team.HAN));
        System.out.printf("초나라: %.1f점%n", teamPoints.get(Team.CHO));
    }

    public void inGame() {
        System.out.println("\n게임을 시작합니다...");
    }

    public void outGame() {
        System.out.println("게임을 종료합니다...");
    }
}
