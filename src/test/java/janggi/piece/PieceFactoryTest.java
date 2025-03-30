package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PieceFactoryTest {
    @DisplayName("정상: 말 생성 확인 (왕)")
    @Test
    void createPieceKing() {
        Piece king = PieceFactory.createPiece("K", "초", "ALIVE", 4, 1);

        assertThat(king).isInstanceOf(King.class);
    }

    @DisplayName("정상: 말 생성 확인 (졸병)")
    @Test
    void createPieceSoldier() {
        Piece soldier = PieceFactory.createPiece("S", "한", "ALIVE", 0, 6);

        assertThat(soldier).isInstanceOf(Soldier.class);
    }

    @DisplayName("정상: 말 생성 확인 (포)")
    @Test
    void createPieceCannon() {
        Piece cannon = PieceFactory.createPiece("P", "초", "ALIVE", 1, 2);

        assertThat(cannon).isInstanceOf(Cannon.class);
    }

    @DisplayName("정상: 말 생성 확인 (차)")
    @Test
    void createPieceChariot() {
        Piece chariot = PieceFactory.createPiece("C", "한", "ALIVE", 1, 7);

        assertThat(chariot).isInstanceOf(Chariot.class);
    }

    @DisplayName("정상: 말 생성 확인 (상)")
    @Test
    void createPieceElephant() {
        Piece elephant = PieceFactory.createPiece("E", "초", "ALIVE", 1, 0);

        assertThat(elephant).isInstanceOf(Elephant.class);
    }

    @DisplayName("정상: 말 생성 확인 (마)")
    @Test
    void createPieceHorse() {
        Piece horse = PieceFactory.createPiece("H", "한", "ALIVE", 2, 9);

        assertThat(horse).isInstanceOf(Horse.class);
    }

    @DisplayName("정상: 말 생성 확인 (사)")
    @Test
    void createPieceGuard() {
        Piece guard = PieceFactory.createPiece("G", "초", "ALIVE", 3, 0);

        assertThat(guard).isInstanceOf(Guard.class);
    }
}
