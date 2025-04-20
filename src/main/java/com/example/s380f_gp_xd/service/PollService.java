package com.example.s380f_gp_xd.service;


import com.example.s380f_gp_xd.entity.Option;
import com.example.s380f_gp_xd.entity.Poll;
import com.example.s380f_gp_xd.repository.OptionRepository;
import com.example.s380f_gp_xd.repository.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PollService {

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private OptionRepository optionRepository;

    // Create poll
    public void createPoll(Poll poll) {
        for (Option option : poll.getOptions()) {
            option.setPoll(poll);
        }
        pollRepository.save(poll);
    }

    // Vote
    public void vote(Long optionId) {
        Option option = optionRepository.findById(optionId)
                .orElseThrow(() -> new IllegalArgumentException("Option not found"));
        option.setVoteCount(option.getVoteCount() + 1);
        optionRepository.save(option);
    }

    // Get polls
    public List<Poll> getAllPolls() {
        return pollRepository.findAll();
    }

    // Get poll by ID
    public Poll getPollById(Long id) {
        return pollRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Poll not found"));
    }
}