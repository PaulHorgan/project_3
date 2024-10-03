package com.legacy.demo;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/Issues")
public class IssueController {
    private final IssueService  service;

    public IssueController(IssueService service){this.service = service;}

    @GetMapping("/getAll")
    public List<IssueDto> getAll() {
        return this.service.getAll();
    }


    @PostMapping("/new")
    public ResponseEntity<ResponseEntity<?>> addIssue(@RequestBody Issue issue) {
        ResponseEntity<?> savedIssue = service.addIssue(issue);
        return ResponseEntity.ok(savedIssue);  // Return the saved item as JSON
    }
}
