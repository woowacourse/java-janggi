package janggi.dto;

public record GameRoom(long id,
                       String turn,
                       boolean finished,
                       double choScore,
                       double hanScore) {
}
