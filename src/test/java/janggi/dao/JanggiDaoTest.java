package janggi.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.Piece;
import janggi.domain.PieceType;
import janggi.domain.Position;
import janggi.domain.Team;
import janggi.domain.board.Board;
import janggi.domain.turn.ChoTurn;
import janggi.domain.turn.Turn;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiDaoTest {

    private JanggiDao janggiDAO;

    @BeforeEach
    void setUp() {
        DbConnection.initializeDatabase();
        janggiDAO = new JanggiDao();

        String deleteQuery = "DELETE FROM game";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("저장소 초기화 실패");
        }
    }

    @Test
    @DisplayName("보드를 저장하고 불러오면 같은 기물이 같은 위치에 존재한다")
    void 보드_저장_후_불러오면_기물_제자리_위치() {
        //given
        Map<Position, Piece> state = new HashMap<>();
        state.put(new Position(1, 1), new Piece(Team.CHO, PieceType.CHA));
        state.put(new Position(5, 7), new Piece(Team.HAN, PieceType.ZOL));
        Board customBoard = new Board(state);
        Turn choTurn = new ChoTurn();

        //when
        janggiDAO.saveGame(choTurn, customBoard);
        Map<Position, Piece> loadedBoard = janggiDAO.loadBoard();

        //then
        assertAll(
                () -> assertThat(loadedBoard).hasSize(2),
                () -> assertThat(loadedBoard.get(new Position(1, 1)))
                        .isEqualTo(new Piece(Team.CHO, PieceType.CHA)),
                () -> assertThat(loadedBoard.get(new Position(5, 7)))
                        .isEqualTo(new Piece(Team.HAN, PieceType.ZOL))
        );
    }

    @Test
    @DisplayName("현재 턴을 저장하고 불러오면 같은 팀의 턴이 반환된다")
    void 턴_정보_저장_확인() {
        //given
        Board customBoard = new Board(new HashMap<>());
        Turn choTurn = new ChoTurn();
        janggiDAO.saveGame(choTurn, customBoard);

        //when
        Turn loadedCurrentTurn = janggiDAO.loadCurrentTurn();

        //then
        assertThat(loadedCurrentTurn.getTeam()).isEqualTo(Team.CHO);
    }

    @Test
    @DisplayName("저장된 게임이 없으면 hasSavedGame은 false로 반환한다")
    void 저장된_게임_없을_시() {
        assertThat(janggiDAO.hasSavedGame()).isFalse();
    }
}
