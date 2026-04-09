package janggi.domain.board;

import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import janggi.domain.Team;

import java.util.Arrays;
import java.util.Map;

public enum PieceSetup {
    LEFT_ELEPHANT("1") {
        @Override
        public void apply(Map<Position, Piece> base, Team team) {
            if (team == Team.HAN) {
                swap(base, Position.of(1, 2), Position.of(1, 3));
            }
            if (team == Team.CHO) {
                swap(base, Position.of(10, 7), Position.of(10, 8));
            }
        }
    },
    RIGHT_ELEPHANT("2") {
        @Override
        public void apply(Map<Position, Piece> base, Team team) {
            if (team == Team.HAN) {
                swap(base, Position.of(1, 7), Position.of(1, 8));
            }
            if (team == Team.CHO) {
                swap(base, Position.of(10, 2), Position.of(10, 3));
            }
        }
    },
    INNER_ELEPHANT("3") {
        @Override
        public void apply(Map<Position, Piece> base, Team team) {
            if (team == Team.HAN) {
                swap(base, Position.of(1, 7), Position.of(1, 8));
                swap(base, Position.of(1, 2), Position.of(1, 3));
            }
            if (team == Team.CHO) {
                swap(base, Position.of(10, 7), Position.of(10, 8));
                swap(base, Position.of(10, 2), Position.of(10, 3));
            }
        }
    },
    OUTER_ELEPHANT("4") {
        @Override
        public void apply(Map<Position, Piece> base, Team team) {
            // 바깥상차림은 기본 배치
        }
    };

    private final String value;

    PieceSetup(String value) {
        this.value = value;
    }

    public static PieceSetup from(String value) {
        return Arrays.stream(values())
                .filter(setup -> setup.value.equals(value))
                .findAny().orElseThrow(
                        () -> new IllegalArgumentException("[ERROR] 올바른 차림 번호를 입력해주세요.")
                );
    }

    public abstract void apply(Map<Position, Piece> base, Team team);

    private static void swap(Map<Position, Piece> base, Position position1, Position position2) {
        Piece piece1 = base.get(position1);
        Piece piece2 = base.get(position2);

        base.put(position1, piece2);
        base.put(position2, piece1);
    }
}
