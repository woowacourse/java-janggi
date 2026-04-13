package domain.piece.rule;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.coordination.Coordination;
import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Piece;
import domain.piece.Soldier;
import domain.piece.Team;
import domain.piece.error.PieceException;
import java.util.List;
import org.junit.jupiter.api.Test;

class PieceRuleTest {

    @Test
    void 차의_직선과_궁성_대각선_이동_규칙을_검증한다() {
        PieceRule rule = new ChariotRule();

        assertThatCode(() -> rule.validate(Coordination.of(1, 10), Coordination.of(1, 8), Team.CHO))
                .doesNotThrowAnyException();
        assertThatCode(() -> rule.validate(Coordination.of(4, 10), Coordination.of(6, 8), Team.CHO))
                .doesNotThrowAnyException();
        assertThatThrownBy(() -> rule.validate(Coordination.of(1, 10), Coordination.of(2, 9), Team.CHO))
                .isInstanceOf(PieceException.class);
    }

    @Test
    void 차의_궁성_대각선_경로를_반환한다() {
        PieceRule rule = new ChariotRule();

        assertThat(rule.resolvePath(Coordination.of(4, 10), Coordination.of(6, 8), Team.CHO))
                .containsExactly(Coordination.of(5, 9));
    }

    @Test
    void 포는_경로에_정확히_하나의_기물이_있어야_한다() {
        PieceRule rule = new CannonRule();
        Piece chariot = new Chariot(Team.CHO);

        assertThatCode(() -> rule.validatePath(List.of(chariot)))
                .doesNotThrowAnyException();
        assertThatThrownBy(() -> rule.validatePath(List.of()))
                .isInstanceOf(PieceException.class);
        assertThatThrownBy(() -> rule.validatePath(List.of(chariot, new Soldier(Team.CHO))))
                .isInstanceOf(PieceException.class);
    }

    @Test
    void 포는_포를_넘거나_잡을_수_없다() {
        CannonRule rule = new CannonRule();

        assertThatThrownBy(() -> rule.validatePath(List.of(new Cannon(Team.CHO))))
                .isInstanceOf(PieceException.class);
        assertThatThrownBy(() -> rule.validateTarget(new Cannon(Team.HAN)))
                .isInstanceOf(PieceException.class);
    }

    @Test
    void 졸과_병의_기본_이동과_상대_궁성_대각선_이동을_검증한다() {
        PieceRule rule = new SoldierRule();

        assertThatCode(() -> rule.validate(Coordination.of(1, 7), Coordination.of(1, 6), Team.CHO))
                .doesNotThrowAnyException();
        assertThatCode(() -> rule.validate(Coordination.of(4, 3), Coordination.of(5, 2), Team.CHO))
                .doesNotThrowAnyException();
        assertThatThrownBy(() -> rule.validate(Coordination.of(4, 3), Coordination.of(5, 4), Team.CHO))
                .isInstanceOf(PieceException.class);
    }
}
