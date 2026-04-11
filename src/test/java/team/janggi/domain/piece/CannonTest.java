package team.janggi.domain.piece;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import team.janggi.domain.EmptyBoardFactory;
import team.janggi.domain.JanggiFormation;
import team.janggi.domain.Position;
import team.janggi.domain.Team;
import team.janggi.domain.board.BoardStatus;
import team.janggi.domain.board.LocalMemoryBoardStatus;

public class CannonTest {

    private final BoardStatus boardStatus = new LocalMemoryBoardStatus();

    @BeforeEach
    void setUp() {
        new EmptyBoardFactory(JanggiFormation.HEEH, JanggiFormation.HEEH).initBoardStatus(boardStatus);
    }

    @ParameterizedTest
    @CsvSource({
            "5, 5,  5, 2,   5, 3,   false,  true", // 상 3칸 이동, 경로 사이에 기물이 존재하고, 목표 위치에 기물 없음
            "5, 5,  5, 8,   5, 7,   false,  true", // 하 3칸 이동, 경로 사이에 기물이 존재하고, 목표 위치에 기물 없음
            "5, 5,  2, 5,   3, 5,   false,  true", // 좌 3칸 이동, 경로 사이에 기물이 존재하고, 목표 위치에 기물 없음
            "5, 5,  8, 5,   7, 5,   false,  true", // 우 3칸 이동, 경로 사이에 기물이 존재하고, 목표 위치에 기물 없음

            "5, 5,  5, 2,   5, 3,   true,  true", // 상 3칸 이동, 경로 사이에 기물이 존재하고, 목표 위치에 적 기물 있음
            "5, 5,  5, 8,   5, 7,   true,  true", // 하 3칸 이동, 경로 사이에 기물이 존재하고, 목표 위치에 적 기물 있음
            "5, 5,  2, 5,   3, 5,   true,  true", // 좌 3칸 이동, 경로 사이에 기물이 존재하고, 목표 위치에 적 기물 있음
            "5, 5,  8, 5,   7, 5,   true,  true", // 우 3칸 이동, 경로 사이에 기물이 존재하고, 목표 위치에 적 기물 있음
    })
    void 포는_상하좌우로_기물을_건너뛰어_이동하거나_적을_잡을수있다(
            int startX, int startY,
            int endX, int endY,
            int obstacleX, int obstacleY,
            boolean isEnemyExist,
            boolean expected
    ) {
        // given
        final Team myTeam = Team.CHO;
        final Team opponentTeam = Team.HAN;

        final Piece me = new Cannon(myTeam);
        final Position from = new Position(startX, startY);
        final Position to = new Position(endX, endY);
        final Position obstaclePosition = new Position(obstacleX, obstacleY);

        // when
        // 포 피스 세팅
        boardStatus.setPiece(from, me);
        // 장애물 피스 세팅
        boardStatus.setPiece(obstaclePosition, new Soldier(myTeam));
        // 목표 위치에 적기물 세팅
        if (isEnemyExist) {
            boardStatus.setPiece(to, new Soldier(opponentTeam));
        }

        // then
        final boolean canMove = me.canMove(from, to, boardStatus.getBoardStatus());
        Assertions.assertEquals(expected, canMove);
    }

    @ParameterizedTest
    @CsvSource({
            "5, 5,  5, 2,   5, 3,    false", // 상 3칸 이동, 경로 사이에 기물이 존재하고, 목표 위치에 아군 기물 있음
            "5, 5,  5, 8,   5, 7,    false", // 하 3칸 이동, 경로 사이에 기물이 존재하고, 목표 위치에 아군 기물 있음
            "5, 5,  2, 5,   3, 5,    false", // 좌 3칸 이동, 경로 사이에 기물이 존재하고, 목표 위치에 아군 기물 있음
            "5, 5,  8, 5,   7, 5,    false", // 우 3칸 이동, 경로 사이에 기물이 존재하고, 목표 위치에 아군 기물 있음
    })
    void 포는_상하좌우로_기물을_건너뛰어_이동할수있지만_아군을_잡을순_없다(
            int startX, int startY,
            int endX, int endY,
            int obstacleX, int obstacleY,
            boolean expected
    ) {
        // given
        final Team myTeam = Team.CHO;
        final Team opponentTeam = Team.HAN;

        final Piece me = new Cannon(myTeam);
        final Position from = new Position(startX, startY);
        final Position to = new Position(endX, endY);
        final Position obstaclePosition = new Position(obstacleX, obstacleY);

        // when
        // 포 피스 세팅
        boardStatus.setPiece(from, me);
        // 장애물 피스 세팅
        boardStatus.setPiece(obstaclePosition, new Soldier(myTeam));
        // 목표 위치에 적기물 세팅
        boardStatus.setPiece(to, new Soldier(myTeam));

        // then
        final boolean canMove = me.canMove(from, to, boardStatus.getBoardStatus());
        Assertions.assertEquals(expected, canMove);
    }

    @ParameterizedTest
    @CsvSource({
            "5, 5,  6, 6,   false", // 우하 대각선
            "5, 5,  6, 4,   false", // 우상 대각선
            "5, 5,  4, 4,   false", // 좌상 대각선
            "5, 5,  4, 6,   false"  // 좌하 대각선
    })
    void 포는_상하좌우를_제외한_위치로_이동이_불가능하다(
            int startX, int startY,
            int endX, int endY,
            boolean expected) {
        // given
        final Piece me = new Cannon(Team.CHO);
        final Position from = new Position(startX, startY);
        final Position to = new Position(endX, endY);

        // 포 피스 세팅
        boardStatus.setPiece(from, me);

        // when
        final boolean canMove = me.canMove(from, to, boardStatus.getBoardStatus());

        // when & then
        Assertions.assertEquals(expected, canMove);
    }

    @ParameterizedTest
    @CsvSource({
            "5, 5,  5, 2,   5, 3,   false", // 상 3칸 이동, 경로 사이에 포 있음
            "5, 5,  5, 8,   5, 7,   false", // 하 3칸 이동, 경로 사이에 포 있음
            "5, 5,  2, 5,   3, 5,   false", // 좌 3칸 이동, 경로 사이에 포 있음
            "5, 5,  8, 5,   7, 5,   false", // 우 3칸 이동, 경로 사이에 포 있음
    })
    void 포는_포를_건너뛰어_이동할수없다(
            int startX, int startY,
            int endX, int endY,
            int obstacleX, int obstacleY,
            boolean expected) {
        // given
        final Piece me = new Cannon(Team.CHO);
        final Position from = new Position(startX, startY);
        final Position to = new Position(endX, endY);
        final Position obstaclePosition = new Position(obstacleX, obstacleY);

        // 포 피스 세팅
        boardStatus.setPiece(from, me);
        // 장애물 피스 세팅
        boardStatus.setPiece(obstaclePosition, new Cannon(Team.CHO));

        // when
        final boolean canMove = me.canMove(from, to, boardStatus.getBoardStatus());

        // when & then
        Assertions.assertEquals(expected, canMove);
    }

    @ParameterizedTest
    @CsvSource({
            "5, 5,  5, 2,   false", // 상 3칸 이동, 경로 사이에 장애물 없음
            "5, 5,  5, 8,   false", // 하 3칸 이동, 경로 사이에 장애물 없음
            "5, 5,  2, 5,   false", // 좌 3칸 이동, 경로 사이에 장애물 없음
            "5, 5,  8, 5,   false", // 우 3칸 이동, 경로 사이에 장애물 없음
    })
    void 포는_장애물이_사이에없으면_이동할수없다(
            int startX, int startY,
            int endX, int endY,
            boolean expected) {
        // given
        final Piece me = new Cannon(Team.CHO);
        final Position from = new Position(startX, startY);
        final Position to = new Position(endX, endY);

        // 포 피스 세팅
        boardStatus.setPiece(from, me);

        // when
        final boolean canMove = me.canMove(from, to, boardStatus.getBoardStatus());

        // when & then
        Assertions.assertEquals(expected, canMove);
    }

    @ParameterizedTest
    @CsvSource({
            "5, 5,  5, 2,   5, 4,   5, 3,   false", // 상 3칸 이동, 경로 사이에 기물 2개 있음
            "5, 5,  5, 8,   5, 6,   5, 7,   false", // 하 3칸 이동, 경로 사이에 기물 2개 있음
            "5, 5,  2, 5,   4, 5,   3, 5,   false", // 좌 3칸 이동, 경로 사이에 기물 2개 있음
            "5, 5,  8, 5,   6, 5,   7, 5,   false", // 우 3칸 이동, 경로 사이에 기물 2개 있음
    })
    void 포는_두개_이상_기물을_뛰어넘을수_없다(
            int startX, int startY,
            int endX, int endY,
            int obstacleX, int obstacleY,
            int obstacle2X, int obstacle2Y,
            boolean expected) {
        // given
        final Piece me = new Cannon(Team.CHO);
        final Position from = new Position(startX, startY);
        final Position to = new Position(endX, endY);
        final Position obstaclePosition = new Position(obstacleX, obstacleY);
        final Position obstaclePosition2 = new Position(obstacle2X, obstacle2Y);

        // 포 피스 세팅
        boardStatus.setPiece(from, me);
        // 장애물 피스 세팅
        boardStatus.setPiece(obstaclePosition, new Soldier(Team.CHO));
        // 장애물 피스 세팅
        boardStatus.setPiece(obstaclePosition2, new Soldier(Team.CHO));

        // when
        final boolean canMove = me.canMove(from, to, boardStatus.getBoardStatus());

        // when & then
        Assertions.assertEquals(expected, canMove);
    }

    @ParameterizedTest
    @CsvSource({
            "3, 7, 4, 8, 5, 9",
            "5, 7, 4, 8, 3, 9",
            "5, 9, 4, 8, 3, 7",
            "3, 9, 4, 8, 5, 7",
    })
    void 초의_포는_궁성영역_에서_기물을_건너뛰어_대각선_이동이_가능하다(int startX, int startY, int middleX, int middleY, int endX, int endY) {
        // given
        final Piece cannon = new Cannon(Team.CHO);
        final Position from = new Position(startX, startY);
        final Position to = new Position(endX, endY);
        final Position obstaclePosition = new Position(middleX, middleY);

        // 포 피스 세팅
        boardStatus.setPiece(from, cannon);
        // 장애물 피스 세팅
        boardStatus.setPiece(obstaclePosition, new Soldier(Team.CHO));

        // when
        final boolean canMove = cannon.canMove(from, to, boardStatus.getBoardStatus());

        // when & then
        Assertions.assertTrue(canMove);
    }

    @ParameterizedTest
    @CsvSource({
            "3, 0, 4, 1, 5, 2",
            "5, 2, 4, 1, 3, 0",
            "5, 0, 4, 1, 3, 2",
            "3, 2, 4, 1, 5, 0",
    })
    void 한의_포는_궁성영역_에서_기물을_건너뛰어_대각선_이동이_가능하다(int startX, int startY, int middleX, int middleY, int endX, int endY) {
        // given
        final Piece cannon = new Cannon(Team.HAN);
        final Position from = new Position(startX, startY);
        final Position to = new Position(endX, endY);
        final Position obstaclePosition = new Position(middleX, middleY);

        // 포 피스 세팅
        boardStatus.setPiece(from, cannon);
        // 장애물 피스 세팅
        boardStatus.setPiece(obstaclePosition, new Soldier(Team.HAN));

        // when
        final boolean canMove = cannon.canMove(from, to, boardStatus.getBoardStatus());

        // when & then
        Assertions.assertTrue(canMove);
    }
}
