package janggi.view;

import janggi.domain.Side;
import janggi.dto.PieceDto;
import java.util.ArrayList;
import java.util.List;

public class ConsoleWriter implements Output {

    private static final String BLANK = "   ";
    private static final List<String> FULL_WIDTH_NUMBERS =
            List.of("", "１", "２", "３", "４", "５", "６", "７", "８", "９");
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_BLUE = "\u001B[34m";

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
    public void printPieceMatrix(List<List<PieceDto>> matrix) {
        StringBuilder matrixSnapshot = new StringBuilder();

        List<String> colIndexInfo = createCoordinationIndexInfo(matrix.getFirst().size());
        matrixSnapshot.append(String.join("", colIndexInfo)).append("\n");

        for (int row = 0; row < matrix.size(); row++) {
            matrixSnapshot.append(String.format("%2d ", row + 1));

            for (PieceDto pieceDto : matrix.get(row)) {
                String piece = convertPieceInfo(pieceDto);
                matrixSnapshot.append(piece).append(" ");
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

    private String convertPieceInfo(PieceDto pieceDto) {
        String pieceType = pieceDto.type();
        Side side = pieceDto.side();

        if (side.equals(Side.HAN)) {
            return ANSI_RED + pieceType + ANSI_RESET;
        }

        if (side.equals(Side.CHO)) {
            return ANSI_BLUE + pieceType + ANSI_RESET;
        }

        return pieceType;
    }
}
