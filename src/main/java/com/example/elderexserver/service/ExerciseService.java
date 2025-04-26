package com.example.elderexserver.service;

import com.example.elderexserver.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExerciseService {
    @Autowired
    private ExerciseRepository exerciseRepository;
}
