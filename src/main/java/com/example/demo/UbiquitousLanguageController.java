package com.example.demo;

import jakarta.websocket.MessageHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

record ParticipantId (String id) {}
record ParticipantName (String name) {}
record ProfileThumbnailLink (String link) {}
class Participant {
    ParticipantId participantId;
    ParticipantName participantName;
    ProfileThumbnailLink profileThumbnailLink;
}

record ConceptTitle (String title) {}

class Concept {

}

class Definition {
    Participant participant;

}



@RestController
public class UbiquitousLanguageController {

    /*
    Returns the list of participants in the current session
    DependsOn:: SessionId (from auth token)
     */
    @GetMapping("/participant")
    public List<Participant> participants() {
        return List.of();
    }

    @GetMapping("/definition")
    public List<Definition> definition(String participantId) {
        return List.of();
    }
}
