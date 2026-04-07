package janggi.repository.dao;


import janggi.domain.Location;
import janggi.repository.entity.GameEntity;
import janggi.repository.entity.PieceEntity;
import janggi.repository.util.TransactionManager;
import janggi.support.TestDBConnectionProvider;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceDaoTest {

    TransactionManager transactionManager;
    PieceDao pieceDao;
    GameDao gameDao;

    GameEntity gameEntity;
    Long gameId;

    @BeforeEach
    void setup() {
        transactionManager = new TransactionManager(new TestDBConnectionProvider());
        pieceDao = new PieceDao(transactionManager);
        gameDao = new GameDao(transactionManager);
        transactionManager.begin();

        gameEntity = new GameEntity("CHO", true);
        gameId = gameDao.insert(gameEntity);

    }

    @AfterEach
    void tearDown() {
        transactionManager.rollback();
        transactionManager.close();
    }

    @Test
    @DisplayName("데이터베이스에 새로운 기물을 저장한다.")
    void insert_PersistsEntityAndCanBeFoundByBoardId() {
        // given
        PieceEntity cha = new PieceEntity(gameId, "CHA", "CHO", 1, 1);

        // when
        Long id = pieceDao.insert(cha);
        Optional<PieceEntity> piece = pieceDao.findById(id);

        // then
        Assertions.assertThat(piece).get()
                .extracting("id")
                .isEqualTo(id);
    }

    @Test
    @DisplayName("게임 식별자와 위치 정보를 전달하면 해당 좌표에 존재하는 기물을 반환한다.")
    void findByGameIdAndLocation_ReturnsPieceAtGivenLocation() {
        // given
        Location location = Location.of(1, 1);
        PieceEntity cha = new PieceEntity(gameId, "CHA", "CHO", location.row(), location.col());
        pieceDao.insert(cha);

        // when & then
        Assertions.assertThat(pieceDao.findByGameIdAndLocation(gameId, location)).get()
                .extracting("rowIdx", "colIdx")
                .containsExactly(1, 1);
    }

    @Test
    @DisplayName("게임 식별자를 전달하면 해당 게임에 존재하는 기물 목록을 반환한다.")
    void findByGameId_ReturnsPiecesInGame() {
        // given
        PieceEntity cha = new PieceEntity(gameId, "CHA", "CHO", 1, 1);
        PieceEntity po = new PieceEntity(gameId, "PO", "CHO", 1, 2);
        PieceEntity ma = new PieceEntity(gameId, "MA", "CHO", 1, 3);
        pieceDao.insert(cha);
        pieceDao.insert(po);
        pieceDao.insert(ma);

        // when
        List<PieceEntity> pieceEntities = pieceDao.findByGameId(gameId);

        // then
        Assertions.assertThat(pieceEntities).hasSize(3)
                .extracting("type", "side")
                .containsExactlyInAnyOrder(
                        Assertions.tuple("CHA", "CHO"),
                        Assertions.tuple("PO", "CHO"),
                        Assertions.tuple("MA", "CHO")
                );
    }
    @Test
    @DisplayName("기물의 식별자와 이동할 행, 열 인덱스를 전달하면 데이터베이스의 위치 정보가 갱신된다.")
    void updatePosition_UpdatesRowAndColumnIdx() {
        // given
        PieceEntity cha = new PieceEntity(gameId, "CHA", "CHO", 1, 1);
        Long pieceId = pieceDao.insert(cha);
        Location to = Location.of(2, 2);

        // when
        pieceDao.updatePosition(pieceId, to);

        // then
        Assertions.assertThat(pieceDao.findById(pieceId)).get()
                .extracting("rowIdx", "colIdx")
                .containsExactly(2, 2);
    }

    @Test
    @DisplayName("기물의 식별자를 전달하면 해당 기물 정보를 데이터베이스에서 영구 삭제한다.")
    void deleteById_RemovesPieceFromDatabase() {
        // given
        PieceEntity pieceEntity = new PieceEntity(gameId, "CHA", "CHO", 1, 1);
        Long savedId = pieceDao.insert(pieceEntity);

        // when
        pieceDao.deleteById(savedId);

        // then
        Optional<PieceEntity> foundPiece = pieceDao.findById(savedId);
        Assertions.assertThat(foundPiece).isEmpty();
    }
}
