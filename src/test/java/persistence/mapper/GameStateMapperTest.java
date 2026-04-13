package persistence.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Board;
import domain.GameStatus;
import domain.JanggiGame;
import domain.Piece;
import domain.PieceProperty;
import domain.PieceType;
import domain.Position;
import domain.Team;
import factory.MoveStrategyFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.GameState;
import persistence.entity.PieceState;

class GameStateMapperTest {

    private final MoveStrategyFactory moveStrategyFactory = new MoveStrategyFactory();
    private final PieceProperty horseGreenProperty = new PieceProperty(PieceType.HORSE, Team.GREEN);
    private final PieceProperty soldierGreenProperty = new PieceProperty(PieceType.SOLDIER, Team.GREEN);
    private final PieceProperty generalGreenProperty = new PieceProperty(PieceType.GENERAL, Team.GREEN);

    @Test
    @DisplayName("기물의 상태들과 장기 게임의 상태를 저장 상태로 변환한다.")
    void janggi_game_can_be_mapped_to_game_state_test() {
        Position horsePosition = new Position(9, 1);
        Position soldierPosition = new Position(6, 0);
        Position generalPosition = new Position(8, 4);

        Piece horsePiece = Piece.of(horseGreenProperty, moveStrategyFactory.createMoveStrategy(horseGreenProperty));
        Piece soldierPiece = Piece.of(soldierGreenProperty, moveStrategyFactory.createMoveStrategy(soldierGreenProperty));
        Piece generalPiece = Piece.of(generalGreenProperty, moveStrategyFactory.createMoveStrategy(generalGreenProperty));

        Map<Position, Piece> testBoard = new HashMap<>();
        testBoard.put(horsePosition, horsePiece);
        testBoard.put(soldierPosition, soldierPiece);
        testBoard.put(generalPosition, generalPiece);

        JanggiGame janggiGame = JanggiGame.of(Board.of(testBoard), GameStatus.GREEN_PLAYER_TURN);
        GameStateMapper gameStateMapper = new GameStateMapper(new PieceStateMapper());

        GameState actual = gameStateMapper.mapFrom(janggiGame);

        PieceState horseState = new PieceState(horseGreenProperty, horsePosition);
        PieceState soldierState = new PieceState(soldierGreenProperty, soldierPosition);
        PieceState generalState = new PieceState(generalGreenProperty, generalPosition);
        GameState expected = new GameState(List.of(horseState, soldierState, generalState), GameStatus.GREEN_PLAYER_TURN);
        assertThat(actual.gameStatus()).isEqualTo(expected.gameStatus());
        assertThat(actual.pieceStates()).containsExactlyInAnyOrderElementsOf(expected.pieceStates());
    }

    @Test
    @DisplayName("게임 저장 상태를 장기 게임으로 복원한다.")
    void game_state_can_be_restored_to_janggi_game_test() {
        Position horsePosition = new Position(9, 1);
        Position soldierPosition = new Position(6, 0);
        Position generalPosition = new Position(8, 4);

        PieceState horseState = new PieceState(horseGreenProperty, horsePosition);
        PieceState soldierState = new PieceState(soldierGreenProperty, soldierPosition);
        PieceState generalState = new PieceState(generalGreenProperty, generalPosition);
        GameState gameState = new GameState(List.of(horseState, soldierState, generalState), GameStatus.GREEN_PLAYER_TURN);

        GameStateMapper gameStateMapper = new GameStateMapper(new PieceStateMapper());
        JanggiGame actual = gameStateMapper.mapToJanggiGame(gameState);

        assertThat(actual.allFactors().board().get(horsePosition).pieceProperty()).isEqualTo(horseGreenProperty);
        assertThat(actual.allFactors().board().get(soldierPosition).pieceProperty()).isEqualTo(soldierGreenProperty);
        assertThat(actual.allFactors().board().get(generalPosition).pieceProperty()).isEqualTo(generalGreenProperty);
        assertThat(actual.gameStatus()).isEqualTo(GameStatus.GREEN_PLAYER_TURN);
    }

}
