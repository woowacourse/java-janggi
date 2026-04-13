package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Map;
import model.coordinate.Position;
import model.piece.Chariot;
import model.piece.Piece;
import model.testdouble.SpyBoard;
import org.junit.jupiter.api.Test;

class JanggiGameTest {

    @Test
    void 장기_게임을_시작하면_초나라부터_시작하고_이어서_게임을_진행할_수_있다() {
        // given: 왕만 배치
        SpyBoard board = SpyBoard.withBothGenerals();

        // when
        JanggiGame janggiGame = new JanggiGame(board);

        // then
        assertThat(janggiGame)
                .extracting(JanggiGame::turn, JanggiGame::canPlaying)
                .containsExactly(Team.CHO, true);
    }

    @Test
    void 장기게임_시작_후_정상적으로_기물을_옮기면_다음_차례가_된다() {
        // given: 왕만 배치
        SpyBoard board = SpyBoard.withBothGenerals();
        JanggiGame janggiGame = new JanggiGame(board);
        Team prevTurn = janggiGame.turn();

        // when: 초나라 왕만 옆으로 이동
        janggiGame.movePiece(new Position(8, 4), new Position(8, 5));

        // then
        assertThat(janggiGame)
                .extracting(JanggiGame::turn, JanggiGame::canPlaying)
                .containsExactly(prevTurn.opposite(), true);
    }

    @Test
    void 현재_턴의_팀에_맞는_기물을_선택할_수_있다() {
        // given
        Position position = new Position(1, 1);
        Piece piece = new Chariot(Team.CHO);
        SpyBoard board = SpyBoard.withBothGenerals().addPiece(position, piece);
        JanggiGame janggiGame = new JanggiGame(board);

        // when
        Piece selectedPiece = janggiGame.selectPiece(position);

        // then
        assertThat(selectedPiece).isSameAs(piece);
    }

    @Test
    void 다른_팀의_기물을_선택하면_예외가_발생한다() {
        // given: CHO 턴인데 HAN 기물 배치
        Position position = new Position(1, 1);
        Piece piece = new Chariot(Team.HAN);
        SpyBoard board = SpyBoard.withBothGenerals().addPiece(position, piece);
        JanggiGame janggiGame = new JanggiGame(board);

        // when & then
        assertThatThrownBy(() -> janggiGame.selectPiece(position))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 초나라_기물이_한나라_왕을_잡을_경우_게임은_종료되고_승자는_초나라가된다() {
        // given: 차가 왕을 잡을 수 있는 위치에 존재
        Position killPosition = new Position(6, 4);
        Position hanGeneralPosition = new Position(1, 4);
        SpyBoard board = SpyBoard.withBothGenerals().addPiece(killPosition, new Chariot(Team.CHO));
        JanggiGame janggiGame = new JanggiGame(board);

        // when: 초나라 차로 한나라 왕 잡기
        janggiGame.movePiece(killPosition, hanGeneralPosition);

        // then
        assertThat(janggiGame)
                .extracting(JanggiGame::canPlaying, JanggiGame::resolveWinner)
                .containsExactly(false, Team.CHO);
    }

    @Test
    void 한나라_기물이_초나라_왕을_잡을_경우_게임은_종료되고_승자는_한나라가된다() {
        // given: 차가 왕을 잡을 수 있는 위치에 존재 + 다음 차례를 위해 초나라 왕을 일직선 상으로 한칸 아래 이동
        Position killPosition = new Position(3, 4);
        Position choGeneralPosition = new Position(9, 4);
        SpyBoard board = SpyBoard.withBothGenerals().addPiece(killPosition, new Chariot(Team.HAN));
        JanggiGame janggiGame = new JanggiGame(board);
        janggiGame.movePiece(new Position(8, 4), choGeneralPosition);

        // when: 한나라 차로 초나라 왕 잡기
        janggiGame.movePiece(killPosition, choGeneralPosition);

        // then
        assertThat(janggiGame)
                .extracting(JanggiGame::canPlaying, JanggiGame::resolveWinner)
                .containsExactly(false, Team.HAN);
    }

    @Test
    void 게임이_종료되지_않았을_때_승자를_요청하면_예외가_발생한다() {
        // given
        SpyBoard board = SpyBoard.withBothGenerals();
        JanggiGame janggiGame = new JanggiGame(board);

        // when & then
        assertThatThrownBy(janggiGame::resolveWinner)
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 정상적으로_게임이_종료되었을_때_게임을_진행할_수_없다() {
        // given: 초나라가 승리한 상황
        Position killPosition = new Position(6, 4);
        Position hanGeneralPosition = new Position(1, 4);
        SpyBoard board = SpyBoard.withBothGenerals().addPiece(killPosition, new Chariot(Team.CHO));
        JanggiGame janggiGame = new JanggiGame(board);
        janggiGame.movePiece(killPosition, hanGeneralPosition);

        // when & then
        assertAll(
                () -> assertThatThrownBy(() -> janggiGame.selectPiece(hanGeneralPosition))
                        .isInstanceOf(IllegalStateException.class),
                () -> assertThatThrownBy(() -> janggiGame.movePiece(hanGeneralPosition, killPosition))
                        .isInstanceOf(IllegalStateException.class)
        );
    }

    @Test
    void 기물_이동_후_왕이_마주보고_있는_상황이_되면_빅장_상태가_되고_게임은_유지된다() {
        // given: 왕 사이를 막고 있는 기물이 있는 보드 제공
        Position barrierPos = new Position(6, 4);
        SpyBoard board = SpyBoard.withBothGenerals().addPiece(barrierPos, new Chariot(Team.CHO));
        JanggiGame janggiGame = new JanggiGame(board);

        // when: 왕 사이를 막고 있는 차를 이동
        janggiGame.movePiece(barrierPos, new Position(6, 0));

        // then
        assertThat(janggiGame)
                .extracting(JanggiGame::isBigJang, JanggiGame::canPlaying)
                .containsExactly(true, true);
    }

    @Test
    void 빅장_상태가_되면_게임을_빅장으로_종료_할_수_있다() {
        // given: 빅장 상태
        Position barrierPos = new Position(6, 4);
        SpyBoard board = SpyBoard.withBothGenerals().addPiece(barrierPos, new Chariot(Team.CHO));
        JanggiGame janggiGame = new JanggiGame(board);
        janggiGame.movePiece(barrierPos, new Position(6, 0));

        // when
        janggiGame.finishByBigJang();

        // then
        assertThat(janggiGame)
                .extracting(JanggiGame::isBigJangDone, JanggiGame::canPlaying, JanggiGame::isBigJang)
                .containsExactly(true, false, false);
    }

    @Test
    void 빅장_상태가_아닐_때_빅장으로_종료_할_수_없다() {
        // given: 빅장 상태가 아님
        SpyBoard board = SpyBoard.withBothGenerals();
        JanggiGame janggiGame = new JanggiGame(board);

        // when & then
        assertThatThrownBy(janggiGame::finishByBigJang)
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 빅장_상태에서_승자를_요청하면_예외가_발생한다() {
        // given
        Position barrierPos = new Position(6, 4);
        SpyBoard board = SpyBoard.withBothGenerals().addPiece(barrierPos, new Chariot(Team.CHO));
        JanggiGame janggiGame = new JanggiGame(board);
        janggiGame.movePiece(barrierPos, new Position(6, 0));

        // when & then
        assertThatThrownBy(janggiGame::resolveWinner)
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 장기에서_기물의_점수를_계산하면_후차례에게_가점을_준다() {
        // given: 양 팀 다 0점인 상황
        SpyBoard board = SpyBoard.withBothGenerals();
        JanggiGame janggiGame = new JanggiGame(board);

        // when
        Map<Team, Double> finalScore = janggiGame.calculateFinalScore();

        // then: 후 턴은 1.5점을 더한 결과를 받는다.
        double startTurnScore = finalScore.get(Team.CHO);
        double afterTurnScore = finalScore.get(Team.HAN);
        assertThat(afterTurnScore).isEqualTo(startTurnScore + 1.5);
    }

    @Test
    void 빅장으로_게임이_종료되었을_때_초나라의_점수가_더_높다면_초나라가_승리한다() {
        // given: 빅장으로 종료한 상황 + 초나라 기물 하나 남아있음.
        Position barrierPos = new Position(6, 4);
        SpyBoard board = SpyBoard.withBothGenerals().addPiece(barrierPos, new Chariot(Team.CHO));
        JanggiGame janggiGame = new JanggiGame(board);
        janggiGame.movePiece(barrierPos, new Position(6, 0));
        janggiGame.finishByBigJang();

        // when
        Team winner = janggiGame.resolveWinner();

        // then
        assertThat(winner).isEqualTo(Team.CHO);
    }

    @Test
    void 빅장으로_게임이_종료되었을_때_두나라의_점수가_동일하다면_후턴인_한나라가_승리한다() {
        // given: 두 나라 모두 장만 남고 + 빅장으로 종료한 상황
        SpyBoard board = SpyBoard.withBothGenerals();
        JanggiGame janggiGame = new JanggiGame(board);
        janggiGame.movePiece(new Position(8, 4), new Position(9, 4));   // 아래로만 이동
        janggiGame.finishByBigJang();

        // when
        Team winner = janggiGame.resolveWinner();

        // then
        assertThat(winner).isEqualTo(Team.HAN);
    }
}