package janggi.dto;

public record GameResponseDto(int id, String name, String createdAt, String updatedAt, String side, int turn) {
}
