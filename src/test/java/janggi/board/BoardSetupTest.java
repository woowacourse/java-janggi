package janggi.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.piece.Elephant;
import janggi.piece.Horse;
import janggi.piece.Piece;
import janggi.team.TeamName;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BoardSetupTest {
    @DisplayName("정상: 보드 설정에 따른 기물 모음을 생성")
    @Test
    void ofBoardSetup() {
        List<Piece> pieces = BoardSetup.of(List.of("한", "EHEH")).getPieces();

        assertThat(pieces).containsExactly(
                new Elephant(TeamName.HAN, new Position(1, 9)),
                new Horse(TeamName.HAN, new Position(2, 9)),
                new Elephant(TeamName.HAN, new Position(6, 9)),
                new Horse(TeamName.HAN, new Position(7, 9))
        );
    }

    @DisplayName("예외: 보드 설정을 찾을 수 없는 경우")
    @Test
    void validateBoardSetup() {

        assertThatThrownBy(() -> BoardSetup.of(List.of("초", "KGKG")))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
