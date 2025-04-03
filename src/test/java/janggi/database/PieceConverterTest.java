package janggi.database;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.piece.Piece;
import janggi.piece.PieceDto;
import janggi.piece.PieceType;
import janggi.piece.Pieces;
import janggi.position.Position;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceConverterTest {

    @Test
    @DisplayName("PieceDto를 받아 Piece 객체로 변환한다")
    void should_convertToPiece() {
        // given
        PieceDto pieceDto = new PieceDto(1, 1, "TANK", "RED");

        // when
        Piece piece = PieceConverter.convertToPiece(pieceDto);

        // then
        assertThat(piece.getPieceType()).isEqualTo(PieceType.TANK);
        assertThat(piece.getColorMessage()).isEqualTo("RED");
    }

    @Test
    @DisplayName("PieceDto 리스트를 받아 Pieces 객체로 변환한다")
    void should_convertToPieces() {
        // given
        PieceDto pieceDto1 = new PieceDto(1, 1, "TANK", "RED");
        PieceDto pieceDto2 = new PieceDto(2, 2, "CANNON", "BLUE");
        List<PieceDto> pieceDtos = List.of(pieceDto1, pieceDto2);

        // when
        Pieces pieces = PieceConverter.convertToPieces(pieceDtos);

        // then
        assertAll(() ->assertThat(pieces.getPieces().size()).isEqualTo(2),
                ()-> assertThat(pieces.getPieceByPosition(new Position(1,1)).getPieceType())
                        .isEqualTo(PieceType.TANK),
                ()-> assertThat(pieces.getPieceByPosition(new Position(2,2)).getPieceType())
                        .isEqualTo(PieceType.CANNON)
        );
    }
}
