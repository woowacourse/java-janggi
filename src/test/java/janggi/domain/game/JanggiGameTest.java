package janggi.domain.game;

import janggi.domain.board.Board;
import janggi.domain.board.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceFactory;
import janggi.domain.piece.Score;
import janggi.domain.piece.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class JanggiGameTest {

    @Test
    @DisplayName("궁이 잡히면 게임이 종료된다.")
    void testGameOverWhenGeneralCaptured() {
        // given
        Map<Position, Piece> pieces = new LinkedHashMap<>();
        pieces.put(new Position(1, 1), PieceFactory.createChariot(Team.HAN));
        pieces.put(new Position(1, 5), PieceFactory.createGeneral(Team.CHO));
        pieces.put(new Position(5, 2), PieceFactory.createGeneral(Team.HAN));
        JanggiGame game = new JanggiGame(new Board(pieces), Team.HAN);

        // when
        game.move(new Position(1, 1), new Position(1, 5));

        // then
        assertThat(game.isOver()).isTrue();
    }

    @Test
    @DisplayName("양쪽 궁이 모두 살아있으면 게임이 종료되지 않는다.")
    void testGameNotOverWhenBothGeneralsAlive() {
        // given
        Map<Position, Piece> pieces = new LinkedHashMap<>();
        pieces.put(new Position(5, 2), PieceFactory.createGeneral(Team.HAN));
        pieces.put(new Position(5, 9), PieceFactory.createGeneral(Team.CHO));
        JanggiGame game = new JanggiGame(new Board(pieces), Team.HAN);

        // when & then
        assertThat(game.isOver()).isFalse();
    }

    @Test
    @DisplayName("게임 종료 후 남은 기물의 점수를 계산한다.")
    void testCalculateScoreAfterGameOver() {
        // given
        Map<Position, Piece> pieces = new LinkedHashMap<>();
        pieces.put(new Position(1, 1), PieceFactory.createChariot(Team.HAN));
        pieces.put(new Position(2, 3), PieceFactory.createCannon(Team.HAN));
        pieces.put(new Position(5, 2), PieceFactory.createGeneral(Team.HAN));
        pieces.put(new Position(1, 5), PieceFactory.createGeneral(Team.CHO));
        JanggiGame game = new JanggiGame(new Board(pieces), Team.HAN);

        // when
        game.move(new Position(1, 1), new Position(1, 5));

        // then
        assertThat(game.calculateScore(Team.HAN)).isEqualTo(new Score(20));
        assertThat(game.calculateScore(Team.CHO)).isEqualTo(new Score(0));
    }

    @Test
    @DisplayName("한 턴이 끝나면 상대 진영 턴으로 변경된다.")
    void testTurnChangesAfterMove() {
        // given
        Map<Position, Piece> pieces = new LinkedHashMap<>();
        pieces.put(new Position(1, 1), PieceFactory.createChariot(Team.HAN));
        pieces.put(new Position(5, 9), PieceFactory.createGeneral(Team.CHO));
        JanggiGame game = new JanggiGame(new Board(pieces), Team.HAN);

        // when
        game.move(new Position(1, 1), new Position(1, 5));

        // then
        assertThat(game.getTurn().isCurrentTeam(Team.CHO)).isTrue();
    }
}
