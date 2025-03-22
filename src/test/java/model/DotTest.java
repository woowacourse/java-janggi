package model;

import static org.assertj.core.api.Assertions.assertThat;

import model.janggiboard.Dot;
import model.piece.Jang;
import model.piece.Piece;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DotTest {
    @Test
    @DisplayName("교차점 위에 기물이 존재하는지 여부 반환")
    void test1() {
        Dot dot = new Dot();
        Piece jang = new Jang(Team.RED);
        dot.place(jang);

        assertThat(dot.isPlaced()).isTrue();
    }
}
