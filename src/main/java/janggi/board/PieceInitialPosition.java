package janggi.board;

import janggi.coordinate.JanggiPosition;
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
            List.of(new JanggiPosition(2, 5)),
            List.of(new JanggiPosition(9, 5)),
            General::new
    ),
    GUARD(
            List.of(new JanggiPosition(1, 4), new JanggiPosition(1, 6)),
            List.of(new JanggiPosition(10, 4), new JanggiPosition(10, 6)),
            Guard::new
    ),
    HORSE(
            List.of(new JanggiPosition(1, 2), new JanggiPosition(1, 8)),
            List.of(new JanggiPosition(10, 3), new JanggiPosition(10, 7)),
            Horse::new
    ),
    ELEPHANT(
            List.of(new JanggiPosition(1, 3), new JanggiPosition(1, 7)),
            List.of(new JanggiPosition(10, 2), new JanggiPosition(10, 8)),
            Elephant::new
    ),
    CHARIOT(
            List.of(new JanggiPosition(1, 1), new JanggiPosition(1, 9)),
            List.of(new JanggiPosition(10, 1), new JanggiPosition(10, 9)),
            Chariot::new
    ),
    CANNON(
            List.of(new JanggiPosition(3, 2), new JanggiPosition(3, 8)),
            List.of(new JanggiPosition(8, 2), new JanggiPosition(8, 8)),
            Cannon::new
    ),
    SOLDIER(
            List.of(new JanggiPosition(4, 1), new JanggiPosition(4, 3), new JanggiPosition(4, 5), new JanggiPosition(4, 7), new JanggiPosition(4, 9)),
            List.of(new JanggiPosition(7, 1), new JanggiPosition(7, 3), new JanggiPosition(7, 5), new JanggiPosition(7, 7), new JanggiPosition(7, 9)),
            Soldier::new
    );

    private final Map<Country, List<JanggiPosition>> initPositions;
    private final Function<Country, ? extends Piece> construct;

    PieceInitialPosition(final List<JanggiPosition> redTeam, final List<JanggiPosition> blueTeam,
                         final Function<Country, ? extends Piece> construct) {
        initPositions = Map.of(
                Country.HAN, redTeam,
                Country.CHO, blueTeam
        );
        this.construct = construct;
    }

    public Map<JanggiPosition, Piece> makeInitPieces(final Country country) {
        return initPositions.get(country).stream()
                .collect(Collectors.toMap(
                        Function.identity(), position -> construct.apply(country)
                ));
    }

}
