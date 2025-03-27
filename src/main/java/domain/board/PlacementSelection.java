package domain.board;

import static domain.piece.Team.CHO;
import static domain.piece.Team.HAN;

import domain.piece.Elephant;
import domain.piece.Horse;
import domain.piece.Piece;
import java.util.Map;

public enum PlacementSelection {

    HEHE(
            Map.of(
                    new BoardLocation(2, 1), new Horse(HAN),
                    new BoardLocation(3, 1), new Elephant(HAN),
                    new BoardLocation(7, 1), new Horse(HAN),
                    new BoardLocation(8, 1), new Elephant(HAN)
            ),
            Map.of(
                    new BoardLocation(2, 10), new Horse(CHO),
                    new BoardLocation(3, 10), new Elephant(CHO),
                    new BoardLocation(7, 10), new Horse(CHO),
                    new BoardLocation(8, 10), new Elephant(CHO)
            )
    ),
    HEEH(
            Map.of(
                    new BoardLocation(2, 1), new Horse(HAN),
                    new BoardLocation(3, 1), new Elephant(HAN),
                    new BoardLocation(7, 1), new Elephant(HAN),
                    new BoardLocation(8, 1), new Horse(HAN)
            ),
            Map.of(
                    new BoardLocation(2, 10), new Horse(CHO),
                    new BoardLocation(3, 10), new Elephant(CHO),
                    new BoardLocation(7, 10), new Elephant(CHO),
                    new BoardLocation(8, 10), new Horse(CHO)
            )
    ),
    EHEH(
            Map.of(
                    new BoardLocation(2, 1), new Elephant(HAN),
                    new BoardLocation(3, 1), new Horse(HAN),
                    new BoardLocation(7, 1), new Elephant(HAN),
                    new BoardLocation(8, 1), new Horse(HAN)
            ),
            Map.of(
                    new BoardLocation(2, 10), new Elephant(CHO),
                    new BoardLocation(3, 10), new Horse(CHO),
                    new BoardLocation(7, 10), new Elephant(CHO),
                    new BoardLocation(8, 10), new Horse(CHO)
            )
    ),
    EHHE(
            Map.of(
                    new BoardLocation(2, 1), new Elephant(HAN),
                    new BoardLocation(3, 1), new Horse(HAN),
                    new BoardLocation(7, 1), new Horse(HAN),
                    new BoardLocation(8, 1), new Elephant(HAN)
            ),
            Map.of(
                    new BoardLocation(2, 10), new Elephant(CHO),
                    new BoardLocation(3, 10), new Horse(CHO),
                    new BoardLocation(7, 10), new Horse(CHO),
                    new BoardLocation(8, 10), new Elephant(CHO)
            )
    );

    private final Map<BoardLocation, Piece> han;
    private final Map<BoardLocation, Piece> cho;

    PlacementSelection(Map<BoardLocation, Piece> han, Map<BoardLocation, Piece> cho) {
        this.han = han;
        this.cho = cho;
    }

    public Map<BoardLocation, Piece> getHan() {
        return han;
    }

    public Map<BoardLocation, Piece> getCho() {
        return cho;
    }
}
