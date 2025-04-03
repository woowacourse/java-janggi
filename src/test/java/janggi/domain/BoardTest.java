package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.piece.Cannon;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.General;
import janggi.domain.piece.Guard;
import janggi.domain.piece.Horse;
import janggi.domain.piece.None;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Position;
import janggi.domain.piece.Soldier;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {

    Map<Position, Piece> pieceMap;

    @BeforeEach
    void setUp() {
        pieceMap = new HashMap<>();
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 9; j++) {
                pieceMap.put(new Position(i, j), new None(new Position(i, j)));
            }
        }
    }

    @DisplayName("현재 기물 위치와 이동 시킬 위치를 받아 기물을 이동시킨다.")
    @Test
    void movePiece() {
        Board board = BoardFactory.getInitializedBoard(HorseSide.LEFT, HorseSide.LEFT,
            HorseSide.LEFT, HorseSide.LEFT);

        Position beforePosition = new Position(7, 1);
        Position afterPosition = new Position(6, 1);
        board.movePiece(beforePosition, afterPosition);
        Assertions.assertAll(
            () -> assertThat(board.getPieceByPosition(beforePosition)).isInstanceOf(None.class),
            () -> assertThat(board.getPieceByPosition(afterPosition)).isInstanceOf(Soldier.class)
        );
    }

    @DisplayName("차의 이동 위치에 같은 편 기물이 있으면 이동이 안된다")
    @Test
    void move1() {
        Position befoerPosition = new Position(5, 5);
        Chariot chariot = new Chariot(befoerPosition, Team.BLUE);
        Position positionToMove = new Position(4, 5);
        Soldier otherSoldier = new Soldier(positionToMove, Team.BLUE);
        pieceMap.put(chariot.getPosition(), chariot);
        pieceMap.put(otherSoldier.getPosition(), otherSoldier);
        Board board = new Board(pieceMap, Turn.First());
        board.movePiece(chariot.getPosition(), otherSoldier.getPosition());
        assertThat(board.getPieceByPosition(befoerPosition))
            .isInstanceOf(Chariot.class);
    }

    @DisplayName("궁의 이동 위치에 같은 편 기물이 있으면 이동할 수 없다")
    @Test
    void move2() {
        Position beforePosition = new Position(8, 5);
        General general = new General(beforePosition, Team.BLUE);
        Position positionToMove = new Position(9, 5);
        Soldier otherSoldier = new Soldier(positionToMove, Team.BLUE);
        pieceMap.put(general.getPosition(), general);
        pieceMap.put(otherSoldier.getPosition(), otherSoldier);
        Board board = new Board(pieceMap, Turn.First());
        board.movePiece(general.getPosition(), otherSoldier.getPosition());
        assertThat(board.getPieceByPosition(beforePosition))
            .isInstanceOf(General.class);
    }

    @DisplayName("사의 이동 위치에 같은 편 기물이 있으면 예외를 던진다")
    @Test
    void move3() {
        Position beforePosition = new Position(9, 5);
        Guard guard = new Guard(beforePosition, Team.BLUE);
        Position positionToMove = new Position(8, 5);
        Soldier otherSoldier = new Soldier(positionToMove, Team.BLUE);
        pieceMap.put(guard.getPosition(), guard);
        pieceMap.put(otherSoldier.getPosition(), otherSoldier);
        Board board = new Board(pieceMap, Turn.First());
        board.movePiece(beforePosition, positionToMove);
        assertThat(board.getPieceByPosition(beforePosition))
            .isInstanceOf(Guard.class);
    }

    @DisplayName("졸의 이동 위치에 같은 편 기물이 있으면 이동 할 수 없다")
    @Test
    void move4() {
        Position beforePosition = new Position(5, 5);
        Soldier soldier = new Soldier(beforePosition, Team.BLUE);
        Position positionToMove = new Position(4, 5);
        Soldier otherSoldier = new Soldier(positionToMove, Team.BLUE);
        pieceMap.put(soldier.getPosition(), soldier);
        pieceMap.put(otherSoldier.getPosition(), otherSoldier);
        Board board = new Board(pieceMap, Turn.First());
        board.movePiece(beforePosition, positionToMove);
        assertThat(board.getPieceByPosition(beforePosition))
            .isInstanceOf(Soldier.class);
    }

    @DisplayName("차를 잡으면 13점이 줄어든다")
    @Test
    void move5() {
        Piece chariot = new Chariot(new Position(5, 5), Team.RED);
        Piece soldier = new Soldier(new Position(6, 5), Team.BLUE);
        pieceMap.put(chariot.getPosition(), chariot);
        pieceMap.put(soldier.getPosition(), soldier);
        Board board = new Board(pieceMap, Turn.First());
        double beforeScore = board.getScore(Team.RED).getValue();
        board.movePiece(new Position(6, 5), new Position(5, 5));
        double afterScore = board.getScore(Team.RED).getValue();
        assertThat(beforeScore - 13).isEqualTo(afterScore);
    }

    @DisplayName("차를 잡으면 13점이 줄어든다")
    @Test
    void move6() {
        Piece chariot = new Chariot(new Position(5, 5), Team.RED);
        Piece soldier = new Soldier(new Position(6, 5), Team.BLUE);
        pieceMap.put(chariot.getPosition(), chariot);
        pieceMap.put(soldier.getPosition(), soldier);
        Board board = new Board(pieceMap, Turn.First());
        double beforeScore = board.getScore(Team.RED).getValue();
        board.movePiece(new Position(6, 5), new Position(5, 5));
        double afterScore = board.getScore(Team.RED).getValue();
        assertThat(beforeScore - 13).isEqualTo(afterScore);
    }

    @DisplayName("포를 잡으면 7점이 줄어든다")
    @Test
    void move7() {
        Piece cannon = new Cannon(new Position(5, 5), Team.RED);
        Piece soldier = new Soldier(new Position(6, 5), Team.BLUE);
        pieceMap.put(cannon.getPosition(), cannon);
        pieceMap.put(soldier.getPosition(), soldier);
        Board board = new Board(pieceMap, Turn.First());
        double beforeScore = board.getScore(Team.RED).getValue();
        board.movePiece(new Position(6, 5), new Position(5, 5));
        double afterScore = board.getScore(Team.RED).getValue();
        assertThat(beforeScore - 7).isEqualTo(afterScore);
    }

    @DisplayName("마를 잡으면 5점이 줄어든다")
    @Test
    void move8() {
        Piece horse = new Horse(new Position(5, 5), Team.RED);
        Piece soldier = new Soldier(new Position(6, 5), Team.BLUE);
        pieceMap.put(horse.getPosition(), horse);
        pieceMap.put(soldier.getPosition(), soldier);
        Board board = new Board(pieceMap, Turn.First());
        double beforeScore = board.getScore(Team.RED).getValue();
        board.movePiece(new Position(6, 5), new Position(5, 5));
        double afterScore = board.getScore(Team.RED).getValue();
        assertThat(beforeScore - 5).isEqualTo(afterScore);
    }

    @DisplayName("상을 잡으면 3점이 줄어든다")
    @Test
    void move9() {
        Piece elephant = new Elephant(new Position(5, 5), Team.RED);
        Piece soldier = new Soldier(new Position(6, 5), Team.BLUE);
        pieceMap.put(elephant.getPosition(), elephant);
        pieceMap.put(soldier.getPosition(), soldier);
        Board board = new Board(pieceMap, Turn.First());
        double beforeScore = board.getScore(Team.RED).getValue();
        board.movePiece(new Position(6, 5), new Position(5, 5));
        double afterScore = board.getScore(Team.RED).getValue();
        assertThat(beforeScore - 3).isEqualTo(afterScore);
    }

    @DisplayName("상을 잡으면 3점이 줄어든다")
    @Test
    void move10() {
        Piece elephant = new Elephant(new Position(5, 5), Team.RED);
        Piece soldier = new Soldier(new Position(6, 5), Team.BLUE);
        pieceMap.put(elephant.getPosition(), elephant);
        pieceMap.put(soldier.getPosition(), soldier);
        Board board = new Board(pieceMap, Turn.First());
        double beforeScore = board.getScore(Team.RED).getValue();
        board.movePiece(new Position(6, 5), new Position(5, 5));
        double afterScore = board.getScore(Team.RED).getValue();
        assertThat(beforeScore - 3).isEqualTo(afterScore);
    }

    @DisplayName("사를 잡으면 3점이 줄어든다")
    @Test
    void move11() {
        Piece guard = new Guard(new Position(2, 5), Team.RED);
        Piece soldier = new Soldier(new Position(3, 5), Team.BLUE);
        pieceMap.put(guard.getPosition(), guard);
        pieceMap.put(soldier.getPosition(), soldier);
        Board board = new Board(pieceMap, Turn.First());
        double beforeScore = board.getScore(Team.RED).getValue();
        board.movePiece(new Position(3, 5), new Position(2, 5));
        double afterScore = board.getScore(Team.RED).getValue();
        assertThat(beforeScore - 3).isEqualTo(afterScore);
    }

    @DisplayName("졸을 잡으면 2점이 줄어든다")
    @Test
    void move12() {
        Piece soldier = new Soldier(new Position(5, 5), Team.RED);
        Piece otherSoldier = new Soldier(new Position(6, 5), Team.BLUE);
        pieceMap.put(soldier.getPosition(), soldier);
        pieceMap.put(otherSoldier.getPosition(), otherSoldier);
        Board board = new Board(pieceMap, Turn.First());
        double beforeScore = board.getScore(Team.RED).getValue();
        board.movePiece(new Position(6, 5), new Position(5, 5));
        double afterScore = board.getScore(Team.RED).getValue();
        assertThat(beforeScore - 2).isEqualTo(afterScore);
    }

    @DisplayName("청 팀의 왕이 살아있으면 true를 반환한다")
    @Test
    void boardBlueKingAliveTest() {
        Piece general = new General(new Position(9, 5), Team.BLUE);
        pieceMap.put(general.getPosition(), general);
        Board board = new Board(pieceMap, Turn.First());
        assertThat(board.isKingAlive(Team.BLUE)).isTrue();
    }

    @DisplayName("청 팀의 왕이 죽었으면 false를 반환한다")
    @Test
    void boardBlueKingTest() {
        Board board = new Board(pieceMap, Turn.First());
        assertThat(board.isKingAlive(Team.BLUE)).isFalse();
    }

    @DisplayName("청 팀의 왕이 살아있으면 true를 반환한다")
    @Test
    void boardRedKingAliveTest() {
        Piece general = new General(new Position(9, 5), Team.RED);
        pieceMap.put(general.getPosition(), general);
        Board board = new Board(pieceMap, Turn.First());
        assertThat(board.isKingAlive(Team.RED)).isTrue();
    }

    @DisplayName("청 팀의 왕이 죽었으면 false를 반환한다")
    @Test
    void boardRedKingTest() {
        Board board = new Board(pieceMap, Turn.First());
        assertThat(board.isKingAlive(Team.RED)).isFalse();
    }

    @DisplayName("초기 점수가 청팀은 72점, 홍팀은 73.5점이다")
    @Test
    void scoreTest() {
        Board board = BoardFactory.getInitializedBoard(
            HorseSide.LEFT,
            HorseSide.LEFT,
            HorseSide.LEFT,
            HorseSide.LEFT
        );
        Assertions.assertAll(
            () -> assertThat(board.getScore(Team.BLUE).getValue()).isEqualTo(72),
            () -> assertThat(board.getScore(Team.RED).getValue()).isEqualTo(73.5)
        );
    }

    @Test
    @DisplayName("양 팀 왕이 살아있으면 예외를 던진다")
    void winnerNoneTest() {
        Piece redGeneral = new General(new Position(2, 5), Team.RED);
        Piece blueGeneral = new General(new Position(9, 5), Team.BLUE);

        pieceMap.put(redGeneral.getPosition(), redGeneral);
        pieceMap.put(blueGeneral.getPosition(), blueGeneral);
        Board board = new Board(pieceMap, Turn.First());

        assertThatThrownBy(
            () -> board.getWinner()
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("홍 팀 왕만 살아있으면 우승자는 RED 이다")
    void winnerRedTest() {
        Piece redGeneral = new General(new Position(2, 5), Team.RED);

        pieceMap.put(redGeneral.getPosition(), redGeneral);
        Board board = new Board(pieceMap, Turn.First());

        assertThat(board.getWinner()).isEqualTo(Team.RED);
    }

    @Test
    @DisplayName("청 팀 왕만 살아있으면 우승자는 RED 이다")
    void winnerBlueTest() {
        Piece blueGeneral = new General(new Position(2, 5), Team.BLUE);

        pieceMap.put(blueGeneral.getPosition(), blueGeneral);
        Board board = new Board(pieceMap, Turn.First());

        assertThat(board.getWinner()).isEqualTo(Team.BLUE);
    }

    @Test
    @DisplayName("양 팀 왕이 죽었다면 우승자는 NONE 이다")
    void winnerAllDeadTest() {
        Board board = new Board(pieceMap, Turn.First());

        assertThat(board.getWinner()).isEqualTo(Team.NONE);
    }
}
