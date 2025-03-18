public class Chariot extends Piece {
    Chariot(PieceColor color) {
        super(color);
    }

    @Override
    public boolean canMove(Position source, Position destination) {
        int rowDifference = source.rowDifference(destination);
        int columnDifference = source.columnDifference(destination);

        if (rowDifference == 0 || columnDifference == 0) {
            return true;
        }
        return false;
    }
}
