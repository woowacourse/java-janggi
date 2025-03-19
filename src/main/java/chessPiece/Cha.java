package chessPiece;

public class Cha extends ChessPiece {

    public Cha(final String name, final BoardPosition boardPosition) {
        super(name, boardPosition);
    }

    @Override
    public Cha move(BoardPosition boardPosition) {
        if (super.getBoardPosition().getRow() == boardPosition.getRow()
                || super.getBoardPosition().getCol() == boardPosition.getCol()) {
            return new Cha("차", boardPosition);
        }
        throw new IllegalArgumentException("[ERROR] 차가 움직일 수 없는 위치입니다.");
    }

}
