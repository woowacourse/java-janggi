package domain.piece.policy;


import domain.Board;
import domain.piece.Team;
import domain.position.Position;
import domain.settingType.SettingType;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PoMovementPolicyTest {
    @Test
    void 경로에_포가_있는_경우_예외가_발생해야_한다() {
        Board board = Board.of(SettingType.LEFT, SettingType.LEFT);
        board.move(Team.CHO, Position.of(4, 1), Position.of(4, 2));
        List<Position> path = List.of(
                Position.of(4, 2),
                Position.of(5, 2),
                Position.of(6, 2),
                Position.of(7, 2),
                Position.of(8, 2),
                Position.of(9, 2));

        //when & then
        PoMovementPolicy poMovementPolicy = new PoMovementPolicy();
        Assertions.assertThatThrownBy(() -> poMovementPolicy.validate(board, path, Position.of(3, 2), null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 경로에_기물이_1개만_있는_경우_갈_수_있어야_한다() {
        Board board = Board.of(SettingType.LEFT, SettingType.LEFT);
        board.move(Team.CHO, Position.of(4, 1), Position.of(4, 2));
        List<Position> path = List.of(
                Position.of(4, 2),
                Position.of(5, 2),
                Position.of(6, 2));

        //when & then
        PoMovementPolicy poMovementPolicy = new PoMovementPolicy();
        Assertions.assertThatNoException()
                .isThrownBy(() -> poMovementPolicy.validate(board, path, Position.of(3, 2), null));
    }

    @Test
    void 경로에_기물이_2개_이상인_경우_예외가_발생해야_한다() {
        Board board = Board.of(SettingType.LEFT, SettingType.LEFT);
        board.move(Team.CHO, Position.of(4, 1), Position.of(4, 2));
        board.move(Team.HAN, Position.of(7, 1), Position.of(7, 2));
        board.move(Team.HAN, Position.of(7, 2), Position.of(6, 2));
        List<Position> path = List.of(
                Position.of(4, 2),
                Position.of(5, 2),
                Position.of(6, 2),
                Position.of(7, 2));

        //when & then
        PoMovementPolicy poMovementPolicy = new PoMovementPolicy();
        Assertions.assertThatThrownBy(() -> poMovementPolicy.validate(board, path, Position.of(3, 2), null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 경로에_기물이_없으면_예외가_발생해야_한다() {
        Board board = Board.of(SettingType.LEFT, SettingType.LEFT);
        List<Position> path = List.of(
                Position.of(4, 2),
                Position.of(5, 2),
                Position.of(6, 2),
                Position.of(7, 2));

        //when, then
        PoMovementPolicy poMovementPolicy = new PoMovementPolicy();
        Assertions.assertThatThrownBy(() -> poMovementPolicy.validate(board, path, Position.of(3, 2), null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}