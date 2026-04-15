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
import java.util.Map;
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
    @DisplayName("기물이 하나도 없는 상태에서 한나라는 1.5점, 초나라는 0점이다.")
    void 기물_없을_때_각_나라_점수() {
        // given
        Board board = new Board();

        // when
        Map<Team, Double> teamScores = board.calculateScore();

        // then
        assertThat(teamScores.get(Team.HAN)).isEqualTo(1.5);
        assertThat(teamScores.get(Team.CHO)).isEqualTo(0.0);
    }

    @Test
    @DisplayName("모든 기물이 다 있는 초기 상태에서 한나라는 73.5점, 초나라는 72점으로 시작한다.")
    void 초기_상태_각_나라_점수() {
        // given
        Board board = new Board();
        BoardInitiator boardInitiator = new BoardInitiator();
        boardInitiator.initializeByFormation(board, BoardFormation.MA_SANG_MA_SANG, Team.CHO);
        boardInitiator.initializeByFormation(board, BoardFormation.MA_SANG_MA_SANG, Team.HAN);

        // when
        Map<Team, Double> teamScores = board.calculateScore();

        // then
        assertThat(teamScores.get(Team.HAN)).isEqualTo(73.5);
        assertThat(teamScores.get(Team.CHO)).isEqualTo(72.0);
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
    @DisplayName("한나라 졸은 궁성 내에서도 진행방향쪽의 대각선만 가능")
    void 한나라_졸_궁성에서_대각선_이동() {
        //given
        Board board = new Board();
        Position position = new Position(4, 8);
        board.place(position, new Piece(Team.HAN, PieceType.ZOL));
        Position zolDown = new Position(4, 9);
        Position zolLeft = new Position(5, 8);
        Position zolRight = new Position(3, 8);
        Position zolDownLeft = new Position(5, 9);

        //when
        List<Position> zolRoutesPositions = board.findAvailablePositions(position);

        //then
        assertThat(zolRoutesPositions).containsExactlyInAnyOrder(zolDown, zolLeft, zolRight, zolDownLeft);
    }


    @Test
    @DisplayName("초나라 졸은 궁성 내에서도 진행방향쪽의 대각선만 가능")
    void 초나라_졸_궁성에서_대각선_이동() {
        //given
        Board board = new Board();
        Position position = new Position(5, 2);
        board.place(position, new Piece(Team.CHO, PieceType.ZOL));
        Position zolUp = new Position(5, 1);
        Position zolLeft = new Position(4, 2);
        Position zolRight = new Position(6, 2);
        Position zolUpLeft = new Position(4, 1);
        Position zolUpRight = new Position(6, 1);

        //when
        List<Position> zolRoutesPositions = board.findAvailablePositions(position);

        //then
        assertThat(zolRoutesPositions).containsExactlyInAnyOrder(zolUp, zolLeft, zolRight, zolUpLeft, zolUpRight);
    }

    @Test
    @DisplayName("마는 이동 경로(멱)에 장애물이 없으면 8방향 모두 이동할 수 있다")
    void 마_장애물_없을때_8방향_이동_성공() {
        //given
        Board board = new Board();
        Position position = new Position(4, 6);
        board.place(position, new Piece(Team.CHO, PieceType.MA));
        Position maPosition1 = new Position(3, 4);
        Position maPosition2 = new Position(5, 4);
        Position maPosition3 = new Position(6, 5);
        Position maPosition4 = new Position(6, 7);
        Position maPosition5 = new Position(3, 8);
        Position maPosition6 = new Position(5, 8);
        Position maPosition7 = new Position(2, 5);
        Position maPosition8 = new Position(2, 7);
        List<Position> rightAnswer = List.of(maPosition1, maPosition2, maPosition3, maPosition4,
                maPosition5, maPosition6, maPosition7, maPosition8);

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
        Position maPosition1 = new Position(6, 5);
        Position maPosition2 = new Position(6, 7);
        Position maPosition3 = new Position(3, 8);
        Position maPosition4 = new Position(5, 8);
        Position maPosition5 = new Position(2, 5);
        Position maPosition6 = new Position(2, 7);
        List<Position> rightAnswer = List.of(maPosition1, maPosition2, maPosition3,
                maPosition4, maPosition5, maPosition6);

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
        Position maPosition1 = new Position(3, 4);
        Position maPosition2 = new Position(5, 4);
        Position maPosition3 = new Position(6, 5);
        Position maPosition4 = new Position(6, 7);
        Position maPosition5 = new Position(3, 8);
        Position maPosition6 = new Position(5, 8);
        Position maPosition7 = new Position(2, 5);
        List<Position> rightAnswer = List.of(maPosition1, maPosition2, maPosition3, maPosition4,
                maPosition5, maPosition6, maPosition7);

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
        Position sangPosition1 = new Position(3, 4);
        Position sangPosition2 = new Position(7, 4);
        Position sangPosition3 = new Position(8, 5);
        Position sangPosition4 = new Position(8, 9);
        Position sangPosition5 = new Position(3, 10);
        Position sangPosition6 = new Position(7, 10);
        Position sangPosition7 = new Position(2, 5);
        Position sangPosition8 = new Position(2, 9);
        List<Position> rightAnswer = List.of(sangPosition1, sangPosition2, sangPosition3, sangPosition4,
                sangPosition5, sangPosition6, sangPosition7, sangPosition8);

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
        Position sangPosition1 = new Position(3, 4);
        Position sangPosition2 = new Position(8, 5);
        Position sangPosition3 = new Position(8, 9);
        Position sangPosition4 = new Position(3, 10);
        Position sangPosition5 = new Position(7, 10);
        Position sangPosition6 = new Position(2, 5);
        Position sangPosition7 = new Position(2, 9);
        List<Position> rightAnswer = List.of(sangPosition1, sangPosition2, sangPosition3, sangPosition4,
                sangPosition5, sangPosition6, sangPosition7);

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
        Position sangPosition1 = new Position(8, 5);
        Position sangPosition2 = new Position(8, 9);
        Position sangPosition3 = new Position(3, 10);
        Position sangPosition4 = new Position(7, 10);
        Position sangPosition5 = new Position(2, 5);
        Position sangPosition6 = new Position(2, 9);
        List<Position> rightAnswer = List.of(sangPosition1, sangPosition2, sangPosition3,
                sangPosition4, sangPosition5, sangPosition6);

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
        Position sangPosition1 = new Position(3, 4);
        Position sangPosition2 = new Position(7, 4);
        Position sangPosition3 = new Position(8, 5);
        Position sangPosition4 = new Position(8, 9);
        Position sangPosition5 = new Position(3, 10);
        Position sangPosition6 = new Position(7, 10);
        Position sangPosition7 = new Position(2, 5);
        List<Position> rightAnswer = List.of(sangPosition1, sangPosition2, sangPosition3, sangPosition4,
                sangPosition5, sangPosition6, sangPosition7);

        //when
        List<Position> sangRoutesPositions = board.findAvailablePositions(position);

        //then
        assertThat(sangRoutesPositions).hasSize(7)
                .containsExactlyInAnyOrderElementsOf(rightAnswer);
    }

    @Test
    @DisplayName("사는 궁성 안에서만 움직일 수 있고 대각선으로도 이동할 수 있다")
    void 사_궁성_안에서_이동_성공() {
        //given
        Board board = new Board();
        Position position = new Position(4, 10);
        board.place(position, new Piece(Team.CHO, PieceType.SA));
        Position saPosition1 = new Position(4, 9);
        Position saPosition2 = new Position(5, 9);
        Position saPosition3 = new Position(5, 10);
        List<Position> rightAnswer = List.of(saPosition1, saPosition2, saPosition3);

        //when
        List<Position> saRoutesPositions = board.findAvailablePositions(position);

        //then
        assertThat(saRoutesPositions).hasSize(3)
                .containsExactlyInAnyOrderElementsOf(rightAnswer);
    }

    @Test
    @DisplayName("사의 목적지에 아군 기물이 있으면 이동할 수 없다")
    void 사_목적지에_아군_존재시_이동_불가() {
        //given
        Board board = new Board();
        Position position = new Position(4, 10);
        board.place(position, new Piece(Team.CHO, PieceType.SA));
        board.place(new Position(5, 9), new Piece(Team.CHO, PieceType.KING));
        Position saPosition1 = new Position(4, 9);
        Position saPosition2 = new Position(5, 10);
        List<Position> rightAnswer = List.of(saPosition1, saPosition2);

        //when
        List<Position> saRoutesPositions = board.findAvailablePositions(position);

        //then
        assertThat(saRoutesPositions).hasSize(2)
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
        Position saPosition1 = new Position(4, 9);
        Position saPosition2 = new Position(5, 10);
        Position saPosition3 = new Position(5, 9);
        List<Position> rightAnswer = List.of(saPosition1, saPosition2, saPosition3);

        //when
        List<Position> saRoutesPositions = board.findAvailablePositions(position);

        //then
        assertThat(saRoutesPositions).hasSize(3)
                .containsExactlyInAnyOrderElementsOf(rightAnswer);
    }

    @Test
    @DisplayName("왕은 궁성 안에서만 움직일 수 있고 대각선으로도 이동할 수 있다")
    void 왕_궁성_안에서_이동_성공() {
        //given
        Board board = new Board();
        Position position = new Position(5, 9);
        board.place(position, new Piece(Team.CHO, PieceType.KING));
        Position kingPosition1 = new Position(4, 8);
        Position kingPosition2 = new Position(5, 8);
        Position kingPosition3 = new Position(6, 8);
        Position kingPosition4 = new Position(6, 9);
        Position kingPosition5 = new Position(6, 10);
        Position kingPosition6 = new Position(5, 10);
        Position kingPosition7 = new Position(4, 10);
        Position kingPosition8 = new Position(4, 9);
        List<Position> rightAnswer = List.of(kingPosition1, kingPosition2, kingPosition3, kingPosition4,
                kingPosition5, kingPosition6, kingPosition7, kingPosition8);

        //when
        List<Position> kingRoutesPositions = board.findAvailablePositions(position);

        //then
        assertThat(kingRoutesPositions).hasSize(8)
                .containsExactlyInAnyOrderElementsOf(rightAnswer);
    }

    @Test
    @DisplayName("왕의 이동하려는 목적지에 아군 기물이 있으면 이동할 수 없다")
    void 왕_목적지에_아군_존재시_이동_불가() {
        //given
        Board board = new Board();
        Position position = new Position(5, 9);
        board.place(position, new Piece(Team.CHO, PieceType.KING));
        board.place(new Position(4, 9), new Piece(Team.CHO, PieceType.SA));
        board.place(new Position(4, 10), new Piece(Team.CHO, PieceType.SA));
        Position kingPosition1 = new Position(4, 8);
        Position kingPosition2 = new Position(5, 8);
        Position kingPosition3 = new Position(6, 8);
        Position kingPosition4 = new Position(6, 9);
        Position kingPosition5 = new Position(6, 10);
        Position kingPosition6 = new Position(5, 10);
        List<Position> rightAnswer = List.of(kingPosition1, kingPosition2, kingPosition3,
                kingPosition4, kingPosition5, kingPosition6);

        //when
        List<Position> kingRoutesPositions = board.findAvailablePositions(position);

        //then
        assertThat(kingRoutesPositions).hasSize(6)
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
        Position kingPosition1 = new Position(4, 8);
        Position kingPosition2 = new Position(5, 8);
        Position kingPosition3 = new Position(6, 8);
        Position kingPosition4 = new Position(6, 9);
        Position kingPosition5 = new Position(6, 10);
        Position kingPosition6 = new Position(5, 10);
        Position kingPosition7 = new Position(4, 10);
        Position kingPosition8 = new Position(4, 9);
        List<Position> rightAnswer = List.of(kingPosition1, kingPosition2, kingPosition3, kingPosition4,
                kingPosition5, kingPosition6, kingPosition7, kingPosition8);

        //when
        List<Position> kingRoutesPositions = board.findAvailablePositions(position);

        //then
        assertThat(kingRoutesPositions).hasSize(8)
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
    @DisplayName("차는 궁성 안에서 대각선 경로상에 장애물이 없으면 끝까지 이동할 수 있다")
    void 차_궁성_안에_대각선_경로에_장애물_없을때_직선_끝까지_이동_성공() {
        //given
        Board board = new Board();
        Position position = new Position(4, 10);
        Position blockPosition1 = new Position(3, 10);
        Position blockPosition2 = new Position(4, 9);
        Position blockPosition3 = new Position(5, 10);
        board.place(position, new Piece(Team.HAN, PieceType.CHA));
        board.place(blockPosition1, new Piece(Team.HAN, PieceType.MA));
        board.place(blockPosition2, new Piece(Team.HAN, PieceType.PO));
        board.place(blockPosition3, new Piece(Team.HAN, PieceType.ZOL));
        List<Position> diagonalRoutes = List.of(
                new Position(5, 9), new Position(6, 8)
        );

        List<Position> rightAnswer = new ArrayList<>();
        rightAnswer.addAll(diagonalRoutes);

        //when
        List<Position> chaRoutesPositions = board.findAvailablePositions(position);

        //then
        assertThat(chaRoutesPositions).hasSize(2)
                .containsExactlyInAnyOrderElementsOf(rightAnswer);
    }

    @Test
    @DisplayName("차는 궁성 중앙에서 대각선_경로에_장애물이 없으면 대각선 방향으로 4가지 경로로 이동할 수 있다")
    void 차_궁성_중앙에서_대각선_경로에_장애물_없을때_대각선_방향_4가지_경로_이동_성공() {
        //given
        Board board = new Board();
        Position position = new Position(5, 9);
        board.place(position, new Piece(Team.HAN, PieceType.CHA));
        Position chaPosition1 = new Position(4, 8);
        Position chaPosition2 = new Position(6, 8);
        Position chaPosition3 = new Position(4, 10);
        Position chaPosition4 = new Position(6, 10);
        board.place(new Position(5, 8), new Piece(Team.HAN, PieceType.CHA));
        board.place(new Position(4, 9), new Piece(Team.HAN, PieceType.MA));
        board.place(new Position(5, 10), new Piece(Team.HAN, PieceType.SANG));
        board.place(new Position(6, 9), new Piece(Team.HAN, PieceType.PO));
        List<Position> rightAnswer = List.of(chaPosition1, chaPosition2, chaPosition3, chaPosition4);

        //when
        List<Position> chaRoutesPositions = board.findAvailablePositions(position);

        //then
        assertThat(chaRoutesPositions).hasSize(4)
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
        List<Position> poRoutesPositions = board.findAvailablePositions(position);

        //then
        assertThat(poRoutesPositions).hasSize(5)
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
        List<Position> poRoutesPositions = board.findAvailablePositions(position);

        //then
        assertThat(poRoutesPositions).hasSize(1)
                .containsExactlyInAnyOrderElementsOf(rightAnswer);
    }

    @Test
    @DisplayName("포는 궁성 안에서 대각선 경로상에 일반 기물(포다리)이 딱 1개 존재하고 "
            + "목적지에 일반 적군 기물이 존재하면 해당 기물을 포획하며 이동할 수 있다")
    void 포_궁성_안에_대각선_경로에_일반_기물_포다리가_1개_목적지에_일반_적군_기물이_있을때_이동_성공() {
        //given
        Board board = new Board();
        Position position = new Position(4, 8);
        Position bridge = new Position(5, 9);
        Position destination = new Position(6, 10);
        board.place(position, new Piece(Team.HAN, PieceType.PO));
        board.place(bridge, new Piece(Team.CHO, PieceType.SA));
        board.place(destination, new Piece(Team.CHO, PieceType.KING));

        //when
        List<Position> poRoutesPositions = board.findAvailablePositions(position);

        //then
        assertThat(poRoutesPositions).hasSize(1).containsExactlyInAnyOrder(destination);
    }

    @Test
    @DisplayName("포는 궁성 안에서 대각선 경로상에 포가 존재하면 그 너머로 이동할 수 없다")
    void 포_궁성_안에_대각선_경로에_포가_있으면_포획_및_이동_불가() {
        //given
        Board board = new Board();
        Position position = new Position(4, 8);
        Position otherPoPosition = new Position(5, 9);
        Position destination = new Position(6, 10);
        board.place(position, new Piece(Team.HAN, PieceType.PO));
        board.place(otherPoPosition, new Piece(Team.CHO, PieceType.PO));
        board.place(destination, new Piece(Team.CHO, PieceType.KING));

        //when
        List<Position> poRoutesPositions = board.findAvailablePositions(position);

        //then
        assertThat(poRoutesPositions).isEmpty();
    }
}
