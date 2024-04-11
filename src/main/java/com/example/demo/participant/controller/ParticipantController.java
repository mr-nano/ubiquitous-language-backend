package com.example.demo.participant.controller;

import com.example.demo.participant.domain.Participant;
import com.example.demo.participant.domain.ParticipantId;
import com.example.demo.participant.service.ParticipantService;
import com.example.demo.http.parsing.SingleValuePropertyEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ParticipantController {

    @Autowired
    ParticipantService participantService;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(ParticipantId.class, new SingleValuePropertyEditor<>(ParticipantId.class));
//        webDataBinder.registerCustomEditor(ConceptId.class, new SingleValuePropertyEditor<>(ConceptId.class));
//        webDataBinder.registerCustomEditor(ConceptTitle.class, new SingleValuePropertyEditor<>(ConceptTitle.class));
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

}
