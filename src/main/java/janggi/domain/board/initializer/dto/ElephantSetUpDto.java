package janggi.domain.board.initializer.dto;

import janggi.domain.Position;
import janggi.domain.board.initializer.ElephantSetUp;
import janggi.domain.piece.Camp;
import janggi.domain.piece.Piece;
import java.util.Map;

public record ElephantSetUpDto(Camp camp, ElephantSetUp elephantSetUp) {

    public Map<Position, Piece> settingUp() {
        return elephantSetUp.settingUp(camp);
    }
}
