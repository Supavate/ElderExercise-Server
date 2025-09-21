package com.example.elderexserver.repository;

import com.example.elderexserver.data.exercise.DTO.ExerciseSessionDetailListView;
import com.example.elderexserver.data.routine.DTO.*;
import com.example.elderexserver.data.routine.Patient_Routine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PatientRoutineRepository extends JpaRepository<Patient_Routine, Integer> {

    @Query(value = """
        SELECT
            e.name AS exercise_name,
            esd.*
        FROM
            exercise_session_detail esd
        JOIN exercise_session es ON
            esd.session_id = es.id
        JOIN patient_routine pr ON
            es.patient_routine_id = pr.id
        JOIN patient p ON
            pr.patient_id = p.id
        JOIN exercise e ON
            esd.exercise_id = e.id
        WHERE
            p.id =:patientId AND DATE(esd.start_time) =:date
        ORDER BY
            es.start_time,
            esd.start_time;
    """, nativeQuery = true)
    List<ExerciseSessionDetailListView> findActualExerciseDetailListByPatientIdAndDate(@Param("date") String date, @Param("patientId") Integer patientId);

    @Query(value = """
        SELECT
            r.name AS routine_name,
            r.description AS routine_description,
            pr.id AS patient_routine_id,
            pr.start_date,
            pr.end_date,
            e.name AS exercise_name,
            re.rep,
            re.set,
            re.day
        FROM
            patient_routine pr
        LEFT JOIN ROUTINE r ON
            pr.routine_id = r.id
        LEFT JOIN routine_exercises re ON
            re.routine_id = pr.routine_id
        LEFT JOIN exercise e ON
            re.exercise_id = e.id
        WHERE
            pr.patient_id = :patientId
    """, nativeQuery = true)
    List<PatientRoutineView> findPatientRoutineByPatientId(@Param("patientId") Integer patientId);

    @Query(value = """
        SELECT
            r.name AS routine_name,
            r.description AS routine_description,
            pr.id AS patient_routine_id,
            pr.start_date,
            pr.end_date,
            e.name AS exercise_name,
            re.rep,
            re.set,
            re.day
        FROM
            patient_routine pr
        LEFT JOIN ROUTINE r ON
            pr.routine_id = r.id
        LEFT JOIN routine_exercises re ON
            re.routine_id = pr.routine_id
        LEFT JOIN exercise e ON
            re.exercise_id = e.id
        WHERE
            pr.patient_id = 1 AND pr.start_date < NOW() AND pr.end_date > NOW()
        LIMIT 1;
    """, nativeQuery = true)
    PatientRoutineView findCurrentPatientRoutineByPatientId(@Param("patientId") Integer patientId);
}
