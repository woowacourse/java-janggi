package domain;

record PieceProperty(String pieceType, Team team) {

    PieceProperty of(PieceType pieceType, Team team) {
        return new PieceProperty(pieceType.description(), team);
    }
}
