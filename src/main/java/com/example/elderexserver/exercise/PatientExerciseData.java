package com.example.elderexserver.exercise;

import com.example.elderexserver.patient.Patient_Routine;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.util.*;

//Have problem of Actual_Exercise_Detail without Patient_Routine in date range
public class PatientExerciseData {
    List<Week> weeks;
    //Actual_Exercise_Detail group by day done
    List<List<Actual_Exercise_Detail>> exerciseDetails;

    public PatientExerciseData(List<Patient_Routine> patientRoutines, List<List<Actual_Exercise_Detail>> exerciseDetails) {
        for (Patient_Routine patientRoutine : patientRoutines) {
            this.weeks = new ArrayList<>();

            for (int i = 0; i < patientRoutine.getWeek(); i++) {
                Week week = new Week(patientRoutine.getRoutine(), getDate(patientRoutine.getStart_date(), i));
                weeks.add(week);
            }
        }

        this.exerciseDetails = exerciseDetails;
    }

    List<Week> getData() {
        int index = 0;
        for (Week week : weeks) {
            for (Day day : week.getDays()) {
                day.setDone(exerciseDetails.get(index++));
            }
        }
        return weeks;
    }

    public String getJson() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(getData());
    }

    private Date getDate(Date startDate, int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DATE, i * 7);
        return calendar.getTime();
    }

    class Week {
        Day[] days;
        Date date;

        Week(Routine routine, Date date) {
            this.days = new Day[7];
            this.date = date;

            for (int i = 0; i < 7; i++) {
                days[i] = new Day(getDate(date, i));
            }

            for (Routine_exercises routine_exercise : routine.getExercises()) {
                days[routine_exercise.getWeek_day_id()].setGoal(routine_exercise);
            }
        }

        public Day[] getDays() {
            return days;
        }

        private Date getDate(Date startDate, int i) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.add(Calendar.DATE, i);
            return calendar.getTime();
        }
    }

    class Day {
        Date date;
        //Map of Exercises id and Exercises target
        Map<Integer, Integer> exerciseTarget;
        //Map of Exercises id  Exercises done
        Map<Integer, Integer> exerciseDone;
        //List of all exercise done int that day
        List<Actual_Exercise_Detail> exercises;

        Day(Date date) {
            this.exerciseTarget = new HashMap<>();
            this.exerciseDone = new HashMap<>();
            this.date = date;
        }

        void setGoal(Routine_exercises routine_exercise) {
            exerciseTarget.put(routine_exercise.getExercise_id(), exerciseTarget.getOrDefault(routine_exercise.getExercise_id(), 0) + routine_exercise.getRep());
        }

        void setDone(List<Actual_Exercise_Detail> exercises) {
            this.exercises = exercises;

            for (Actual_Exercise_Detail exercise : exercises) {
                exerciseDone.put(exercise.getExercise_id(), exerciseDone.getOrDefault(exercise.getExercise_id(), 0) + exercise.getReps());
            }
        }
    }
}
