package com.webservice.graduate_coach.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Student {
    private String user_name;
    private String depart_name;
    private String major_name;
    private String id;
    private Integer year;
    private Float total_credit;
    private Float require_credit;
    private Float foundation_major_credit;
    private Float foundation_edu_credit;
    private Float essential_general_edu_credit;
    private Float optional_general_edu_credit;
    private Float advanced_course_credit;
    private Boolean foreign;
    private Boolean communication;

    private Integer UID;
    private Integer depart_uid;
    private Integer major_uid;
}
