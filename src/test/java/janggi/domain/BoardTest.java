package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import janggi.domain.board.Board;
import janggi.domain.board.BoardFactory;
import janggi.domain.piece.HorseSide;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Pieces;
import janggi.domain.piece.Position;
import janggi.domain.piece.Team;
import janggi.domain.piece.impl.Chariot;
import janggi.domain.piece.impl.General;
import janggi.domain.piece.impl.None;
import janggi.domain.piece.impl.Soldier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {
    private final Set<Position> palacePositions = Set.of();
    private final Position redGeneralPosition = new Position(1, 4);
    private final Position blueGeneralPosition = new Position(9, 4);
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

    @DisplayName("궁성 이동에 해당하는 경우, 이동 시 궁성 이동 규칙을 요청한다.")
    @Test
    void palaceCheck() {
        Position beforePosition = new Position(1, 4);
        Position afterPosition = new Position(2, 5);
        PalaceCheckingPiece piece = new PalaceCheckingPiece("궁", Team.RED);

        Map<Position, Piece> rawBoard = new HashMap<>();
        rawBoard.put(beforePosition, piece);

        Set<Position> palacePositions = Set.of(beforePosition, afterPosition);
        Board board = new Board(rawBoard, palacePositions);
        board.movePiece(Team.RED, beforePosition, afterPosition);

        assertTrue(piece.wasPalaceRuleChecked(), "궁성 이동 규칙이 호출되어야 한다.");
    }

    @Test
    @DisplayName("양쪽 장군이 모두 살아있는 경우 승자가 없다.")
    void noWinnerWhenBothGeneralsExist() {
        Map<Position, Piece> boardMap = new HashMap<>();
        boardMap.put(redGeneralPosition, new General(Team.RED));
        boardMap.put(blueGeneralPosition, new General(Team.BLUE));
        Board board = new Board(boardMap, palacePositions);

        Team winner = board.getWinner();

        assertThat(winner).isEqualTo(Team.NONE);
    }

    @Test
    @DisplayName("RED 장군이 없으면 BLUE가 승리한다.")
    void blueWinsWhenRedGeneralIsMissing() {
        Map<Position, Piece> boardMap = new HashMap<>();
        boardMap.put(blueGeneralPosition, new General(Team.BLUE));
        Board board = new Board(boardMap, palacePositions);

        Team winner = board.getWinner();

        assertThat(winner).isEqualTo(Team.BLUE);
    }

    @Test
    @DisplayName("BLUE 장군이 없으면 RED가 승리한다.")
    void redWinsWhenBlueGeneralIsMissing() {
        Map<Position, Piece> boardMap = new HashMap<>();
        boardMap.put(redGeneralPosition, new General(Team.RED));
        Board board = new Board(boardMap, palacePositions);

        Team winner = board.getWinner();

        assertThat(winner).isEqualTo(Team.RED);
    }

    static class PalaceCheckingPiece extends Piece {
        private boolean palaceRuleChecked = false;

        public PalaceCheckingPiece(final String name, final Team team) {
            super(name, team);
        }

        @Override
        public boolean is(final Team team) {
            return true;
        }

        @Override
        public boolean isPalacePiece() {
            return true;
        }

        @Override
        public int getScore() {
            return 0;
        }

        @Override
        public Consumer<Pieces> getMovableValidator(final Position beforePosition, final Position afterPosition) {
            return null;
        }

        @Override
        public Consumer<Pieces> getPalaceMovableValidator(final Position beforePosition, final Position afterPosition) {
            return pieces -> palaceRuleChecked = true;
        }

        public boolean wasPalaceRuleChecked() {
            return palaceRuleChecked;
        }
    }
}
