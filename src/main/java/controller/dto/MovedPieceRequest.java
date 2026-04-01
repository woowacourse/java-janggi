package controller.dto;

public record MovedPieceRequest(int currentRow,
                                int currentColumn,
                                int nextRow,
                                int nextColumn,
                                String pieceType) {

    public static MovedPieceRequest of(String sourcePositionAndPieceType, String targetPosition) {
        String[] sourceAndPieceType = sourcePositionAndPieceType.split(" ");

        return new MovedPieceRequest(
                Integer.parseInt(String.valueOf(sourceAndPieceType[0].charAt(1))),
                Integer.parseInt(String.valueOf(sourceAndPieceType[0].charAt(3))),
                Integer.parseInt(String.valueOf(targetPosition.charAt(1))),
                Integer.parseInt(String.valueOf(targetPosition.charAt(3))),
                sourceAndPieceType[1]
        );
    }
}
