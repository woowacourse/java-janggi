package janggi.dao;

import janggi.dto.PieceDto;
import janggi.dto.PositionDto;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class JanggiDaoTest {

    DaoSettings daoSettings = new DaoSettings("localhost:13306", "testjanggi", "root", "root");
    JanggiDao janggiDao = new JanggiDao(daoSettings);

    @BeforeEach
    void resetDatabase() {
        janggiDao.deleteAllPieces();

        List<PieceDto> initialData = List.of(
                new PieceDto("Cannon", "HAN", 1, 2),
                new PieceDto("Soldier", "CHO", 1, 3)
        );
        janggiDao.addPieces(initialData);
    }

    @DisplayName("현재 저장된 기물 데이터를 List 컬렉션으로 반환한다.")
    @Test
    public void test1() {
        // given
        List<PieceDto> expected = List.of(
                new PieceDto("Cannon", "HAN", 1, 2),
                new PieceDto("Soldier", "CHO", 1, 3)
        );

        // when
        List<PieceDto> actual = janggiDao.getPieces();

        // then
        assertThat(actual).hasSameElementsAs(expected);
    }

    @DisplayName("좌표가 변경된 기물의 위치를 데이터베이스에 갱신할 수 있다.")
    @Test
    public void test2() {
        // given
        PositionDto oldPosition = new PositionDto(1, 2);
        PositionDto newPosition = new PositionDto(2, 5);
        PieceDto targetPiece = new PieceDto("Cannon", "HAN", newPosition.row(), newPosition.column());

        // when
        janggiDao.updatePiece(oldPosition, targetPiece);
        List<PieceDto> pieceDtos = janggiDao.getPieces();
        PieceDto actual = pieceDtos.stream()
                .filter(pieceDto -> pieceDto.name().equals("Cannon"))
                .findFirst()
                .orElseThrow();

        // then
        assertAll(
                () -> assertThat(actual.row()).isEqualTo(newPosition.row()),
                () -> assertThat(actual.column()).isEqualTo(newPosition.column())
        );
    }

    @DisplayName("모든 기물 데이터를 삭제할 수 있다.")
    @Test
    public void test3() {
        // when
        janggiDao.deleteAllPieces();
        List<PieceDto> actual = janggiDao.getPieces();

        // then
        assertThat(actual).isEmpty();
    }

    @DisplayName("기물 데이터를 추가할 수 있다.")
    @Test
    public void test4() {
        // when
        PieceDto newData = new PieceDto("Horse", "CHO", 1, 4);
        List<PieceDto> expected = List.of(
                new PieceDto("Cannon", "HAN", 1, 2),
                new PieceDto("Soldier", "CHO", 1, 3),
                newData
        );

        // when
        janggiDao.addPieces(List.of(newData));
        List<PieceDto> actual = janggiDao.getPieces();

        // then
        assertThat(actual).hasSameElementsAs(expected);
    }
}
