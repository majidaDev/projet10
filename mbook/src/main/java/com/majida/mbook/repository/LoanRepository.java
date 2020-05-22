package com.majida.mbook.repository;

import com.majida.mbook.entity.Copy;
import com.majida.mbook.entity.Loan;
import com.majida.mbook.entity.Status;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends CrudRepository<Loan, Long> {

    @Query(value = "SELECT * FROM Loan l WHERE l.id_person = ?", nativeQuery = true)
    List<Loan> getAllLoansPersonId(Long id);

    @Query(value = "Select l from Loan l where l.id_person =:idPerson and l.status in (:status)", nativeQuery = true)
    List<Loan> findByIdPersonAndStatus(Long idPerson, List<Status> status);

    @Query(value = "Select l from Loan l where l.copy =:bookCopy and l.status in (:status)")
    List<Loan> findByBookCopyAndStatus(Copy bookCopy, List<Status> status);

}
