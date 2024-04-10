package com.example.demo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;
import java.util.List;

record ParticipantId (
        @JsonValue String id
) {}
record ParticipantName (
        @JsonValue String name
) {}
record ProfileThumbnailLink (
        @JsonValue String link
) {}

@AllArgsConstructor
@NoArgsConstructor
@JsonFormat(shape =  JsonFormat.Shape.OBJECT)
class Participant {
    @JsonProperty("participantId") ParticipantId participantId;
    @JsonProperty("participantName") ParticipantName participantName;
    @JsonProperty("profileThumbnailLink") ProfileThumbnailLink profileThumbnailLink;
}

record ConceptTitle (String title) {}
record ConceptId (String id) {}

class Concept {
    ConceptId conceptId;
    ConceptTitle conceptTitle;
}

class Definition {
    ParticipantId participantId;
    ConceptId conceptId;
    String definition;
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

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(ParticipantId.class, new SingleValuePropertyEditor<>(ParticipantId.class));
    }

    /*
    Returns the list of participants in the current session
    DependsOn:: SessionId (from auth token)
     */
    @GetMapping("/participants")
    public List<Participant> participants() {
        return List.of(
                new Participant(
                        new ParticipantId("ABC"),
                        new ParticipantName("Name"),
                        new ProfileThumbnailLink("link")
                )
        );
    }

    @GetMapping("/participants/{participantId}")
    public Participant participant(@PathVariable ParticipantId participantId) {
        return new Participant(participantId, new ParticipantName("hey"), new ProfileThumbnailLink("link"));
    }

    @GetMapping("/concepts")
    public List<Concept> concepts() {
        return List.of();
    }

    @GetMapping("/concepts/{conceptId}")
    public Concept concepts(@PathVariable ConceptId conceptId) {
        return null;
    }

    @GetMapping("/definitions")
    public List<Definition> definitions(@RequestParam ParticipantId participantId,@RequestParam ConceptId conceptId) {
        return List.of();
    }
}
