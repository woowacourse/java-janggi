package repository;

import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.InitializeSetting;
import org.h2.tools.Server;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class GameDaoTest {
    @Test
    void test() throws SQLException {
        // 1. 내장 서버 시작 (콘솔 확인용)
        Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();

        GameDao gameDao = new GameDao();
        PieceDao pieceDao = new PieceDao();

        // 2. 새로운 장기판 생성
        Board board = BoardFactory.createBoard(
                InitializeSetting.LEFT_ELEPHANT_SETTING,
                InitializeSetting.RIGHT_ELEPHANT_SETTING
        );

        // 3. 게임 저장 및 ID 획득
        long gameId = gameDao.save("녀녕의 첫 영속성 장기", "CHO");
        System.out.println("✅ 게임 저장 완료! ID: " + gameId);

        // 4. 기물 32개 스냅샷 저장
        pieceDao.saveAll(gameId, board.getPieces());
        System.out.println("✅ 기물 32개 저장 완료!");
    }
}
