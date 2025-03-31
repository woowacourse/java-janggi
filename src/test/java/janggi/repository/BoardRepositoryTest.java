package janggi.repository;

import janggi.dao.InMemoryBoardDao;
import janggi.domain.Board;
import janggi.domain.Position;
import janggi.domain.Team;
import janggi.domain.piece.General;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.entity.BoardEntity;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BoardRepositoryTest {

    @Test
    @DisplayName("대상 위치의 boardEntity가 DB에 존재하지 않을 경우, 새로 저장한다")
    void insertWhenNotExistSameTargetPosition() {
        //given
        InMemoryBoardDao boardDao = new InMemoryBoardDao();
        Piece piece = new General(Team.GREEN);
        BoardEntity target = BoardEntity.of(1, piece, Position.of(1, 1), true);
        BoardRepository boardRepository = new BoardRepository(boardDao);

        //when
        boardRepository.save(target, Position.of(1, 1));

        //then
        BoardEntity expected = boardDao.getIdToBoard().get(1L);
        BoardEntity actual = new BoardEntity(1, 1, piece.getPieceType().name(), piece.getTeam().name(), 1, 1, true);
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    @DisplayName("대상 위치 조건에 맞는 boardEntity를 제시한 entity로 저장한다.")
    void updateWhenExistSameTargetPosition() {
        //given
        InMemoryBoardDao boardDao = new InMemoryBoardDao();
        Piece piece = new General(Team.GREEN);
        BoardEntity target = BoardEntity.of(1, piece, Position.of(1, 1), true);
        boardDao.save(target);
        BoardRepository boardRepository = new BoardRepository(boardDao);

        //when
        boardRepository.save(BoardEntity.of(target.boardId(),
                        target.janggiId(),
                        piece,
                        Position.of(2, 2),
                        true),
                Position.of(1, 1));

        //then
        BoardEntity expected = boardDao.getIdToBoard().get(1L);
        BoardEntity actual = new BoardEntity(1, 1, piece.getPieceType().name(), piece.getTeam().name(), 2, 2, true);
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    @DisplayName("Board 도메인을 DB에 저장한다")
    void saveAll() {
        //given
        InMemoryBoardDao boardDao = new InMemoryBoardDao();
        BoardRepository boardRepository = new BoardRepository(boardDao);

        Map<Position, Piece> positionToPiece = new HashMap<>();
        positionToPiece.put(Position.of(1, 1), new General(Team.RED));
        positionToPiece.put(Position.of(2, 2), new General(Team.GREEN));

        //when
        boardRepository.saveAll(1L, new Board(positionToPiece));

        //then
        Map<Long, BoardEntity> actual = Map.of(
                1L, new BoardEntity(1L, 1L, PieceType.GENERAL.name(), Team.RED.name(), 1, 1, true),
                2L, new BoardEntity(2L, 1L, PieceType.GENERAL.name(), Team.GREEN.name(), 2, 2, true)
        );

        assertThat(boardDao.getIdToBoard()).isEqualTo(actual);
    }
}
