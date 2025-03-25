package janggi.board;

import janggi.coordinate.Position;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import janggi.piece.Cannon;
import janggi.piece.Chariot;
import janggi.piece.Elephant;
import janggi.piece.General;
import janggi.piece.Guard;
import janggi.piece.Horse;
import janggi.piece.Piece;
import janggi.piece.Soldier;
import janggi.piece.Country;
import java.util.stream.Collectors;

public enum PieceInitialPosition {

    GENERAL(
            List.of(new Position(2, 5)),
            List.of(new Position(9, 5)),
            General::new
    ),
    GUARD(
            List.of(new Position(1, 4), new Position(1, 6)),
            List.of(new Position(10, 4), new Position(10, 6)),
            Guard::new
    ),
    HORSE(
            List.of(new Position(1, 2), new Position(1, 8)),
            List.of(new Position(10, 3), new Position(10, 7)),
            Horse::new
    ),
    ELEPHANT(
            List.of(new Position(1, 3), new Position(1, 7)),
            List.of(new Position(10, 2), new Position(10, 8)),
            Elephant::new
    ),
    CHARIOT(
            List.of(new Position(1, 1), new Position(1, 9)),
            List.of(new Position(10, 1), new Position(10, 9)),
            Chariot::new
    ),
    CANNON(
            List.of(new Position(3, 2), new Position(3, 8)),
            List.of(new Position(8, 2), new Position(8, 8)),
            Cannon::new
    ),
    SOLDIER(
            List.of(new Position(4, 1), new Position(4, 3), new Position(4, 5), new Position(4, 7), new Position(4, 9)),
            List.of(new Position(7, 1), new Position(7, 3), new Position(7, 5), new Position(7, 7), new Position(7, 9)),
            Soldier::new
    );

    private final Map<Country, List<Position>> initPositions;
    private final Function<Country, ? extends Piece> construct;

    PieceInitialPosition(final List<Position> redTeam, final List<Position> blueTeam,
                         final Function<Country, ? extends Piece> construct) {
        initPositions = Map.of(
                Country.HAN, redTeam,
                Country.CHO, blueTeam
        );
        this.construct = construct;
    }

    public Map<Position, Piece> makeInitPieces(final Country country) {
        return initPositions.get(country).stream()
                .collect(Collectors.toMap(
                        Function.identity(), position -> construct.apply(country)
                ));
    }

}
