package com.legacy.demo;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssueService {
    private final IssueRepo repo;

    public IssueService(IssueRepo repo) {this.repo = repo;}

    public List<IssueDto> getAll() {return repo.findAll().stream().map(IssueDto::new).toList();}

    // Updated method to return the newly created item as an ItemDto
    public ResponseEntity<IssueDto> addIssue(Issue issue) {
        Issue savedIssue = repo.save(issue); // Save the item and return the saved instance
        return ResponseEntity.ok(new IssueDto(savedIssue)); // Return the saved item as an ItemDto response
    }


}
