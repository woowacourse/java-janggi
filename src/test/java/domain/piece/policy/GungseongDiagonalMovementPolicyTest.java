package domain.piece.policy;

import domain.Board;
import domain.position.Position;
import domain.settingType.SettingType;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class GungseongDiagonalMovementPolicyTest {

    public static final GungseongDiagonalMovementPolicy GUNGSEONG_MOVEMENT_POLICY = new GungseongDiagonalMovementPolicy();
    public static final Board BOARD = Board.of(SettingType.LEFT, SettingType.LEFT);

    @Test
    void 궁성_영역_외부인_경우_대각선으로_움직이면_예외가_발생해야_한다() {
        Position outOfGungseong = Position.of(1, 1);
        List<Position> movablePath = List.of(Position.of(2, 2));
        Assertions.assertThatThrownBy(
                        () -> GUNGSEONG_MOVEMENT_POLICY.validate(BOARD, movablePath, outOfGungseong, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 궁성_영역_외부인_경우_대각선이_아니면_예외가_발생하지_않는다() {
        Position outOfGungseong = Position.of(1, 1);
        List<Position> movablePath = List.of(Position.of(2, 1));
        Assertions.assertThatNoException()
                .isThrownBy(() -> GUNGSEONG_MOVEMENT_POLICY.validate(BOARD, movablePath, outOfGungseong, null));
    }

    @ParameterizedTest
    @MethodSource("wrongDirections")
    void 궁성_내부이면서_이동할_수_없는_방향인_경우_예외가_발생해야_한다(Position position, List<Position> path) {
        Assertions.assertThatThrownBy(() -> GUNGSEONG_MOVEMENT_POLICY.validate(BOARD, path, position, null))
                .isInstanceOf(IllegalArgumentException.class);

    }

    private static Stream<Arguments> wrongDirections() {
        return Stream.of(
                Arguments.of(Position.of(1, 4), List.of(Position.of(2, 3))),
                Arguments.of(Position.of(1, 6), List.of(Position.of(2, 7))),
                Arguments.of(Position.of(3, 4), List.of(Position.of(2, 3))),
                Arguments.of(Position.of(3, 6), List.of(Position.of(4, 7))),
                Arguments.of(Position.of(8, 4), List.of(Position.of(7, 3))),
                Arguments.of(Position.of(8, 6), List.of(Position.of(9, 7))),
                Arguments.of(Position.of(10, 4), List.of(Position.of(9, 3))),
                Arguments.of(Position.of(10, 6), List.of(Position.of(9, 7))));
    }

    @ParameterizedTest
    @MethodSource("rightDirections")
    void 궁성_내부이면서_방향이_제대로_된_경우_예외가_발생하지_않는다(Position position, List<Position> path) {
        Assertions.assertThatNoException()
                .isThrownBy(() -> GUNGSEONG_MOVEMENT_POLICY.validate(BOARD, path, position, path.getLast()));
    }


    private static Stream<Arguments> rightDirections() {
        return Stream.of(
                Arguments.of(Position.of(1, 4), List.of(Position.of(2, 5))),
                Arguments.of(Position.of(1, 6), List.of(Position.of(2, 5))),

                Arguments.of(Position.of(2, 5), List.of(Position.of(1, 4))),
                Arguments.of(Position.of(2, 5), List.of(Position.of(1, 5))),
                Arguments.of(Position.of(2, 5), List.of(Position.of(1, 6))),
                Arguments.of(Position.of(2, 5), List.of(Position.of(2, 4))),
                Arguments.of(Position.of(2, 5), List.of(Position.of(2, 6))),
                Arguments.of(Position.of(2, 5), List.of(Position.of(3, 4))),
                Arguments.of(Position.of(2, 5), List.of(Position.of(4, 5))),
                Arguments.of(Position.of(2, 5), List.of(Position.of(3, 6))),

                Arguments.of(Position.of(3, 4), List.of(Position.of(2, 5))),
                Arguments.of(Position.of(3, 6), List.of(Position.of(2, 5))),
                Arguments.of(Position.of(8, 4), List.of(Position.of(9, 5))),
                Arguments.of(Position.of(8, 6), List.of(Position.of(9, 5))),

                Arguments.of(Position.of(9, 5), List.of(Position.of(8, 4))),
                Arguments.of(Position.of(9, 5), List.of(Position.of(8, 5))),
                Arguments.of(Position.of(9, 5), List.of(Position.of(8, 6))),
                Arguments.of(Position.of(9, 5), List.of(Position.of(9, 4))),
                Arguments.of(Position.of(9, 5), List.of(Position.of(9, 6))),
                Arguments.of(Position.of(9, 5), List.of(Position.of(10, 4))),
                Arguments.of(Position.of(9, 5), List.of(Position.of(10, 5))),
                Arguments.of(Position.of(9, 5), List.of(Position.of(10, 6))),

                Arguments.of(Position.of(10, 4), List.of(Position.of(9, 5))),
                Arguments.of(Position.of(10, 6), List.of(Position.of(9, 5))));
    }


}