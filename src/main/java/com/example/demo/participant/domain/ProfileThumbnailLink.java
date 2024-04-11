package com.example.demo.participant.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public record ProfileThumbnailLink(
        @JsonValue String link
) {
}
