package view;

import domain.Coordinate;
import domain.board.SettingUp;
import domain.piece.Country;
import java.util.Scanner;

public class InputView {

    private final static Scanner scanner = new Scanner(System.in);

    public Coordinate readMoveFrom(String countryName) {
        System.out.println(countryName + "의 옮길 기물의 좌표를 입력해주세요.");
        String coordinate = scanner.nextLine();
        return new Coordinate(
                Integer.parseInt(coordinate.split(",")[0]),
                Integer.parseInt(coordinate.split(",")[1]));
    }

    public Coordinate readMoveTo() {
        System.out.println("기물을 옮길 좌표를 입력해주세요.");
        String coordinate = scanner.nextLine();
        return new Coordinate(
                Integer.parseInt(coordinate.split(",")[0]),
                Integer.parseInt(coordinate.split(",")[1]));
    }

    public String readSettingUp(Country country) {
        System.out.println(country.getCountryName() + "의 상차림 전략을 선택해주세요. " + "["
                + SettingUp.SANG_MA_MA_SANG.getSettingUpName() + ", "
                + SettingUp.MA_SANG_MA_SANG.getSettingUpName() + ", "
                + SettingUp.MA_SANG_SANG_MA.getSettingUpName() + ", "
                + SettingUp.SANG_MA_SANG_MA.getSettingUpName() + "]");
        return scanner.nextLine();
    }
}
