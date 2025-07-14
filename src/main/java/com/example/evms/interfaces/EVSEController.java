package com.example.evms.interfaces;

import com.example.evms.application.EVSEService;
import com.example.evms.domain.EVSE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/evses")
public class EVSEController {
    @Autowired
    private EVSEService evseService;

    @PostMapping
    public ResponseEntity<EVSE> addEVSE(@RequestBody EVSE evse) {
        EVSE saved = evseService.addEVSE(evse);
        return new ResponseEntity<>(saved, org.springframework.http.HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<EVSE> changeStatus(@RequestBody EVSE evse) {
        return ResponseEntity.ok(evseService.changeStatus(evse));
    }
} 