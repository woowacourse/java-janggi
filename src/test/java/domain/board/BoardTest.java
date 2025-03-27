package domain.board;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.board.factory.BoardFactory;
import domain.player.TeamType;
import exceptions.JanggiGameRuleWarningException;
import java.util.EnumMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public final class BoardTest {

    @Nested
    @DisplayName("기물을 이동할 때")
    class TestMakeMovementsThrowException {

        @Test
        @DisplayName("해당 경로로 이동할 수 없을 경우, 예외를 던진다")
        void test_throwExceptionWhenPieceIsNotMovable() {
            // given
            final EnumMap<TeamType, Integer> setups = new EnumMap<>(TeamType.class);
            setups.put(TeamType.HAN, 1);
            setups.put(TeamType.CHO, 1);
            final Board board = BoardFactory.generateBoard(setups);
            Point startPoint = new Point(0, 0);
            Point arrivalpoint = new Point(5, 0);

            // when
            assertThatThrownBy(() -> board.canMovePiece(startPoint, arrivalpoint, TeamType.CHO))
                    .isInstanceOf(JanggiGameRuleWarningException.class)
                    .hasMessageContaining("해당 경로로 이동할 수 없습니다.");
        }

        @Test
        @DisplayName("도착점이 이동할 수 없는 위치일 경우, 예외를 던진다")
        void test_throwExceptionWhenPieceIsNotAbleToArrive() {
            // given
            final EnumMap<TeamType, Integer> setups = new EnumMap<>(TeamType.class);
            setups.put(TeamType.HAN, 1);
            setups.put(TeamType.CHO, 1);
            final Board board = BoardFactory.generateBoard(setups);

            Point startPoint = new Point(0, 0);
            Point arrivalpoint = new Point(1, 1);

            // when
            assertThatThrownBy(() -> board.canMovePiece(startPoint, arrivalpoint, TeamType.HAN))
                    .isInstanceOf(JanggiGameRuleWarningException.class)
                    .hasMessageContaining("아군 기물만 움직일 수 있습니다.");
        }

        @Test
        @DisplayName("이동할 기물이 존재하지 않을 경우, 예외를 던진다")
        void test_NoPieceOnStartPoint() {
            // given
            final EnumMap<TeamType, Integer> setups = new EnumMap<>(TeamType.class);
            setups.put(TeamType.HAN, 1);
            setups.put(TeamType.CHO, 1);
            final Board board = BoardFactory.generateBoard(setups);
            Point startPoint = new Point(1, 0);
            Point arrivalpoint = new Point(1, 1);

            // when & then
            assertThatThrownBy(() -> board.canMovePiece(startPoint, arrivalpoint, TeamType.HAN))
                    .isInstanceOf(JanggiGameRuleWarningException.class)
                    .hasMessageContaining("출발점에 이동할 기물이 없습니다.");
        }
    }

}
