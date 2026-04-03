package domain.piece;

import domain.Position;
import domain.country.CountryType;

public class Cannon extends MoveStraightPiece {
    private static final String INVALID_JUMP_PIECE_COUNT = "[ERROR] 포는 하나의 기물만 뛰어 넘을 수 있습니다.";
    private static final String CANNOT_JUMP_CANNON = "[ERROR] 포는 포를 뛰어 넘을 수 없습니다.";
    private static final String CANNOT_KILL_CANNON = "[ERROR] 포는 포를 잡을 수 없습니다.";

    private static final int CANNON_JUMP_PIECE_COUNT = 1;

    public Cannon(CountryType countryType) {
        super(new PieceInfo(PieceType.CANNON, countryType));
    }

    @Override
    void validateToPiece(PieceInfos pieceInfos, Position from, Position to) {
        super.validateToPiece(pieceInfos, from, to);
        if (pieceInfos.isEmptyPosition(to)) {
            return;
        }
        PieceInfo fromPiece = pieceInfos.get(from);
        PieceInfo toPiece = pieceInfos.get(to);
        if (fromPiece.pieceType() == toPiece.pieceType()) {
            throw new IllegalArgumentException(CANNOT_KILL_CANNON);
        }
    }

    @Override
    void validatePath(PieceInfos pieceInfos) {
        if (pieceInfos.getSize() != CANNON_JUMP_PIECE_COUNT) {
            throw new IllegalArgumentException(INVALID_JUMP_PIECE_COUNT);
        }
        PieceInfo pieceInfo = pieceInfos.getValues().getFirst();
        if (pieceInfo.pieceType() == PieceType.CANNON) {
            throw new IllegalArgumentException(CANNOT_JUMP_CANNON);
        }
    }
}
