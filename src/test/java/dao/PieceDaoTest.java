package dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import domain.Coordinate;
import domain.Team;
import domain.piece.pathPiece.Cha;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceDaoTest {

    @Test
    @DisplayName("DB 연결 테스트")
    void connection() {
        //given
        final var dao = new PieceDao();

        //when
        final var connection = dao.getConnection();

        //then
        assertThat(connection).isNotNull();
    }

    @Test
    @DisplayName("피스를 저장할 수 있다.")
    void savePiece() {
        //given
        final var dao = new PieceDao();
        final var piece = new Cha(Team.HAN, new Coordinate(1, 1));

        //when
        dao.save(piece);

        //then
        final var findPieces = dao.findAll();
        dao.deleteByCoordinate(new Coordinate(1, 1));
        assertThat(findPieces).contains(new Cha(Team.HAN, new Coordinate(1, 1)));
    }

    @Test
    @DisplayName("피스의 좌표를 수정할 수 있다.")
    void updatePiece() {
        //given
        final var dao = new PieceDao();
        final var piece = new Cha(Team.HAN, new Coordinate(1, 1));
        dao.save(piece);

        //when
        dao.update(new Coordinate(1, 1), new Coordinate(1, 3));

        //then
        final var findPieces = dao.findAll();
        assertAll(
            () -> assertThat(findPieces).doesNotContain(piece),
            () -> assertThat(findPieces).contains(new Cha(Team.HAN, new Coordinate(1, 3)))
        );
    }

    @Test
    @DisplayName("피스를 삭제할 수 있다.")
    void deletePiece() {
        //given
        final var dao = new PieceDao();
        final var piece = new Cha(Team.HAN, new Coordinate(1, 1));
        dao.save(piece);

        //when
        dao.deleteByCoordinate(new Coordinate(1, 1));

        //then
        final var findPieces = dao.findAll();
        assertThat(findPieces).doesNotContain(piece);
    }

    @Test
    @DisplayName("모든 피스를 삭제할 수 있다.")
    void clear() {
        //given
        final var dao = new PieceDao();
        dao.save(new Cha(Team.HAN, new Coordinate(1, 1)));
        dao.save(new Cha(Team.HAN, new Coordinate(9, 1)));

        //when
        dao.clear();

        //then
        final var findPieces = dao.findAll();
        assertThat(findPieces).isEmpty();
    }

    @Test
    @DisplayName("다음 차례 팀을 변경할 수 있다.")
    void passTurn() {
        //given
        final var dao = new PieceDao();

        //when
        dao.setTurn(Team.HAN);

        //then
        final var turn = dao.getTurn();
        assertThat(turn).isEqualTo(Team.HAN);
    }
}
