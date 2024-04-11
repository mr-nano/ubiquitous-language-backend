package com.example.demo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.servlet.http.Part;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

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
@Builder
class Concept {
    @JsonProperty("conceptId")
    ConceptId conceptId;

    @JsonProperty("conceptTitle")
    ConceptTitle conceptTitle;

    @JsonProperty("createdBy")
    ParticipantId createdBy;
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

@Service
class ParticipantService {

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


    List<Participant> getAllParticipants() {
        return participantsById.values().stream().toList();
    }

    Optional<Participant> getParticipant(ParticipantId participantId) {
        Participant participant = participantsById.get(participantId);
        if (participant == null) {
            return Optional.empty();
        }
        return Optional.of(participant);
    }

    Optional<Participant> getByName(ParticipantName name) {
        Participant participant = participantsByName.get(name);
        if (participant == null) {
            return Optional.empty();
        }
        return Optional.of(participant);
    }


}

class ParticipantNotAuthorized extends RuntimeException {
    public ParticipantNotAuthorized(ParticipantName participantName) {
        super("Participant not authorized, please register the partner with the session first");
    }
}

@ControllerAdvice
class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ParticipantNotAuthorized.class)
    public ResponseEntity<Object> handleParticipantNotAuthorized(ParticipantNotAuthorized participantNotAuthorized, WebRequest webRequest) {
        HttpStatusCode statusCode = HttpStatusCode.valueOf(HttpStatus.UNAUTHORIZED.value());
        ErrorResponse errorResponse = ErrorResponse.builder(participantNotAuthorized, statusCode, "E001" ).build();
        return handleExceptionInternal(participantNotAuthorized, errorResponse, new HttpHeaders(), statusCode, webRequest);
    }
}

@RestController
public class UbiquitousLanguageController {

    @Autowired
    ParticipantService participantService;

    List<Concept> concepts = new ArrayList<>();
    AtomicLong atomicLong = new AtomicLong();

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
        return participantService.getAllParticipants();
    }

    @GetMapping("/participants/{participantId}")
    public ResponseEntity<Participant> participant(@PathVariable ParticipantId participantId) {
        Optional<Participant> participant = participantService.getParticipant(participantId);
        return participant.map(p -> ResponseEntity.ok().body(p)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
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

    /*
    curl -i -u user:user -X POST \
      http://localhost:8080/concepts \
      -H 'Content-Type: application/json' \
      -d '{
            "title": "title"
    }'
     */
    @PostMapping("/concepts")
    public ResponseEntity<Concept> createConcept(@RequestBody ConceptCreationRequest conceptCreationRequest) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        System.out.println("the user is " + userDetails.getUsername());
        System.out.println("the user's password is " + userDetails.getPassword());
        System.out.println("the user authorities are " + userDetails.getAuthorities().stream().toList());

        ParticipantName participantName = new ParticipantName(userDetails.getUsername());

        Optional<Participant> byName = participantService.getByName(participantName);

        if (byName.isEmpty()) {
            throw new ParticipantNotAuthorized(participantName);
        }

        ParticipantId participantId = byName.get().participantId;

        long conceptId = atomicLong.incrementAndGet();
        Concept concept = Concept.builder()
                .conceptId(new ConceptId(String.valueOf(conceptId)))
                .conceptTitle(new ConceptTitle(conceptCreationRequest.conceptTitle))
                .createdBy(participantId)
                .build();
        concepts.add(concept);
        return ResponseEntity.ok(concept);
    }

    @GetMapping("/definitions")
    public List<Definition> definitions(@RequestParam ParticipantId participantId, @RequestParam ConceptId conceptId) {
        return List.of();
    }
}
