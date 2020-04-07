package com.majida.mbook.service;

import com.majida.mbook.entity.Copy;
import com.majida.mbook.entity.Loan;
import com.majida.mbook.repository.CopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CopyService {

    @Autowired
    private CopyRepository copyRepository;

    public List<Copy> getAllCopies() {
        List<Copy> copies = new ArrayList<Copy>();
        copyRepository.findAll()
                .forEach(copies::add);
        return copies;
    }

    public List<Copy> getCopiesByBookId(Long id) {
        List<Copy> copies = copyRepository.getAllCopiesByBookId(id);
        return copies;
    }


    public Optional<Copy> getCopy(Long id) {
        return copyRepository.findById(id);
    }

    public void addCopy(Copy copy) {
        copyRepository.save(copy);
    }

    public void updateCopy(Long id, Copy copy) {
        copyRepository.save(copy);
    }

    public void deleteCopy(Long id) {
        copyRepository.deleteById(id);
    }
}
