package domain.board;

import domain.piece.Delta;
import domain.piece.Position;
import domain.player.Team;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class PalaceInitializer {

    public static Palace initialize() {
        return new Palace(generateArea(), generateMovables());
    }

    private static Map<Team, Set<Position>> generateArea() {
        return Map.of(
                Team.HAN, Set.of(
                        Position.of(1, 4), Position.of(1, 5), Position.of(1, 6),
                        Position.of(2, 4), Position.of(2, 5), Position.of(2, 6),
                        Position.of(3, 4), Position.of(3, 5), Position.of(3, 6)
                ),
                Team.CHO, Set.of(
                        Position.of(8, 4), Position.of(8, 5), Position.of(8, 6),
                        Position.of(9, 4), Position.of(9, 5), Position.of(9, 6),
                        Position.of(10, 4), Position.of(10, 5), Position.of(10, 6)
                )
        );
    }

    private static Map<Position, List<Delta>> generateMovables() {
        return Map.ofEntries(
                // HAN 중앙
                Map.entry(
                        Position.of(2, 5),
                        List.of(Delta.LEFT_UP, Delta.RIGHT_UP, Delta.LEFT_DOWN, Delta.RIGHT_DOWN)
                ),

                // HAN 꼭짓점
                Map.entry(Position.of(1, 4), List.of(Delta.RIGHT_DOWN)),
                Map.entry(Position.of(1, 6), List.of(Delta.LEFT_DOWN)),
                Map.entry(Position.of(3, 4), List.of(Delta.RIGHT_UP)),
                Map.entry(Position.of(3, 6), List.of(Delta.LEFT_UP)),

                // CHO 중앙
                Map.entry(
                        Position.of(9, 5),
                        List.of(Delta.LEFT_UP, Delta.RIGHT_UP, Delta.LEFT_DOWN, Delta.RIGHT_DOWN)
                ),

                // CHO 꼭짓점
                Map.entry(Position.of(8, 4), List.of(Delta.RIGHT_DOWN)),
                Map.entry(Position.of(8, 6), List.of(Delta.LEFT_DOWN)),
                Map.entry(Position.of(10, 4), List.of(Delta.RIGHT_UP)),
                Map.entry(Position.of(10, 6), List.of(Delta.LEFT_UP))
        );
    }
}
