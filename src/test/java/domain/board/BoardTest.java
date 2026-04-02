package domain.board;

import static domain.piece.PieceType.CANNON;
import static domain.piece.PieceType.CHARIOT;
import static domain.piece.PieceType.ELEPHANT;
import static domain.piece.PieceType.GENERAL;
import static domain.piece.PieceType.GUARD;
import static domain.piece.PieceType.HORSE;
import static domain.piece.PieceType.SOLDIER;
import static domain.player.Team.CHO;
import static domain.player.Team.HAN;
import static org.assertj.core.api.Assertions.assertThat;

import domain.piece.Piece;
import domain.piece.Position;
import dto.PieceInfoDto;
import dto.PieceInfosDto;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    @DisplayName("보드 초기화 테스트")
    public void boardInitTest() {
        // given
        Board board = BoardInitializer.initialize(ElephantSetup.InnerElephantSetup, ElephantSetup.InnerElephantSetup);

        // when
        PieceInfosDto pieceInfos = board.getPieceInfos();

        // then
        assertThat(pieceInfos.pieceInfos()).containsAll(List.of(
                // ===== CHO =====
                PieceInfoDto.of(Piece.of(CHARIOT, CHO), Position.of(10, 1)),
                PieceInfoDto.of(Piece.of(CHARIOT, CHO), Position.of(10, 9)),
                PieceInfoDto.of(Piece.of(HORSE, CHO), Position.of(10, 2)),
                PieceInfoDto.of(Piece.of(ELEPHANT, CHO), Position.of(10, 3)),
                PieceInfoDto.of(Piece.of(GUARD, CHO), Position.of(10, 4)),
                PieceInfoDto.of(Piece.of(GENERAL, CHO), Position.of(9, 5)),
                PieceInfoDto.of(Piece.of(GUARD, CHO), Position.of(10, 6)),
                PieceInfoDto.of(Piece.of(ELEPHANT, CHO), Position.of(10, 7)),
                PieceInfoDto.of(Piece.of(HORSE, CHO), Position.of(10, 8)),
                PieceInfoDto.of(Piece.of(CANNON, CHO), Position.of(8, 2)),
                PieceInfoDto.of(Piece.of(CANNON, CHO), Position.of(8, 8)),
                PieceInfoDto.of(Piece.of(SOLDIER, CHO), Position.of(7, 1)),
                PieceInfoDto.of(Piece.of(SOLDIER, CHO), Position.of(7, 3)),
                PieceInfoDto.of(Piece.of(SOLDIER, CHO), Position.of(7, 5)),
                PieceInfoDto.of(Piece.of(SOLDIER, CHO), Position.of(7, 7)),
                PieceInfoDto.of(Piece.of(SOLDIER, CHO), Position.of(7, 9)),

                // ===== HAN =====
                PieceInfoDto.of(Piece.of(CHARIOT, HAN), Position.of(1, 1)),
                PieceInfoDto.of(Piece.of(HORSE, HAN), Position.of(1, 2)),
                PieceInfoDto.of(Piece.of(ELEPHANT, HAN), Position.of(1, 3)),
                PieceInfoDto.of(Piece.of(GUARD, HAN), Position.of(1, 4)),
                PieceInfoDto.of(Piece.of(GENERAL, HAN), Position.of(2, 5)),
                PieceInfoDto.of(Piece.of(GUARD, HAN), Position.of(1, 6)),
                PieceInfoDto.of(Piece.of(ELEPHANT, HAN), Position.of(1, 7)),
                PieceInfoDto.of(Piece.of(HORSE, HAN), Position.of(1, 8)),
                PieceInfoDto.of(Piece.of(CHARIOT, HAN), Position.of(1, 9)),
                PieceInfoDto.of(Piece.of(CANNON, HAN), Position.of(3, 2)),
                PieceInfoDto.of(Piece.of(CANNON, HAN), Position.of(3, 8)),
                PieceInfoDto.of(Piece.of(SOLDIER, HAN), Position.of(4, 1)),
                PieceInfoDto.of(Piece.of(SOLDIER, HAN), Position.of(4, 3)),
                PieceInfoDto.of(Piece.of(SOLDIER, HAN), Position.of(4, 5)),
                PieceInfoDto.of(Piece.of(SOLDIER, HAN), Position.of(4, 7)),
                PieceInfoDto.of(Piece.of(SOLDIER, HAN), Position.of(4, 9))
        ));
    }
}
