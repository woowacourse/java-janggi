package janggi.domain.piece;

import janggi.domain.ReplaceUnderBar;
import janggi.domain.position.Position;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@ReplaceUnderBar
class KingTest {

    public static final Side ALLY_SIDE = Side.HAN;
    public static final Side ENEMY_SIDE = Side.CHO;
    public static final Position ALLY_KING_START_POSITION = new Position(4, 1);
    public static final Position ENEMY_KING_START_POSITION = new Position(4, 8);
    public static final King ALLY_KING = new King(ALLY_SIDE, ALLY_KING_START_POSITION.getX(), ALLY_KING_START_POSITION.getY());
    public static final King ENEMY_KING = new King(ENEMY_SIDE, ENEMY_KING_START_POSITION.getX(), ENEMY_KING_START_POSITION.getY());

    public static Stream<Arguments> 왕은_궁성_내에서_상하좌우_대각선_한칸을_이동할_수_있다_테스트_케이스() {
        return Stream.of(
            // 한나라 왕 중앙에서 이동할 수 있는 모든 경우
            Arguments.of(ALLY_KING, new Position(3,2)),
            Arguments.of(ALLY_KING, new Position(4,2)),
            Arguments.of(ALLY_KING, new Position(5,2)),
            Arguments.of(ALLY_KING, new Position(5,1)),
            Arguments.of(ALLY_KING, new Position(5,0)),
            Arguments.of(ALLY_KING, new Position(4,0)),
            Arguments.of(ALLY_KING, new Position(3,0)),
            Arguments.of(ALLY_KING, new Position(3,1)),

            // 초나라 왕 중앙에서 이동할 수 있는 모든 경우
            Arguments.of(ENEMY_KING, new Position(3,9)),
            Arguments.of(ENEMY_KING, new Position(4,9)),
            Arguments.of(ENEMY_KING, new Position(5,9)),
            Arguments.of(ENEMY_KING, new Position(5,8)),
            Arguments.of(ENEMY_KING, new Position(5,7)),
            Arguments.of(ENEMY_KING, new Position(4,7)),
            Arguments.of(ENEMY_KING, new Position(3,7)),
            Arguments.of(ENEMY_KING, new Position(3,8)),

            // 한나라 왕 귀통이에서 이동
            Arguments.of(new King(ALLY_SIDE,3,0), new Position(3,1)),
            Arguments.of(new King(ALLY_SIDE,3,0), new Position(4,1)),
            Arguments.of(new King(ALLY_SIDE,3,0), new Position(4,0)),
            Arguments.of(new King(ALLY_SIDE,5,2), new Position(4,2)),
            Arguments.of(new King(ALLY_SIDE,5,2), new Position(4,1)),
            Arguments.of(new King(ALLY_SIDE,5,2), new Position(5,1)),

            // 초나라 왕 귀퉁이 이동
            Arguments.of(new King(ENEMY_SIDE,3,9), new Position(3,8)),
            Arguments.of(new King(ENEMY_SIDE,3,9), new Position(4,8)),
            Arguments.of(new King(ENEMY_SIDE,3,9), new Position(4,9)),
            Arguments.of(new King(ENEMY_SIDE,5,7), new Position(5,8)),
            Arguments.of(new King(ENEMY_SIDE,5,7), new Position(4,8)),
            Arguments.of(new King(ENEMY_SIDE,5,7), new Position(4,7))
        );
    }

    public static Stream<Arguments> 왕은_궁성_밖_또는_두칸이상_이동할_수_없다_테스트_케이스() {
        return Stream.of(
            // 한나라 왕 궁성 밖 이동
            Arguments.of(new King(ALLY_SIDE, 3,1), new Position(2,1)),
            Arguments.of(new King(ALLY_SIDE, 3,2), new Position(2,2)),
            Arguments.of(new King(ALLY_SIDE, 5,0), new Position(6,0)),
            Arguments.of(new King(ALLY_SIDE, 4,1), new Position(6,1)),

            // 한나라 왕 궁성 내 두칸 이동
            Arguments.of(new King(ALLY_SIDE, 3,0), new Position(5,2)),
            Arguments.of(new King(ALLY_SIDE, 3,2), new Position(3,0)),
            Arguments.of(new King(ALLY_SIDE, 3,2), new Position(5,0)),

            // 초나라 왕 궁성 밖 이동
            Arguments.of(new King(ENEMY_SIDE, 3,9), new Position(2,9)),
            Arguments.of(new King(ENEMY_SIDE, 3,7), new Position(2,7)),
            Arguments.of(new King(ENEMY_SIDE, 5,9), new Position(6,9)),
            Arguments.of(new King(ENEMY_SIDE, 4,8), new Position(6,8)),

            // 한나라 왕 궁성 내 두칸 이동
            Arguments.of(new King(ENEMY_SIDE, 3,9), new Position(5,7)),
            Arguments.of(new King(ENEMY_SIDE, 3,7), new Position(3,9)),
            Arguments.of(new King(ENEMY_SIDE, 3,7), new Position(5,9))
        );
    }

    public static Stream<Arguments> 왕은_도착지에_아군_기물이_있는_경우_움직일_수_없다_테스트_케이스() {
        return Stream.of(
            Arguments.of(
                new King(ALLY_SIDE, 4,1), new Position(3,2), List.of(new Pawn(ALLY_SIDE, 3, 2))),
            Arguments.of(
                new King(ALLY_SIDE, 4,1), new Position(5,2), List.of(new Pawn(ALLY_SIDE, 5, 2))),
            Arguments.of(
                new King(ALLY_SIDE, 4,1), new Position(5,0), List.of(new Pawn(ALLY_SIDE, 5, 0))),
            Arguments.of(
                new King(ALLY_SIDE, 4,1), new Position(3,0), List.of(new Pawn(ALLY_SIDE, 3, 0))),

            Arguments.of(
                new King(ENEMY_SIDE, 4,8), new Position(4,9), List.of(new Pawn(ENEMY_SIDE, 4, 9))),
            Arguments.of(
                new King(ENEMY_SIDE, 4,8), new Position(5,8), List.of(new Pawn(ENEMY_SIDE, 5, 8))),
            Arguments.of(
                new King(ENEMY_SIDE, 4,8), new Position(4,7), List.of(new Pawn(ENEMY_SIDE, 4, 7))),
            Arguments.of(
                new King(ENEMY_SIDE, 4,8), new Position(3,8), List.of(new Pawn(ENEMY_SIDE, 3, 8)))
        );
    }

    public static Stream<Arguments> 왕은_도착지에_아무기물이_없거나_적군_기물이_있는_경우_움직일_수_있다_테스트_케이스() {
        return Stream.of(
            Arguments.of(
                new King(ALLY_SIDE, 4,1), new Position(3,2), List.of(new Pawn(ENEMY_SIDE, 3, 2))),
            Arguments.of(
                new King(ALLY_SIDE, 4,1), new Position(5,2), List.of(new Pawn(ENEMY_SIDE, 5, 2))),
            Arguments.of(
                new King(ALLY_SIDE, 4,1), new Position(5,0), List.of(new Pawn(ENEMY_SIDE, 5, 0))),
            Arguments.of(
                new King(ALLY_SIDE, 4,1), new Position(3,0), List.of(new Pawn(ENEMY_SIDE, 3, 0))),

            Arguments.of(
                new King(ENEMY_SIDE, 4,8), new Position(4,9), List.of(new Pawn(ALLY_SIDE, 4, 9))),
            Arguments.of(
                new King(ENEMY_SIDE, 4,8), new Position(5,8), List.of(new Pawn(ALLY_SIDE, 5, 8))),
            Arguments.of(
                new King(ENEMY_SIDE, 4,8), new Position(4,7), List.of(new Pawn(ALLY_SIDE, 4, 7))),
            Arguments.of(
                new King(ENEMY_SIDE, 4,8), new Position(3,8), List.of(new Pawn(ALLY_SIDE, 3, 8)))
        );
    }

    @ParameterizedTest
    @MethodSource("왕은_궁성_내에서_상하좌우_대각선_한칸을_이동할_수_있다_테스트_케이스")
    void 왕은_궁성_내에서_상하좌우_대각선_한칸을_이동할_수_있다(King king, Position destination) {
        assertThat(king.isMoveablePosition(destination)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("왕은_궁성_밖_또는_두칸이상_이동할_수_없다_테스트_케이스")
    void 왕은_궁성_밖_또는_두칸이상_이동할_수_없다(King king, Position destination) {
        assertThat(king.isMoveablePosition(destination)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("왕은_도착지에_아군_기물이_있는_경우_움직일_수_없다_테스트_케이스")
    void 왕은_도착지에_아군_기물이_있는_경우_움직일_수_없다(King king, Position destination, List<Piece> existingPieces) {
        assertThat(king.isMoveablePath(existingPieces, destination)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("왕은_도착지에_아무기물이_없거나_적군_기물이_있는_경우_움직일_수_있다_테스트_케이스")
    void 왕은_도착지에_아무기물이_없거나_적군_기물이_있는_경우_움직일_수_있다(King king, Position destination, List<Piece> existingPieces) {
        assertThat(king.isMoveablePath(existingPieces, destination)).isTrue();
    }
}
