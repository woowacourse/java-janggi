package dao;

import domain.janggiPiece.Cannon;
import domain.janggiPiece.JanggiChessPiece;
import domain.janggiPiece.Piece;
import domain.position.JanggiPositionFactory;
import domain.type.JanggiTeam;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.assertj.core.api.Assertions.assertThat;

class JanggiBoardDaoTest {
    private final JanggiBoardDao dao = new JanggiBoardDao();
    private final JanggiTeam team = JanggiTeam.BLUE;

    @DisplayName("드라이버 연결 테스트")
    @Test
    void driverConnection() {
        // given

        // when
        Connection connection = dao.getConnection();

        // then
        assertThat(connection).isNotNull();
    }

    @DisplayName("기물 추가 테스트")
    @Test
    void addPiece() {
        // given

        // when
        dao.save(JanggiPositionFactory.of(1, 1), new Cannon(team));

        // then
    }

    @DisplayName("기물 조회 테스트")
    @Test
    void viewPiece() {
        // given

        // when
        JanggiChessPiece piece = dao.findByPosition(JanggiPositionFactory.of(1, 1));

        // then
        SoftAssertions.assertSoftly((softly) -> {
            softly.assertThat(piece.getTeam()).isSameAs(team);
            softly.assertThat(piece.getChessPieceType()).isSameAs(Piece.CANNON);
        });
    }
}
