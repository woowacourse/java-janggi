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

public class ChariotTest {

    private final BoardStatus boardStatus = new LocalMemoryBoardStatus();

    @BeforeEach
    void setUp() {
        new EmptyBoardFactory(JanggiFormation.HEEH, JanggiFormation.HEEH).initBoardStatus(boardStatus);
    }

    @ParameterizedTest
    @CsvSource({
            "5, 5, 5, 4, true, true", // 상 1칸 이동, 목표 위치에 기물 있음
            "5, 5, 5, 6, true, true", // 하 1칸 이동, 목표 위치에 기물 있음
            "5, 5, 4, 5, true, true", // 좌 1칸 이동, 목표 위치에 기물 있음
            "5, 5, 6, 5, true, true", // 우 1칸 이동, 목표 위치에 기물 있음

            "5, 5, 5, 3, true, true", // 상 2칸 이동, 목표 위치에 기물 있음
            "5, 5, 5, 7, true, true", // 하 2칸 이동, 목표 위치에 기물 있음
            "5, 5, 3, 5, true, true", // 좌 2칸 이동, 목표 위치에 기물 있음
            "5, 5, 7, 5, true, true", // 우 2칸 이동, 목표 위치에 기물 있음

            "5, 5, 5, 2, true, true", // 상 3칸 이동, 목표 위치에 기물 있음
            "5, 5, 5, 8, true, true", // 하 3칸 이동, 목표 위치에 기물 있음
            "5, 5, 2, 5, true, true", // 좌 3칸 이동, 목표 위치에 기물 있음
            "5, 5, 8, 5, true, true", // 우 3칸 이동, 목표 위치에 기물 있음

            "5, 5, 5, 4, false, true", // 상 1칸 이동, 목표 위치에 기물 없음
            "5, 5, 5, 6, false, true", // 하 1칸 이동, 목표 위치에 기물 없음
            "5, 5, 4, 5, false, true", // 좌 1칸 이동, 목표 위치에 기물 없음
            "5, 5, 6, 5, false, true", // 우 1칸 이동, 목표 위치에 기물 없음

            "5, 5, 5, 3, false, true", // 상 2칸 이동, 목표 위치에 기물 없음
            "5, 5, 5, 7, false, true", // 하 2칸 이동, 목표 위치에 기물 없음
            "5, 5, 3, 5, false, true", // 좌 2칸 이동, 목표 위치에 기물 없음
            "5, 5, 7, 5, false, true", // 우 2칸 이동, 목표 위치에 기물 없음

            "5, 5, 5, 2, false, true", // 상 3칸 이동, 목표 위치에 기물 없음
            "5, 5, 5, 8, false, true", // 하 3칸 이동, 목표 위치에 기물 없음
            "5, 5, 2, 5, false, true", // 좌 3칸 이동, 목표 위치에 기물 없음
            "5, 5, 8, 5, false, true", // 우 3칸 이동, 목표 위치에 기물 없음

            "5, 5, 6, 6, false, false", // 우하 대각선
            "5, 5, 6, 4, false, false", // 우상 대각선
            "5, 5, 4, 4, false, false", // 좌상 대각선
            "5, 5, 4, 6, false, false",  // 좌하 대각선
    })
    void 차는_상하좌우로만_이동하여_적을_잡을수있다(
            int startX, int startY,
            int endX, int endY,
            boolean isEnemyExist,
            boolean expected
    ) {
        // given
        final Team myTeam = Team.CHO;
        final Team opponentTeam = Team.HAN;

        Piece me = new Chariot(myTeam);
        Position from = new Position(startX, startY);
        Position to = new Position(endX, endY);

        // 피스 세팅
        boardStatus.setPiece(from, me);
        if (isEnemyExist) {
            boardStatus.setPiece(to, new Soldier(opponentTeam));
        }

        // when
        boolean canMove = me.canMove(from, to, boardStatus.getBoardStatus());

        // when & then
        Assertions.assertEquals(expected, canMove);
    }

    @ParameterizedTest
    @CsvSource({
            "5, 5, 5, 4, false", // 상 1칸 이동
            "5, 5, 5, 6, false", // 하 1칸 이동
            "5, 5, 4, 5, false", // 좌 1칸 이동
            "5, 5, 6, 5, false", // 우 1칸 이동

            "5, 5, 5, 3, false", // 상 2칸 이동
            "5, 5, 5, 7, false", // 하 2칸 이동
            "5, 5, 3, 5, false", // 좌 2칸 이동
            "5, 5, 7, 5, false", // 우 2칸 이동

            "5, 5, 5, 2, false", // 상 3칸 이동
            "5, 5, 5, 8, false", // 하 3칸 이동
            "5, 5, 2, 5, false", // 좌 3칸 이동
            "5, 5, 8, 5, false", // 우 3칸 이동

            "5, 5, 6, 6, false", // 우하 대각선
            "5, 5, 6, 4, false", // 우상 대각선
            "5, 5, 4, 4, false", // 좌상 대각선
            "5, 5, 4, 6, false"  // 좌하 대각선
    })
    void 차는_상하좌우로만_이동할수있지만_아군은_잡을순_없다(
            int startX, int startY,
            int endX, int endY,
            boolean expected
    ) {
        // given
        final Team myTeam = Team.CHO;

        Piece me = new Chariot(myTeam);
        Position from = new Position(startX, startY);
        Position to = new Position(endX, endY);

        // 피스 세팅
        boardStatus.setPiece(from, me);
        boardStatus.setPiece(to, new Soldier(myTeam)); // 목표 위치에 아군 기물 세팅

        // when
        boolean canMove = me.canMove(from, to, boardStatus.getBoardStatus());

        // when & then
        Assertions.assertEquals(expected, canMove);
    }

    @ParameterizedTest
    @CsvSource({
            "3, 7, 5, 9",
            "3, 9, 5, 7",
            "5, 9, 3, 7",
            "5, 7, 3, 9",
            "3, 0, 5, 2",
            "3, 2, 5, 0",
            "5, 2, 3, 0",
            "5, 0, 3, 2"
    })
    void 차는_모서리위치의_궁성영역_에서_대각선으로_이동할_수_있다(int startX, int startY, int endX, int endY) {
        // given
        Piece chariot = new Chariot(Team.CHO);
        Position from = new Position(startX, startY);
        Position to = new Position(endX, endY);

        boardStatus.setPiece(from, chariot);

        // when
        boolean canMove = chariot.canMove(from, to, boardStatus.getBoardStatus());
        Assertions.assertTrue(canMove);
    }

    @ParameterizedTest
    @CsvSource({
            "3, 8, 4, 7",
            "3, 8, 4, 9",
            "5, 8, 4, 9",
            "5, 8, 4, 7",
            "4, 0, 3, 1",
            "4, 2, 3, 1",
            "4, 2, 5, 1",
            "5, 1, 4, 0"
    })
    void 차는_모서리위치가_아닌_영역에서_대각선으로_이동할_수_없다(int startX, int startY, int endX, int endY) {
        // given
        Piece chariot = new Chariot(Team.CHO);
        Position from = new Position(startX, startY);
        Position to = new Position(endX, endY);

        boardStatus.setPiece(from, chariot);

        // when
        boolean canMove = chariot.canMove(from, to, boardStatus.getBoardStatus());
        Assertions.assertFalse(canMove);
    }
}
