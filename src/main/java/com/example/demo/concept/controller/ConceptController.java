package com.example.demo.concept.controller;

import com.example.demo.concept.domain.Concept;
import com.example.demo.concept.domain.ConceptId;
import com.example.demo.concept.domain.ConceptTitle;
import com.example.demo.concept.exceptions.ParticipantNotAuthorized;
import com.example.demo.concept.request.ConceptCreationRequest;
import com.example.demo.http.parsing.SingleValuePropertyEditor;
import com.example.demo.participant.domain.Participant;
import com.example.demo.participant.domain.ParticipantId;
import com.example.demo.participant.domain.ParticipantName;
import com.example.demo.participant.service.ParticipantService;
import com.example.demo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ConceptController {

    @Autowired
    ParticipantService participantService;

    @Autowired
    UserService userService;

    AtomicLong atomicLong = new AtomicLong();

    List<Concept> concepts = new ArrayList<>();

    @GetMapping("/concepts")
    public List<Concept> concept() {
        return concepts;
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(ParticipantId.class, new SingleValuePropertyEditor<>(ParticipantId.class));
        webDataBinder.registerCustomEditor(ConceptId.class, new SingleValuePropertyEditor<>(ConceptId.class));
        webDataBinder.registerCustomEditor(ConceptTitle.class, new SingleValuePropertyEditor<>(ConceptTitle.class));
    }

    @GetMapping("/concepts/{conceptId}")
    public ResponseEntity<Concept> concept(@PathVariable ConceptId conceptId) {
        Optional<Concept> maybeConcept = concepts.stream().filter(concept -> concept.getConceptId().equals(conceptId)).findFirst();
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
        ParticipantName participantName = new ParticipantName(userService.getUsername());

        Optional<Participant> byName = participantService.getByName(participantName);

        if (byName.isEmpty()) {
            throw new ParticipantNotAuthorized(participantName);
        }

        ParticipantId participantId = byName.get().getParticipantId();

        long conceptId = atomicLong.incrementAndGet();
        Concept concept = Concept.builder()
                .conceptId(new ConceptId(String.valueOf(conceptId)))
                .conceptTitle(new ConceptTitle(conceptCreationRequest.getConceptTitle()))
                .createdBy(participantId)
                .build();
        concepts.add(concept);
        return ResponseEntity.ok(concept);
    }
}
