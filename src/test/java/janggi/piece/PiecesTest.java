package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.setting.CampType;
import janggi.value.Position;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PiecesTest {

    private static final Position STANDARD_POSITION = new Position(4, 4);
    private static final Position MOVED_POSITION = new Position(5, 4);
    Pieces pieces = new Pieces(CampType.CHO, List.of(new Piece(PieceType.JOL, STANDARD_POSITION)));

    @DisplayName("장기말을 이동시킬 수 있다.")
    @Test
    void canMovePiece() {
        pieces.movePiece(List.of(), STANDARD_POSITION, MOVED_POSITION);

        assertThat(pieces.searchPiece(MOVED_POSITION)).isPresent();
    }

    @DisplayName("이동시킬 장기말을 찾는 좌표가 범위를 벗어난 경우 예외를 발생시킨다.")
    @ParameterizedTest
    @MethodSource()
    void canNotMovePieceBecauseWrongTargetPiecePosition(Position invalidPosition) {
        assertThatThrownBy(() -> pieces.movePiece(List.of(), invalidPosition, MOVED_POSITION))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] x좌표는 0~8, y좌표는 0~9 사이로 입력해주세요.");
    }

    static Stream<Arguments> canNotMovePieceBecauseWrongTargetPiecePosition() {
        return Stream.of(
                Arguments.of(new Position(-1, 0)),
                Arguments.of(new Position(9, 0)),
                Arguments.of(new Position(0, -1)),
                Arguments.of(new Position(0, 10))
        );
    }

    @DisplayName("이동시킬 좌표에 장기말이 존재하지 않는 경우 예외를 발생시킨다.")
    @Test
    void canNotMoveNonExistentPiece() {
        Position invalidPosition = new Position(5, 8);

        assertThatThrownBy(() -> pieces.movePiece(List.of(), invalidPosition, MOVED_POSITION))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 위치에 이동할 말이 존재하지 않습니다.");
    }

    @DisplayName("목적지 좌표가 범위를 벗어난 경우 예외를 발생시킨다.")
    @ParameterizedTest
    @MethodSource()
    void canNotMovePieceBecauseWrongDestination(Position invalidPosition) {
        assertThatThrownBy(() -> pieces.movePiece(List.of(), STANDARD_POSITION, invalidPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] x좌표는 0~8, y좌표는 0~9 사이로 입력해주세요.");
    }

    static Stream<Arguments> canNotMovePieceBecauseWrongDestination() {
        return Stream.of(
                Arguments.of(new Position(-1, 0)),
                Arguments.of(new Position(9, 0)),
                Arguments.of(new Position(0, -1)),
                Arguments.of(new Position(0, 10))
        );
    }

    @DisplayName("장기말이 목적지로 이동할 수 없는 경우 예외를 발생시킨다.")
    @Test
    void canNotMovePieceToDestination() {
        Position destination = new Position(0, 0);

        assertThatThrownBy(() -> pieces.movePiece(List.of(), STANDARD_POSITION, destination))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동이 불가능합니다.");
    }

    @DisplayName("죽은 말을 제거할 수 있다.")
    @Test
    void canRemoveDyingPiece() {
        pieces.removeDyingPiece(STANDARD_POSITION);

        Optional<Piece> dyingPiece = pieces.searchPiece(STANDARD_POSITION);
        assertThat(dyingPiece).isEmpty();
    }

    @DisplayName("상대말을 잡을 수 있다.")
    @Test
    void canKillEnemyPiece() {
        Piece enemy = new Piece(PieceType.JOL, STANDARD_POSITION);

        pieces.killEnemyPiece(List.of(enemy), STANDARD_POSITION);

        List<Piece> dyingEnemy = pieces.getDyingEnemy();
        assertThat(dyingEnemy).containsExactly(enemy);
    }

    @Test
    @DisplayName("현재 진영의 점수를 계산할 수 있다.")
    void canCalculateScore() {
        List<Piece> enemy = List.of(
                new Piece(PieceType.JOL, new Position(4, 5)),
                new Piece(PieceType.CHA, new Position(4, 6)));
        pieces.killEnemyPiece(enemy, new Position(4, 5));
        pieces.killEnemyPiece(enemy, new Position(4, 6));

        assertThat(pieces.getScore()).isEqualTo(15.0);
    }

    @Test
    @DisplayName("진영에 따라 기본점수를 부여한다.")
    void canAddDefaultScore() {
        Pieces pieces = new Pieces(CampType.HAN, List.of(new Piece(PieceType.JOL, STANDARD_POSITION)));
        List<Piece> enemy = List.of(
                new Piece(PieceType.JOL, new Position(4, 5)),
                new Piece(PieceType.CHA, new Position(4, 6)));

        pieces.killEnemyPiece(enemy, new Position(4, 5));
        pieces.killEnemyPiece(enemy, new Position(4, 6));

        assertThat(pieces.getScore()).isEqualTo(15.5);
    }
}