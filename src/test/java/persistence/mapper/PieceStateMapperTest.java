package persistence.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Piece;
import domain.PieceProperty;
import domain.PieceType;
import domain.Position;
import domain.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.PieceState;

class PieceStateMapperTest {

    @Test
    @DisplayName("기물(종류 및 팀) 위치를 저장 상태로 변환한다.")
    void pieceType_team_position_mapFrom_can_save_pieceState_test() {
        PieceProperty soldierProperty = new PieceProperty(PieceType.SOLDIER, Team.GREEN);
        Position position = new Position(6, 0);
        PieceStateMapper pieceStateMapper = new PieceStateMapper();

        PieceState actual = pieceStateMapper.mapFrom(soldierProperty, position);

        PieceState expected = new PieceState(soldierProperty, position);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("기물 저장 상태를 기물로 복원한다.")
    void pieceState_restored_piece_test() {
        PieceProperty soldierProperty = new PieceProperty(PieceType.SOLDIER, Team.GREEN);
        Position position = new Position(6, 0);
        PieceStateMapper pieceStateMapper = new PieceStateMapper();
        PieceState pieceState = new PieceState(soldierProperty, position);

        Piece actual = pieceStateMapper.mapToPiece(pieceState);

        assertThat(actual.pieceProperty()).isEqualTo(soldierProperty);
    }
}
