package janggi.domain.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.board.Board;
import janggi.domain.board.Position;
import janggi.domain.piece.ChariotPiece;
import janggi.domain.piece.GeneralPiece;
import janggi.domain.piece.Piece;
import janggi.domain.piece.SoldierPiece;
import janggi.domain.piece.Team;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiGameTest {

    @Test
    @DisplayName("한 수를 두면 턴이 상대 팀으로 넘어간다.")
    void testChangeTurnAfterMove() {
        JanggiGame janggiGame = JanggiGame.start(createBoard(
                new Position(1, 1), new ChariotPiece(Team.HAN),
                new Position(5, 9), new GeneralPiece(Team.CHO)
        ));

        janggiGame.move(new Position(1, 1), new Position(1, 2));

        assertThat(janggiGame.currentTurnTeam()).isEqualTo(Team.CHO);
    }

    @Test
    @DisplayName("장을 잡으면 게임이 종료된다.")
    void testFinishGameAfterCapturingGeneral() {
        JanggiGame janggiGame = JanggiGame.start(createBoard(
                new Position(1, 1), new ChariotPiece(Team.HAN),
                new Position(1, 3), new GeneralPiece(Team.CHO)
        ));

        janggiGame.move(new Position(1, 1), new Position(1, 3));

        assertThat(janggiGame.isPlaying()).isFalse();
    }

    @Test
    @DisplayName("종료된 게임에서는 더 이상 이동할 수 없다.")
    void testMoveAfterFinishedGame() {
        JanggiGame janggiGame = JanggiGame.start(createBoard(
                new Position(1, 1), new ChariotPiece(Team.HAN),
                new Position(1, 3), new GeneralPiece(Team.CHO),
                new Position(2, 1), new SoldierPiece(Team.HAN)
        ));

        janggiGame.move(new Position(1, 1), new Position(1, 3));

        assertThatThrownBy(() -> janggiGame.move(new Position(2, 1), new Position(2, 2)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 종료된 게임입니다.");
    }

    private Board createBoard(Position firstPosition, Piece firstPiece,
                              Position secondPosition, Piece secondPiece) {
        Map<Position, Piece> board = new LinkedHashMap<>();
        board.put(firstPosition, firstPiece);
        board.put(secondPosition, secondPiece);
        return new Board(board);
    }

    private Board createBoard(Position firstPosition, Piece firstPiece,
                              Position secondPosition, Piece secondPiece,
                              Position thirdPosition, Piece thirdPiece) {
        Map<Position, Piece> board = new LinkedHashMap<>();
        board.put(firstPosition, firstPiece);
        board.put(secondPosition, secondPiece);
        board.put(thirdPosition, thirdPiece);
        return new Board(board);
    }
}
