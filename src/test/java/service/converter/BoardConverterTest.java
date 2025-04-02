package service.converter;

import static org.assertj.core.api.Assertions.assertThat;

import dao.PieceInfo;
import janggiGame.Position;
import janggiGame.piece.Chariot;
import janggiGame.piece.King;
import janggiGame.piece.Piece;
import janggiGame.piece.character.Dynasty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class BoardConverterTest {
    private static Stream<Arguments> providePositionAndPiece() {
        return Stream.of(
                Arguments.of(Position.of(1, 1), new Chariot(Dynasty.HAN)),
                Arguments.of(Position.of(2, 2), new King(Dynasty.CHO))
        );
    }

    @DisplayName("Board를 DB의 데이터 형식으로 변환한다.")
    @ParameterizedTest
    @MethodSource("providePositionAndPiece")
    void boardConverterTest(Position position, Piece piece) {
        // given
        Map<Position, Piece> survivedPieces = new HashMap<>();
        survivedPieces.put(position, piece);

        Dynasty dynasty = Dynasty.HAN;
        if (piece.hasDynasty(Dynasty.CHO)) {
            dynasty = Dynasty.CHO;
        }

        // when
        List<PieceInfo> pieceInfos = BoardConverter.convertToPieceInfos(survivedPieces);

        // then
        assertThat(pieceInfos.getFirst().row()).isEqualTo(position.getRow());
        assertThat(pieceInfos.getFirst().column()).isEqualTo(position.getColumn());
        assertThat(pieceInfos.getFirst().type()).isEqualTo(piece.getType().name());
        assertThat(pieceInfos.getFirst().dynasty()).isEqualTo(dynasty.name());
    }
}