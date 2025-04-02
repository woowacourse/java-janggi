package service.converter;

import static org.assertj.core.api.Assertions.assertThat;

import dao.PieceInfo;
import janggiGame.Board;
import janggiGame.Position;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DBConverterTest {
    @DisplayName("테스트")
    @Test
    void Naming_Test() {
        // given
        List<PieceInfo> pieceInfos = new ArrayList<>(
                List.of(new PieceInfo(1, 1, "KING", "HAN"),
                        new PieceInfo(2, 2, "CANNON", "CHO"))
        );

        // when
        Board board = DBConverter.convertToBoard(pieceInfos);

        // then
        assertThat(board.getSurvivedPieces()).hasSize(2);
        assertThat(board.getSurvivedPieces().containsKey(Position.of(1, 1))).isTrue();
        assertThat(board.getSurvivedPieces().containsKey(Position.of(2, 2))).isTrue();
        assertThat(board.getSurvivedPieces().containsKey(Position.of(1, 3))).isFalse();
    }
}