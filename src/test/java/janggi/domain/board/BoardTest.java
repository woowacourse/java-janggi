package janggi.domain.board;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

import janggi.domain.common.Position;
import janggi.domain.common.Team;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BoardTest {

    @Test
    @DisplayName("보드에 기물을 놓을 수 있다")
    void 보드에_기물_두기() {
        // given
        Board board = new Board();
        Position position = new Position(1, 3);
        Piece piece = new Piece(Team.CHO, PieceType.PO);

        // when
        board.place(position, piece);

        // then
        assertThat(board.hasPiece(position)).isTrue();
        assertThat(board.pieceAt(position)).isEqualTo(piece);
    }

    @Test
    @DisplayName("이동할 기물의 현재 좌표를 통해 목적지로 기물을 이동할 수 있다")
    void 목적지_좌표로_기물_이동() {
        // given
        Board board = new Board();
        Position movePiecePosition = new Position(1, 5);
        board.place(movePiecePosition, new Piece(Team.CHO, PieceType.CHA));
        Position destinationPosition = new Position(1, 3);

        // when
        board.movePiece(movePiecePosition, destinationPosition);

        // then
        assertThat(board.hasPiece(movePiecePosition)).isFalse();
        assertThat(board.hasPiece(destinationPosition)).isTrue();
    }

    @Test
    @DisplayName("빈 칸은 이동시킬 수 없다")
    void 빈_칸_이동_불가() {
        // given
        Board board = new Board();
        Position emptyPosition = new Position(1, 3);
        Position destinationPosition = new Position(1, 2);

        // when & then
        assertThatThrownBy(() -> board.movePiece(emptyPosition, destinationPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 빈 칸은 이동시킬 수 없습니다.");
    }

    @Test
    @DisplayName("좌표를 통해 기물을 알아낼 수 있다")
    void 좌표를_통해_기물_확인() {
        // given
        Board board = new Board();
        Position position = new Position(2, 5);
        Piece piece = new Piece(Team.HAN, PieceType.CHA);
        board.place(position, piece);

        // when
        Piece result = board.pieceAt(position);

        // then
        assertThat(result).isEqualTo(piece);
    }

    @Test
    @DisplayName("해당 좌표에 기물이 있는지 확인할 수 있다")
    void 좌표에_기물_있는지_판단() {
        // given
        Board board = new Board();
        Position position = new Position(1, 5);
        Position emptyPosition = new Position(2, 7);
        board.place(position, new Piece(Team.CHO, PieceType.PO));

        // when
        boolean result = board.hasPiece(position);
        boolean result2 = board.hasPiece(emptyPosition);

        // then
        assertThat(result).isTrue();
        assertThat(result2).isFalse();
    }

    @Test
    @DisplayName("초나라 졸은 경로에 장애물이 없으면 위쪽, 왼쪽, 오른쪽으로 이동할 수 있다")
    void 초나라_졸_장애물_없을때_이동_성공() {
        //given
        Board board = new Board();
        Position position = new Position(5, 7);
        board.place(position, new Piece(Team.CHO, PieceType.ZOL));
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
        Board board = new Board();
        Position position = new Position(5, 7);
        board.place(position, new Piece(Team.CHO, PieceType.ZOL));
        Position zolUp = new Position(5, 6);
        board.place(zolUp, new Piece(Team.CHO, PieceType.ZOL));
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
        Board board = new Board();
        Position position = new Position(4, 6);
        board.place(position, new Piece(Team.CHO, PieceType.MA));
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
        Board board = new Board();
        Position position = new Position(4, 6);
        board.place(position, new Piece(Team.CHO, PieceType.MA));
        board.place(new Position(4, 5), new Piece(Team.HAN, PieceType.CHA));
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
        Board board = new Board();
        Position position = new Position(4, 6);
        board.place(position, new Piece(Team.CHO, PieceType.MA));
        board.place(new Position(2, 7), new Piece(Team.CHO, PieceType.CHA));
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
        Board board = new Board();
        Position position = new Position(5, 7);
        board.place(position, new Piece(Team.CHO, PieceType.SANG));
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
        Board board = new Board();
        Position position = new Position(5, 7);
        board.place(position, new Piece(Team.CHO, PieceType.SANG));
        board.place(new Position(6, 5), new Piece(Team.CHO, PieceType.ZOL));
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
        Board board = new Board();
        Position position = new Position(5, 7);
        board.place(position, new Piece(Team.CHO, PieceType.SANG));
        board.place(new Position(5, 6), new Piece(Team.HAN, PieceType.ZOL));
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
        Board board = new Board();
        Position position = new Position(5, 7);
        board.place(position, new Piece(Team.CHO, PieceType.SANG));
        board.place(new Position(2, 9), new Piece(Team.CHO, PieceType.ZOL));
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
        Board board = new Board();
        Position position = new Position(4, 10);
        board.place(position, new Piece(Team.CHO, PieceType.SA));
        Position maPos1 = new Position(4, 9);
        Position maPos2 = new Position(5, 9);
        Position maPos3 = new Position(5, 10);
        Position maPos4 = new Position(3, 9);
        Position maPos5 = new Position(3, 10);
        List<Position> rightAnswer = List.of(maPos1, maPos2, maPos3, maPos4, maPos5);

        //when
        List<Position> maRoutesPositions = board.findAvailablePositions(position);

        //then
        assertThat(maRoutesPositions).hasSize(5)
                .containsExactlyInAnyOrderElementsOf(rightAnswer);
    }

    @Test
    @DisplayName("사의 목적지에 아군 기물이 있으면 이동할 수 없다 (사이클1 규칙)")
    void 사_목적지에_아군_존재시_이동_불가() {
        //given
        Board board = new Board();
        Position position = new Position(4, 10);
        board.place(position, new Piece(Team.CHO, PieceType.SA));
        board.place(new Position(5, 9), new Piece(Team.CHO, PieceType.KING));
        Position maPos1 = new Position(4, 9);
        Position maPos2 = new Position(5, 10);
        Position maPos3 = new Position(3, 9);
        Position maPos4 = new Position(3, 10);
        List<Position> rightAnswer = List.of(maPos1, maPos2, maPos3, maPos4);

        //when
        List<Position> maRoutesPositions = board.findAvailablePositions(position);

        //then
        assertThat(maRoutesPositions).hasSize(4)
                .containsExactlyInAnyOrderElementsOf(rightAnswer);
    }

    @Test
    @DisplayName("사의 목적지에 적군 기물이 있으면 이동할 수 있다 (사이클1 규칙)")
    void 사_목적지에_적군_존재시_이동_가능() {
        //given
        Board board = new Board();
        Position position = new Position(4, 10);
        board.place(position, new Piece(Team.CHO, PieceType.SA));
        board.place(new Position(5, 9), new Piece(Team.HAN, PieceType.CHA));
        Position maPos1 = new Position(4, 9);
        Position maPos2 = new Position(5, 10);
        Position maPos3 = new Position(3, 9);
        Position maPos4 = new Position(3, 10);
        Position maPos5 = new Position(5, 9);
        List<Position> rightAnswer = List.of(maPos1, maPos2, maPos3, maPos4, maPos5);

        //when
        List<Position> maRoutesPositions = board.findAvailablePositions(position);

        //then
        assertThat(maRoutesPositions).hasSize(5)
                .containsExactlyInAnyOrderElementsOf(rightAnswer);
    }

    @Test
    @DisplayName("왕은 이동경로에 장애물이 없는 곳으로 이동할 수 있다 (사이클1 규칙)")
    void 왕_장애물_없을때_이동_성공() {
        //given
        Board board = new Board();
        Position position = new Position(5, 9);
        board.place(position, new Piece(Team.CHO, PieceType.KING));
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
        Board board = new Board();
        Position position = new Position(5, 9);
        board.place(position, new Piece(Team.CHO, PieceType.KING));
        board.place(new Position(4, 9), new Piece(Team.CHO, PieceType.SA));
        board.place(new Position(4, 10), new Piece(Team.CHO, PieceType.SA));
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
        Board board = new Board();
        Position position = new Position(5, 9);
        board.place(position, new Piece(Team.CHO, PieceType.KING));
        board.place(new Position(4, 9), new Piece(Team.HAN, PieceType.ZOL));
        board.place(new Position(4, 10), new Piece(Team.HAN, PieceType.CHA));
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
        Board board = new Board();
        Position position = new Position(1, 10);
        board.place(position, new Piece(Team.CHO, PieceType.CHA));
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
        Board board = new Board();
        Position position = new Position(1, 10);
        board.place(position, new Piece(Team.CHO, PieceType.CHA));
        board.place(new Position(1, 7), new Piece(Team.CHO, PieceType.ZOL));
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
        Board board = new Board();
        Position position = new Position(1, 10);
        board.place(position, new Piece(Team.CHO, PieceType.CHA));
        board.place(new Position(1, 7), new Piece(Team.HAN, PieceType.CHA));
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
        Board board = new Board();
        Position position = new Position(5, 8);
        board.place(position, new Piece(Team.CHO, PieceType.PO));
        board.place(new Position(5, 6), new Piece(Team.HAN, PieceType.CHA));
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
    @DisplayName("포는 넘어가려는 목적지에 또 다른 포가 있으면, 포는 포를 포획할 수 없으므로 이동할 수 없다")
    void 포_목적지에_다른_포가_있으면_포획_및_이동_불가() {
        // given
        Board board = new Board();
        Position position = new Position(5, 8);
        board.place(position, new Piece(Team.CHO, PieceType.PO));
        board.place(new Position(5, 6), new Piece(Team.HAN, PieceType.CHA));
        Position destination = new Position(5, 5);
        board.place(destination, new Piece(Team.HAN, PieceType.PO));

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            board.validateDestination(position, destination);
        });
    }

    @Test
    @DisplayName("포는 포다리 너머에 일반 적군 기물이 존재하면 해당 기물을 포획하며 이동할 수 있다")
    void 포_목적지에_일반_적군_기물이_있을때_이동_성공() {
        //given
        Board board = new Board();
        Position position = new Position(5, 8);
        board.place(position, new Piece(Team.CHO, PieceType.PO));
        board.place(new Position(5, 6), new Piece(Team.HAN, PieceType.CHA));
        board.place(new Position(5, 5), new Piece(Team.HAN, PieceType.ZOL));
        List<Position> upRoutes = List.of(new Position(5, 5));

        List<Position> rightAnswer = new ArrayList<>(upRoutes);

        //when
        List<Position> chaRoutesPositions = board.findAvailablePositions(position);

        //then
        assertThat(chaRoutesPositions).hasSize(1)
                .containsExactlyInAnyOrderElementsOf(rightAnswer);
    }
}
