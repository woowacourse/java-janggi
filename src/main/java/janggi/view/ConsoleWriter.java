package janggi.view;

import java.util.ArrayList;
import java.util.List;

public class ConsoleWriter implements Output {

    private static final String BLANK = "   ";
    private static final List<String> FULL_WIDTH_NUMBERS =
            List.of("", "１", "２", "３", "４", "５", "６", "７", "８", "９");

    @Override
    public void printPromptMessage(String promptMessage) {
        System.out.println(promptMessage);
    }

    @Override
    public void printErrorMessage(String errorMessage) {
        System.out.println("[ERROR] " + errorMessage);
        System.out.println();
    }

    @Override
    public void printStringMatrix(List<List<String>> matrix) {
        StringBuilder matrixSnapshot = new StringBuilder();

        List<String> colIndexInfo = createCoordinationIndexInfo(matrix.getFirst().size());
        matrixSnapshot.append(String.join("", colIndexInfo)).append("\n");

        for (int row = 0; row < matrix.size(); row++) {
            matrixSnapshot.append(String.format("%2d ", row + 1));

            for (String cell : matrix.get(row)) {
                matrixSnapshot.append(cell).append(" ");
            }
            matrixSnapshot.append("\n");
        }

        System.out.println();
        System.out.println(matrixSnapshot);
    }

    private List<String> createCoordinationIndexInfo(int length) {
        List<String> coordinationInfo = new ArrayList<>();
        coordinationInfo.add(BLANK);
        for (int index = 1; index <= length; index++) {
            coordinationInfo.add(FULL_WIDTH_NUMBERS.get(index) + " ");
        }
        return coordinationInfo;
    }
}
