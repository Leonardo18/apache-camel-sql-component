package com.apache.camel.sql.component.api;

import org.springframework.http.ResponseEntity;

import java.net.URI;

public class ApiBase {

    protected <T> ResponseEntity<T> ok(T body) {
        return ResponseEntity.ok(body);
    }

    <T> ResponseEntity<T> created(URI uri, T body) {
        return ResponseEntity.created(uri).body(body);
    }
}
