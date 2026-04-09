package domain.piece;

import domain.board.Board;
import domain.board.Placement;
import domain.config.TestDataSourceConfig;
import domain.position.Position;
import janggigame.GameMetaData;
import janggigame.JanggiGameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.SchemaInitializer;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PieceRepositoryTest {
    PieceRepository pieceRepository;
    JanggiGameRepository janggiGameRepository;

    @BeforeEach
    void setUp() {
        DataSource dataSource = TestDataSourceConfig.testDataSource();
        SchemaInitializer.initialize(dataSource);
        pieceRepository = new PieceRepository(dataSource);
        janggiGameRepository = new JanggiGameRepository(dataSource);
    }

    @Test
    @DisplayName("같은 gameId를 가진 피스들의 정보를 가져올 수 있다.")
    void findByGameId_테스트() {
        // given
        GameMetaData firstGame = janggiGameRepository.save(GameMetaData.newGame());
        GameMetaData secondGame = janggiGameRepository.save(GameMetaData.newGame());

        Board firstBoard = new Board();
        firstBoard.placePieces(Side.HAN, Placement.INNER_ELEPHANT);
        pieceRepository.savePlacement(firstBoard, firstGame, Side.HAN);

        Board secondBoard = new Board();
        secondBoard.placePieces(Side.CHO, Placement.INNER_ELEPHANT);
        pieceRepository.savePlacement(secondBoard, secondGame, Side.CHO);

        // when
        List<PieceSnapshot> pieceSnapshots = pieceRepository.findByGameId(firstGame);

        // then
        assertThat(pieceSnapshots.size()).isEqualTo(16);
        assertThat(pieceSnapshots).allMatch(pieceSnapshot -> pieceSnapshot.getSide().equals(Side.HAN));
    }

    @Test
    @DisplayName("상차림 배치를 저장할 수 있다.")
    void savePlacement_테스트() {
        // given
        GameMetaData firstGame = janggiGameRepository.save(GameMetaData.newGame());
        Board firstBoard = new Board();
        firstBoard.placePieces(Side.HAN, Placement.INNER_ELEPHANT);

        // when
        pieceRepository.savePlacement(firstBoard, firstGame, Side.HAN);

        // then
        List<PieceSnapshot> pieceSnapshots = pieceRepository.findByGameId(firstGame);
        assertThat(pieceSnapshots.size()).isEqualTo(16);
        assertThat(pieceSnapshots).allMatch(pieceSnapshot -> pieceSnapshot.getSide().equals(Side.HAN));
    }

    @Test
    @DisplayName("기물의 위치정보를 변경할 수 있다.")
    void updatePiecePosition_테스트() {
        // given
        GameMetaData firstGame = janggiGameRepository.save(GameMetaData.newGame());
        Board firstBoard = new Board();
        firstBoard.placePieces(Side.HAN, Placement.INNER_ELEPHANT);
        pieceRepository.savePlacement(firstBoard, firstGame, Side.HAN);

        // when
        pieceRepository.updatePiecePosition(Position.of(7, 9), Position.of(7, 8), firstGame);

        // then
        List<PieceSnapshot> pieceSnapshots = pieceRepository.findByGameId(firstGame);
        assertThat(pieceSnapshots.size()).isEqualTo(16);
        assertThat(pieceSnapshots)
                .noneMatch(pieceSnapshot -> pieceSnapshot.getPositionX() == 7 && pieceSnapshot.getPositionY() == 9);
        assertThat(pieceSnapshots)
                .anyMatch(pieceSnapshot -> pieceSnapshot.getPositionX() == 7 && pieceSnapshot.getPositionY() == 8);
    }

    @Test
    @DisplayName("기물의 위치 정보를 삭제할 수 있다.")
    void deletePiecePosition_테스트() {
        // given
        GameMetaData firstGame = janggiGameRepository.save(GameMetaData.newGame());
        Board firstBoard = new Board();
        firstBoard.placePieces(Side.HAN, Placement.INNER_ELEPHANT);
        pieceRepository.savePlacement(firstBoard, firstGame, Side.HAN);

        // when
        pieceRepository.deletePiecePosition(Position.of(7, 9), firstGame);

        // then
        List<PieceSnapshot> pieceSnapshots = pieceRepository.findByGameId(firstGame);
        assertThat(pieceSnapshots.size()).isEqualTo(15);
        assertThat(pieceSnapshots)
                .noneMatch(pieceSnapshot -> pieceSnapshot.getPositionX() == 7 && pieceSnapshot.getPositionY() == 9);
    }
}
