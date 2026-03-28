package domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Position;
import dto.PieceInfoDto;
import dto.PieceInfosDto;
import java.util.Map;
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
        assertThat(pieceInfos.pieceInfos()).containsAllEntriesOf(Map.ofEntries(
                // ===== CHO =====
                Map.entry(Position.of(10, 1), PieceInfoDto.from(Piece.choPieceOf(PieceType.CHARIOT))),
                Map.entry(Position.of(10, 9), PieceInfoDto.from(Piece.choPieceOf(PieceType.CHARIOT))),
                Map.entry(Position.of(10, 2), PieceInfoDto.from(Piece.choPieceOf(PieceType.HORSE))),
                Map.entry(Position.of(10, 3), PieceInfoDto.from(Piece.choPieceOf(PieceType.ELEPHANT))),
                Map.entry(Position.of(10, 4), PieceInfoDto.from(Piece.choPieceOf(PieceType.GUARD))),
                Map.entry(Position.of(9, 5), PieceInfoDto.from(Piece.choPieceOf(PieceType.GENERAL))),
                Map.entry(Position.of(10, 6), PieceInfoDto.from(Piece.choPieceOf(PieceType.GUARD))),
                Map.entry(Position.of(10, 7), PieceInfoDto.from(Piece.choPieceOf(PieceType.ELEPHANT))),
                Map.entry(Position.of(10, 8), PieceInfoDto.from(Piece.choPieceOf(PieceType.HORSE))),
                Map.entry(Position.of(8, 2), PieceInfoDto.from(Piece.choPieceOf(PieceType.CANNON))),
                Map.entry(Position.of(8, 8), PieceInfoDto.from(Piece.choPieceOf(PieceType.CANNON))),
                Map.entry(Position.of(7, 1), PieceInfoDto.from(Piece.choPieceOf(PieceType.SOLDIER))),
                Map.entry(Position.of(7, 3), PieceInfoDto.from(Piece.choPieceOf(PieceType.SOLDIER))),
                Map.entry(Position.of(7, 5), PieceInfoDto.from(Piece.choPieceOf(PieceType.SOLDIER))),
                Map.entry(Position.of(7, 7), PieceInfoDto.from(Piece.choPieceOf(PieceType.SOLDIER))),
                Map.entry(Position.of(7, 9), PieceInfoDto.from(Piece.choPieceOf(PieceType.SOLDIER))),

                // ===== HAN =====
                Map.entry(Position.of(1, 1), PieceInfoDto.from(Piece.hanPieceOf(PieceType.CHARIOT))),
                Map.entry(Position.of(1, 2), PieceInfoDto.from(Piece.hanPieceOf(PieceType.HORSE))),
                Map.entry(Position.of(1, 3), PieceInfoDto.from(Piece.hanPieceOf(PieceType.ELEPHANT))),
                Map.entry(Position.of(1, 4), PieceInfoDto.from(Piece.hanPieceOf(PieceType.GUARD))),
                Map.entry(Position.of(2, 5), PieceInfoDto.from(Piece.hanPieceOf(PieceType.GENERAL))),
                Map.entry(Position.of(1, 6), PieceInfoDto.from(Piece.hanPieceOf(PieceType.GUARD))),
                Map.entry(Position.of(1, 7), PieceInfoDto.from(Piece.hanPieceOf(PieceType.ELEPHANT))),
                Map.entry(Position.of(1, 8), PieceInfoDto.from(Piece.hanPieceOf(PieceType.HORSE))),
                Map.entry(Position.of(1, 9), PieceInfoDto.from(Piece.hanPieceOf(PieceType.CHARIOT))),
                Map.entry(Position.of(3, 2), PieceInfoDto.from(Piece.hanPieceOf(PieceType.CANNON))),
                Map.entry(Position.of(3, 8), PieceInfoDto.from(Piece.hanPieceOf(PieceType.CANNON))),
                Map.entry(Position.of(4, 1), PieceInfoDto.from(Piece.hanPieceOf(PieceType.SOLDIER))),
                Map.entry(Position.of(4, 3), PieceInfoDto.from(Piece.hanPieceOf(PieceType.SOLDIER))),
                Map.entry(Position.of(4, 5), PieceInfoDto.from(Piece.hanPieceOf(PieceType.SOLDIER))),
                Map.entry(Position.of(4, 7), PieceInfoDto.from(Piece.hanPieceOf(PieceType.SOLDIER))),
                Map.entry(Position.of(4, 9), PieceInfoDto.from(Piece.hanPieceOf(PieceType.SOLDIER)))
        ));
    }
}
