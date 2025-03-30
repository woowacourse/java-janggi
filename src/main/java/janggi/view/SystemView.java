package janggi.view;

import janggi.domain.game.Game;
import janggi.domain.game.Team;
import janggi.dto.GameDto;
import java.time.format.DateTimeFormatter;
import java.util.EnumMap;
import java.util.List;

public final class SystemView {

    public void displayGameDescriptions() {
        System.out.println("""
                ======================= 기물 표기법 =======================
                  한 초
                궁 k K
                차 c C
                포 p P
                마 h H
                상 e E
                사 g G
                병 s S""");
        System.out.println("""
                ======================= 명령어 안내 =======================
                end: 게임을 종료합니다. 중간에 종료된 게임은 자동 저장됩니다.
                move [시작위치] [종료위치]` : 시작위치에서 종료위치로 기물을 이동합니다.
                    `[위치]` : 열번호, 행번호 순으로 입력합니다.""");
        System.out.println("""
                ======================== 게임 규칙 ========================
                - 초나라가 선공이며 번갈아가면서 공격을 할 수 있습니다.
                - 상대의 궁을 잡으면 게임이 종료됩니다.
                - 보드에 남아있는 기물의 점수를 합산하여 점수를 게산합니다.
                    - 차(C): 13점
                    - 포(P): 7점
                    - 마(H): 5점
                    - 상(E): 3점
                    - 사(G): 3점
                    - 병(S): 2점
                - 한은 후공이기 때문에 추가 점수 1.5점을 받습니다.
                =========================================================""");
    }

    public void displayStoredGames(final List<GameDto> allGames) {
        System.out.println("\n게임 번호를 선택하세요.");
        System.out.println("0: 새 게임 시작하기");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd a hh:mm:ss");
        for (GameDto gameDto : allGames) {
            System.out.printf("%d: %s에 시작한 게임%n", gameDto.id(), dateTimeFormatter.format(gameDto.createdAt()));
        }
    }

    public void displaySetupMenus() {
        System.out.println("""

                상차림을 입력하세요.
                1. 안상
                2. 바깥상
                3. 오른상
                4. 왼상""");
    }

    public void displayPoints(final Game game) {
        EnumMap<Team, Double> teamPoints = game.getTeamPoints();
        System.out.println();
        System.out.printf("한나라: %.1f점%n", teamPoints.get(Team.HAN));
        System.out.printf("초나라: %.1f점%n", teamPoints.get(Team.CHO));
    }

    public void inGame(final int gameId) {
        System.out.printf("%d번 게임을 시작합니다...%n", gameId);
    }

    public void outGame() {
        System.out.println("게임을 종료합니다...");
    }

    public void win(final Team team) {
        System.out.printf("%s나라의 승리로 게임이 종료되었습니다.", team.getName());
    }
}
