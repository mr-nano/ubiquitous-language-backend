package com.example.demo.concept.domain;

import com.example.demo.participant.domain.ParticipantId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Concept {
    @JsonProperty("conceptId")
    ConceptId conceptId;

    @JsonProperty("conceptTitle")
    ConceptTitle conceptTitle;

    @JsonProperty("createdBy")
    ParticipantId createdBy;
}
