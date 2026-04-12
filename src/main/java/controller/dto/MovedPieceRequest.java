package controller.dto;

public record MovedPieceRequest(int currentRow,
                                int currentColumn,
                                int nextRow,
                                int nextColumn) {

    public static MovedPieceRequest of(String sourcePosition, String targetPosition) {
        String[] sourceRowAndColumn = sourcePosition.split(",");
        String[] targetRowAndColumn = targetPosition.split(",");

        return new MovedPieceRequest(
                Integer.parseInt(String.valueOf(sourceRowAndColumn[0])),
                Integer.parseInt(String.valueOf(sourceRowAndColumn[1])),
                Integer.parseInt(String.valueOf(targetRowAndColumn[0])),
                Integer.parseInt(String.valueOf(targetRowAndColumn[1]))
        );
    }
}
