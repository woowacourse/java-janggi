package domain;

public record PathInfo(Position position, Piece piece) {
    public boolean hasPiece(){
        return piece != null;
    }
}
