package com.samicodev.service.impl;

import com.samicodev.model.LoanReport;
import com.samicodev.exception.DuplicateLoanException;
import com.samicodev.exception.ErrorMessages;
import com.samicodev.exception.LoanNotFoundException;
import com.samicodev.model.Loan;
import com.samicodev.service.ILoanService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoanServiceImpl implements ILoanService {
    private List<Loan> loans = new ArrayList<>();

    @Override
    public void registerLoan(Loan loan) {
        boolean isDuplicateLoan = loans.stream()
                .anyMatch(existingLoan -> existingLoan.getBook().equals(loan.getBook())
                        && existingLoan.getStudent().equals(loan.getStudent()));

        if (isDuplicateLoan) {
//            throw new DuplicateLoanException("Loan already exists for Book ID: " +
//                    loan.getBook().getTitle() + " and Student ID: " + loan.getStudent().getName());
            throw new DuplicateLoanException(ErrorMessages.DUPLICATE_LOAN.formatMessage(
                    loan.getBook().getTitle(), loan.getStudent().getName()));
        }

        loans.add(loan);
    }

    @Override
    public List<Loan> listLoans() {
        return loans;
    }

    @Override
    public List<Loan> filterLoansByDateRange(LocalDate startDate, LocalDate endDate) {
        return loans.stream()
                .filter(loan -> loan.getLoanDate().isAfter(startDate) && loan.getLoanDate().isBefore(endDate))
                .toList();
    }

    @Override
    public List<Loan> filterLoansByDniStudent(String studentDni) {
        return loans.stream()
                .filter(loan -> loan.getStudent().getDni().equals(studentDni))
                .toList();
    }

    @Override
    public Loan findLoanById(String loanId) {
        return loans.stream()
                .filter(loan -> loan.getId().equals(loanId))
                .findFirst()
                //.orElseThrow(() -> new LoanNotFoundException("Loan not found with ID: " + loanId));
                .orElseThrow(() -> new LoanNotFoundException(
                        ErrorMessages.LOAN_NOT_FOUND.formatMessage(loanId)
                ));
    }


    //loan.getLoanDate().isEqual(startDate) || loan.getLoanDate().isAfter(startDate):
    // Esta parte del filtro verifica si la fecha de préstamo (loanDate) es igual o posterior a la fecha de inicio (startDate).
    // Está permitiendo préstamos que comienzan en la fecha de inicio o después.
    //
    //loan.getLoanDate().isEqual(endDate) || loan.getLoanDate().isBefore(endDate):
    // Esta parte verifica si la fecha de préstamo (loanDate) es igual o anterior a la fecha de finalización (endDate).
    // Está permitiendo préstamos que terminan en la fecha de finalización o antes.
    @Override
    public List<LoanReport> getLoansByDateRange(LocalDate startDate, LocalDate endDate) {
        return loans.stream()
                .filter(loan -> (loan.getLoanDate().isEqual(startDate) || loan.getLoanDate().isAfter(startDate))
                        && (loan.getLoanDate().isEqual(endDate) || loan.getLoanDate().isBefore(endDate)))
                .map(loan -> new LoanReport(
                        loan.getBook().getTitle(),
                        loan.getBook().getAuthor(),
                        loan.getBook().getYear(),
                        loan.getLoanDate(),
                        loan.getReturnDate(),
                        loan.getStudent().getName()
                ))
                .toList();
    }


}
