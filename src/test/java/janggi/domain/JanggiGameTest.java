package janggi.domain;

import janggi.domain.board.Board;
import janggi.domain.piece.Implementation.Cha;
import janggi.domain.piece.Implementation.Pho;
import janggi.domain.piece.Piece;
import janggi.domain.point.Point;
import janggi.domain.status.HanTurn;
import janggi.domain.status.Team;
import java.util.LinkedHashMap;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiGameTest {

    @Test
    @DisplayName("한나라의 기물이 차와 포만 있으면 총합은 1.5점 가산점을 더해서 21.5이다.")
    void han_total_score() {
        Map<Point, Piece> pieces = new LinkedHashMap<>();
        pieces.put(Point.of(0, 0), new Cha(Team.HAN));
        pieces.put(Point.of(1, 1), new Pho(Team.HAN));
        Board board = new Board(pieces);
        JanggiGame game = new JanggiGame(board, new HanTurn());
        Assertions.assertThat(game.getHanScore()).isEqualTo(21.5);
    }
}
