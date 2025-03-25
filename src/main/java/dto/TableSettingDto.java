package dto;

import model.board.TableSetting;

public record TableSettingDto(
    TableSetting tableSetting
) {

    public static TableSettingDto from(String input) {
        return new TableSettingDto(toTableSetting(toInt(input)));
    }

    private static TableSetting toTableSetting(int menu) {
        return switch (menu) {
            case 1 ->  TableSetting.LEFT;
            case 2 ->  TableSetting.RIGHT;
            case 3 ->  TableSetting.INSIDE;
            case 4 ->  TableSetting.OUTSIDE;
            default -> throw new IllegalArgumentException("[ERROR] 주어진 상차림 내에서 선택해주세요. " + menu);
        };
    }

    private static int toInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 숫자를 입력해야 합니다. " + input);
        }
    }
}
