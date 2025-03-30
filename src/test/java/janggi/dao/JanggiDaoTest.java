package janggi.dao;

import janggi.board.Board;
import janggi.board.BoardFactory;
import janggi.board.SangSetting;
import janggi.dto.PieceTypeDto;
import janggi.dto.TeamTypeDto;
import janggi.manager.ConnectionManager;
import janggi.piece.PieceType;
import janggi.team.TeamType;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

class JanggiDaoTest {

    private JanggiDao janggiDao;

    @BeforeEach
    void setUp() {
        janggiDao = new JanggiDao(new ConnectionManager());
    }

    @Disabled
    @Nested
    class 쿼리에_따라_각_CRUD가_정상적으로_동작하는지_테스트한다 {

        @Test
        void 초기_장기_기물_종류를_추가한다() {
            janggiDao.insertInitialPieceType();
        }

        @Test
        void 초기_팀을_추가한다() {
            janggiDao.insertInitialTeam(List.of(TeamType.CHO, TeamType.HAN), TeamType.CHO);
        }

        @Test
        void 팀_ID를_조회한다() {
            final int id = 1;
            final var team = janggiDao.findTeamById(id);

            assertThat(team)
                    .isEqualTo(new TeamTypeDto(id, TeamType.CHO.getTitle(), true));
        }

        @Test
        void 기물_타입_ID를_조회한다() {
            final int id = 1;
            final var pieceType = janggiDao.findPieceTypeById(id);

            assertThat(pieceType)
                    .isEqualTo(new PieceTypeDto(id, PieceType.GUNG.toString()));
        }

        @Test
        void 초기_기물을_장기판에_추가한다() {
            final BoardFactory boardFactory = new BoardFactory();
            final Board board = boardFactory.makeBoard(SangSetting.INNER_SANG, SangSetting.INNER_SANG);

            assertThatNoException()
                    .isThrownBy(() -> janggiDao.insertPieces(board.getPieces()));
        }

        @Test
        void 기물_종류를_모두_삭제한다() {
            assertThatNoException()
                    .isThrownBy(janggiDao::deleteAllPieceTypeIfExists);
        }

        @Test
        void 팀을_모두_삭제한다() {
            assertThatNoException()
                    .isThrownBy(janggiDao::deleteAllTeamIfExists);
        }

        @Test
        void 기물을_모두_삭제한다() {
            assertThatNoException()
                    .isThrownBy(janggiDao::deleteAllPieceIfExists);
        }

        @Test
        void 저장된_기물을_불러온다() {
            assertThatNoException()
                    .isThrownBy(janggiDao::findPieces);
        }

        @Test
        void 해당_팀의_순서로_변경한다() {
            // Given
            final TeamType currentTeam = TeamType.HAN;

            // When
            janggiDao.updateTeamOrder(currentTeam);

            // Then
            assertThat(janggiDao.findTeamById(2).current())
                    .isTrue();
        }
    }
}
