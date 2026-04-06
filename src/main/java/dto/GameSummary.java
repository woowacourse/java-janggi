package dto;

import java.util.Date;

public record GameSummary(long id, Date startedAt, String currentTurn) {
}
