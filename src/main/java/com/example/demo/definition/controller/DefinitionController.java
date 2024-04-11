package com.example.demo.definition.controller;

import com.example.demo.concept.domain.ConceptId;
import com.example.demo.concept.domain.ConceptTitle;
import com.example.demo.definition.domain.Definition;
import com.example.demo.http.parsing.SingleValuePropertyEditor;
import com.example.demo.participant.domain.ParticipantId;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DefinitionController {

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(ParticipantId.class, new SingleValuePropertyEditor<>(ParticipantId.class));
        webDataBinder.registerCustomEditor(ConceptId.class, new SingleValuePropertyEditor<>(ConceptId.class));
        webDataBinder.registerCustomEditor(ConceptTitle.class, new SingleValuePropertyEditor<>(ConceptTitle.class));
    }

    @GetMapping("/definitions")
    public List<Definition> definitions(@RequestParam ParticipantId participantId, @RequestParam ConceptId conceptId) {
        return List.of();
    }

}
