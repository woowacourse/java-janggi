package domain.board;

import domain.movestrategy.MoveStrategyType;
import domain.piece.Piece;
import domain.piece.PieceStatus;
import domain.piece.PieceType;
import domain.player.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class MoveResultTest {

    @Test
    @DisplayName("기물을 잡지 않으면 0점을 얻는다.")
    void without_capture_then_zero_score() {
        //given
        final MoveResult moveResult = MoveResult.withoutCapture();

        //then
        assertThat(moveResult.capturedScore()).isZero();
    }

    @ParameterizedTest
    @MethodSource("기물_타입_및_점수")
    @DisplayName("기물을 잡으면 해당 기물 타입의 점수를 얻는다.")
    void get_score_with_capture_piece(final PieceStatus pieceStatus, final int expectedScore) {
        //given
        final Piece capturedPiece = Piece.of(pieceStatus, Team.HAN);

        //when
        final MoveResult moveResult = MoveResult.withCapture(capturedPiece);

        //then
        assertThat(moveResult.capturedScore()).isEqualTo(expectedScore);
    }

    private static Stream<Arguments> 기물_타입_및_점수() {
        return Stream.of(
                Arguments.of(new PieceStatus(PieceType.GENERAL, MoveStrategyType.GENERAL), PieceType.GENERAL.getScore()),
                Arguments.of(new PieceStatus(PieceType.GUARD, MoveStrategyType.GUARD), PieceType.GUARD.getScore()),
                Arguments.of(new PieceStatus(PieceType.HORSE, MoveStrategyType.HORSE), PieceType.HORSE.getScore()),
                Arguments.of(new PieceStatus(PieceType.ELEPHANT, MoveStrategyType.ELEPHANT), PieceType.ELEPHANT.getScore()),
                Arguments.of(new PieceStatus(PieceType.CHARIOT, MoveStrategyType.CHARIOT), PieceType.CHARIOT.getScore()),
                Arguments.of(new PieceStatus(PieceType.CANNON, MoveStrategyType.CANNON), PieceType.CANNON.getScore()),
                Arguments.of(new PieceStatus(PieceType.SOLDIER, MoveStrategyType.SOLDIER), PieceType.SOLDIER.getScore())
        );
    }

    @Test
    @DisplayName("왕을 잡았는지 알 수 있다.")
    void know_that_capture_general() {
        //given
        final Piece capturedPiece = Piece.of(new PieceStatus(PieceType.GENERAL, MoveStrategyType.GENERAL), Team.HAN);
        final MoveResult moveResult = MoveResult.withCapture(capturedPiece);

        //then
        assertThat(moveResult.capturesGeneral()).isTrue();
    }
}
