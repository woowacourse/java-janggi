package janggi.repository.dto;

import janggi.model.Janggi;

public record LatestInProgressGameResponse(
        Long gameId,
        Janggi janggi
) {}
