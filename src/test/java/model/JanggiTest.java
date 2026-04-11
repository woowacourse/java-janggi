package model;

import model.board.Board;
import model.board.ScoreResult;
import model.coordinate.PalacePositions;
import model.coordinate.Position;
import model.game.GameStatus;
import model.game.Janggi;
import model.game.Team;
import model.piece.Chariot;
import model.piece.General;
import model.piece.Piece;
import model.piece.Soldier;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JanggiTest {

    @Test
    void 한나라의_궁이_잡히면_초나라가_승리한다() {
        // given
        Map<Position, Piece> boardMap = new HashMap<>();
        boardMap.put(PalacePositions.HAN_PALACE_CENTER, new General(Team.HAN));
        boardMap.put(PalacePositions.HAN_PALACE_BOTTOM_LEFT, new Chariot(Team.CHO));
        Board board = new Board(boardMap);
        Janggi janggi = new Janggi(board);

        // when
        GameStatus gameStatus = janggi.move(PalacePositions.HAN_PALACE_BOTTOM_LEFT, PalacePositions.HAN_PALACE_CENTER);

        // then
        assertThat(gameStatus).isEqualTo(GameStatus.WIN_BY_CAPTURE);
    }

    @Test
    void 초나라의_궁이_잡히면_한나라가_승리한다() {
        // given
        Map<Position, Piece> boardMap = new HashMap<>();
        boardMap.put(PalacePositions.CHO_PALACE_CENTER, new General(Team.CHO));
        boardMap.put(new Position(0, 0), new Chariot(Team.CHO));
        boardMap.put(new Position(8, 0), new Chariot(Team.HAN));
        Board board = new Board(boardMap);
        Janggi janggi = new Janggi(board);

        // when
        janggi.move(new Position(0, 0), new Position(1, 0));
        GameStatus gameStatus = janggi.move(new Position(8, 0), PalacePositions.CHO_PALACE_CENTER);

        // then
        assertThat(gameStatus).isEqualTo(GameStatus.WIN_BY_CAPTURE);
    }

    @Test
    void 궁을_잡지_않으면_게임이_계속된다() {
        // given
        Map<Position, Piece> boardMap = new HashMap<>();
        boardMap.put(new Position(0, 0), new Chariot(Team.CHO));
        Board board = new Board(boardMap);
        Janggi janggi = new Janggi(board);

        // when
        GameStatus gameStatus = janggi.move(new Position(0, 0), new Position(1, 0));

        // then
        assertThat(gameStatus).isEqualTo(GameStatus.PLAYING);
    }

    @Test
    void 상대_팀_기물을_움직이면_예외가_발생한다() {
        // given
        Map<Position, Piece> boardMap = new HashMap<>();
        boardMap.put(new Position(0, 0), new Chariot(Team.HAN));
        Board board = new Board(boardMap);
        Janggi janggi = new Janggi(board);

        // when & then
        assertThatThrownBy(() -> janggi.move(new Position(0, 0), new Position(1, 0)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 점수_비교로_승자를_결정한다() {
        // given
        Map<Position, Piece> boardMap = new HashMap<>();
        boardMap.put(new Position(0, 0), new Chariot(Team.CHO));
        boardMap.put(new Position(1, 0), new Soldier(Team.HAN));
        Board board = new Board(boardMap);
        Janggi janggi = new Janggi(board);

        // when
        ScoreResult scoreResult = janggi.calculateScoreResultOfTeams();

        // then
        assertThat(janggi.isPlaying()).isFalse();
        assertThat(scoreResult.winner()).isEqualTo(Team.CHO);
    }

    @Test
    void 기물_점수가_동일하면_한나라가_덤으로_승리한다() {
        // given
        Map<Position, Piece> boardMap = new HashMap<>();
        boardMap.put(new Position(0, 0), new Soldier(Team.CHO));
        boardMap.put(new Position(1, 0), new Soldier(Team.HAN));
        Board board = new Board(boardMap);
        Janggi janggi = new Janggi(board);

        // when
        ScoreResult scoreResult = janggi.calculateScoreResultOfTeams();

        // then
        assertThat(scoreResult.winner()).isEqualTo(Team.HAN);
    }
}