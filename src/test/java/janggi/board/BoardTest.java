package janggi.board;

import janggi.piece.Byeong;
import janggi.piece.Cannon;
import janggi.piece.Chariot;
import janggi.piece.Jol;
import janggi.piece.King;
import janggi.piece.Piece;
import janggi.piece.Team;
import janggi.position.Position;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BoardTest {

    @ParameterizedTest
    @MethodSource
    void 게임을_계속_진행하기_위해_두_나라의_왕이_모두_존재하면_true를_반환한다(final Map<Position, Piece> pieces, final boolean expected) {
        // Given
        Board board = new Board(pieces);

        // When & Then
        assertThat(board.canContinue()).isEqualTo(expected);
    }

    private static Stream<Arguments> 게임을_계속_진행하기_위해_두_나라의_왕이_모두_존재하면_true를_반환한다() {
        King king1 = new King(Team.HAN);
        King king2 = new King(Team.CHO);
        Position position1 = new Position(1, 1);
        Position position2 = new Position(1, 2);
        return Stream.of(
                Arguments.of(
                        Map.of(
                                position1, king1,
                                position2, king2
                        ), true),
                Arguments.of(
                        Map.of(
                                position1, king1
                        ), false)
        );
    }

    @Test
    void 승리한_팀을_반환한다() {
        // Given
        Team team = Team.HAN;
        King king = new King(team);
        Position position = new Position(1, 1);
        Board board = new Board(Map.of(position, king));

        // When & Then
        assertThat(board.findWinningTeam()).isEqualTo(team);
    }

    @Test
    void 왕이_두_팀_모두_존재하면_승리팀을_판별할_수_없다() {
        // Given
        Team team = Team.HAN;
        King king1 = new King(team);
        King king2 = new King(team);
        Position position1 = new Position(1, 1);
        Position position2 = new Position(1, 2);
        Board board = new Board(Map.of(
                position1, king1,
                position2, king2
        ));

        // When & Then
        assertThatThrownBy(board::findWinningTeam)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("[ERROR] 왕이 하나가 아니라면 접근할 수 없습니다.");
    }

    @Test
    void 두_좌표를_입력_받아_기물을_이동한다() {
        // Given
        final Jol jol = new Jol();
        final Byeong byeong = new Byeong();
        final Position position1 = new Position(10, 1);
        final Position position2 = new Position(1, 1);
        int currentPositionValue = 101;
        int arrivalPositionValue = 91;

        Board board = new Board(Map.of(
                position1, jol,
                position2, byeong
        ));

        // When
        board.move(List.of(currentPositionValue, arrivalPositionValue), jol.getTeam());

        // Then
        assertThat(board.getPieces().get(Position.from(arrivalPositionValue)))
                .isEqualTo(jol);
    }

    @Test
    void 다른_팀의_기물을_이동하려_하면_예외가_발생한다() {
        // Given
        final Jol jol = new Jol();
        final Byeong byeong = new Byeong();
        final Position position1 = new Position(10, 1);
        final Position position2 = new Position(1, 1);
        int currentPositionValue = 101;
        int arrivalPositionValue = 91;

        Board board = new Board(Map.of(
                position1, jol,
                position2, byeong
        ));

        // When & Then
        assertThatThrownBy(() -> board.move(List.of(currentPositionValue, arrivalPositionValue), byeong.getTeam()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 자신의 팀 기물만 움직일 수 있습니다.");
    }

    @Test
    void 해당_위치에_기물이_없을_경우_예외가_발생한다() {
        // Given
        final Jol jol = new Jol();
        final Byeong byeong = new Byeong();
        final Position position1 = new Position(10, 1);
        final Position position2 = new Position(1, 1);
        int currentPositionValue = 102;
        int arrivalPositionValue = 91;

        Board board = new Board(Map.of(
                position1, jol,
                position2, byeong
        ));

        // When & Then
        assertThatThrownBy(() -> board.move(List.of(currentPositionValue, arrivalPositionValue), jol.getTeam()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 해당 좌표에 기물이 존재하지 않습니다.");
    }

    @Test
    void 같은_위치로_이동을_시도하는_경우_예외가_발생한다() {
        // Given
        final Jol jol = new Jol();
        final Byeong byeong = new Byeong();
        final Position position1 = new Position(10, 1);
        final Position position2 = new Position(1, 1);
        int currentPositionValue = 101;
        int arrivalPositionValue = 101;

        Board board = new Board(Map.of(position1, jol, position2, byeong));

        // When & Then
        assertThatThrownBy(() -> board.move(List.of(currentPositionValue, arrivalPositionValue), jol.getTeam()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 같은 위치로는 이동할 수 없습니다.");
    }

    @Test
    void 두_좌표를_입력받아_다른_팀의_기물을_잡는다() {
        // Given
        final Jol jol = new Jol();
        final Byeong byeong = new Byeong();
        final Position position1 = new Position(10, 1);
        final Position position2 = new Position(9, 1);
        int currentPositionValue = 101;
        int arrivalPositionValue = 91;

        Board board = new Board(Map.of(
                position1, jol,
                position2, byeong
        ));

        // When
        board.move(List.of(currentPositionValue, arrivalPositionValue), jol.getTeam());

        // Then
        assertThat(board.getPieces().get(Position.from(arrivalPositionValue)))
                .isEqualTo(jol);
    }

    @Test
    void 두_좌표를_입력받아_포를_이동한다() {
        // Given
        final Jol jol = new Jol();
        final Byeong byeong = new Byeong();
        final Cannon cannon = new Cannon(jol.getTeam());
        final Position position1 = new Position(7, 1);
        final Position position2 = new Position(5, 1);
        final Position position3 = new Position(10, 1);
        int currentPositionValue = 101;
        int arrivalPositionValue = 61;

        Board board = new Board(Map.of(
                position1, jol,
                position2, byeong,
                position3, cannon
        ));

        // When
        board.move(List.of(currentPositionValue, arrivalPositionValue), cannon.getTeam());

        // Then
        assertThat(board.getPieces().get(Position.from(arrivalPositionValue)))
                .isEqualTo(cannon);
    }

    @Test
    void 두_좌표를_입력받아_포가_상대팀_기물을_잡는다() {
        // Given
        final Jol jol = new Jol();
        final Byeong byeong = new Byeong();
        final Cannon cannon = new Cannon(jol.getTeam());
        final Position position1 = new Position(7, 1);
        final Position position2 = new Position(5, 1);
        final Position position3 = new Position(10, 1);
        int currentPositionValue = 101;
        int arrivalPositionValue = 51;

        Board board = new Board(Map.of(
                position1, jol,
                position2, byeong,
                position3, cannon
        ));

        // When
        board.move(List.of(currentPositionValue, arrivalPositionValue), cannon.getTeam());

        // Then
        assertThat(board.getPieces()).isEqualTo(Map.of(
                position1, jol,
                position2, cannon
        ));
    }

    @Test
    void 포가_포를_잡으려고_하면_예외가_발생한다() {
        // Given
        final Jol jol = new Jol();
        final Cannon targetCannon = new Cannon(Team.HAN);
        final Cannon cannon = new Cannon(jol.getTeam());
        final Position position1 = new Position(7, 1);
        final Position position2 = new Position(5, 1);
        final Position position3 = new Position(10, 1);
        int currentPositionValue = 101;
        int arrivalPositionValue = 51;

        Board board = new Board(Map.of(
                position1, jol,
                position2, targetCannon,
                position3, cannon
        ));

        // When & Then
        assertThatThrownBy(() -> board.move(List.of(currentPositionValue, arrivalPositionValue), cannon.getTeam()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 포는 포끼리 뛰어넘거나 잡을 수 없습니다.");
    }

    @Test
    void 포는_중간에_기물이_없으면_이동하지_못한다() {
        // Given
        final Jol jol = new Jol();
        final Cannon cannon = new Cannon(jol.getTeam());
        final Position position1 = new Position(7, 1);
        final Position position3 = new Position(10, 1);
        int currentPositionValue = 101;
        int arrivalPositionValue = 81;

        Board board = new Board(Map.of(
                position1, jol,
                position3, cannon
        ));

        // When & Then
        assertThatThrownBy(() -> board.move(List.of(currentPositionValue, arrivalPositionValue), cannon.getTeam()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 오직 하나의 기물만 뛰어넘을 수 있습니다.");
    }

    @Test
    void 포를_제외한_기물은_중간에_기물이_있으면_이동하지_못한다() {
        // Given
        final Jol jol = new Jol();
        final Chariot cannon = new Chariot(jol.getTeam());
        final Position position1 = new Position(7, 1);
        final Position position3 = new Position(10, 1);
        int currentPositionValue = 101;
        int arrivalPositionValue = 61;

        Board board = new Board(Map.of(
                position1, jol,
                position3, cannon
        ));

        // When & Then
        assertThatThrownBy(() -> board.move(List.of(currentPositionValue, arrivalPositionValue), cannon.getTeam()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 경로에 기물이 존재하여 이동할 수 없습니다.");
    }

    @Test
    void 자신의_팀_기물은_잡을_수_없다() {
        // Given
        final Jol jol = new Jol();
        final Chariot chariot = new Chariot(jol.getTeam());
        final Position position1 = new Position(10, 1);
        final Position position2 = new Position(9, 1);
        int currentPositionValue = 101;
        int arrivalPositionValue = 91;

        Board board = new Board(Map.of(
                position1, jol,
                position2, chariot
        ));

        // When & Then
        assertThatThrownBy(() -> board.move(List.of(currentPositionValue, arrivalPositionValue), jol.getTeam()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 자신의 팀 기물은 잡을 수 없습니다.");
    }
}
