package janggi.dao.dao;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.dao.DatabaseTest;
import janggi.dao.piece.PieceEntity;
import janggi.model.board.Board;
import janggi.model.board.BoardType;
import janggi.model.position.absolute.Column;
import janggi.model.position.absolute.Position;
import janggi.model.position.absolute.Row;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JdbcPieceDaoTest extends DatabaseTest {

    Long gameId;

    @BeforeEach
    void beforeEach() {
        this.gameId = gameDao.saveGame(connection, "CHO");
    }

    @DisplayName("해당 게임의 기물들을 모두 조회한다.")
    @Test
    void findAllPiecesByGameId_success() {
        //given
        pieceEntityDao.savePiece(connection, gameId, "BYEONG", 1, 1, "CHO");
        pieceEntityDao.savePiece(connection, gameId, "JANG", 1, 2, "HAN");
        pieceEntityDao.savePiece(connection, gameId, "SA", 1, 3, "CHO");

        //when
        List<PieceEntity> pieceEntities = pieceEntityDao.findAllPiecesByGameId(connection, gameId);

        //then
        PieceEntity first = pieceEntities.get(0);
        PieceEntity second = pieceEntities.get(1);
        PieceEntity third = pieceEntities.get(2);

        assertThat(first.gameId()).isEqualTo(gameId);
        assertThat(second.gameId()).isEqualTo(gameId);
        assertThat(third.gameId()).isEqualTo(gameId);

        assertThat(first.pieceType()).isEqualTo("BYEONG");
        assertThat(second.pieceType()).isEqualTo("JANG");
        assertThat(third.pieceType()).isEqualTo("SA");
    }

    @DisplayName("보드 전체를 저장한다.")
    @Test
    void savePieceBoard() {
        //given
        Board board = BoardType.FIRST.getBoard();

        //when
        pieceEntityDao.saveBoard(connection, board.getBoardInfo(), gameId);

        //then
        assertThat(pieceEntityDao.findAllPiecesByGameId(connection, gameId).size())
                .isEqualTo(32);
    }

    @DisplayName("해당 게임 아이디의 기물이 없으면 빈 리스트를 반환한다.")
    @Test
    void findAllPiecesByGameId_fail() {
        //when
        List<PieceEntity> pieceEntities = pieceEntityDao.findAllPiecesByGameId(connection, 1L);

        //then
        assertThat(pieceEntities).isEmpty();
    }

    @DisplayName("해당하는 위치의 기물을 조회한다.")
    @Test
    void findPieceByPosition_success() {
        //given
        pieceEntityDao.savePiece(connection, gameId, "BYEONG", 1, 1, "CHO");

        //when
        Optional<PieceEntity> result = pieceEntityDao.findPieceByPosition(
                connection,
                new Position(Row.ONE, Column.ONE)
        );

        //then
        PieceEntity found = result.get();
        assertThat(found.pieceType()).isEqualTo("BYEONG");
        assertThat(found.team()).isEqualTo("CHO");
    }

    @DisplayName("해당하는 위치에 기물이 없으면 Optional.empty()를 반환한다.")
    @Test
    void findPieceByPosition_fail() {
        //when
        Optional<PieceEntity> result = pieceEntityDao.findPieceByPosition(
                connection,
                new Position(Row.ONE, Column.ONE)
        );

        //then
        assertThat(result).isEmpty();
    }

    @DisplayName("기물의 위치를 업데이트 한다.")
    @Test
    void updatePieceOfPosition() {
        //given
        pieceEntityDao.savePiece(connection, gameId, "BYEONG", 1, 1, "CHO");
        Long pieceId = pieceEntityDao
                .findAllPiecesByGameId(connection, gameId)
                .getFirst().
                id();

        //when
        pieceEntityDao.updatePieceOfPosition(connection, pieceId, new Position(Row.TWO, Column.ONE));

        //then
        PieceEntity updated = pieceEntityDao
                .findPieceByPosition(connection, new Position(Row.TWO, Column.ONE)).get();

        assertThat(updated.id()).isEqualTo(pieceId);
    }

    @DisplayName("해당 위치의 기물을 삭제한다.")
    @Test
    void deletePieceByPosition() {
        //given
        pieceEntityDao.savePiece(connection, gameId, "BYEONG", 1, 1, "CHO");
        Position position = new Position(Row.ONE, Column.ONE);

        //when
        pieceEntityDao.deletePieceByPosition(connection, position);

        //then
        Optional<PieceEntity> result = pieceEntityDao.findPieceByPosition(connection, position);
        assertThat(result).isEmpty();
    }
}