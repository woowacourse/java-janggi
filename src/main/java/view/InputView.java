package view;

import java.util.Scanner;

import dto.CommandDto;
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
}
