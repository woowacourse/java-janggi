package view;

import domain.Coordinate;
import domain.board.strategy.MaSangMaSang;
import domain.board.strategy.MaSangSangMa;
import domain.board.strategy.SangMaMaSang;
import domain.board.strategy.SangMaSangMa;
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
}
