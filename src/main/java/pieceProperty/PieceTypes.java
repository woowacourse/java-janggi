package pieceProperty;

import java.util.ArrayList;
import java.util.List;

public class PieceTypes {
    private final List<PieceType> pieceTypes;

    public PieceTypes(List<PieceType> pieceTypes) {
        this.pieceTypes = new ArrayList<>(pieceTypes);
    }

    public int calculateSum() {
        return pieceTypes.stream()
                .mapToInt(PieceType::getScore)
                .sum();
    }

    public List<PieceType> getPieceTypes() {
        return pieceTypes;
    }
}
