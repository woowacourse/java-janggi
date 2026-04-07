package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.status.HanTurn;
import janggi.domain.status.Team;
import janggi.domain.piece.Piece;
import janggi.dto.PositionInfo;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JanggiGameTest {

    @Test
    @DisplayName("새 게임은 초나라 턴으로 시작")
    void startChoTurn() {
        // given
        Board board = initBoard(
                PositionInfo.from(Team.CHO, "JANG", 4, 1),
                PositionInfo.from(Team.HAN, "JANG", 4, 8)
        );

        // when
        JanggiGame janggiGame = new JanggiGame(board);

        // then
        assertThat(janggiGame.currentTurn()).isEqualTo(Team.CHO);
    }

    @Test
    @DisplayName("현재 보드 상태를 저장용 목록으로 조회")
    void getBoardStatus() {
        // given
        Board board = initBoard(
                PositionInfo.from(Team.CHO, "JANG", 4, 1),
                PositionInfo.from(Team.HAN, "JANG", 4, 8)
        );

        // when
        JanggiGame janggiGame = new JanggiGame(board);
        Map<Point, Piece> boardStatus = janggiGame.boardStatus();

        // then
        assertThat(boardStatus).hasSize(2);
        assertThat(boardStatus).containsKeys(Point.of(4, 1), Point.of(4, 8));
    }

    @Test
    @DisplayName("저장된 게임은 해당 턴 차례로 시작")
    void restoreTurn() {
        // given
        Board board = initBoard(
                PositionInfo.from(Team.CHO, "JANG", 4, 1),
                PositionInfo.from(Team.HAN, "JANG", 4, 8)
        );

        // when
        JanggiGame janggiGame = new JanggiGame(board, new HanTurn());

        // then
        assertThat(janggiGame.currentTurn()).isEqualTo(Team.HAN);
    }

    private Board initBoard(PositionInfo... positionInfos) {
        Board board = new Board();
        board.init(PositionInfo.toPiecesByPoint(List.of(positionInfos)));
        return board;
    }
}
