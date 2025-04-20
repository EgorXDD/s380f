package com.example.s380f_gp_xd.controller;

import com.example.s380f_gp_xd.entity.Poll;
import com.example.s380f_gp_xd.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/polls")
public class PollController {

    @Autowired
    private PollService pollService;

    // Display all polls
    @GetMapping
    public String listPolls(Model model) {
        model.addAttribute("polls", pollService.getAllPolls());
        return "poll";
    }

    // Show form to create a new poll
    @GetMapping("/new")
    public String showPollForm(Model model) {
        model.addAttribute("poll", new Poll());
        return "poll-form";
    }

    // Handle poll creation
    @PostMapping
    public String createPoll(@ModelAttribute Poll poll) {
        pollService.createPoll(poll);
        return "redirect:/polls";
    }

    // Display a poll and its options
    @GetMapping("/{id}")
    public String viewPoll(@PathVariable Long id, Model model) {
        Poll poll = pollService.getPollById(id);
        model.addAttribute("poll", poll);
        return "poll-view";
    }

    // Handle voting
    @PostMapping("/{pollId}/vote")
    public String vote(@PathVariable Long pollId, @RequestParam Long optionId) {
        pollService.vote(optionId);
        return "redirect:/polls/" + pollId;
    }
}