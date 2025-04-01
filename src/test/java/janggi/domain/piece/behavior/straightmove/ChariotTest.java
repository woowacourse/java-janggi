package janggi.domain.piece.behavior.straightmove;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Board;
import janggi.domain.Team;
import janggi.domain.move.Position;
import janggi.domain.piece.BoardPositionInfo;
import janggi.domain.piece.Piece;
import janggi.domain.piece.behavior.Soldier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChariotTest {

    @DisplayName("차가 움직일 수 있는 포지션들을 반환한다.")
    @Test
    void test1() {
        // given
        Position position = Position.of(10, 1);
        Chariot chariot = new Chariot();
        Piece chariotPiece = new Piece(Team.HAN, chariot);

        Map<Position, Piece> map = Map.of(position, chariotPiece);

        // when
        Board board = new Board(new HashMap<>(map));
        BoardPositionInfo boardPositionInfo = new BoardPositionInfo(board, position, Team.HAN);
        Set<Position> actual = chariot.generateAvailableMovePositions(boardPositionInfo);

        // then
        assertThat(actual).hasSize(17)
                .contains(Position.of(1, 1), Position.of(10, 9));
    }

    @DisplayName("차 앞에 팀의 기물이 있다면 갈 수 없다.")
    @Test
    void test2() {
        // given
        Position position = Position.of(10, 1);
        Chariot chariot = new Chariot();
        Position soldierPosition = Position.of(9, 1);
        Soldier soldier = new Soldier();
        Piece chariotPiece = new Piece(Team.HAN, chariot);
        Piece soldierPiece = new Piece(Team.HAN, soldier);

        Map<Position, Piece> map = Map.of(position, chariotPiece, soldierPosition, soldierPiece);

        // when
        Board board = new Board(new HashMap<>(map));
        BoardPositionInfo boardPositionInfo = new BoardPositionInfo(board, position, Team.HAN);
        Set<Position> actual = chariot.generateAvailableMovePositions(boardPositionInfo);

        // then
        assertThat(actual).hasSize(8);
    }

    @DisplayName("차 앞에 상대의 기물이 있다면 해당 위치까지 갈 수 있다.")
    @Test
    void test3() {
        // given
        Position position = Position.of(10, 1);
        Chariot chariot = new Chariot();
        Position soldierPosition = Position.of(9, 1);
        Soldier soldier = new Soldier();
        Piece chariotPiece = new Piece(Team.HAN, chariot);
        Piece soldierPiece = new Piece(Team.CHO, soldier);

        Map<Position, Piece> map = Map.of(position, chariotPiece, soldierPosition, soldierPiece);

        // when
        Board board = new Board(new HashMap<>(map));
        BoardPositionInfo boardPositionInfo = new BoardPositionInfo(board, position, Team.HAN);
        Set<Position> actual = chariot.generateAvailableMovePositions(boardPositionInfo);

        // then
        assertThat(actual).hasSize(9);
    }

    @DisplayName("궁성 내에서의 대각선 움직임을 허용한다.")
    @Test
    void test6() {
        Position position = Position.of(10, 6);
        Chariot chariot = new Chariot();
        Piece piece = new Piece(Team.CHO, chariot);

        Board board = new Board(Map.of(position, piece));

        BoardPositionInfo boardPositionInfo = new BoardPositionInfo(board, position, Team.CHO);
        Set<Position> actual = chariot.generateAvailableMovePositions(boardPositionInfo);

        assertThat(actual).contains(Position.of(9, 5), Position.of(8, 4))
                .hasSize(19);
    }
}
