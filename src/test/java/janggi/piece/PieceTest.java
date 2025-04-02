package janggi.piece;

import static janggi.fixture.PositionFixture.createPosition;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.PieceType;
import janggi.Team;
import janggi.board.Board;
import janggi.board.position.Position;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PieceTest {
    @CsvSource(value = {"GREEN:false", "RED:true"}, delimiterString = ":")
    @ParameterizedTest
    void 같은_팀인지_여부를_반환한다(Team team, boolean expected) {
        // given
        Piece soldier = PieceCreator.create(Team.RED, PieceType.SOLDIER);

        // when
        boolean result = soldier.isSameTeam(team);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @CsvSource(value = {"GREEN:true", "RED:false"}, delimiterString = ":")
    @ParameterizedTest
    void 다른_팀인지_여부를_반환한다(Team team, boolean expected) {
        // given
        Piece soldier = PieceCreator.create(Team.RED, PieceType.SOLDIER);

        // when
        boolean result = soldier.isDifferentTeam(team);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("궁성_내에서_선이_없는_대각으로_이동하면_예외를_발생한다")
    @CsvSource(value = {"RED:4:9:3:8", "RED:4:9:5:8", "RED:3:8:4:9", "RED:3:8:4:7", "RED:4:7:3:8", "RED:4:7:5:8",
            "RED:5:8:4:7", "RED:5:8:4:9", "GREEN:4:0:3:1", "GREEN:4:0:5:1", "GREEN:3:1:4:0", "GREEN:3:1:4:2",
            "GREEN:4:2:3:1", "GREEN:4:2:5:1", "GREEN:5:1:4:2", "GREEN:5:1:4:0"},
            delimiterString = ":")
    @ParameterizedTest
    void should_ThrowException_WhenInvalidDiagonalPath(
            Team team,
            int startColumn,
            int startRow,
            int goalColumn,
            int goalRow
    ) {
        // given
        Map<Position, Piece> initialBoard = new HashMap<>();
        Position start = createPosition(startColumn, startRow);
        Position goal = createPosition(goalColumn, goalRow);
        Piece piece = PieceCreator.create(team, PieceType.GUARD);

        initialBoard.put(start, piece);
        Board board = new Board(initialBoard);

        // then
        assertThatThrownBy(() -> piece.validateMovable(board, start, goal))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 선이 존재하는 경우에만 대각으로 이동할 수 있습니다.");
    }
}
