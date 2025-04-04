package com.example.s380f_gp_xd.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String question;

    @ElementCollection
    private List<String> options = new ArrayList<>();  // Exactly 4 options

    @ElementCollection
    private Map<String, Integer> votes = new HashMap<>();  // Option -> Vote count

    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    public Poll() {
        options.add("Option 1");
        options.add("Option 2");
        options.add("Option 3");
        options.add("Option 4");
        for (String option : options) {
            votes.put(option, 0);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }
    public List<String> getOptions() { return options; }
    public void setOptions(List<String> options) { this.options = options; }
    public Map<String, Integer> getVotes() { return votes; }
    public void setVotes(Map<String, Integer> votes) { this.votes = votes; }
    public List<Comment> getComments() { return comments; }
    public void setComments(List<Comment> comments) { this.comments = comments; }
}