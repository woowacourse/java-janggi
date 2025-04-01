package domain.dto;

import java.time.LocalDateTime;

public record GameRoomDto(String gameRoomName, String currTurn, LocalDateTime creationDate) {
}
