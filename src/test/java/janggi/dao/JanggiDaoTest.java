package janggi.dao;

import janggi.board.Board;
import janggi.board.BoardFactory;
import janggi.board.SangSetting;
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
            final var team = janggiDao.findTeamType(TeamType.CHO);

            assertThat(team)
                    .isEqualTo(1);
        }

        @Test
        void 기물_타입_ID를_조회한다() {
            final var pieceType = janggiDao.findPieceType(PieceType.GUNG);

            assertThat(pieceType)
                    .isEqualTo(1);
        }

        @Test
        void 초기_기물을_장기판에_추가한다() {
            final BoardFactory boardFactory = new BoardFactory();
            final Board board = boardFactory.makeBoard(SangSetting.INNER_SANG, SangSetting.INNER_SANG);

            assertThatNoException()
                    .isThrownBy(() -> janggiDao.insertInitialPieces(board.getPieces()));
        }

        @Test
        void 기물_종류를_모두_삭제한다() {
            assertThatNoException()
                    .isThrownBy(janggiDao::deleteAllPieceType);
        }

        @Test
        void 팀을_모두_삭제한다() {
            assertThatNoException()
                    .isThrownBy(janggiDao::deleteAllTeam);
        }

        @Test
        void 기물을_모두_삭제한다() {
            assertThatNoException()
                    .isThrownBy(janggiDao::deleteAllPiece);
        }

        @Test
        void 저장된_기물을_불러온다() {
            assertThatNoException()
                    .isThrownBy(janggiDao::findPieces);
        }

        @Test
        void 다음_턴으로_넘어간다() {
            assertThatNoException()
                    .isThrownBy(janggiDao::updateTeamOrder);
        }
    }
}
