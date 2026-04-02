package domain.piece;

import domain.Position;
import java.util.Map;
import java.util.Objects;

public class PieceInfos {
    private final Map<Position, PieceInfo> pieceInfos;

    public PieceInfos(Map<Position, PieceInfo> pieceInfos) {
        this.pieceInfos = pieceInfos;
    }

    public PieceInfo get(Position position) {
        return pieceInfos.get(position);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PieceInfos that = (PieceInfos) o;
        return Objects.equals(pieceInfos, that.pieceInfos);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(pieceInfos);
    }
}
