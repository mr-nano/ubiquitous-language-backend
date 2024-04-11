package com.example.demo.definition.domain;

import com.example.demo.concept.domain.ConceptId;
import com.example.demo.participant.domain.ParticipantId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class Definition {
    @JsonProperty("participantId")
    ParticipantId participantId;

    @JsonProperty("conceptId")
    ConceptId conceptId;

    @JsonProperty("definition")
    String definition;

    @JsonProperty("linkedConcepts")
    List<ConceptId> linkedConcepts;

}
