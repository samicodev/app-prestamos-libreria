package com.samicodev.model;

import java.time.LocalDate;

public record LoanReport(
        String title,
        String author,
        int year,
        LocalDate loanDate,
        LocalDate returnDate,
        String studentName
) {}
