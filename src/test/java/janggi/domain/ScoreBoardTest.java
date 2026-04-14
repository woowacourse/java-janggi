package janggi.domain;

import janggi.domain.board.Board;
import janggi.domain.janggiGame.ScoreBoard;
import janggi.domain.piece.*;
import janggi.domain.vo.position.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ScoreBoardTest {
    private static Stream<Arguments> provideVariousScoreScenarios() {
        return Stream.of(
                // 기물 점수는 같지만 덤 때문에 한 승리 (12 vs 13.5)
                Arguments.of(
                        List.of(new Tank(Team.CHO)),
                        List.of(new Tank(Team.HAN)),
                        Team.HAN
                ),
                // 초가 근소하게 앞서서 승리 (3+2=5.0 vs 3+1.5=4.5)
                Arguments.of(
                        List.of(new Advisor(Team.CHO), new Soldier(Team.CHO)),
                        List.of(new Advisor(Team.HAN)),
                        Team.CHO
                ),
                // 왕(King)은 점수에 영향을 주지 않음 (0 vs 1.5)
                Arguments.of(
                        List.of(new King(Team.CHO)),
                        List.of(new King(Team.HAN)),
                        Team.HAN
                )
        );
    }

    @ParameterizedTest
    @MethodSource("provideVariousScoreScenarios")
    void 다양한_점수_판정_테스트(List<Piece> choPieces, List<Piece> hanPieces, Team expectedWinner) {
        // when
        ScoreBoard scoreBoard = new ScoreBoard(hanPieces, choPieces);

        // then
        assertThat(scoreBoard.winner()).isEqualTo(expectedWinner);
    }

    @Test
    void 최소_경계값_테스트() {
        // given, when
        ScoreBoard scoreBoard = new ScoreBoard(List.of(new King(Team.HAN)), List.of(new King(Team.CHO)));

        // then
        assertThat(scoreBoard.winner()).isEqualTo(Team.HAN);
    }

    @Test
    void 보드_기물기반_판정_테스트() {
        // given
        Board board = createBoardWithKings(
                new Position(0, 0), new Tank(Team.HAN),   // 12.0
                new Position(0, 1), new Cannon(Team.HAN), // 7.0
                new Position(9, 0), new Soldier(Team.CHO) // 2.0
        );

        // when
        ScoreBoard scoreBoard = new ScoreBoard(board.piecesOf(Team.HAN), board.piecesOf(Team.CHO));

        // then
        assertThat(scoreBoard.winner()).isEqualTo(Team.HAN);
    }

    private Board createBoardWithKings(Object... extraPieces) {
        Board board = Board.createBoardWith(
                new Position(1, 4), new King(Team.HAN),
                new Position(8, 4), new King(Team.CHO)
        );

        for (int i = 0; i < extraPieces.length; i += 2) {
            board.place((Position) extraPieces[i], (Piece) extraPieces[i + 1]);
        }

        return board;
    }
}