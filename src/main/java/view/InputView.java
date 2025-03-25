package view;

import domain.JanggiCoordinate;

import java.util.Scanner;

public class InputView {

    private static final int ROW_IDX = 0;
    private static final int COL_IDX = 1;

    private final static Scanner scanner = new Scanner(System.in);

    public JanggiCoordinate readMovePiece() {
        System.out.println("옮길 기물을 입력해주세요 : ");
        String coordinate = scanner.nextLine();
        validateInput(coordinate);
        String[] parsedCoordinate = coordinate.split("");
        return convertToJanggiCoordinate(parsedCoordinate[ROW_IDX], parsedCoordinate[COL_IDX]);
    }

    public JanggiCoordinate readMoveDestination() {
        System.out.println("옮길 위치를 입력해 주세요 : ");
        String coordinate = scanner.nextLine();
        validateInput(coordinate);
        String[] parsedCoordinate = coordinate.split("");
        return convertToJanggiCoordinate(parsedCoordinate[ROW_IDX], parsedCoordinate[COL_IDX]);
    }

    private JanggiCoordinate convertToJanggiCoordinate(String row, String col) {
        int rowNum = Integer.parseInt(row);
        int colNum = Integer.parseInt(col);

        if (rowNum == 0) {
            rowNum = 10;
        }
        return new JanggiCoordinate(rowNum, colNum);
    }

    private void validateInput(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 숫자만 입력 가능합니다. 예시) 23, 35");
        }
    }
}
