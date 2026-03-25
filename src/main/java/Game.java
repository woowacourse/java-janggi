import java.util.HashMap;
import java.util.Map;

public class Game {

    private final Board board;

    public Game() {
        Map<Position, Piece> fixedPieces = generateInitialBoard();
        this.board = new Board(fixedPieces);
    }

    private static Map<Position, Piece> generateInitialBoard() {
        Map<Position, Piece> initialPieces = new HashMap<>();
        String choSelection = "1";
        if (choSelection.equals("1")) {
            initialPieces.put(new Position(1, 0), new Elephant(Side.CHO));
            initialPieces.put(new Position(6, 0), new Elephant(Side.CHO));
            initialPieces.put(new Position(2, 0), new Horse(Side.CHO));
            initialPieces.put(new Position(7, 0), new Horse(Side.CHO));
        }
        initialPieces.put(new Position(0, 0), new Chariot(Side.CHO));
        initialPieces.put(new Position(8, 0), new Chariot(Side.CHO));
        initialPieces.put(new Position(3, 0), new Guard(Side.CHO));
        initialPieces.put(new Position(5, 0), new Guard(Side.CHO));
        initialPieces.put(new Position(4, 1), new General(Side.CHO));
        initialPieces.put(new Position(1, 2), new Cannon(Side.CHO));
        initialPieces.put(new Position(7, 2), new Cannon(Side.CHO));
        initialPieces.put(new Position(0, 3), new Soldier(Side.CHO));
        initialPieces.put(new Position(2, 3), new Soldier(Side.CHO));
        initialPieces.put(new Position(4, 3), new Soldier(Side.CHO));
        initialPieces.put(new Position(6, 3), new Soldier(Side.CHO));
        initialPieces.put(new Position(8, 3), new Soldier(Side.CHO));

        String hanSelection = "1";
        if (hanSelection.equals("1")) {
            initialPieces.put(new Position(2, 9), new Elephant(Side.HAN));
            initialPieces.put(new Position(7, 9), new Elephant(Side.HAN));
            initialPieces.put(new Position(1, 9), new Horse(Side.HAN));
            initialPieces.put(new Position(6, 9), new Horse(Side.HAN));
        }
        initialPieces.put(new Position(0, 9), new Chariot(Side.HAN));
        initialPieces.put(new Position(8, 9), new Chariot(Side.HAN));
        initialPieces.put(new Position(3, 9), new Guard(Side.HAN));
        initialPieces.put(new Position(5, 9), new Guard(Side.HAN));
        initialPieces.put(new Position(4, 8), new General(Side.HAN));
        initialPieces.put(new Position(1, 7), new Cannon(Side.HAN));
        initialPieces.put(new Position(7, 7), new Cannon(Side.HAN));
        initialPieces.put(new Position(0, 6), new Soldier(Side.HAN));
        initialPieces.put(new Position(2, 6), new Soldier(Side.HAN));
        initialPieces.put(new Position(4, 6), new Soldier(Side.HAN));
        initialPieces.put(new Position(6, 6), new Soldier(Side.HAN));
        initialPieces.put(new Position(8, 6), new Soldier(Side.HAN));
        return initialPieces;
    }
}
