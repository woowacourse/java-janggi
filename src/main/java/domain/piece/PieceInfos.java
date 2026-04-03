package domain.piece;

import domain.Position;
import java.util.List;
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

    public List<Position> getKeys() {
        return pieceInfos.keySet()
                .stream()
                .toList();
    }

    public List<PieceInfo> getValues() {
        return pieceInfos.values()
                .stream()
                .toList();
    }

    public void deleteFromAndTo(Position from, Position to) {
        pieceInfos.remove(from);
        if (isEmptyPosition(to)) {
            return;
        }
        pieceInfos.remove(to);
    }

    public boolean isEmptyPosition(Position position) {
        return !pieceInfos.containsKey(position);
    }

    public int getSize() {
        return pieceInfos.size();
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
