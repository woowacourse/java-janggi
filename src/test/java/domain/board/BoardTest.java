package domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import domain.piece.Piece;
import domain.piece.PieceType;
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
        Board board = Board.init(ElephantSetup.InnerElephantSetup, ElephantSetup.InnerElephantSetup);

        // when
        PieceInfosDto pieceInfos = board.getPieceInfos();

        // then
        assertThat(pieceInfos.pieceInfos()).containsAll(List.of(
                // ===== CHO =====
                PieceInfoDto.of(Piece.choPieceOf(PieceType.CHARIOT), Position.of(10, 1)),
                PieceInfoDto.of(Piece.choPieceOf(PieceType.CHARIOT), Position.of(10, 9)),
                PieceInfoDto.of(Piece.choPieceOf(PieceType.HORSE), Position.of(10, 2)),
                PieceInfoDto.of(Piece.choPieceOf(PieceType.ELEPHANT), Position.of(10, 3)),
                PieceInfoDto.of(Piece.choPieceOf(PieceType.GUARD), Position.of(10, 4)),
                PieceInfoDto.of(Piece.choPieceOf(PieceType.GENERAL), Position.of(9, 5)),
                PieceInfoDto.of(Piece.choPieceOf(PieceType.GUARD), Position.of(10, 6)),
                PieceInfoDto.of(Piece.choPieceOf(PieceType.ELEPHANT), Position.of(10, 7)),
                PieceInfoDto.of(Piece.choPieceOf(PieceType.HORSE), Position.of(10, 8)),
                PieceInfoDto.of(Piece.choPieceOf(PieceType.CANNON), Position.of(8, 2)),
                PieceInfoDto.of(Piece.choPieceOf(PieceType.CANNON), Position.of(8, 8)),
                PieceInfoDto.of(Piece.choPieceOf(PieceType.SOLDIER), Position.of(7, 1)),
                PieceInfoDto.of(Piece.choPieceOf(PieceType.SOLDIER), Position.of(7, 3)),
                PieceInfoDto.of(Piece.choPieceOf(PieceType.SOLDIER), Position.of(7, 5)),
                PieceInfoDto.of(Piece.choPieceOf(PieceType.SOLDIER), Position.of(7, 7)),
                PieceInfoDto.of(Piece.choPieceOf(PieceType.SOLDIER), Position.of(7, 9)),

                // ===== HAN =====
                PieceInfoDto.of(Piece.hanPieceOf(PieceType.CHARIOT), Position.of(1, 1)),
                PieceInfoDto.of(Piece.hanPieceOf(PieceType.HORSE), Position.of(1, 2)),
                PieceInfoDto.of(Piece.hanPieceOf(PieceType.ELEPHANT), Position.of(1, 3)),
                PieceInfoDto.of(Piece.hanPieceOf(PieceType.GUARD), Position.of(1, 4)),
                PieceInfoDto.of(Piece.hanPieceOf(PieceType.GENERAL), Position.of(2, 5)),
                PieceInfoDto.of(Piece.hanPieceOf(PieceType.GUARD), Position.of(1, 6)),
                PieceInfoDto.of(Piece.hanPieceOf(PieceType.ELEPHANT), Position.of(1, 7)),
                PieceInfoDto.of(Piece.hanPieceOf(PieceType.HORSE), Position.of(1, 8)),
                PieceInfoDto.of(Piece.hanPieceOf(PieceType.CHARIOT), Position.of(1, 9)),
                PieceInfoDto.of(Piece.hanPieceOf(PieceType.CANNON), Position.of(3, 2)),
                PieceInfoDto.of(Piece.hanPieceOf(PieceType.CANNON), Position.of(3, 8)),
                PieceInfoDto.of(Piece.hanPieceOf(PieceType.SOLDIER), Position.of(4, 1)),
                PieceInfoDto.of(Piece.hanPieceOf(PieceType.SOLDIER), Position.of(4, 3)),
                PieceInfoDto.of(Piece.hanPieceOf(PieceType.SOLDIER), Position.of(4, 5)),
                PieceInfoDto.of(Piece.hanPieceOf(PieceType.SOLDIER), Position.of(4, 7)),
                PieceInfoDto.of(Piece.hanPieceOf(PieceType.SOLDIER), Position.of(4, 9))
        ));
    }
}
