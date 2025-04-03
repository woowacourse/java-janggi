package janggi.repository;

import janggi.dao.InMemoryBoardDao;
import janggi.dao.InMemoryPieceDao;
import janggi.domain.Board;
import janggi.domain.Position;
import janggi.domain.Team;
import janggi.domain.piece.General;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.entity.PieceEntity;
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
        InMemoryPieceDao pieceDao = new InMemoryPieceDao();
        InMemoryBoardDao boardDao = new InMemoryBoardDao();
        Piece piece = new General(Team.GREEN);
        PieceEntity target = PieceEntity.of(1, piece, Position.of(1, 1), true);
        BoardRepository boardRepository = new BoardRepository(boardDao, pieceDao);

        //when
        boardRepository.savePiece(target, Position.of(1, 1));

        //then
        PieceEntity expected = pieceDao.getIdToBoard().get(1L);
        PieceEntity actual = new PieceEntity(1, 1, piece.getPieceType().name(), piece.getTeam().name(), 1, 1, true);
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    @DisplayName("대상 위치 조건에 맞는 boardEntity를 제시한 entity로 저장한다.")
    void updateWhenExistSameTargetPosition() {
        //given
        InMemoryPieceDao pieceDao = new InMemoryPieceDao();
        InMemoryBoardDao boardDao = new InMemoryBoardDao();
        Piece piece = new General(Team.GREEN);
        PieceEntity target = PieceEntity.of(1, piece, Position.of(1, 1), true);
        pieceDao.save(target);
        BoardRepository boardRepository = new BoardRepository(boardDao, pieceDao);

        //when
        boardRepository.savePiece(PieceEntity.of(target.pieceId(),
                        target.boardId(),
                        piece,
                        Position.of(2, 2),
                        true),
                Position.of(1, 1));

        //then
        PieceEntity expected = pieceDao.getIdToBoard().get(1L);
        PieceEntity actual = new PieceEntity(1, 1, piece.getPieceType().name(), piece.getTeam().name(), 2, 2, true);
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    @DisplayName("Board 도메인을 DB에 저장한다")
    void savePieceAll() {
        //given
        InMemoryPieceDao pieceDao = new InMemoryPieceDao();
        InMemoryBoardDao boardDao = new InMemoryBoardDao();
        BoardRepository boardRepository = new BoardRepository(boardDao, pieceDao);

        Map<Position, Piece> positionToPiece = new HashMap<>();
        positionToPiece.put(Position.of(1, 1), new General(Team.RED));
        positionToPiece.put(Position.of(2, 2), new General(Team.GREEN));

        //when
        boardRepository.savePieceAll(1L, new Board(positionToPiece));

        //then
        Map<Long, PieceEntity> actual = Map.of(
                1L, new PieceEntity(1L, 1L, PieceType.GENERAL.name(), Team.RED.name(), 1, 1, true),
                2L, new PieceEntity(2L, 1L, PieceType.GENERAL.name(), Team.GREEN.name(), 2, 2, true)
        );

        assertThat(pieceDao.getIdToBoard()).isEqualTo(actual);
    }
}
