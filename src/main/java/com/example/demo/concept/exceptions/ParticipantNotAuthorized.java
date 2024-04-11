package com.example.demo.concept.exceptions;

import com.example.demo.participant.domain.ParticipantName;

public class ParticipantNotAuthorized extends RuntimeException {
    public ParticipantNotAuthorized(ParticipantName participantName) {
        super("Participant not authorized, please register the partner with the session first");
    }
}
