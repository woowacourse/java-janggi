package domain.janggi.service;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import domain.board.Board;
import domain.board.BoardPosition;
import domain.piece.Jju;
import domain.piece.Team;
import domain.turn.Turn;
import fake.InMemoryBoardRepository;
import fake.InMemoryTurnRepository;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class JanggiServiceTest {

    private final InMemoryBoardRepository boardRepository = new InMemoryBoardRepository();
    private final InMemoryTurnRepository turnRepository = new InMemoryTurnRepository();

    @BeforeEach
    void setUp() {
        boardRepository.deleteAll();
        turnRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        boardRepository.deleteAll();
        turnRepository.deleteAll();
    }

    @Nested
    class ValidCases {

        @Test
        @DisplayName("저장된 게임이 없으면 초기화 한다.")
        void startGame_noSavedGame() {
            // given
            JanggiService janggiService = new JanggiService(boardRepository, turnRepository);

            // when
            janggiService.startGame();

            // then
            assertSoftly(softly -> {
                softly.assertThat(turnRepository.findLast().get().getCurrentTeam()).isEqualTo(
                    Team.GREEN);
                softly.assertThat(boardRepository.load().get().getPieces()).isNotEmpty();
            });
        }

        @Test
        @DisplayName("게임 상태를 저장하고 불러올 수 있다.")
        void startGame_savedGameExists() {
            // given
            JanggiService janggiService = new JanggiService(boardRepository, turnRepository);
            final Board savedBoard = new Board(Map.of(
                new BoardPosition(0, 0), new Jju(Team.RED)
            ));
            final Turn savedTurn = new Turn(Team.RED);

            boardRepository.save(savedBoard);
            turnRepository.save(savedTurn);

            // when
            janggiService.startGame();

            // then
            assertSoftly(softly -> {
                softly.assertThat(turnRepository.findLast().get().getCurrentTeam()).isEqualTo(
                    Team.RED);
                softly.assertThat(boardRepository.load().get().getPieces()).isEqualTo(
                    savedBoard.getPieces());
            });
        }

        @Test
        @DisplayName("한 턴을 진행하면 저장된 상태가 갱신된다.")
        void playTurn() {
            // given
            JanggiService janggiService = new JanggiService(boardRepository, turnRepository);
            janggiService.startGame();

            final BoardPosition from = new BoardPosition(0, 3);
            final BoardPosition to = new BoardPosition(0, 4);

            // when
            janggiService.playTurn(from, to);

            // then
            assertSoftly(softly -> {
                softly.assertThat(boardRepository.load().get().getPieces()).containsKey(to);
                softly.assertThat(turnRepository.findLast().get().getCurrentTeam()).isEqualTo(
                    Team.RED);
            });
        }

        @Test
        @DisplayName("게임 저장 데이터를 삭제할 수 있다.")
        void deleteSavedGame() {
            // given
            JanggiService janggiService = new JanggiService(boardRepository, turnRepository);
            janggiService.startGame();

            // when
            janggiService.deleteSavedGame();

            // then
            assertSoftly(softly -> {
                softly.assertThat(boardRepository.hasAnyPiece()).isFalse();
                softly.assertThat(turnRepository.exists()).isFalse();
            });
        }
    }
}
