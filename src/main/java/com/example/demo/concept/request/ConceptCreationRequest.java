package com.example.demo.concept.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ConceptCreationRequest {
    @JsonProperty("title")
    String conceptTitle;

    @Override
    public String toString() {
        return "ConceptCreationRequest{" +
               "conceptTitle='" + conceptTitle + '\'' +
               '}';
    }
}
