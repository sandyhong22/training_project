package com.example.backend_project.repository;

import com.example.backend_project.entity.Attendance;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findAllByUsername(String username);

    List<Attendance> findAllByDate(LocalDate date);

    @Query(value = "SELECT a FROM Attendance AS a WHERE a.username= :username AND a.date= :date")
    Optional<Attendance> existByUsernameAndDate(@Param("username") String username, @Param("date") LocalDate date);

    @Query(value = "SELECT a.clockOutTime FROM Attendance AS a WHERE a.username= :username AND a.date= :date")
    Optional<Attendance> existClockOutTimeByUsernameAndDate(@Param("username") String username, @Param("date") LocalDate date);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Attendance AS a SET a.clockOutTime=:clockOutTime  WHERE a.username=:username AND a.date=:date")
    void clockOut(@Param("clockOutTime") LocalDateTime clockOutTime, @Param("username") String username, @Param("date") LocalDate date);
}