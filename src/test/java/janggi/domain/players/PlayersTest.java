package janggi.domain.players;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import janggi.domain.piece.position.Position;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class PlayersTest {

    @ParameterizedTest
    @MethodSource
    void 게임을_계속_진행하기_위해_두_나라의_왕이_모두_존재하면_true를_반환한다(
            final Board hanBoard,
            final Board choBoard,
            final boolean expected
    ) {
        // Given
        final Players players = new Players(Map.of(Team.HAN, hanBoard, Team.CHO, choBoard));

        // When & Then
        assertThat(players.canContinue()).isEqualTo(expected);
    }

    private static Stream<Arguments> 게임을_계속_진행하기_위해_두_나라의_왕이_모두_존재하면_true를_반환한다() {
        final Position position1 = new Position(1, 1);
        final Position position2 = new Position(1, 2);
        final Piece king = Piece.KING;

        return Stream.of(
                Arguments.of(new Board(Map.of(position1, king)),
                        new Board(Map.of(position2, king)), true),
                Arguments.of(new Board(Map.of(position1, king)), new Board(Map.of()), false)
        );
    }

    @Test
    void 승리한_팀을_반환한다() {
        // Given
        final Team team = Team.HAN;
        final Position position = new Position(1, 1);
        final Board board = new Board(Map.of(position, Piece.KING));
        final Players players = new Players(Map.of(Team.CHO, board, Team.HAN, new Board(Map.of())));

        // When & Then
        assertThat(players.findWinningTeam()).isEqualTo(team.getOppositeTeam());
    }

    @Test
    void 왕이_두_팀_모두_존재하면_점수로_판단한다() {
        // Given
        final Position position1 = new Position(1, 1);
        final Position position2 = new Position(1, 2);
        final Position position3 = new Position(1, 3);
        final Board choBoard = new Board(Map.of(position1, Piece.KING, position3, Piece.CHO_SOLDIER));
        final Board hanBoard = new Board(Map.of(position2, Piece.KING));
        final Players players = new Players(Map.of(Team.CHO, choBoard, Team.HAN, hanBoard));

        // When & Then
        assertThat(players.findWinningTeam()).isEqualTo(Team.CHO);
    }

    @Test
    void 다른_팀의_기물을_이동하려_하면_예외가_발생한다() {
        // Given
        final Position currentPosition = new Position(10, 1);
        final Position arrivalPosition = new Position(9, 1);
        final Team otherTeam = Team.HAN;

        final Board choBoard = new Board(Map.of(currentPosition, Piece.CHO_SOLDIER));
        final Board hanBoard = new Board(Map.of());
        final Players players = new Players(Map.of(Team.CHO, choBoard, Team.HAN, hanBoard));

        // When & Then
        assertThatThrownBy(
                () -> players.move(currentPosition, arrivalPosition, otherTeam))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 해당 좌표에 자신의 팀 기물이 존재하지 않습니다.");
    }

    @Test
    void 해당_위치에_자신의_팀_기물이_없을_경우_예외가_발생한다() {
        // Given
        final Position currentPosition = new Position(8, 1);
        final Position arrivalPosition = new Position(9, 1);
        final Position anotherPosition = new Position(10, 1);

        final Board choBoard = new Board(Map.of(currentPosition, Piece.CHO_SOLDIER));
        final Board hanBoard = new Board(Map.of());
        final Players players = new Players(Map.of(Team.CHO, choBoard, Team.HAN, hanBoard));

        // When & Then
        assertThatThrownBy(() -> players.move(anotherPosition, arrivalPosition, Team.HAN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 해당 좌표에 자신의 팀 기물이 존재하지 않습니다.");
    }

    @Test
    void 두_좌표를_입력받아_다른_팀의_기물을_잡는다() {
        // Given
        final Position choPosition = new Position(10, 1);
        final Position hanPosition = new Position(9, 1);
        final Players players = new Players(
                Map.of(
                        Team.CHO, new Board(Map.of(choPosition, Piece.CHO_SOLDIER)),
                        Team.HAN, new Board(Map.of(hanPosition, Piece.HAN_SOLDIER))));

        // When
        players.move(choPosition, hanPosition, Team.CHO);

        // Then
        assertThat(players.getHanPieces().getBoard().get(hanPosition)).isNull();
    }

    @Test
    void 자신의_팀_기물은_잡을_수_없다() {
        // Given
        final Position soldierPosition = new Position(10, 1);
        final Position guardPosition = new Position(9, 1);
        final Players players = new Players(
                Map.of(Team.CHO, new Board(Map.of(soldierPosition, Piece.CHO_SOLDIER, guardPosition, Piece.GUARD)),
                        Team.HAN, new Board(Map.of())));

        // When & Then
        assertThatThrownBy(() -> players.move(soldierPosition, guardPosition, Team.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 같은 팀 기물을 잡을 수 없습니다.");
    }

    @Test
    void 같은_위치로_이동을_시도하는_경우_예외가_발생한다() {
        // Given
        final Position position = new Position(10, 1);
        final Players players = new Players(Map.of(Team.CHO, new Board(Map.of(position, Piece.CHO_SOLDIER))));

        // When & Then
        assertThatThrownBy(() -> players.move(position, position, Team.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 같은 위치로는 이동할 수 없습니다.");
    }

    @ParameterizedTest
    @CsvSource({
            "CHO, 15.0",
            "HAN, 16.5",
    })
    void 팀별_점수를_계산한다(final Team team, final double score) {
        // Given
        final Position soldierPosition = new Position(10, 1);
        final Position chariotPosition = new Position(8, 1);
        final Players players = new Players(
                Map.of(team, new Board(Map.of(soldierPosition, Piece.CHO_SOLDIER, chariotPosition, Piece.CHARIOT))));

        // When
        final Map<Team, Double> scores = players.calculateScore();

        // Then
        assertThat(scores.get(team)).isEqualTo(score);
    }
}
