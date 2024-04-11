package com.example.demo.participant.service;

import com.example.demo.participant.domain.Participant;
import com.example.demo.participant.domain.ParticipantId;
import com.example.demo.participant.domain.ParticipantName;
import com.example.demo.participant.domain.ProfileThumbnailLink;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ParticipantService {

    Map<ParticipantId, Participant> participantsById = Map.of( // Dhruv - realize map is just an index
            new ParticipantId("1"),
            new Participant(
                    new ParticipantId("1"),
                    new ParticipantName("Dhruv"),
                    new ProfileThumbnailLink("link")
            )
    );

    Map<ParticipantName, Participant> participantsByName = Map.of( // Dhruv - realize map is just an index
            new ParticipantName("Dhruv"),
            new Participant(
                    new ParticipantId("1"),
                    new ParticipantName("Dhruv"),
                    new ProfileThumbnailLink("link")
            )
    );


    public List<Participant> getAllParticipants() {
        return participantsById.values().stream().toList();
    }

    public Optional<Participant> getParticipant(ParticipantId participantId) {
        Participant participant = participantsById.get(participantId);
        if (participant == null) {
            return Optional.empty();
        }
        return Optional.of(participant);
    }

    public Optional<Participant> getByName(ParticipantName name) {
        Participant participant = participantsByName.get(name);
        if (participant == null) {
            return Optional.empty();
        }
        return Optional.of(participant);
    }


}
