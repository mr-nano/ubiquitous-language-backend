package com.example.demo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

record ParticipantId(
        @JsonValue String id
) {
}

record ParticipantName(
        @JsonValue String name
) {
}

record ProfileThumbnailLink(
        @JsonValue String link
) {
}

@AllArgsConstructor
@NoArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
class Participant {
    @JsonProperty("participantId")
    ParticipantId participantId;
    @JsonProperty("participantName")
    ParticipantName participantName;
    @JsonProperty("profileThumbnailLink")
    ProfileThumbnailLink profileThumbnailLink;
}

record ConceptTitle(@JsonValue String title) {
}

record ConceptId(@JsonValue String id) {
}

@AllArgsConstructor
@NoArgsConstructor
class Concept {
    @JsonProperty("conceptId")
    ConceptId conceptId;

    @JsonProperty("conceptTitle")
    ConceptTitle conceptTitle;
}

@AllArgsConstructor
@NoArgsConstructor
class ConceptCreationRequest {
    @JsonProperty("title")
    String conceptTitle;

    @Override
    public String toString() {
        return "ConceptCreationRequest{" +
               "conceptTitle='" + conceptTitle + '\'' +
               '}';
    }
}

@AllArgsConstructor
@NoArgsConstructor
class Definition {
    @JsonProperty("participantId")
    ParticipantId participantId;

    @JsonProperty("conceptId")
    ConceptId conceptId;

    @JsonProperty("definition")
    String definition;

    @JsonProperty("linkedConcepts")
    List<ConceptId> linkedConcepts;

}


class SingleValuePropertyEditor<T> extends PropertyEditorSupport {

    private final Class<T> targetType;

    public SingleValuePropertyEditor(Class<T> targetType) {
        this.targetType = targetType;
    }

    @SneakyThrows
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(this.targetType.getDeclaredConstructor(String.class).newInstance(text));
    }
}

@RestController
public class UbiquitousLanguageController {

    List<Concept> concepts = new ArrayList<>();
    AtomicLong atomicLong = new AtomicLong();
    Map<ParticipantId, Participant> participants = Map.of( // Dhruv - realize map is just an index
            new ParticipantId("1"),
            new Participant(
                    new ParticipantId("1"),
                    new ParticipantName("Dhruv"),
                    new ProfileThumbnailLink("link")
            )
    );

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(ParticipantId.class, new SingleValuePropertyEditor<>(ParticipantId.class));
        webDataBinder.registerCustomEditor(ConceptId.class, new SingleValuePropertyEditor<>(ConceptId.class));
        webDataBinder.registerCustomEditor(ConceptTitle.class, new SingleValuePropertyEditor<>(ConceptTitle.class));
    }

    /*
    Returns the list of participants in the current session
    DependsOn:: SessionId (from auth token)
     */
    @GetMapping("/participants")
    public List<Participant> participants() {
        return participants.values().stream().toList();
    }

    @GetMapping("/participants/{participantId}")
    public ResponseEntity<Participant> participant(@PathVariable ParticipantId participantId) {
        Participant participant = participants.get(participantId);
        if(participant == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().body(participant);
    }

    @GetMapping("/concepts")
    public List<Concept> concept() {
        return concepts;
    }

    @GetMapping("/concepts/{conceptId}")
    public ResponseEntity<Concept> concept(@PathVariable ConceptId conceptId) {
        Optional<Concept> maybeConcept = concepts.stream().filter(concept -> concept.conceptId.equals(conceptId)).findFirst();
        return maybeConcept.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/concepts")
    public ResponseEntity<Concept> createConcept(@RequestBody ConceptCreationRequest conceptCreationRequest) {
        long conceptId = atomicLong.incrementAndGet();
        Concept concept = new Concept(new ConceptId(String.valueOf(conceptId)), new ConceptTitle(conceptCreationRequest.conceptTitle));
        concepts.add(concept);
        return ResponseEntity.ok(concept);
    }

    @GetMapping("/definitions")
    public List<Definition> definitions(@RequestParam ParticipantId participantId, @RequestParam ConceptId conceptId) {
        return List.of();
    }
}
