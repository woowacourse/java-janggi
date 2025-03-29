package dao;

import domain.janggiPiece.Cannon;
import domain.janggiPiece.JanggiChessPiece;
import domain.position.JanggiPosition;
import domain.position.JanggiPositionFactory;
import domain.type.JanggiTeam;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.assertj.core.api.Assertions.assertThat;

class JanggiBoardDaoTest {
    private final JanggiBoardDao dao = new JanggiBoardDao();
    private final JanggiPosition position = JanggiPositionFactory.of(1, 1);
    private final JanggiTeam team = JanggiTeam.BLUE;
    private final Cannon piece = new Cannon(team);


    @DisplayName("드라이버 연결 테스트")
    @Test
    void driverConnection() {
        // given

        // when
        Connection connection = dao.getConnection();

        // then
        assertThat(connection).isNotNull();
    }

    @Disabled
    @DisplayName("기물 추가 테스트")
    @Test
    void addPiece() {
        // given

        // when
        dao.save(position, piece);

        // then
    }

    @Disabled
    @DisplayName("기물 조회 테스트")
    @Test
    void viewPiece() {
        // given

        // when
        JanggiChessPiece actual = dao.findByPosition(position);

        // then
        SoftAssertions.assertSoftly((softly) -> {
            softly.assertThat(actual.getTeam()).isSameAs(team);
            softly.assertThat(actual.getChessPieceType()).isSameAs(piece.getChessPieceType());
        });
    }

    @Disabled
    @DisplayName("기물 삭제 테스트")
    @Test
    void deletePiece() {
        // given

        // when
        dao.delete(position);
        JanggiChessPiece piece = dao.findByPosition(position);

        // then
        assertThat(piece).isNull();
    }

    @Disabled
    @DisplayName("기물 수정 테스트")
    @Test
    void updatePiece() {
        // given
        dao.save(position, piece);

        // when
        JanggiPosition afterPosition = JanggiPositionFactory.of(5, 5);
        dao.updatePosition(position, afterPosition);

        // then
        JanggiChessPiece actual = dao.findByPosition(afterPosition);
        SoftAssertions.assertSoftly((softly) -> {
            softly.assertThat(actual.getTeam()).isSameAs(team);
            softly.assertThat(actual.getChessPieceType()).isSameAs(piece.getChessPieceType());
        });
    }
}
