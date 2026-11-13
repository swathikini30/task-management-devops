

package com.example.taskapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@SpringBootApplication
@RestController
public class TaskApiApplication {

    private final List<Task> tasks = new CopyOnWriteArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    public static void main(String[] args) {
        SpringApplication.run(TaskApiApplication.class, args);
    }

    record Task(Long id, String title, boolean completed) {}

    @GetMapping("/tasks")
    public List<Task> getTasks() {
        return tasks;
    }

    @PostMapping("/tasks")
    public Task addTask(@RequestBody Map<String, String> request) {
        Task task = new Task(
            counter.incrementAndGet(),
            request.get("title"),
            false
        );
        tasks.add(task);
        return task;
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP");
    }
}

