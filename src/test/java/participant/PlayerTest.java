package participant;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.EnumSource.Mode;
import pieces.PieceScore;
import pieces.PieceType;
import pieces.Side;

class PlayerTest {

    @ParameterizedTest
    @EnumSource(value = PieceType.class, mode = Mode.EXCLUDE, names = "GUNG")
    void 상대_기물을_잡으면_기물의_점수만큼_점수가_증가한다(PieceType pieceType) {
        // given
        Score pieceScore = PieceScore.from(pieceType);
        Score beforeScore = new Score(0L);
        Player beforePlayer = new Player(Side.HAN, beforeScore);
        // when
        Player after = beforePlayer.attack(pieceType);
        // then
        long expected = pieceScore.value() + beforeScore.value();
        assertThat(after.score().value()).isEqualTo(expected);
    }
}