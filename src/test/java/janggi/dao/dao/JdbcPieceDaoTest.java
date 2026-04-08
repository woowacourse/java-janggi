package janggi.dao.dao;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.dao.DatabaseTest;
import janggi.dao.piece.PieceEntity;
import janggi.model.Team;
import janggi.model.board.Board;
import janggi.model.board.BoardType;
import janggi.model.piece.Byeong;
import janggi.model.piece.Piece;
import janggi.model.piece.palace.Jang;
import janggi.model.piece.palace.Sa;
import janggi.model.position.absolute.Column;
import janggi.model.position.absolute.Position;
import janggi.model.position.absolute.Row;
import java.util.List;
import java.util.Map;
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

    @DisplayName("보드 전체를 저장한다.")
    @Test
    void saveBoard() {
        //given
        Board board = BoardType.FIRST.getBoard();

        //when
        pieceDao.saveBoard(connection, board.getBoardInfo(), gameId);

        //then
        assertThat(pieceDao.findAllPiecesByGameId(connection, gameId).size())
                .isEqualTo(32);
    }

    @DisplayName("해당 게임의 기물들을 모두 조회한다.")
    @Test
    void findAllPiecesByGameId_success() {
        //given
        Map<Position, Piece> boardInfo = Map.of(
                new Position(Row.ONE, Column.ONE), new Byeong(Team.CHO),
                new Position(Row.ONE, Column.TWO), new Jang(Team.HAN),
                new Position(Row.ONE, Column.THREE), new Sa(Team.CHO)
        );

        pieceDao.saveBoard(connection, boardInfo, gameId);

        //when
        List<PieceEntity> pieceEntities = pieceDao.findAllPiecesByGameId(connection, gameId);

        //then
        assertThat(
                pieceEntities.stream()
                        .map(PieceEntity::pieceType)
                        .toList()
        ).contains("BYEONG", "JANG", "SA");
    }

    @DisplayName("해당 게임 아이디의 기물이 없으면 빈 리스트를 반환한다.")
    @Test
    void findAllPiecesByGameId_fail() {
        //when
        List<PieceEntity> pieceEntities = pieceDao.findAllPiecesByGameId(connection, 1L);

        //then
        assertThat(pieceEntities).isEmpty();
    }

    @DisplayName("해당하는 위치의 기물을 조회한다.")
    @Test
    void findPieceByPosition_success() {
        //given
        Map<Position, Piece> boardInfo = Map.of(new Position(Row.ONE, Column.ONE), new Byeong(Team.CHO));
        pieceDao.saveBoard(connection, boardInfo, gameId);

        //when
        Optional<PieceEntity> result = pieceDao.findPieceByPosition(
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
        Optional<PieceEntity> result = pieceDao.findPieceByPosition(
                connection,
                new Position(Row.ONE, Column.ONE)
        );

        //then
        assertThat(result).isEmpty();
    }

    @DisplayName("기물의 위치를 업데이트 한다.")
    @Test
    void updatePosition() {
        //given
        Map<Position, Piece> boardInfo = Map.of(new Position(Row.ONE, Column.ONE), new Byeong(Team.CHO));
        pieceDao.saveBoard(connection, boardInfo, gameId);

        Long pieceId = pieceDao
                .findAllPiecesByGameId(connection, gameId)
                .getFirst().
                id();

        //when
        pieceDao.updatePosition(connection, pieceId, new Position(Row.TWO, Column.ONE));

        //then
        PieceEntity updated = pieceDao
                .findPieceByPosition(connection, new Position(Row.TWO, Column.ONE)).get();

        assertThat(updated.id()).isEqualTo(pieceId);
    }

    @DisplayName("해당 위치의 기물을 삭제한다.")
    @Test
    void deletePieceByPosition() {
        //given
        Position position = new Position(Row.ONE, Column.ONE);

        Map<Position, Piece> boardInfo = Map.of(position, new Byeong(Team.CHO));
        pieceDao.saveBoard(connection, boardInfo, gameId);

        //when
        pieceDao.deletePieceByPosition(connection, position);

        //then
        Optional<PieceEntity> result = pieceDao.findPieceByPosition(connection, position);
        assertThat(result).isEmpty();
    }
}