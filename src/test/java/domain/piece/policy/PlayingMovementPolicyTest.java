package domain.piece.policy;


import domain.Board;
import domain.position.Position;
import domain.settingType.SettingType;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PlayingMovementPolicyTest {
    private static final Position START_POSITION = Position.of(1, 1);

    @Test
    void 경로에_기물이_없으면_예외가_발생하지_않는다() {
        //given
        Board board = Board.of(SettingType.LEFT, SettingType.LEFT);
        List<Position> path = List.of(Position.of(4, 2));
        //when & then
        Assertions.assertThatNoException()
                .isThrownBy(() -> new NormalMovementPolicy().validate(board, path, START_POSITION, null));
    }


    @Test
    void 경로에_기물이_있으면_예외가_발생해야_한다() {
        //given
        Board board = Board.of(SettingType.LEFT, SettingType.LEFT);
        List<Position> path = List.of(
                Position.of(2, 1),
                Position.of(3, 1),
                Position.of(4, 1),
                Position.of(5, 1)
        );

        //when & then
        Assertions.assertThatThrownBy(() -> new NormalMovementPolicy().validate(board, path, START_POSITION, null));
    }
}