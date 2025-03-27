package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.piece.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class BoardTest {
    Map<Position, Piece> pieceMap;

    @BeforeEach
    void setUp() {
        pieceMap = new HashMap<>();
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 9; j++) {
                pieceMap.put(new Position(i, j), new None());
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
        Board board = new Board(pieceMap);
        board.movePiece(chariot.getPosition(), otherSoldier.getPosition());
        assertThat(board.getPieceByPosition(befoerPosition))
                .isInstanceOf(Chariot.class);
    }

    @DisplayName("궁의 이동 위치에 같은 편 기물이 있으면 이동할 수 없다")
    @Test
    void move2() {
        Position beforePosition = new Position(5, 5);
        General general = new General(beforePosition, Team.BLUE);
        Position positionToMove = new Position(4, 5);
        Soldier otherSoldier = new Soldier(positionToMove, Team.BLUE);
        pieceMap.put(general.getPosition(), general);
        pieceMap.put(otherSoldier.getPosition(), otherSoldier);
        Board board = new Board(pieceMap);
        board.movePiece(general.getPosition(), otherSoldier.getPosition());
        assertThat(board.getPieceByPosition(beforePosition))
                .isInstanceOf(General.class);
    }

    @DisplayName("사의 이동 위치에 같은 편 기물이 있으면 예외를 던진다")
    @Test
    void move3() {
        Position beforePosition = new Position(5, 5);
        Guard guard = new Guard(beforePosition, Team.BLUE);
        Position positionToMove = new Position(4, 5);
        Soldier otherSoldier = new Soldier(positionToMove, Team.BLUE);
        pieceMap.put(guard.getPosition(), guard);
        pieceMap.put(otherSoldier.getPosition(), otherSoldier);
        Board board = new Board(pieceMap);
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
        Board board = new Board(pieceMap);
        board.movePiece(beforePosition, positionToMove);
        assertThat(board.getPieceByPosition(beforePosition))
                .isInstanceOf(Soldier.class);
    }
}
