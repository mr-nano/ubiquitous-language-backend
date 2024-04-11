package com.example.demo.concept.controller;

import com.example.demo.concept.domain.Concept;
import com.example.demo.concept.domain.ConceptTitle;
import com.example.demo.concept.exceptions.ParticipantNotAuthorized;
import com.example.demo.concept.request.ConceptCreationRequest;
import com.example.demo.participant.domain.Participant;
import com.example.demo.participant.domain.ParticipantId;
import com.example.demo.participant.domain.ParticipantName;
import com.example.demo.participant.domain.ProfileThumbnailLink;
import com.example.demo.participant.service.ParticipantService;
import com.example.demo.user.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;


@SpringBootTest
class ConceptControllerTest {

    @Mock
    private ParticipantService participantService;

    @Mock
    private UserService userService;

    @InjectMocks
    private ConceptController conceptController;

    @Test
    void contextLoads() {
    }

    @Test
    void dontAllowCreationOfConceptWhenParticipantIsNotRecognised() {
        _given_Dhruv_is_a_valid_user();
        _given_Dhruv_is_not_a_participant();

        _assert_that_request_fails_with_ParticipantNotAuthorized_exception();
    }

    @Test
    void createConceptWhenParticipantIsRecognised() {
        _given_Dhruv_is_a_valid_user();
        _given_Dhruv_is_a_participant();

        ResponseEntity<Concept> conceptResponse = _when_a_concept_is_created();

        // HTTP assertions
        _assert_request_is_successful(conceptResponse);
        _assert_there_is_a_body(conceptResponse);

        // data specific assertions
        _assert_title_matches(conceptResponse);
        _assert_concept_id_is_present(conceptResponse);
    }

    @SuppressWarnings("ConstantConditions")
    private static void _assert_concept_id_is_present(ResponseEntity<Concept> conceptResponse) {
        Assertions.assertNotNull(conceptResponse.getBody().getConceptId());
    }

    private static void _assert_title_matches(ResponseEntity<Concept> conceptResponse) {
        //noinspection ConstantConditions
        Assertions.assertEquals(conceptResponse.getBody().getConceptTitle(), new ConceptTitle("Concept title"));
    }

    private static void _assert_there_is_a_body(ResponseEntity<Concept> conceptResponse) {
        Assertions.assertTrue(conceptResponse.hasBody());
    }

    private static void _assert_request_is_successful(ResponseEntity<Concept> conceptResponse) {
        Assertions.assertEquals(conceptResponse.getStatusCode(), HttpStatus.OK);
    }

    private ResponseEntity<Concept> _when_a_concept_is_created() {
        return conceptController.createConcept(new ConceptCreationRequest("Concept title"));
    }

    private void _assert_that_request_fails_with_ParticipantNotAuthorized_exception() {
        Assertions.assertThrows(ParticipantNotAuthorized.class, () -> {
            conceptController.createConcept(new ConceptCreationRequest("Hey"));
        });
    }

    @SuppressWarnings("UnusedReturnValue")
    private OngoingStubbing<Optional<Participant>> _given_Dhruv_is_not_a_participant() {
        return Mockito.when(participantService.getByName(ArgumentMatchers.any())).thenReturn(Optional.empty());
    }

    @SuppressWarnings("UnusedReturnValue")
    private OngoingStubbing<Optional<Participant>> _given_Dhruv_is_a_participant() {
        return Mockito.when(participantService.getByName(ArgumentMatchers.any())).thenReturn(Optional.of(
                Participant.builder()
                        .participantId(new ParticipantId("1"))
                        .participantName(new ParticipantName("Dhruv"))
                        .profileThumbnailLink(new ProfileThumbnailLink("link"))
                        .build()
        ));
    }

    @SuppressWarnings("UnusedReturnValue")
    private OngoingStubbing<String> _given_Dhruv_is_a_valid_user() {
        return Mockito.when(userService.getUsername()).thenReturn("Dhruv");
    }
}