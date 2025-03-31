package view;

import java.util.List;
import java.util.Scanner;

import constant.JanggiConstant;
import dto.CommandDto;
import dto.GameDto;
import dto.TableSettingDto;
import dto.TeamDto;
import view.util.Color;

public class InputView {
    private final Scanner scanner = new Scanner(System.in);

    public CommandDto command() {
        System.out.println("""
            이동할 기물의 현재 위치와 이동할 대상 위치를 좌표로 입력해주세요.(ex. i1 i2)
            (Q를 입력하여 기권할 수 있습니다.)""");

        return CommandDto.from(scanner.nextLine());
    }

    public TableSettingDto tableSetting(TeamDto dto) {
        System.out.printf("""
            %s는 상차림을 선택해주세요.
            1. 왼상차림     :  차 상 마 사 궁 사 상 마 차
            2. 오른상차림 :  차 마 상 사 궁 사 마 상 차
            3. 안상차림    :  차 마 상 사 궁 사 상 마 차
            4. 바깥상차림 :  차 상 마 사 궁 사 마 상 차
            """, Color.apply(dto, dto.getDisplayName()));

        return TableSettingDto.from(scanner.nextLine());
    }

    public int selectGameRoom(List<GameDto> games) {
        System.out.printf("참여할 방 id를 입력해주세요. (새로운 방을 만들려면 %d를 입력해주세요)%n", JanggiConstant.NEW_GAME_ROOM_ID);
        games.stream()
            .map(game -> String.format("%,d: %20s (현재 턴: %s)", game.id(), game.name(), game.turn().getDisplayName()))
            .forEach(System.out::println);
        return Integer.parseInt(scanner.nextLine());
    }

    public String gameName() {
        System.out.println("생성할 방 이름을 입력해주세요.");
        return scanner.nextLine();
    }
}
