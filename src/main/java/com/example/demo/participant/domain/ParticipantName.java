package com.example.demo.participant.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public record ParticipantName(
        @JsonValue String name
) {
}
