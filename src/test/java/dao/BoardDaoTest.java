package dao;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Board;
import domain.BoardFactory;
import domain.Piece;
import domain.Team;
import domain.Type;
import domain.vo.Position;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardDaoTest {

    private BoardDao boardDao;

    @BeforeEach
    void setUp() {
        boardDao = new BoardDao();
    }

    @Test
    @DisplayName("보드를 저장하고 gameId를 반환한다")
    void 보드를_저장하고_gameId를_반환한다() {
        // given
        Board board = BoardFactory.setUp();

        // when
        String gameId = boardDao.save(board, 0);

        // then
        assertThat(gameId).isNotNull();
    }

    @Test
    @DisplayName("저장한 보드를 gameId로 조회할 수 있다")
    void 저장한_보드를_gameId로_조회할_수_있다() {
        // given
        Board board = BoardFactory.setUp();
        String gameId = boardDao.save(board, 0);

        // when
        Board foundBoard = boardDao.findBoardByGameId(gameId);

        // then
        assertThat(foundBoard.getBoard()).hasSize(board.getBoard().size());
    }

    @Test
    @DisplayName("저장한 보드의 기물 정보가 일치한다")
    void 저장한_보드의_기물_정보가_일치한다() {
        // given
        Map<Position, Piece> boardMap = new HashMap<>();
        boardMap.put(Position.of(0, 0), Piece.of(Team.CHU, Type.CHARIOT));
        boardMap.put(Position.of(9, 8), Piece.of(Team.HAN, Type.GENERAL));
        Board board = Board.of(boardMap);
        String gameId = boardDao.save(board, 0);

        // when
        Board foundBoard = boardDao.findBoardByGameId(gameId);

        // then
        Piece piece = foundBoard.findPieceByPosition(Position.of(0, 0)).get();
        assertThat(piece.getTeamName()).isEqualTo("초");
        assertThat(piece.getTypeName()).isEqualTo("차");
    }

    @Test
    @DisplayName("턴 카운트를 저장하고 조회할 수 있다")
    void 턴_카운트를_저장하고_조회할_수_있다() {
        // given
        Board board = BoardFactory.setUp();
        String gameId = boardDao.save(board, 3);

        // when
        int turnCount = boardDao.findTurnCountByGameId(gameId);

        // then
        assertThat(turnCount).isEqualTo(3);
    }

    @Test
    @DisplayName("보드를 업데이트하면 변경된 내용이 반영된다")
    void 보드를_업데이트하면_변경된_내용이_반영된다() {
        // given
        Board board = BoardFactory.setUp();
        String gameId = boardDao.save(board, 0);
        Map<Position, Piece> updatedMap = new HashMap<>();
        updatedMap.put(Position.of(5, 5), Piece.of(Team.HAN, Type.SOLDIER));
        Board updatedBoard = Board.of(updatedMap);

        // when
        boardDao.update(gameId, updatedBoard, 1);

        // then
        Board foundBoard = boardDao.findBoardByGameId(gameId);
        assertThat(foundBoard.getBoard()).hasSize(1);
        int turnCount = boardDao.findTurnCountByGameId(gameId);
        assertThat(turnCount).isEqualTo(1);
    }
}
