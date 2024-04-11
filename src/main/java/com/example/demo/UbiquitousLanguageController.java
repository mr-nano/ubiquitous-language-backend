package com.example.demo;

import com.example.demo.concept.domain.Concept;
import com.example.demo.concept.domain.ConceptId;
import com.example.demo.concept.domain.ConceptTitle;
import com.example.demo.definition.domain.Definition;
import com.example.demo.participant.domain.ParticipantId;
import com.example.demo.participant.service.ParticipantService;
import com.example.demo.http.parsing.SingleValuePropertyEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
public class UbiquitousLanguageController {

    @Autowired
    ParticipantService participantService;

    List<Concept> concepts = new ArrayList<>();




}
