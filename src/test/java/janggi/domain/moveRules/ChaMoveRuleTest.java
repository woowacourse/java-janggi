package janggi.domain.moveRules;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Piece;
import janggi.domain.PieceType;
import janggi.domain.Position;
import janggi.domain.Team;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChaMoveRuleTest {

    @Test
    @DisplayName("차는 직선 경로상에 적군 기물이 있으면 포획할 수 있는 적군 기물 위치까지만 이동할 수 있다")
    void 차_경로에_적군_존재시_적군_위치까지_이동_가능() {
        //given
        Map<Position, Piece> state = new HashMap<>();
        Position position = new Position(1, 10);
        Team choTeam = Team.CHO;
        state.put(position, new Piece(choTeam, PieceType.CHA));
        state.put(new Position(1, 7), new Piece(Team.HAN, PieceType.CHA));
        List<Position> northRoutes = List.of(
                new Position(1, 9), new Position(1, 8), new Position(1, 7)
        );

        List<Position> eastRoutes = List.of(
                new Position(2, 10), new Position(3, 10), new Position(4, 10),
                new Position(5, 10), new Position(6, 10), new Position(7, 10),
                new Position(8, 10), new Position(9, 10)
        );
        List<Position> rightAnswer = new ArrayList<>();
        rightAnswer.addAll(northRoutes);
        rightAnswer.addAll(eastRoutes);
        MoveRule chaMoveRule = new ChaMoveRule();
        //when
        List<Position> chaRoutesPositions = chaMoveRule.calculateAvailablePositions(position, choTeam, state);

        //then
        assertThat(chaRoutesPositions).hasSize(11)
                .containsExactlyInAnyOrderElementsOf(rightAnswer);
    }

    @Test
    @DisplayName("차는 직선 경로상에 장애물이 없으면 끝까지 이동할 수 있다")
    void 차_장애물_없을때_직선_끝까지_이동_성공() {
        //given
        Map<Position, Piece> state = new HashMap<>();
        Position position = new Position(1, 10);
        Team choTeam = Team.CHO;
        state.put(position, new Piece(choTeam, PieceType.CHA));
        List<Position> northRoutes = List.of(
                new Position(1, 9), new Position(1, 8), new Position(1, 7),
                new Position(1, 6), new Position(1, 5), new Position(1, 4),
                new Position(1, 3), new Position(1, 2), new Position(1, 1)
        );

        List<Position> eastRoutes = List.of(
                new Position(2, 10), new Position(3, 10), new Position(4, 10),
                new Position(5, 10), new Position(6, 10), new Position(7, 10),
                new Position(8, 10), new Position(9, 10)
        );
        List<Position> rightAnswer = new ArrayList<>();
        rightAnswer.addAll(northRoutes);
        rightAnswer.addAll(eastRoutes);
        MoveRule chaMoveRule = new ChaMoveRule();
        //when
        List<Position> chaRoutesPositions = chaMoveRule.calculateAvailablePositions(position, choTeam, state);

        //then
        assertThat(chaRoutesPositions).hasSize(17)
                .containsExactlyInAnyOrderElementsOf(rightAnswer);
    }

    @Test
    @DisplayName("차는 직선 경로상에 기물이 있는 곳 까지만 이동할 수 있다")
    void 차_경로에_기물까지만_이동_가능() {
        //given
        Map<Position, Piece> state = new HashMap<>();
        Position position = new Position(1, 10);
        Team choTeam = Team.CHO;
        state.put(position, new Piece(Team.CHO, PieceType.CHA));
        state.put(new Position(1, 7), new Piece(Team.CHO, PieceType.ZOL));
        List<Position> northRoutes = List.of(
                new Position(1, 9), new Position(1, 8), new Position(1, 7)
        );

        List<Position> eastRoutes = List.of(
                new Position(2, 10), new Position(3, 10), new Position(4, 10),
                new Position(5, 10), new Position(6, 10), new Position(7, 10),
                new Position(8, 10), new Position(9, 10)
        );
        List<Position> rightAnswer = new ArrayList<>();
        rightAnswer.addAll(northRoutes);
        rightAnswer.addAll(eastRoutes);
        MoveRule chaMoveRul = new ChaMoveRule();

        //when
        List<Position> chaRoutesPositions = chaMoveRul.calculateAvailablePositions(position, choTeam, state);

        //then
        assertThat(chaRoutesPositions).hasSize(11)
                .containsExactlyInAnyOrderElementsOf(rightAnswer);
    }
}
