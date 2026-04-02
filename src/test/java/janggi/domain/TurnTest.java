package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.piece.Jol;
import janggi.domain.piece.Piece;
import janggi.domain.team.TeamType;
import janggi.dto.BoardSpot;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TurnTest {

    @Test
    @DisplayName("초기 턴의 다음 차례는 초나라다.")
    void createInitialTurn() {
        // given
        Turn turn = Turn.createInitialTurn();

        // when
        TeamType nextTurnTeam = turn.nextTurnTeam();

        // then
        assertThat(nextTurnTeam).isEqualTo(TeamType.CHU);
    }

    @Test
    @DisplayName("현재 차례 팀의 기물 존재 여부를 확인한다.")
    void isMyTeamPieceExist() {
        // given
        Turn turn = Turn.createInitialTurn();
        Position chuJolPosition = new Position(1, 4);
        Position hanJolPosition = new Position(1, 7);

        // when & then
        assertAll(
            () -> assertThat(turn.isMyTeamPieceExist(chuJolPosition)).isTrue(),
            () -> assertThat(turn.isMyTeamPieceExist(hanJolPosition)).isFalse()
        );
    }

    @Test
    @DisplayName("현재 차례 팀의 기물을 조회한다.")
    void findPiece() {
        // given
        Turn turn = Turn.createInitialTurn();
        Position chuJolPosition = new Position(1, 4);

        // when
        Piece piece = turn.findPiece(chuJolPosition);

        // then
        assertThat(piece).isInstanceOf(Jol.class);
    }

    @Test
    @DisplayName("현재 차례 팀의 올바른 이동은 허용한다.")
    void canMove() {
        // given
        Turn turn = Turn.createInitialTurn();
        Position chuJolStartPosition = new Position(1, 4);
        Position chuJolEndPosition = new Position(1, 5);

        // when & then
        assertThatCode(() -> turn.canMove(chuJolStartPosition, chuJolEndPosition))
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
        assertThatThrownBy(() -> turn.canMove(chuJolStartPosition, chuJolEndPosition))
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
            () -> assertThat(turn.nextTurnTeam()).isEqualTo(TeamType.CHU),
            () -> assertThat(movedTurn.nextTurnTeam()).isEqualTo(TeamType.HAN),
            () -> assertThat(originSpot).isNotNull(),
            () -> assertThat(originSpot.position()).isEqualTo(new Position(1, 4)),
            () -> assertThat(originSpot.pieceName()).isEqualTo("졸"),
            () -> assertThat(movedSpot).isNotNull(),
            () -> assertThat(movedSpot.position()).isEqualTo(new Position(1, 5)),
            () -> assertThat(movedSpot.pieceName()).isEqualTo("졸")
        );
    }
}
