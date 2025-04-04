package com.example.s380f_gp_xd.controller;

import com.example.s380f_gp_xd.entity.Poll;
import com.example.s380f_gp_xd.entity.Comment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@WebServlet({"/PollServlet", "/index"})
public class PollServlet extends HttpServlet {
    private EntityManager em;

    @Override
    public void init() throws ServletException {
        em = Persistence.createEntityManagerFactory("CoursePU").createEntityManager();
        if (em.createQuery("SELECT p FROM Poll p", Poll.class).getResultList().isEmpty()) {
            Poll samplePoll = new Poll();
            samplePoll.setQuestion("Which date do you prefer for the mid-term test?");
            samplePoll.setOptions(Arrays.asList("October 10", "October 15", "October 20", "October 25"));
            samplePoll.setVotes(new HashMap<>());
            for (String option : samplePoll.getOptions()) {
                samplePoll.getVotes().put(option, 0);
            }
            em.getTransaction().begin();
            em.persist(samplePoll);
            em.getTransaction().commit();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String path = request.getServletPath();

        if ("/index".equals(path)) {
            List<Poll> polls = em.createQuery("SELECT p FROM Poll p", Poll.class).getResultList();
            out.println("<!DOCTYPE html><html><head><title>Online Course</title>");
            out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css' rel='stylesheet'>");
            out.println("</head><body><div class='container'>");
            out.println("<h1>Course Polls</h1><h2>Available Polls</h2>");
            out.println("<ul class='list-group'>");
            for (Poll poll : polls) {
                out.println("<li class='list-group-item'><a href='PollServlet?id=" + poll.getId() + "'>" + poll.getQuestion() + "</a></li>");
            }
            out.println("</ul></div></body></html>");
        } else {
            Long id = Long.parseLong(request.getParameter("id"));
            Poll poll = em.find(Poll.class, id);
            out.println("<!DOCTYPE html><html><head><title>" + poll.getQuestion() + "</title>");
            out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css' rel='stylesheet'>");
            out.println("</head><body><div class='container'>");
            out.println("<h1>" + poll.getQuestion() + "</h1>");
            out.println("<h3>Options</h3>");
            out.println("<form action='PollServlet' method='post'>");
            out.println("<input type='hidden' name='id' value='" + poll.getId() + "'>");
            int i = 0;
            for (String option : poll.getOptions()) {
                out.println("<div class='form-check'>");
                out.println("<input class='form-check-input' type='radio' name='vote' value='" + option + "' id='option" + i + "'>");
                out.println("<label class='form-check-label' for='option" + i + "'>" + option + " (" + poll.getVotes().get(option) + " votes)</label>");
                out.println("</div>");
                i++;
            }
            out.println("<button type='submit' class='btn btn-primary mt-2'>Vote</button>");
            out.println("</form>");
            out.println("<h3>Comments</h3><ul class='list-group mb-3'>");
            for (Comment comment : poll.getComments()) {
                out.println("<li class='list-group-item'>" + comment.getText() + "</li>");
            }
            out.println("</ul>");
            out.println("<form action='PollServlet' method='post'>");
            out.println("<input type='hidden' name='id' value='" + poll.getId() + "'>");
            out.println("<textarea name='comment' class='form-control mb-2' placeholder='Add a comment'></textarea>");
            out.println("<button type='submit' class='btn btn-primary'>Add Comment</button>");
            out.println("</form></div></body></html>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Poll poll = em.find(Poll.class, id);

        String selectedOption = request.getParameter("vote");
        if (selectedOption != null) {
            em.getTransaction().begin();
            poll.getVotes().put(selectedOption, poll.getVotes().get(selectedOption) + 1);
            em.merge(poll);
            em.getTransaction().commit();
        }

        String commentText = request.getParameter("comment");
        if (commentText != null && !commentText.trim().isEmpty()) {
            Comment comment = new Comment();
            comment.setText(commentText);
            em.getTransaction().begin();
            poll.getComments().add(comment);
            em.merge(poll);
            em.getTransaction().commit();
        }

        response.sendRedirect("PollServlet?id=" + id);
    }
}