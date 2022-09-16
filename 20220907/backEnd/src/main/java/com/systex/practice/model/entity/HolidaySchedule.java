package com.systex.practice.model.entity;

import com.systex.practice.model.entity.type.HolidayType;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "holiday_schedule")
public class HolidaySchedule {

    @Id
    @Column(name = "Date", columnDefinition = "Date")
    private Date date;

    @Enumerated(EnumType.STRING)
    @Column(name = "HolidayType", columnDefinition = "char(5)")
    private HolidayType holidayType;
}
