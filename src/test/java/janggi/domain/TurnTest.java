package janggi.domain;

import janggi.dto.BoardSpot;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

class TurnTest {

    @Test
    @DisplayName("초기 턴의 다음 차례는 초나라다.")
    void createInitialTurn() {
        // given
        Turn turn = Turn.createInitialTurn();

        // when
        String nextTurnTeam = turn.nextTurnTeam();

        // then
        assertThat(nextTurnTeam).isEqualTo("초나라");
    }

    @Test
    @DisplayName("현재 차례 팀의 기물 존재 여부를 확인한다.")
    void isNextTurnTeamPieceExists() {
        // given
        Turn turn = Turn.createInitialTurn();
        Position chuJolPosition = new Position(1, 4);
        Position hanJolPosition = new Position(1, 7);

        // when & then
        assertAll(
            () -> assertThat(turn.isNextTurnTeamPieceExists(chuJolPosition)).isTrue(),
            () -> assertThat(turn.isNextTurnTeamPieceExists(hanJolPosition)).isFalse()
        );
    }

    @Test
    @DisplayName("현재 차례 팀의 올바른 이동은 허용한다.")
    void validateCanMove() {
        // given
        Turn turn = Turn.createInitialTurn();
        Position chuJolStartPosition = new Position(1, 4);
        Position chuJolEndPosition = new Position(1, 5);

        // when & then
        assertThatCode(() -> turn.validateCanMove(chuJolStartPosition, chuJolEndPosition))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("현재 차례 팀의 잘못된 이동은 예외가 발생한다.")
    void throwWhenMoveIsInvalid() {
        // given
        Turn turn = Turn.createInitialTurn();
        Position chuJolStartPosition = new Position(1, 4);
        Position chuJolEndPosition = new Position(1, 6);

        // when & then
        assertThatThrownBy(() -> turn.validateCanMove(chuJolStartPosition, chuJolEndPosition))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("이동할 수 없는 위치입니다.");
    }

    @Test
    @DisplayName("기물을 이동하면 새 Turn을 반환한다.")
    void move() {
        // given
        Turn turn = Turn.createInitialTurn();

        // when
        Turn movedTurn = turn.move(new Position(1, 4), new Position(1, 5));
        BoardSpot originSpot = turn.makeBoardSnapShot().value().get(new Position(1, 4));
        BoardSpot movedSpot = movedTurn.makeBoardSnapShot().value().get(new Position(1, 5));

        // then
        assertAll(
            () -> assertThat(turn.nextTurnTeam()).isEqualTo("초나라"),
            () -> assertThat(movedTurn.nextTurnTeam()).isEqualTo("한나라"),
            () -> assertThat(originSpot).isNotNull(),
            () -> assertThat(originSpot.position()).isEqualTo(new Position(1, 4)),
            () -> assertThat(originSpot.pieceName()).isEqualTo("졸"),
            () -> assertThat(movedSpot).isNotNull(),
            () -> assertThat(movedSpot.position()).isEqualTo(new Position(1, 5)),
            () -> assertThat(movedSpot.pieceName()).isEqualTo("졸")
        );
    }
}
