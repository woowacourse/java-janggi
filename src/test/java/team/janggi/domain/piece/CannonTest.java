package team.janggi.domain.piece;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import team.janggi.domain.EmptyBoardPiecesInitializer;
import team.janggi.domain.Position;
import team.janggi.domain.Team;
import team.janggi.domain.board.BoardPieces;

public class CannonTest {

    private final BoardPieces boardPieces = new BoardPieces();

    @BeforeEach
    void setUp() {
        new EmptyBoardPiecesInitializer().initBoardStatus(boardPieces);
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

        final Piece me = Piece.of(PieceType.CANNON, myTeam);
        final Position from = new Position(startX, startY);
        final Position to = new Position(endX, endY);
        final Position obstaclePosition = new Position(obstacleX, obstacleY);

        // when
        // 포 피스 세팅
        boardPieces.setPiece(from, me);
        // 장애물 피스 세팅
        boardPieces.setPiece(obstaclePosition, Piece.of(PieceType.SOLDIER, myTeam));
        // 목표 위치에 적기물 세팅
        if (isEnemyExist) {
            boardPieces.setPiece(to, Piece.of(PieceType.SOLDIER, opponentTeam));
        }

        // then
        final boolean canMove = me.canMove(from, to, boardPieces.getBoardStateReader());
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

        final Piece me = Piece.of(PieceType.CANNON, myTeam);
        final Position from = new Position(startX, startY);
        final Position to = new Position(endX, endY);
        final Position obstaclePosition = new Position(obstacleX, obstacleY);

        // when
        // 포 피스 세팅
        boardPieces.setPiece(from, me);
        // 장애물 피스 세팅
        boardPieces.setPiece(obstaclePosition, Piece.of(PieceType.SOLDIER, myTeam));
        // 목표 위치에 적기물 세팅
        boardPieces.setPiece(to, Piece.of(PieceType.SOLDIER, myTeam));

        // then
        final boolean canMove = me.canMove(from, to, boardPieces.getBoardStateReader());
        Assertions.assertEquals(expected, canMove);
    }

    @ParameterizedTest
    @CsvSource({
            "5, 5,  5, 2,   5, 3", // 상 3칸 이동, 경로 사이에 기물이 존재하고, 목표 위치에 포  기물 있음
            "5, 5,  5, 8,   5, 7", // 하 3칸 이동, 경로 사이에 기물이 존재하고, 목표 위치에 포  기물 있음
            "5, 5,  2, 5,   3, 5", // 좌 3칸 이동, 경로 사이에 기물이 존재하고, 목표 위치에 포  기물 있음
            "5, 5,  8, 5,   7, 5", // 우 3칸 이동, 경로 사이에 기물이 존재하고, 목표 위치에 포  기물 있음
    })
    void 포는_상하좌우로_기물을_건너뛰어_이동할수있지만_같은_포는_잡을순_없다(
            int startX, int startY,
            int endX, int endY,
            int obstacleX, int obstacleY
    ) {
        // given
        final Team myTeam = Team.CHO;
        final Team enemyTeam = Team.HAN;

        final Piece me = Piece.of(PieceType.CANNON, myTeam);
        final Position from = new Position(startX, startY);
        final Position to = new Position(endX, endY);
        final Position obstaclePosition = new Position(obstacleX, obstacleY);

        // when
        // 포 피스 세팅
        boardPieces.setPiece(from, me);
        // 장애물 피스 세팅
        boardPieces.setPiece(obstaclePosition, Piece.of(PieceType.SOLDIER, myTeam));
        // 목표 위치에 적기물 세팅
        boardPieces.setPiece(to, Piece.of(PieceType.CANNON, enemyTeam));

        // then
        final boolean canMove = me.canMove(from, to, boardPieces.getBoardStateReader());
        Assertions.assertFalse(canMove);
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
        final Piece me = Piece.of(PieceType.CANNON, Team.CHO);
        final Position from = new Position(startX, startY);
        final Position to = new Position(endX, endY);

        // 포 피스 세팅
        boardPieces.setPiece(from, me);

        // when
        final boolean canMove = me.canMove(from, to, boardPieces.getBoardStateReader());

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
        final Piece me = Piece.of(PieceType.CANNON, Team.CHO);
        final Position from = new Position(startX, startY);
        final Position to = new Position(endX, endY);
        final Position obstaclePosition = new Position(obstacleX, obstacleY);

        // 포 피스 세팅
        boardPieces.setPiece(from, me);
        // 장애물 피스 세팅
        boardPieces.setPiece(obstaclePosition, Piece.of(PieceType.CANNON, Team.CHO));

        // when
        final boolean canMove = me.canMove(from, to, boardPieces.getBoardStateReader());

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
        final Piece me = Piece.of(PieceType.CANNON, Team.CHO);
        final Position from = new Position(startX, startY);
        final Position to = new Position(endX, endY);

        // 포 피스 세팅
        boardPieces.setPiece(from, me);

        // when
        final boolean canMove = me.canMove(from, to, boardPieces.getBoardStateReader());

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
        final Piece me = Piece.of(PieceType.CANNON, Team.CHO);
        final Position from = new Position(startX, startY);
        final Position to = new Position(endX, endY);
        final Position obstaclePosition = new Position(obstacleX, obstacleY);
        final Position obstaclePosition2 = new Position(obstacle2X, obstacle2Y);

        // 포 피스 세팅
        boardPieces.setPiece(from, me);
        // 장애물 피스 세팅
        boardPieces.setPiece(obstaclePosition, Piece.of(PieceType.SOLDIER, Team.CHO));
        // 장애물 피스 세팅
        boardPieces.setPiece(obstaclePosition2, Piece.of(PieceType.SOLDIER, Team.CHO));

        // when
        final boolean canMove = me.canMove(from, to, boardPieces.getBoardStateReader());

        // when & then
        Assertions.assertEquals(expected, canMove);
    }

    @ParameterizedTest
    @CsvSource({
            "3,7,   5,9,    true",
            "5,7,   3,9,    true",
            "3,9,   5,7,    true",
            "5,9,   3,7,    true",
            "3,7,   5,9,    false",
            "5,7,   3,9,    false",
            "3,9,   5,7,    false",
            "5,9,   3,7,    false"
    })
    void 포는_궁의_네각에서_정가운데_자리에_장애물이_있을때만_반대_대각선_코너로_이동_할_수_있다(
            int startX, int startY,
            int destinationX, int destinationY,
            boolean isObstacleExist) {
        // given
        Piece cannon = Piece.of(PieceType.CANNON, Team.CHO);
        Position currentPosition = new Position(startX, startY);

        boardPieces.setPiece(currentPosition, cannon);

        // 장애물 세팅
        if (isObstacleExist) {
            Position obstaclePosition = new Position(4, 8); // 궁의 정가운데 위치
            boardPieces.setPiece(obstaclePosition, Piece.of(PieceType.SOLDIER, Team.CHO));
        }

        // when & then
        Position destinationPosition = new Position(destinationX, destinationY);
        Assertions.assertEquals(isObstacleExist, cannon.canMove(currentPosition, destinationPosition, boardPieces.getBoardStateReader()));
    }

    @Test
    void 차는_궁성_밖에서_대각선_이동_할_수_없다() {
        // given
        Piece cannon = Piece.of(PieceType.CANNON, Team.CHO);
        Position currentPosition = new Position(5, 5);

        // when
        boardPieces.setPiece(currentPosition, cannon);
        // 대각선 이동 경로에 장애물 세팅
        boardPieces.setPiece(new Position(6, 6), Piece.of(PieceType.SOLDIER, Team.CHO));

        // then
        boolean canMove = cannon.canMove(currentPosition, new Position(7, 7), boardPieces.getBoardStateReader());
        Assertions.assertFalse(canMove);
    }
}
