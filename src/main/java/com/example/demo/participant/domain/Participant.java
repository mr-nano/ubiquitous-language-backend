package com.example.demo.participant.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public class Participant {
    @JsonProperty("participantId")
    ParticipantId participantId;
    @JsonProperty("participantName")
    ParticipantName participantName;
    @JsonProperty("profileThumbnailLink")
    ProfileThumbnailLink profileThumbnailLink;
}
