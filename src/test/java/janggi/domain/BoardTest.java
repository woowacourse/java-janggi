package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.board.Board;
import janggi.domain.board.BoardFactory;
import janggi.domain.piece.HorseSide;
import janggi.domain.piece.Position;
import janggi.domain.piece.Team;
import janggi.domain.piece.impl.Chariot;
import janggi.domain.piece.impl.General;
import janggi.domain.piece.impl.None;
import janggi.domain.piece.impl.Soldier;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {
    private Board board;

    @BeforeEach
    void setUp() {
        board = BoardFactory.getInitializedBoard(HorseSide.LEFT, HorseSide.LEFT,
                HorseSide.LEFT, HorseSide.LEFT);
    }

    @DisplayName("현재 기물 위치와 이동 시킬 위치를 받아 기물을 이동시킨다.")
    @Test
    void movePiece() {
        Position beforePosition = new Position(7, 1);
        Position afterPosition = new Position(6, 1);

        board.movePiece(Team.BLUE, beforePosition, afterPosition);

        assertThat(board.getBoard().get(beforePosition)).isInstanceOf(None.class);
        assertThat(board.getBoard().get(afterPosition)).isInstanceOf(Soldier.class);
    }

    @DisplayName("기물 별 이동 조건을 만족하지 않는 경우 예외를 발생시킨다.")
    @Test
    void movePieceException() {
        Position beforePosition = new Position(7, 1);
        Position afterPosition = new Position(5, 1);

        assertThatThrownBy(() -> board.movePiece(Team.RED, beforePosition, afterPosition));
    }

    @DisplayName("보드 내에 2개의 궁이 없으면 게임이 종료된다.")
    @Test
    void checkGameOver() {
        Position generalPosition = new Position(9, 5);
        Board oneGeneralBoard = new Board(Map.of(generalPosition, new General(Team.BLUE)), Set.of(generalPosition));

        assertAll(
                () -> assertThat(board.checkGameOver()).isFalse(),
                () -> assertThat(oneGeneralBoard.checkGameOver()).isTrue()
        );
    }

    @DisplayName("선후수와 남은 기물들을 바탕으로 팀별 점수를 계산한다")
    @Test
    void getScoreByTeam() {
        Chariot blueChariot = new Chariot(Team.BLUE);
        int expectedChariotScore = blueChariot.getScore();
        Board chariotBoard = new Board(Map.of(new Position(5, 5), blueChariot), Set.of());
        Turn turn = Turn.startWith(Team.BLUE);

        double blueScore = board.calculateScoreByTeam(Team.BLUE, turn);
        double redScore = board.calculateScoreByTeam(Team.RED, turn);
        double chariotScore = chariotBoard.calculateScoreByTeam(Team.BLUE, turn);

        assertAll(
                () -> assertThat(blueScore).isEqualTo(72),
                () -> assertThat(redScore).isEqualTo(73.5),
                () -> assertThat(chariotScore).isEqualTo(expectedChariotScore)
        );
    }
}
