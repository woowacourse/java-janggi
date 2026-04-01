package domain;

public record Piece(Camp camp, PieceType pieceType, MoveStrategy moveStrategy) {
    public static Piece of(Camp camp, PieceType pieceType){
        return new Piece(camp, pieceType, pieceType.moveStrategy());
    }
}
