package com.example.demo.concept.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public record ConceptTitle(@JsonValue String title) {
}
