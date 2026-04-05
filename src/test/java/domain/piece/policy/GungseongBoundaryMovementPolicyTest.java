package domain.piece.policy;

import domain.Board;
import domain.position.Position;
import domain.settingType.SettingType;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class GungseongBoundaryMovementPolicyTest {

    public static final Board BOARD = Board.of(SettingType.LEFT, SettingType.LEFT);

    @ParameterizedTest
    @MethodSource("insideOfGungseongPositions")
    void 궁성_안으로_움직이면_예외가_발생하지_않는다(Position destination) {
        Assertions.assertThatNoException()
                .isThrownBy(() -> new GungseongBoundaryMovementPolicy().validate(BOARD, null, null, destination));

    }

    private static Stream<Arguments> insideOfGungseongPositions() {
        return Stream.of(
                Arguments.of(Position.of(1, 4)),
                Arguments.of(Position.of(1, 5)),
                Arguments.of(Position.of(1, 6)),
                Arguments.of(Position.of(2, 4)),
                Arguments.of(Position.of(2, 5)),
                Arguments.of(Position.of(2, 6)),
                Arguments.of(Position.of(3, 4)),
                Arguments.of(Position.of(3, 5)),
                Arguments.of(Position.of(3, 6)),
                Arguments.of(Position.of(8, 4)),
                Arguments.of(Position.of(8, 5)),
                Arguments.of(Position.of(8, 6)),
                Arguments.of(Position.of(9, 4)),
                Arguments.of(Position.of(9, 5)),
                Arguments.of(Position.of(9, 6)),
                Arguments.of(Position.of(10, 4)),
                Arguments.of(Position.of(10, 5)),
                Arguments.of(Position.of(10, 6)));
    }

    @ParameterizedTest
    @MethodSource("outOfGungseongPositions")
    void 궁성_밖으로_움직이면_예외가_발생해야_한다(Position destination) {
        Assertions.assertThatThrownBy(
                () -> new GungseongBoundaryMovementPolicy().validate(BOARD, null, null, destination));
    }

    private static Stream<Arguments> outOfGungseongPositions() {
        return Stream.of(
                Arguments.of(Position.of(1, 3)),
                Arguments.of(Position.of(1, 1)),
                Arguments.of(Position.of(5, 5)),
                Arguments.of(Position.of(3, 7)),
                Arguments.of(Position.of(8, 3)));
    }


}