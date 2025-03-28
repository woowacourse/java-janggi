package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.direction.PieceMovement;
import janggi.position.Position;
import janggi.strategy.WalkingStrategy;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PlayersTest {

    @ParameterizedTest
    @MethodSource
    void 게임을_계속_진행하기_위해_두_나라의_왕이_모두_존재하면_true를_반환한다(
            final Pieces hanPieces,
            final Pieces choPieces,
            final boolean expected
    ) {
        // Given
        final Players players = new Players(Map.of(Team.HAN, hanPieces, Team.CHO, choPieces));

        // When & Then
        assertThat(players.canContinue()).isEqualTo(expected);
    }

    private static Stream<Arguments> 게임을_계속_진행하기_위해_두_나라의_왕이_모두_존재하면_true를_반환한다() {
        final Position position1 = new Position(1, 1);
        final Position position2 = new Position(1, 2);
        final Piece king1 = new Piece(new WalkingStrategy(PieceMovement.KING), position1);
        final Piece king2 = new Piece(new WalkingStrategy(PieceMovement.KING), position2);
        return Stream.of(
                Arguments.of(new Pieces(Map.of(position1, king1)),
                        new Pieces(Map.of(position2, king2)), true),
                Arguments.of(new Pieces(Map.of(position1, king1)), new Pieces(Map.of()), false)
        );
    }

    @Test
    void 승리한_팀을_반환한다() {
        // Given
        final Team team = Team.HAN;
        final Position position = new Position(1, 1);
        final Piece king = new Piece(new WalkingStrategy(PieceMovement.KING), position);
        final Pieces pieces = new Pieces(Map.of(position, king));
        final Players players = new Players(Map.of(Team.CHO, pieces, Team.HAN, new Pieces(Map.of())));

        // When & Then
        assertThat(players.findWinningTeam()).isEqualTo(team.getOppositeTeam());
    }

    // TODO : 왕이 두 팀 모두 존재하면 점수로 판단하도록 수정
    @Test
    void 왕이_두_팀_모두_존재하면_승리팀을_판별할_수_없다() {
        // Given
        final Position position1 = new Position(1, 1);
        final Position position2 = new Position(1, 2);
        final Piece king1 = new Piece(new WalkingStrategy(PieceMovement.KING), position1);
        final Pieces choPieces = new Pieces(Map.of(king1.getPosition(), king1));
        final Piece king2 = new Piece(new WalkingStrategy(PieceMovement.KING), position2);
        final Pieces hanPieces = new Pieces(Map.of(king2.getPosition(), king2));
        final Players players = new Players(Map.of(Team.CHO, choPieces, Team.HAN, hanPieces));

        // When & Then
        assertThatThrownBy(players::findWinningTeam)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("[ERROR] 왕이 하나가 아니라면 접근할 수 없습니다.");
    }

    @Test
    void 다른_팀의_기물을_이동하려_하면_예외가_발생한다() {
        // Given
        final Position currentPosition = new Position(10, 1);
        final Position arrivalPosition = new Position(9, 1);
        final Piece choSoldier = new Piece(new WalkingStrategy(PieceMovement.CHO_SOLDIER), currentPosition);
        final Team otherTeam = Team.HAN;

        final Pieces choPieces = new Pieces(Map.of(currentPosition, choSoldier));
        final Pieces hanPieces = new Pieces(Map.of());
        final Players players = new Players(Map.of(Team.CHO, choPieces, Team.HAN, hanPieces));

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
        final Piece choSoldier = new Piece(new WalkingStrategy(PieceMovement.CHO_SOLDIER), new Position(10, 1));

        final Pieces choPieces = new Pieces(Map.of(currentPosition, choSoldier));
        final Pieces hanPieces = new Pieces(Map.of());
        final Players players = new Players(Map.of(Team.CHO, choPieces, Team.HAN, hanPieces));

        // When & Then
        assertThatThrownBy(() -> players.move(currentPosition, arrivalPosition, Team.HAN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 해당 좌표에 자신의 팀 기물이 존재하지 않습니다.");
    }

    @Test
    void 두_좌표를_입력받아_다른_팀의_기물을_잡는다() {
        // Given
        final Position choPosition = new Position(10, 1);
        final Piece choSoldier = new Piece(new WalkingStrategy(PieceMovement.CHO_SOLDIER), choPosition);
        final Position hanPosition = new Position(9, 1);
        final Piece hanSoldier = new Piece(new WalkingStrategy(PieceMovement.HAN_SOLDIER), hanPosition);
        final Players players = new Players(
                Map.of(Team.CHO, new Pieces(Map.of(choPosition, choSoldier)), Team.HAN,
                        new Pieces(Map.of(hanPosition, hanSoldier))));

        // When
        players.move(choPosition, hanPosition, Team.CHO);

        // Then
        assertThat(players.getChoPieces().getPieces()).doesNotContain(hanSoldier);
    }

    @Test
    void 자신의_팀_기물은_잡을_수_없다() {
        // Given
        final Position soldierPosition = new Position(10, 1);
        final Position guardPosition = new Position(9, 1);
        final Piece soldier = new Piece(new WalkingStrategy(PieceMovement.CHO_SOLDIER), soldierPosition);
        final Piece guard = new Piece(new WalkingStrategy(PieceMovement.GUARD), guardPosition);
        final Players players = new Players(
                Map.of(Team.CHO, new Pieces(Map.of(soldierPosition, soldier, guardPosition, guard)), Team.HAN,
                        new Pieces(Map.of())));

        // When & Then
        assertThatThrownBy(() -> players.move(soldierPosition, guardPosition, Team.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 같은 팀 기물을 잡을 수 없습니다.");
    }

    @Test
    void 같은_위치로_이동을_시도하는_경우_예외가_발생한다() {
        // Given
        final Position position = new Position(10, 1);
        final Piece piece = new Piece(new WalkingStrategy(PieceMovement.CHO_SOLDIER), position);
        final Players players = new Players(Map.of(Team.CHO, new Pieces(Map.of(position, piece))));

        // When & Then
        assertThatThrownBy(() -> players.move(position, position, Team.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 같은 위치로는 이동할 수 없습니다.");
    }
}
