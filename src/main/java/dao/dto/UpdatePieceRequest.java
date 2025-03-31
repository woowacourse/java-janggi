package dao.dto;

import domain.board.BoardLocation;

public record UpdatePieceRequest(
        BoardLocation originLocation,
        BoardLocation updateLocation
) {

}
