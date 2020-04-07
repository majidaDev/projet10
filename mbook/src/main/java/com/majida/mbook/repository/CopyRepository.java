package com.majida.mbook.repository;

import com.majida.mbook.entity.Copy;
import com.majida.mbook.entity.Loan;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CopyRepository extends CrudRepository<Copy, Long> {

    @Query(value = "SELECT * FROM Copy c WHERE c.book_id = ?", nativeQuery = true)
    List<Copy> getAllCopiesByBookId(Long id);



}
