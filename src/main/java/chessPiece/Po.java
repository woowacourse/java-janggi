package chessPiece;

public class Po extends ChessPiece {

    public Po(final String name, final BoardPosition boardPosition) {
        super(name, boardPosition);
    }

    @Override
    public Po move(final BoardPosition boardPosition) {
        if (super.getBoardPosition().getRow() == boardPosition.getRow()
                || super.getBoardPosition().getCol() == boardPosition.getCol()) {
            return new Po("포", boardPosition);
        }
        throw new IllegalArgumentException("[ERROR] 포가 움직일 수 없는 위치입니다.");
    }

}
