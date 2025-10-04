package dev.affoysal.backend.Enumeration;

import dev.affoysal.backend.Constant.Constants;

public enum Authority {
    STUDENT(Constants.STUDENT_AUTHORITIES),
    INSTRUCTOR(Constants.INSTRUCTOR_AUTHORITIES),
    ADMIN(Constants.ADMIN_AUTHORITIES);

    private final String value;

    Authority(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
