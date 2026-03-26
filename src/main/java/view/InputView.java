package view;

import domain.SettingType;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InputView {

    private static final String DELIMITER = ",";
    private static final String SETTING_DESCRIPTION = "초나라와 한나라의 차림을 각각 선택하세요(콤마로 구분, 예시 => 1,3)\n"
            + "① 왼상차림 (상마상마), ② 오른상차림 (마상마상), ③ 안상차림 (마상상마), ④ 바깥상차림 (상마마상)";


    private final Scanner sc = new Scanner(System.in);

    public List<SettingType> readSettings() {
        System.out.println(SETTING_DESCRIPTION);
        return Arrays.stream(sc.nextLine().split(DELIMITER))
                .map(String::strip)
                .map(InputView::selectSettingType)
                .toList();
    }

    private static SettingType selectSettingType(String info) {
        if (info.equals("1")) {
            return SettingType.LEFT;
        }
        if (info.equals("2")) {
            return SettingType.RIGHT;
        }
        if (info.equals("3")) {
            return SettingType.INNER;
        }
        if (info.equals("4")) {
            return SettingType.OUTER;
        }

        throw new IllegalArgumentException("1~4 사이의 숫자만 입력해주세요.");
    }
}
