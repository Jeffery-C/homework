package com.systex.practice.model;

import com.systex.practice.model.entity.HolidaySchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface HolidayScheduleRepository extends JpaRepository<HolidaySchedule, Date> {
}
