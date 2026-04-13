package dao;

import static org.assertj.core.api.Assertions.assertThat;

import dao.mongodb.BoardDao;
import dao.mongodb.MongoConnection;
import dto.PieceDto;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardDaoTest {

    private BoardDao boardDao;

    @BeforeEach
    void setUp() {
        boardDao = new BoardDao(new MongoConnection());
    }

    @Test
    @DisplayName("보드를 저장하고 gameId를 반환한다")
    void 보드를_저장하고_gameId를_반환한다() {
        // given
        List<PieceDto> pieces = List.of(
                new PieceDto(0, 0, "초", "차"),
                new PieceDto(9, 8, "한", "궁")
        );

        // when
        String gameId = boardDao.save(pieces, 0);

        // then
        assertThat(gameId).isNotNull();
    }

    @Test
    @DisplayName("저장한 보드를 gameId로 조회할 수 있다")
    void 저장한_보드를_gameId로_조회할_수_있다() {
        // given
        List<PieceDto> pieces = List.of(
                new PieceDto(0, 0, "초", "차"),
                new PieceDto(9, 8, "한", "궁")
        );
        String gameId = boardDao.save(pieces, 0);

        // when
        List<PieceDto> foundPieces = boardDao.findPiecesByGameId(gameId);

        // then
        assertThat(foundPieces).hasSize(2);
    }

    @Test
    @DisplayName("저장한 보드의 기물 정보가 일치한다")
    void 저장한_보드의_기물_정보가_일치한다() {
        // given
        List<PieceDto> pieces = List.of(
                new PieceDto(0, 0, "초", "차"),
                new PieceDto(9, 8, "한", "궁")
        );
        String gameId = boardDao.save(pieces, 0);

        // when
        List<PieceDto> foundPieces = boardDao.findPiecesByGameId(gameId);

        // then
        PieceDto chariot = foundPieces.stream()
                .filter(p -> p.row() == 0 && p.col() == 0)
                .findFirst().get();
        assertThat(chariot.team()).isEqualTo("초");
        assertThat(chariot.type()).isEqualTo("차");
    }

    @Test
    @DisplayName("턴 카운트를 저장하고 조회할 수 있다")
    void 턴_카운트를_저장하고_조회할_수_있다() {
        // given
        List<PieceDto> pieces = List.of(new PieceDto(0, 0, "초", "차"));
        String gameId = boardDao.save(pieces, 3);

        // when
        int turnCount = boardDao.findTurnCountByGameId(gameId);

        // then
        assertThat(turnCount).isEqualTo(3);
    }

    @Test
    @DisplayName("보드를 업데이트하면 변경된 내용이 반영된다")
    void 보드를_업데이트하면_변경된_내용이_반영된다() {
        // given
        List<PieceDto> pieces = List.of(new PieceDto(0, 0, "초", "차"));
        String gameId = boardDao.save(pieces, 0);

        List<PieceDto> updatedPieces = List.of(new PieceDto(5, 5, "한", "졸"));

        // when
        boardDao.update(gameId, updatedPieces, 1);

        // then
        List<PieceDto> foundPieces = boardDao.findPiecesByGameId(gameId);
        assertThat(foundPieces).hasSize(1);
        int turnCount = boardDao.findTurnCountByGameId(gameId);
        assertThat(turnCount).isEqualTo(1);
    }
}