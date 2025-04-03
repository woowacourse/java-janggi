package dao;

import static org.assertj.core.api.Assertions.assertThat;
import static player.Nation.HAN;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pieceProperty.JanggiPieceInitializer;
import pieceProperty.PieceType;
import pieceProperty.Position;

public class JanggiGimulDaoTest {

    @Test
    @DisplayName("초나라 기물 초기화")
    public void insertChoPiecesTest() {
        //given
        JanggiGimulDao janggiGimulDao = new JanggiGimulDao();
        JanggiPieceInitializer janggiPieceInitializer = new JanggiPieceInitializer();
        janggiGimulDao.insertChoPieces(janggiPieceInitializer.choInit());

        //when
        Map<Position, PieceType> pieces = janggiGimulDao.findChoAllGimul();

        //then
        assertThat(pieces.size()).isEqualTo(16);
        janggiGimulDao.deleteAllPieces();
    }

    @Test
    @DisplayName("한나라 기물 초기화")
    public void insertHanPiecesTest() {
        //given
        JanggiGimulDao janggiGimulDao = new JanggiGimulDao();
        JanggiPieceInitializer janggiPieceInitializer = new JanggiPieceInitializer();
        janggiGimulDao.insertHanPieces(janggiPieceInitializer.hanInit());

        //when
        Map<Position, PieceType> pieces = janggiGimulDao.findHanAllGimul();

        //then
        assertThat(pieces.size()).isEqualTo(16);
        janggiGimulDao.deleteAllPieces();
    }

    @Test
    @DisplayName("합산 점수 계산 테스트")
    public void calculateSumTest() {
        //given
        JanggiGimulDao janggiGimulDao = new JanggiGimulDao();
        JanggiPieceInitializer janggiPieceInitializer = new JanggiPieceInitializer();
        janggiGimulDao.insertHanPieces(janggiPieceInitializer.hanInit());

        //when - then
        assertThat(janggiGimulDao.calculateSum("HAN")).isEqualTo(65);
        janggiGimulDao.deleteAllPieces();
    }

    @Test
    @DisplayName("공격 기물 위치 변화 테스트")
    void updateAttackPieceTest() {
        //given
        JanggiGimulDao janggiGimulDao = new JanggiGimulDao();
        JanggiPieceInitializer janggiPieceInitializer = new JanggiPieceInitializer();
        janggiGimulDao.insertHanPieces(janggiPieceInitializer.hanInit());

        //when
        janggiGimulDao.updateAttackGimul(new Position(0, 0), new Position(1, 0), HAN);
        Map<Position, PieceType> hanPieces = janggiGimulDao.findHanAllGimul();

        //then
        assertThat(hanPieces.containsKey(new Position(1, 0))).isTrue();
        janggiGimulDao.deleteAllPieces();
    }

    @Test
    @DisplayName("방어 기물 위치 변화 테스트")
    void updateDefensePieceTest() {
        //given
        JanggiGimulDao janggiGimulDao = new JanggiGimulDao();
        JanggiPieceInitializer janggiPieceInitializer = new JanggiPieceInitializer();
        janggiGimulDao.insertHanPieces(janggiPieceInitializer.hanInit());

        //when
        janggiGimulDao.updateDefenceGimul(new Position(0, 0), HAN);
        Map<Position, PieceType> hanPieces = janggiGimulDao.findHanAllGimul();

        //then
        assertThat(hanPieces.containsKey(new Position(0, 0))).isFalse();
        janggiGimulDao.deleteAllPieces();
    }

}
