package domain.piece;

import domain.Position;
import java.util.Map;

public class PieceInfos {
    private final Map<Position, PieceInfo> pieceInfos;

    public PieceInfos(Map<Position, PieceInfo> pieceInfos) {
        this.pieceInfos = pieceInfos;
    }

    public PieceInfo get(Position position) {
        return pieceInfos.get(position);
    }
}
