package janggi.board;

import janggi.Team.Team;
import janggi.piece.Byeong;
import janggi.piece.Cha;
import janggi.piece.Gung;
import janggi.piece.Jol;
import janggi.piece.Piece;
import janggi.piece.Po;
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
    void 게임을_계속_진행하기_위해_두_나라의_궁이_모두_존재하면_true를_반환한다(final Map<Position, Piece> pieces, final boolean expected) {
        // Given
        Board board = new Board(pieces);

        // When & Then
        assertThat(board.hasEachKing()).isEqualTo(expected);
    }

    private static Stream<Arguments> 게임을_계속_진행하기_위해_두_나라의_궁이_모두_존재하면_true를_반환한다() {
        Gung gung1 = new Gung(Team.HAN);
        Gung gung2 = new Gung(Team.CHO);
        Position currentPosition = new Position(1, 1);
        Position position = new Position(1, 2);
        return Stream.of(
                Arguments.of(
                        Map.of(
                                currentPosition, gung1,
                                position, gung2
                        ), true),
                Arguments.of(
                        Map.of(
                                currentPosition, gung1
                        ), false)
        );
    }

    @Test
    void 승리한_팀을_반환한다() {
        // Given
        Team team = Team.HAN;
        Gung gung = new Gung(team);
        Position position = new Position(1, 1);
        Board board = new Board(Map.of(position, gung));

        // When & Then
        assertThat(board.findWinningTeam()).isEqualTo(team);
    }

    @Test
    void 궁이_두_팀_모두_존재하면_승리팀을_판별할_수_없다() {
        // Given
        Team team = Team.HAN;
        Gung gung1 = new Gung(team);
        Gung gung2 = new Gung(team);
        Position position1 = new Position(1, 1);
        Position position2 = new Position(1, 2);
        Board board = new Board(Map.of(
                position1, gung1,
                position2, gung2
        ));

        // When & Then
        assertThatThrownBy(board::findWinningTeam)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("[ERROR] 궁이 하나가 아니라면 접근할 수 없습니다.");
    }

    @Test
    void 두_좌표를_입력_받아_기물을_이동한다() {
        // Given
        final Jol jol = new Jol();
        final Byeong byeong = new Byeong();
        final Position currentPosition = new Position(10, 1);
        final Position position = new Position(1, 1);
        final Position arrivalPosition = new Position(9, 1);

        Board board = new Board(Map.of(
                currentPosition, jol,
                position, byeong
        ));

        // When
        board.move(List.of(currentPosition, arrivalPosition), jol.getTeam());

        // Then
        assertThat(board.getPieces().get(arrivalPosition))
                .isEqualTo(jol);
    }

    @Test
    void 다른_팀의_기물을_이동하려_하면_예외가_발생한다() {
        // Given
        final Jol jol = new Jol();
        final Byeong byeong = new Byeong();
        final Position currentPosition = new Position(10, 1);
        final Position position = new Position(1, 1);
        final Position arrivalPosition = new Position(9, 1);

        Board board = new Board(Map.of(
                currentPosition, jol,
                position, byeong
        ));

        // When & Then
        assertThatThrownBy(() -> board.move(List.of(currentPosition, arrivalPosition), byeong.getTeam()))
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
        final Position currentPosition = new Position(10, 2);
        final Position arrivalPosition = new Position(9, 1);

        Board board = new Board(Map.of(
                position1, jol,
                position2, byeong
        ));

        // When & Then
        assertThatThrownBy(() -> board.move(List.of(currentPosition, arrivalPosition), jol.getTeam()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 해당 좌표에 기물이 존재하지 않습니다.");
    }

    @Test
    void 같은_위치로_이동을_시도하는_경우_예외가_발생한다() {
        // Given
        final Jol jol = new Jol();
        final Byeong byeong = new Byeong();
        final Position currentPosition = new Position(10, 1);
        final Position position = new Position(1, 1);
        final Position arrivalPosition = new Position(10, 1);

        Board board = new Board(Map.of(currentPosition, jol, position, byeong));

        // When & Then
        assertThatThrownBy(() -> board.move(List.of(currentPosition, arrivalPosition), jol.getTeam()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 같은 위치로는 이동할 수 없습니다.");
    }

    @Test
    void 두_좌표를_입력받아_다른_팀의_기물을_잡는다() {
        // Given
        final Jol jol = new Jol();
        final Byeong byeong = new Byeong();
        final Position currentPosition = new Position(10, 1);
        final Position arrivalPosition = new Position(9, 1);

        Board board = new Board(Map.of(
                currentPosition, jol,
                arrivalPosition, byeong
        ));

        // When
        board.move(List.of(currentPosition, arrivalPosition), jol.getTeam());

        // Then
        assertThat(board.getPieces().get(arrivalPosition))
                .isEqualTo(jol);
    }

    @Test
    void 두_좌표를_입력받아_포를_이동한다() {
        // Given
        final Jol jol = new Jol();
        final Byeong byeong = new Byeong();
        final Po po = new Po(jol.getTeam());
        final Position position1 = new Position(7, 1);
        final Position position2 = new Position(5, 1);
        final Position currentPosition = new Position(10, 1);
        final Position arrivalPosition = new Position(6, 1);

        Board board = new Board(Map.of(
                position1, jol,
                position2, byeong,
                currentPosition, po
        ));

        // When
        board.move(List.of(currentPosition, arrivalPosition), po.getTeam());

        // Then
        assertThat(board.getPieces().get(arrivalPosition))
                .isEqualTo(po);
    }

    @Test
    void 두_좌표를_입력받아_포가_상대팀_기물을_잡는다() {
        // Given
        final Jol jol = new Jol();
        final Byeong byeong = new Byeong();
        final Po po = new Po(jol.getTeam());
        final Position position = new Position(7, 1);
        final Position arrivalPosition = new Position(5, 1);
        final Position currentPosition = new Position(10, 1);

        Board board = new Board(Map.of(
                position, jol,
                arrivalPosition, byeong,
                currentPosition, po
        ));

        // When
        board.move(List.of(currentPosition, arrivalPosition), po.getTeam());

        // Then
        assertThat(board.getPieces()).isEqualTo(Map.of(
                position, jol,
                arrivalPosition, po
        ));
    }

    @Test
    void 포가_포를_잡으려고_하면_예외가_발생한다() {
        // Given
        final Jol jol = new Jol();
        final Po targetPo = new Po(Team.HAN);
        final Po po = new Po(jol.getTeam());
        final Position position = new Position(7, 1);
        final Position arrivalPosition = new Position(5, 1);
        final Position currentPosition = new Position(10, 1);

        Board board = new Board(Map.of(
                position, jol,
                arrivalPosition, targetPo,
                currentPosition, po
        ));

        // When & Then
        assertThatThrownBy(() -> board.move(List.of(currentPosition, arrivalPosition), po.getTeam()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 포는 포끼리 뛰어넘거나 잡을 수 없습니다.");
    }

    @Test
    void 포는_중간에_기물이_없으면_이동하지_못한다() {
        // Given
        final Jol jol = new Jol();
        final Po po = new Po(jol.getTeam());
        final Position position = new Position(7, 1);
        final Position currentPosition = new Position(10, 1);
        final Position arrivalPosition = new Position(8, 1);

        Board board = new Board(Map.of(
                position, jol,
                currentPosition, po
        ));

        // When & Then
        assertThatThrownBy(() -> board.move(List.of(currentPosition, arrivalPosition), po.getTeam()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 오직 하나의 기물만 뛰어넘을 수 있습니다.");
    }

    @Test
    void 포를_제외한_기물은_중간에_기물이_있으면_이동하지_못한다() {
        // Given
        final Jol jol = new Jol();
        final Cha cannon = new Cha(jol.getTeam());
        final Position position = new Position(7, 1);
        final Position currentPosition = new Position(10, 1);
        final Position arrivalPosition = new Position(6, 1);

        Board board = new Board(Map.of(
                position, jol,
                currentPosition, cannon
        ));

        // When & Then
        assertThatThrownBy(() -> board.move(List.of(currentPosition, arrivalPosition), cannon.getTeam()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 경로에 기물이 존재하여 이동할 수 없습니다.");
    }

    @Test
    void 자신의_팀_기물은_잡을_수_없다() {
        // Given
        final Jol jol = new Jol();
        final Cha cha = new Cha(jol.getTeam());
        final Position currentPosition = new Position(10, 1);
        final Position arrivalPosition = new Position(9, 1);

        Board board = new Board(Map.of(
                currentPosition, jol,
                arrivalPosition, cha
        ));

        // When & Then
        assertThatThrownBy(() -> board.move(List.of(currentPosition, arrivalPosition), jol.getTeam()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 자신의 팀 기물은 잡을 수 없습니다.");
    }
}
