package com.example.demo.concept.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public record ConceptId(@JsonValue String id) {
}
