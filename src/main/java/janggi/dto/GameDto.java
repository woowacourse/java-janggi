package janggi.dto;

public record GameDto(
        Long id,
        String choName,
        String hanName,
        String currentTurn
) {
}
