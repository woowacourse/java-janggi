package domain;

import static domain.Team.CHO;
import static domain.Team.HAN;
import static domain.piece.PieceType.ELEPHANT;
import static domain.piece.PieceType.HORSE;

import domain.piece.Piece;
import java.util.Map;

public enum PlacementSelection {

    HEHE(
            Map.of(
                    new BoardLocation(2,1), new Piece(HORSE, HAN),
                    new BoardLocation(3,1), new Piece(ELEPHANT, HAN),
                    new BoardLocation(7,1), new Piece(HORSE, HAN),
                    new BoardLocation(8,1), new Piece(ELEPHANT, HAN)
            ),
            Map.of(
                    new BoardLocation(2,10), new Piece(HORSE, CHO),
                    new BoardLocation(3,10), new Piece(ELEPHANT, CHO),
                    new BoardLocation(7,10), new Piece(HORSE, CHO),
                    new BoardLocation(8,10), new Piece(ELEPHANT, CHO)
            )
    ),
    HEEH(
            Map.of(
                    new BoardLocation(2,1), new Piece(HORSE, HAN),
                    new BoardLocation(3,1), new Piece(ELEPHANT, HAN),
                    new BoardLocation(7,1), new Piece(ELEPHANT, HAN),
                    new BoardLocation(8,1), new Piece(HORSE, HAN)
            ),
            Map.of(
                    new BoardLocation(2,10), new Piece(HORSE, CHO),
                    new BoardLocation(3,10), new Piece(ELEPHANT, CHO),
                    new BoardLocation(7,10), new Piece(ELEPHANT, CHO),
                    new BoardLocation(8,10), new Piece(HORSE, CHO)
            )
    ),
    EHEH(
            Map.of(
                    new BoardLocation(2,1), new Piece(ELEPHANT, HAN),
                    new BoardLocation(3,1), new Piece(HORSE, HAN),
                    new BoardLocation(7,1), new Piece(ELEPHANT, HAN),
                    new BoardLocation(8,1), new Piece(HORSE, HAN)
            ),
            Map.of(
                    new BoardLocation(2,10), new Piece(ELEPHANT, CHO),
                    new BoardLocation(3,10), new Piece(HORSE, CHO),
                    new BoardLocation(7,10), new Piece(ELEPHANT, CHO),
                    new BoardLocation(8,10), new Piece(HORSE, CHO)
            )
    ),
    EHHE(
            Map.of(
                    new BoardLocation(2,1), new Piece(ELEPHANT, HAN),
                    new BoardLocation(3,1), new Piece(HORSE, HAN),
                    new BoardLocation(7,1), new Piece(HORSE, HAN),
                    new BoardLocation(8,1), new Piece(ELEPHANT, HAN)
            ),
            Map.of(
                    new BoardLocation(2,10), new Piece(ELEPHANT, CHO),
                    new BoardLocation(3,10), new Piece(HORSE, CHO),
                    new BoardLocation(7,10), new Piece(HORSE, CHO),
                    new BoardLocation(8,10), new Piece(ELEPHANT, CHO)
            )
    );

    private final Map<BoardLocation, Piece> han;
    private final Map<BoardLocation, Piece> cho;

    PlacementSelection(Map<BoardLocation, Piece> han, Map<BoardLocation, Piece> cho) {
        this.han = han;
        this.cho = cho;
    }
}
