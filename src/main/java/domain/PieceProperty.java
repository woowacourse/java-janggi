package domain;

public record PieceProperty(String pieceType, Team team) {

    public static PieceProperty of(PieceType pieceType, Team team) {
        return new PieceProperty(pieceType.description(), team);
    }
}
