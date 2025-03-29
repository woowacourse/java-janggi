package domain.dto;

import java.time.LocalDateTime;

public record GameRoomDTO(String gameRoomName, String currTurn, LocalDateTime creationDate) {
}
