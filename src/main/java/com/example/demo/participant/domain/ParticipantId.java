package com.example.demo.participant.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public record ParticipantId(
        @JsonValue String id
) {
}
