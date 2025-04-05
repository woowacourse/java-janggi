package janggi.dao.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.Board;
import janggi.domain.board.BoardOrder;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PiecesFactory;
import janggi.domain.piece.position.Position;
import janggi.domain.players.Players;
import janggi.domain.players.Team;
import janggi.dto.PieceDto;
import janggi.dto.PieceMove;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceHistoryManagerTest {

    private final FakePieceDao fakePieceDao = new FakePieceDao();
    private final PieceHistoryManager pieceHistoryManager = new PieceHistoryManager(fakePieceDao);

    @Test
    void 초기화한다() {
        // Given
        final PiecesFactory piecesFactory = new PiecesFactory();
        final Board choBoard = piecesFactory.initializeChoPieces(BoardOrder.ELEPHANT_HORSE_ELEPHANT_HORSE);
        final Board hanBoard = piecesFactory.initializeHanPieces(BoardOrder.ELEPHANT_HORSE_ELEPHANT_HORSE);
        final Players players = new Players(Map.of(Team.CHO, choBoard, Team.HAN, hanBoard));

        // When
        pieceHistoryManager.initialize(players);

        // Then
        assertThat(fakePieceDao.selectAll()).hasSize(32);
    }

    @Test
    void 기물이_없을_경우_초기화해야한다() {
        assertThat(pieceHistoryManager.mustBeInitialize()).isTrue();
    }

    @Test
    void 두_팀_중_하나의_왕이라도_죽었을_경우_초기화해야한다() {
        // Given
        fakePieceDao.insert(new PieceDto(Team.CHO, Piece.KING, 2, 5));

        // When & Then
        assertThat(pieceHistoryManager.mustBeInitialize()).isTrue();
    }

    @Test
    @DisplayName("위치를 업데이트한다")
    void 위치를_업데이트한다() {
        // Given
        final PieceDto hanPiece = new PieceDto(Team.HAN, Piece.CHARIOT, 3, 5);
        fakePieceDao.insert(hanPiece);
        final PieceMove pieceMove = new PieceMove(Team.HAN, Piece.CHARIOT, new Position(3, 5), new Position(2, 5));
        final PieceDto updatedDto = new PieceDto(Team.HAN, Piece.CHARIOT, 2, 5);

        // When
        pieceHistoryManager.updatePiece(pieceMove);

        // Then
        assertThat(fakePieceDao.selectAll()).isEqualTo(List.of(updatedDto));
    }


    @Test
    void 기물이_잡혔을_경우_기물을_제거한후_위치를_업데이트한다() {
        // Given
        final PieceDto hanPiece = new PieceDto(Team.HAN, Piece.CHARIOT, 3, 5);
        fakePieceDao.insert(hanPiece);
        fakePieceDao.insert(new PieceDto(Team.CHO, Piece.KING, 2, 5));
        final PieceMove pieceMove = PieceMove.capture(Team.HAN, Piece.CHARIOT, new Position(3, 5), new Position(2, 5),
                Piece.KING);
        final PieceDto updatedDto = new PieceDto(Team.HAN, Piece.CHARIOT, 2, 5);

        // When
        pieceHistoryManager.updatePiece(pieceMove);

        // Then
        assertThat(fakePieceDao.selectAll()).isEqualTo(List.of(updatedDto));
    }
}
