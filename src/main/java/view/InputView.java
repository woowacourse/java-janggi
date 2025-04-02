package view;

import domain.piece.coordiante.Coordinate;
import domain.board.setting.strategy.MaSangMaSang;
import domain.board.setting.strategy.MaSangSangMa;
import domain.board.setting.strategy.SangMaMaSang;
import domain.board.setting.strategy.SangMaSangMa;
import domain.piece.Country;
import java.util.Scanner;

public class InputView {

    private final static Scanner scanner = new Scanner(System.in);

    public Coordinate readMoveFrom(String countryName) {
        System.out.println(countryName + "의 옮길 기물의 좌표를 입력해주세요.");

        String coordinate = scanner.nextLine();
        String inputRow = coordinate.split(",")[0];
        String inputCol = coordinate.split(",")[1];

        return new Coordinate(convertRow(inputRow), convertCol(inputCol));
    }

    public Coordinate readMoveTo() {
        System.out.println("기물을 옮길 좌표를 입력해주세요.");

        String coordinate = scanner.nextLine();
        String inputRow = coordinate.split(",")[0];
        String inputCol = coordinate.split(",")[1];

        return new Coordinate(convertRow(inputRow), convertCol(inputCol));
    }

    private int convertRow(String inputRow) {
        int row = Integer.parseInt(inputRow);
        if (row == 0) {
            return 10;
        }
        return row;
    }

    private int convertCol(String inputCol) {
        return Integer.parseInt(inputCol);
    }

    public String readSettingUp(Country country) {
        System.out.println(country.getCountryName() + "의 상차림 전략을 선택해주세요. " + "["
                + SangMaMaSang.SANG_MA_MA_SANG + ", "
                + MaSangSangMa.MA_SANG_SANG_MA + ", "
                + SangMaSangMa.SANG_MA_SANG_MA + ", "
                + MaSangMaSang.MA_SANG_MA_SANG + "]");
        return scanner.nextLine();
    }

    public String readJoinGame() {
        System.out.println("참여하실 게임방 이름을 입력해주세요.");
        System.out.println("(새로운 게임을 시작하려면 생성할 방 이름을 입력해주세요.)");
        return scanner.nextLine();
    }
}
