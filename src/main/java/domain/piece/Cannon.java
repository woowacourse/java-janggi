package domain.piece;

import domain.country.CountryType;
import java.util.List;

public class Cannon extends MoveStraightPiece {
    private static final String INVALID_JUMP_PIECE_COUNT = "[ERROR] 포는 하나의 기물만 뛰어 넘을 수 있습니다.";
    private static final String CANNOT_JUMP_CANNON = "[ERROR] 포는 포를 뛰어 넘을 수 없습니다.";
    private static final String CANNOT_KILL_CANNON = "[ERROR] 포는 포를 잡을 수 없습니다.";

    private static final int CANNON_JUMP_PIECE_COUNT = 1;

    public Cannon(CountryType countryType) {
        super(new PieceInfo(PieceType.CANNON, countryType));
    }

    @Override
    void validateToPiece(PieceInfo fromPiece, PieceInfo toPiece) {
        super.validateToPiece(fromPiece, toPiece);
        if (fromPiece.pieceType() == toPiece.pieceType()) {
            throw new IllegalArgumentException(CANNOT_KILL_CANNON);
        }
    }

    @Override
    void validatePath(List<PieceInfo> pieceInfos) {
        int pieceCount = 0;
        for (PieceInfo pieceInfo : pieceInfos) {
            validatePathPiece(pieceInfo);
            pieceCount = adjustPieceCount(pieceInfo, pieceCount);
        }
        validatePieceCount(pieceCount);
    }

    @Override
    void validatePathPiece(PieceInfo pieceInfo) {
        if (pieceInfo.pieceType() == PieceType.CANNON) {
            throw new IllegalArgumentException(CANNOT_JUMP_CANNON);
        }
    }

    private int adjustPieceCount(PieceInfo pieceInfo, int pieceCount) {
        if (pieceInfo.pieceType() != PieceType.NONE) {
            return ++pieceCount;
        }
        return pieceCount;
    }

    private void validatePieceCount(int pieceCount) {
        if (pieceCount != CANNON_JUMP_PIECE_COUNT) {
            throw new IllegalArgumentException(INVALID_JUMP_PIECE_COUNT);
        }
    }
}
