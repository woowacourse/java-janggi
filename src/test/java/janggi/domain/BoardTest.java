package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.Board;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class BoardTest {

    @Test
    @DisplayName("초나라 졸은 경로에 장애물이 없으면 위쪽, 왼쪽, 오른쪽으로 이동할 수 있다")
    void 초나라_졸_장애물_없을때_이동_성공() {
        //given
        Map<Position, Piece> customBoard = new HashMap<>();
        Position position = new Position(5, 7);
        customBoard.put(position, new Piece(Team.CHO, PieceType.ZOL));
        Board board = new Board(customBoard);
        Position zolUp = new Position(5, 6);
        Position zolLeft = new Position(4, 7);
        Position zolRight = new Position(6, 7);
        List<Position> rightAnswer = List.of(zolUp, zolRight, zolLeft);

        //when
        List<Position> zolRoutesPositions = board.findAvailablePositions(position);

        //then
        assertThat(rightAnswer).isEqualTo(zolRoutesPositions);
    }

    @Test
    @DisplayName("초나라 졸의 이동 방향에 아군 기물이 있으면 해당 방향으로는 이동할 수 없다")
    void 초나라_졸_아군이_막고있을때_이동_불가() {
        //given
        Map<Position, Piece> customBoard = new HashMap<>();
        Position position = new Position(5, 7);
        Position zolUp = new Position(5, 6);
        customBoard.put(position, new Piece(Team.CHO, PieceType.ZOL));
        customBoard.put(zolUp, new Piece(Team.CHO, PieceType.ZOL));
        Board board = new Board(customBoard);
        Position zolLeft = new Position(4, 7);
        Position zolRight = new Position(6, 7);
        List<Position> rightAnswer = List.of(zolRight, zolLeft);

        //when
        List<Position> zolRoutesPositions = board.findAvailablePositions(position);

        //then
        assertThat(rightAnswer).isEqualTo(zolRoutesPositions);
    }

    @Test
    @DisplayName("마는 이동 경로(멱)에 장애물이 없으면 8방향 모두 이동할 수 있다")
    void 마_장애물_없을때_8방향_이동_성공() {
        //given
        Map<Position, Piece> customBoard = new HashMap<>();
        Position position = new Position(4, 6);
        customBoard.put(position, new Piece(Team.CHO, PieceType.MA));
        Board board = new Board(customBoard);
        Position maPos1 = new Position(3, 4);
        Position maPos2 = new Position(5, 4);
        Position maPos3 = new Position(6, 5);
        Position maPos4 = new Position(6, 7);
        Position maPos5 = new Position(3, 8);
        Position maPos6 = new Position(5, 8);
        Position maPos7 = new Position(2, 5);
        Position maPos8 = new Position(2, 7);
        List<Position> rightAnswer = List.of(maPos1, maPos2, maPos3, maPos4, maPos5, maPos6, maPos7, maPos8);

        //when
        List<Position> maRoutesPositions = board.findAvailablePositions(position);

        //then
        assertThat(maRoutesPositions).hasSize(8)
                .containsExactlyInAnyOrderElementsOf(rightAnswer);
    }

    @Test
    @DisplayName("마는 이동 경로(멱)에 다른 기물이 있으면 해당 방향으로 이동할 수 없다")
    void 마_멱이_막혀있을때_해당_방향_이동_불가() {
        //given
        Map<Position, Piece> customBoard = new HashMap<>();
        Position position = new Position(4, 6);
        customBoard.put(position, new Piece(Team.CHO, PieceType.MA));
        customBoard.put(new Position(4, 5), new Piece(Team.HAN, PieceType.CHA));
        Board board = new Board(customBoard);
        Position maPos1 = new Position(6, 5);
        Position maPos2 = new Position(6, 7);
        Position maPos3 = new Position(3, 8);
        Position maPos4 = new Position(5, 8);
        Position maPos5 = new Position(2, 5);
        Position maPos6 = new Position(2, 7);
        List<Position> rightAnswer = List.of(maPos1, maPos2, maPos3, maPos4, maPos5, maPos6);

        //when
        List<Position> maRoutesPositions = board.findAvailablePositions(position);

        //then
        assertThat(maRoutesPositions).hasSize(6)
                .containsExactlyInAnyOrderElementsOf(rightAnswer);
    }

    @Test
    @DisplayName("마의 최종 목적지에 아군 기물이 있으면 해당 좌표로 이동할 수 없다")
    void 마_목적지에_아군_존재시_이동_불가() {
        //given
        Map<Position, Piece> customBoard = new HashMap<>();
        Position position = new Position(4, 6);
        customBoard.put(position, new Piece(Team.CHO, PieceType.MA));
        customBoard.put(new Position(2, 7), new Piece(Team.CHO, PieceType.CHA));
        Board board = new Board(customBoard);
        Position maPos1 = new Position(3, 4);
        Position maPos2 = new Position(5, 4);
        Position maPos3 = new Position(6, 5);
        Position maPos4 = new Position(6, 7);
        Position maPos5 = new Position(3, 8);
        Position maPos6 = new Position(5, 8);
        Position maPos7 = new Position(2, 5);
        List<Position> rightAnswer = List.of(maPos1, maPos2, maPos3, maPos4, maPos5, maPos6, maPos7);

        //when
        List<Position> maRoutesPositions = board.findAvailablePositions(position);

        //then
        assertThat(maRoutesPositions).hasSize(7)
                .containsExactlyInAnyOrderElementsOf(rightAnswer);
    }

    @Test
    @DisplayName("상은 이동 경로(멱)에 장애물이 없으면 8방향 모두 이동할 수 있다")
    void 상_장애물_없을때_8방향_이동_성공() {
        //given
        Map<Position, Piece> customBoard = new HashMap<>();
        Position position = new Position(5, 7);
        customBoard.put(position, new Piece(Team.CHO, PieceType.SANG));
        Board board = new Board(customBoard);
        Position maPos1 = new Position(3, 4);
        Position maPos2 = new Position(7, 4);
        Position maPos3 = new Position(8, 5);
        Position maPos4 = new Position(8, 9);
        Position maPos5 = new Position(3, 10);
        Position maPos6 = new Position(7, 10);
        Position maPos7 = new Position(2, 5);
        Position maPos8 = new Position(2, 9);
        List<Position> rightAnswer = List.of(maPos1, maPos2, maPos3, maPos4, maPos5, maPos6, maPos7, maPos8);

        //when
        List<Position> sangRoutesPositions = board.findAvailablePositions(position);

        //then
        assertThat(sangRoutesPositions).hasSize(8)
                .containsExactlyInAnyOrderElementsOf(rightAnswer);
    }

    @Test
    @DisplayName("상은 대각선 경로(멱)에 다른 기물이 있으면 해당 방향으로 이동할 수 없다")
    void 상_대각선_멱이_막혀있을때_이동_불가() {
        //given
        Map<Position, Piece> customBoard = new HashMap<>();
        Position position = new Position(5, 7);
        customBoard.put(position, new Piece(Team.CHO, PieceType.SANG));
        customBoard.put(new Position(6, 5), new Piece(Team.CHO, PieceType.ZOL));
        Board board = new Board(customBoard);
        Position maPos1 = new Position(3, 4);
        Position maPos2 = new Position(8, 5);
        Position maPos3 = new Position(8, 9);
        Position maPos4 = new Position(3, 10);
        Position maPos5 = new Position(7, 10);
        Position maPos6 = new Position(2, 5);
        Position maPos7 = new Position(2, 9);
        List<Position> rightAnswer = List.of(maPos1, maPos2, maPos3, maPos4, maPos5, maPos6, maPos7);

        //when
        List<Position> sangRoutesPositions = board.findAvailablePositions(position);

        //then
        assertThat(sangRoutesPositions).hasSize(7)
                .containsExactlyInAnyOrderElementsOf(rightAnswer);
    }

    @Test
    @DisplayName("상은 직선 경로(멱)에 다른 기물이 있으면 해당 방향으로 이동할 수 없다")
    void 상_직선_멱이_막혀있을때_이동_불가() {
        //given
        Map<Position, Piece> customBoard = new HashMap<>();
        Position position = new Position(5, 7);
        customBoard.put(position, new Piece(Team.CHO, PieceType.SANG));
        customBoard.put(new Position(5, 6), new Piece(Team.HAN, PieceType.ZOL));
        Board board = new Board(customBoard);
        Position maPos1 = new Position(8, 5);
        Position maPos2 = new Position(8, 9);
        Position maPos3 = new Position(3, 10);
        Position maPos4 = new Position(7, 10);
        Position maPos5 = new Position(2, 5);
        Position maPos6 = new Position(2, 9);
        List<Position> rightAnswer = List.of(maPos1, maPos2, maPos3, maPos4, maPos5, maPos6);

        //when
        List<Position> sangRoutesPositions = board.findAvailablePositions(position);

        //then
        assertThat(sangRoutesPositions).hasSize(6)
                .containsExactlyInAnyOrderElementsOf(rightAnswer);
    }

    @Test
    @DisplayName("상의 최종 목적지에 아군 기물이 있으면 해당 좌표로 이동할 수 없다")
    void 상_목적지에_아군_존재시_이동_불가() {
        //given
        Map<Position, Piece> customBoard = new HashMap<>();
        Position position = new Position(5, 7);
        customBoard.put(position, new Piece(Team.CHO, PieceType.SANG));
        customBoard.put(new Position(2, 9), new Piece(Team.CHO, PieceType.ZOL));
        Board board = new Board(customBoard);
        Position maPos1 = new Position(3, 4);
        Position maPos2 = new Position(7, 4);
        Position maPos3 = new Position(8, 5);
        Position maPos4 = new Position(8, 9);
        Position maPos5 = new Position(3, 10);
        Position maPos6 = new Position(7, 10);
        Position maPos7 = new Position(2, 5);
        List<Position> rightAnswer = List.of(maPos1, maPos2, maPos3, maPos4, maPos5, maPos6, maPos7);

        //when
        List<Position> sangRoutesPositions = board.findAvailablePositions(position);

        //then
        assertThat(sangRoutesPositions).hasSize(7)
                .containsExactlyInAnyOrderElementsOf(rightAnswer);
    }

    @Test
    @DisplayName("사는 이동경로에 장애물이 없는 곳으로 이동할 수 있다 (사이클1 규칙)")
    void 사_장애물_없을때_이동_성공() {
        //given
        Map<Position, Piece> customBoard = new HashMap<>();
        Position position = new Position(4, 10);
        customBoard.put(position, new Piece(Team.CHO, PieceType.SA));
        Board board = new Board(customBoard);
        Position maPos1 = new Position(4, 9);
        Position maPos2 = new Position(5, 9);
        Position maPos3 = new Position(5, 10);
        List<Position> rightAnswer = List.of(maPos1, maPos2, maPos3);

        //when
        List<Position> maRoutesPositions = board.findAvailablePositions(position);

        //then
        assertThat(maRoutesPositions).hasSize(3)
                .containsExactlyInAnyOrderElementsOf(rightAnswer);
    }

    @Test
    @DisplayName("사의 목적지에 아군 기물이 있으면 이동할 수 없다 (사이클1 규칙)")
    void 사_목적지에_아군_존재시_이동_불가() {
        //given
        Map<Position, Piece> customBoard = new HashMap<>();
        Position position = new Position(4, 10);
        customBoard.put(position, new Piece(Team.CHO, PieceType.SA));
        customBoard.put(new Position(5, 9), new Piece(Team.CHO, PieceType.KING));
        Board board = new Board(customBoard);
        Position maPos1 = new Position(4, 9);
        Position maPos2 = new Position(5, 10);
        List<Position> rightAnswer = List.of(maPos1, maPos2);

        //when
        List<Position> maRoutesPositions = board.findAvailablePositions(position);

        //then
        assertThat(maRoutesPositions).hasSize(2)
                .containsExactlyInAnyOrderElementsOf(rightAnswer);
    }

    @Test
    @DisplayName("사의 목적지에 적군 기물이 있으면 이동할 수 있다")
    void 사_목적지에_적군_존재시_이동_가능() {
        //given
        Map<Position, Piece> customBoard = new HashMap<>();
        Position position = new Position(4, 10);
        customBoard.put(position, new Piece(Team.CHO, PieceType.SA));
        customBoard.put(new Position(5, 9), new Piece(Team.HAN, PieceType.CHA));
        Board board = new Board(customBoard);
        Position maPos1 = new Position(4, 9);
        Position maPos2 = new Position(5, 10);
        Position maPos5 = new Position(5, 9);
        List<Position> rightAnswer = List.of(maPos1, maPos2, maPos5);

        //when
        List<Position> maRoutesPositions = board.findAvailablePositions(position);

        //then
        assertThat(maRoutesPositions).hasSize(3)
                .containsExactlyInAnyOrderElementsOf(rightAnswer);
    }

    @Test
    @DisplayName("왕은 이동경로에 장애물이 없는 곳으로 이동할 수 있다 (사이클1 규칙)")
    void 왕_장애물_없을때_이동_성공() {
        //given
        Map<Position, Piece> customBoard = new HashMap<>();
        Position position = new Position(5, 9);
        customBoard.put(position, new Piece(Team.CHO, PieceType.KING));
        Board board = new Board(customBoard);
        Position maPos1 = new Position(4, 8);
        Position maPos2 = new Position(5, 8);
        Position maPos3 = new Position(6, 8);
        Position maPos4 = new Position(6, 9);
        Position maPos5 = new Position(6, 10);
        Position maPos6 = new Position(5, 10);
        Position maPos7 = new Position(4, 10);
        Position maPos8 = new Position(4, 9);
        List<Position> rightAnswer = List.of(maPos1, maPos2, maPos3, maPos4, maPos5, maPos6, maPos7, maPos8);

        //when
        List<Position> maRoutesPositions = board.findAvailablePositions(position);

        //then
        assertThat(maRoutesPositions).hasSize(8)
                .containsExactlyInAnyOrderElementsOf(rightAnswer);
    }

    @Test
    @DisplayName("왕의 이동하려는 목적지에 아군 기물이 있으면 이동할 수 없다(사이클1 규칙)")
    void 왕_목적지에_아군_존재시_이동_불가() {
        //given
        Map<Position, Piece> customBoard = new HashMap<>();
        Position position = new Position(5, 9);
        customBoard.put(position, new Piece(Team.CHO, PieceType.KING));
        customBoard.put(new Position(4, 9), new Piece(Team.CHO, PieceType.SA));
        customBoard.put(new Position(4, 10), new Piece(Team.CHO, PieceType.SA));
        Board board = new Board(customBoard);
        Position maPos1 = new Position(4, 8);
        Position maPos2 = new Position(5, 8);
        Position maPos3 = new Position(6, 8);
        Position maPos4 = new Position(6, 9);
        Position maPos5 = new Position(6, 10);
        Position maPos6 = new Position(5, 10);
        List<Position> rightAnswer = List.of(maPos1, maPos2, maPos3, maPos4, maPos5, maPos6);

        //when
        List<Position> maRoutesPositions = board.findAvailablePositions(position);

        //then
        assertThat(maRoutesPositions).hasSize(6)
                .containsExactlyInAnyOrderElementsOf(rightAnswer);
    }

    @Test
    @DisplayName("왕의 목적지에 적군 기물이 있으면 이동할 수 있다 (사이클1 규칙)")
    void 왕_목적있지에_적군_존재시_이동_가능() {
        //given
        Map<Position, Piece> customBoard = new HashMap<>();
        Position position = new Position(5, 9);
        customBoard.put(position, new Piece(Team.CHO, PieceType.KING));
        customBoard.put(new Position(4, 9), new Piece(Team.HAN, PieceType.ZOL));
        customBoard.put(new Position(4, 10), new Piece(Team.HAN, PieceType.CHA));
        Board board = new Board(customBoard);
        Position maPos1 = new Position(4, 8);
        Position maPos2 = new Position(5, 8);
        Position maPos3 = new Position(6, 8);
        Position maPos4 = new Position(6, 9);
        Position maPos5 = new Position(6, 10);
        Position maPos6 = new Position(5, 10);
        Position maPos7 = new Position(4, 10);
        Position maPos8 = new Position(4, 9);
        List<Position> rightAnswer = List.of(maPos1, maPos2, maPos3, maPos4, maPos5, maPos6, maPos7, maPos8);

        //when
        List<Position> maRoutesPositions = board.findAvailablePositions(position);

        //then
        assertThat(maRoutesPositions).hasSize(8)
                .containsExactlyInAnyOrderElementsOf(rightAnswer);
    }

    @Test
    @DisplayName("차는 직선 경로상에 장애물이 없으면 끝까지 이동할 수 있다")
    void 차_장애물_없을때_직선_끝까지_이동_성공() {
        //given
        Map<Position, Piece> customBoard = new HashMap<>();
        Position position = new Position(1, 10);
        customBoard.put(position, new Piece(Team.CHO, PieceType.CHA));
        Board board = new Board(customBoard);
        List<Position> upRoutes = List.of(
                new Position(1, 9), new Position(1, 8), new Position(1, 7),
                new Position(1, 6), new Position(1, 5), new Position(1, 4),
                new Position(1, 3), new Position(1, 2), new Position(1, 1)
        );

        List<Position> rightRoutes = List.of(
                new Position(2, 10), new Position(3, 10), new Position(4, 10),
                new Position(5, 10), new Position(6, 10), new Position(7, 10),
                new Position(8, 10), new Position(9, 10)
        );
        List<Position> rightAnswer = new ArrayList<>();
        rightAnswer.addAll(upRoutes);
        rightAnswer.addAll(rightRoutes);

        //when
        List<Position> chaRoutesPositions = board.findAvailablePositions(position);

        //then
        assertThat(chaRoutesPositions).hasSize(17)
                .containsExactlyInAnyOrderElementsOf(rightAnswer);
    }

    @Test
    @DisplayName("차는 직선 경로상에 아군 기물이 있으면 아군 기물 직전까지만 이동할 수 있다")
    void 차_경로에_아군_존재시_아군_직전까지_이동_가능() {
        //given
        Map<Position, Piece> customBoard = new HashMap<>();
        Position position = new Position(1, 10);
        customBoard.put(position, new Piece(Team.CHO, PieceType.CHA));
        customBoard.put(new Position(1, 7), new Piece(Team.CHO, PieceType.ZOL));
        Board board = new Board(customBoard);
        List<Position> upRoutes = List.of(
                new Position(1, 9), new Position(1, 8)
        );

        List<Position> rightRoutes = List.of(
                new Position(2, 10), new Position(3, 10), new Position(4, 10),
                new Position(5, 10), new Position(6, 10), new Position(7, 10),
                new Position(8, 10), new Position(9, 10)
        );
        List<Position> rightAnswer = new ArrayList<>();
        rightAnswer.addAll(upRoutes);
        rightAnswer.addAll(rightRoutes);

        //when
        List<Position> chaRoutesPositions = board.findAvailablePositions(position);

        //then
        assertThat(chaRoutesPositions).hasSize(10)
                .containsExactlyInAnyOrderElementsOf(rightAnswer);
    }

    @Test
    @DisplayName("차는 직선 경로상에 적군 기물이 있으면 포획할 수 있는 적군 기물 위치까지만 이동할 수 있다")
    void 차_경로에_적군_존재시_적군_위치까지_이동_가능() {
        //given
        Map<Position, Piece> customBoard = new HashMap<>();
        Position position = new Position(1, 10);
        customBoard.put(position, new Piece(Team.CHO, PieceType.CHA));
        customBoard.put(new Position(1, 7), new Piece(Team.HAN, PieceType.CHA));
        Board board = new Board(customBoard);
        List<Position> upRoutes = List.of(
                new Position(1, 9), new Position(1, 8), new Position(1, 7)
        );

        List<Position> rightRoutes = List.of(
                new Position(2, 10), new Position(3, 10), new Position(4, 10),
                new Position(5, 10), new Position(6, 10), new Position(7, 10),
                new Position(8, 10), new Position(9, 10)
        );
        List<Position> rightAnswer = new ArrayList<>();
        rightAnswer.addAll(upRoutes);
        rightAnswer.addAll(rightRoutes);

        //when
        List<Position> chaRoutesPositions = board.findAvailablePositions(position);

        //then
        assertThat(chaRoutesPositions).hasSize(11)
                .containsExactlyInAnyOrderElementsOf(rightAnswer);
    }

    @Test
    @DisplayName("포는 이동 경로상에 일반 기물(포다리)이 딱 1개 존재하면 그 너머로 이동할 수 있다")
    void 포_경로에_일반_기물_포다리가_1개_있을때_이동_성공() {
        //given
        Map<Position, Piece> customBoard = new HashMap<>();
        Position position = new Position(5, 8);
        customBoard.put(position, new Piece(Team.CHO, PieceType.PO));
        customBoard.put(new Position(5, 6), new Piece(Team.HAN, PieceType.CHA));
        Board board = new Board(customBoard);
        List<Position> upRoutes = List.of(
                new Position(5, 5), new Position(5, 4), new Position(5, 3),
                new Position(5, 2), new Position(5, 1)
        );

        List<Position> rightAnswer = new ArrayList<>(upRoutes);

        //when
        List<Position> chaRoutesPositions = board.findAvailablePositions(position);

        //then
        assertThat(chaRoutesPositions).hasSize(5)
                .containsExactlyInAnyOrderElementsOf(rightAnswer);
    }

    @Test
    @DisplayName("보드에 왕이 없으면 IsKingDead가 true를 반환한다")
    void 보드에_왕이_없을_때() {
        //given
        Map<Position, Piece> state = new HashMap<>();
        state.put(new Position(1, 1), new Piece(Team.CHO, PieceType.ZOL));
        Board customBoard = new Board(state);

        //when
        boolean isKingDead = customBoard.isKingDead(Team.CHO);

        //then
        assertThat(isKingDead).isTrue();
    }

    @Test
    @DisplayName("보드에 초나라 차 1개와 상2개 있으면 19.0점을 반환한다")
    void 보드에_차1개와_상2개_점수_반환() {
        //given
        Map<Position, Piece> state = new HashMap<>();
        Team choTeam = Team.CHO;
        state.put(new Position(1, 10), new Piece(choTeam, PieceType.CHA));
        state.put(new Position(2, 10), new Piece(choTeam, PieceType.SANG));
        state.put(new Position(3, 10), new Piece(choTeam, PieceType.SANG));
        state.put(new Position(1, 1), new Piece(Team.HAN, PieceType.CHA));
        Board customBoard = new Board(state);

        //when
        double totalScore = customBoard.calculateScore(choTeam);

        //then
        assertThat(totalScore).isEqualTo(19.0);
    }
}
