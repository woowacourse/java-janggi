public class Soldier extends Piece {

    public Soldier(PieceColor color) {
        super(color);
    }

    @Override
    public boolean canMove(Position source, Position destination) {
        int rowDifference = source.rowDifference(destination);
        int columnDifference = source.columnDifference(destination);

        if ((color == PieceColor.RED) && (rowDifference == 1 && columnDifference == 0)) {
            return true;
        }
        if ((color == PieceColor.BLUE) && (rowDifference == -1 && columnDifference == 0)) {
            return true;
        }
        if ((rowDifference == 0 && columnDifference == -1) || (rowDifference == 0 && columnDifference == 1)) {
            return true;
        }
        return false;
    }

}
