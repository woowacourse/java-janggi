package domain;

import java.util.List;

public enum ElephantFormation {

    RIGHT(List.of(PieceType.ELEPHANT, PieceType.HORSE, PieceType.ELEPHANT, PieceType.HORSE)),
    INNER(List.of(PieceType.HORSE, PieceType.ELEPHANT, PieceType.ELEPHANT, PieceType.HORSE)),
    LEFT(List.of(PieceType.HORSE, PieceType.ELEPHANT, PieceType.HORSE, PieceType.ELEPHANT)),
    OUTER(List.of(PieceType.ELEPHANT, PieceType.HORSE, PieceType.HORSE, PieceType.ELEPHANT));

    private List<PieceType> elephantFormation;

    ElephantFormation(List<PieceType> elephantFormation) {
        this.elephantFormation = elephantFormation;
    }

    public List<PieceType> getElephantFormation() {
        return elephantFormation;
    }
}
