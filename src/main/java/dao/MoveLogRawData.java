package dao;

public record MoveLogRawData(
        int seq,
        String type,
        String turn,
        Integer fromRow,
        Integer fromCol,
        Integer toRow,
        Integer toCol,
        String pieceType
) {
}
