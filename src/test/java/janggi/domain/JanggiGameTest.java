package janggi.domain;

import janggi.domain.board.Board;
import janggi.domain.board.BoardFactory;
import janggi.domain.piece.General;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Soldier;
import janggi.domain.position.Movement;
import janggi.domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static janggi.domain.board.PieceSetup.OUTER_ELEPHANT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class JanggiGameTest {

    @DisplayName("게임을 진행하면 현재 차례 팀이 바뀐다.")
    @Test
    void 턴_진행_후_현재_팀이_전환된다() {
        // given
        JanggiGame game = new JanggiGame(BoardFactory.create(OUTER_ELEPHANT, OUTER_ELEPHANT), Team.FIRST_TURN);
        Movement movement = new Movement(Position.from("71"), Position.from("61"));

        // when
        game.play(movement);

        // then
        assertThat(game.getCurrentTeam()).isEqualTo(Team.HAN);
    }

    @DisplayName("장이 잡히지 않은 상태에서 종료 여부를 확인하면 거짓을 반환한다.")
    @Test
    void 게임_시작_시_종료되지_않은_상태이다() {
        // given
        JanggiGame game = new JanggiGame(BoardFactory.create(OUTER_ELEPHANT, OUTER_ELEPHANT), Team.FIRST_TURN);

        // when & then
        assertThat(game.isFinished()).isFalse();
    }

    @DisplayName("장이 잡힌 상태에서 종료여부를 확인하면 참을 반환한다.")
    @Test
    void 장이_잡히면_게임이_종료된다() {
        // given
        Map<Position, Piece> base = new LinkedHashMap<>();
        base.put(Position.of(2, 5), new General(Team.HAN));
        base.put(Position.of(3, 5), new Soldier(Team.CHO));
        Board board = new Board(base);
        JanggiGame game = new JanggiGame(board, Team.FIRST_TURN);

        // when
        game.play(new Movement(Position.of(3, 5), Position.of(2, 5)));

        // then
        assertThat(game.isFinished()).isTrue();
    }

    @DisplayName("게임이 진행중이면 승자는 NONE이다.")
    @Test
    void 게임이_진행중이면_승자는_NONE이다() {
        // given
        JanggiGame game = new JanggiGame(BoardFactory.create(OUTER_ELEPHANT, OUTER_ELEPHANT), Team.FIRST_TURN);

        // when & then
        assertThat(game.getWinner()).isEqualTo(Team.NONE);
    }

    @DisplayName("게임이 종료된 상태면 승자를 확인할 수 있다.")
    @Test
    void 게임이_종료되면_승자를_반환한다() {
        // given
        Map<Position, Piece> base = new LinkedHashMap<>();
        base.put(Position.of(2, 5), new General(Team.HAN));
        base.put(Position.of(3, 5), new Soldier(Team.CHO));
        Board board = new Board(base);
        JanggiGame game = new JanggiGame(board, Team.FIRST_TURN);

        // when
        game.play(new Movement(Position.of(3, 5), Position.of(2, 5)));

        // then
        assertThat(game.getWinner()).isEqualTo(Team.CHO);
    }

    @DisplayName("게임 시작 시 점수를 확인하면 한나라 73.5점, 초나라 72.0점이다.")
    @Test
    void 점수_반환_테스트() {
        // given
        JanggiGame game = new JanggiGame(BoardFactory.create(OUTER_ELEPHANT, OUTER_ELEPHANT), Team.FIRST_TURN);

        // when
        Score score = game.getScore();

        // then
        assertAll(
                () -> assertThat(score.getHanScore()).isEqualTo(73.5),
                () -> assertThat(score.getChoScore()).isEqualTo(72.0)
        );
    }

    @DisplayName("보드 상태 반환 테스트")
    @Test
    void 보드_상태를_반환한다() {
        // given
        JanggiGame game = new JanggiGame(BoardFactory.create(OUTER_ELEPHANT, OUTER_ELEPHANT), Team.FIRST_TURN);

        // when
        Map<Position, Piece> board = game.getBoard();

        // then
        assertThat(board).isNotEmpty();
    }
}
