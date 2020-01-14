package com.majida.mloanmanagement.service;

import com.majida.mloanmanagement.entity.Copy;
import com.majida.mloanmanagement.repository.CopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CopyService {

    @Autowired
    private CopyRepository copyRepository;

    public Set<Copy> getAllCopies() {
        Set<Copy> copies = new HashSet<Copy>();
        copyRepository.findAll()
                .forEach(copies::add);
        return copies;
    }

    public Set<Copy> getCopiesByBookId(Long id) {
        Set<Copy> copies = copyRepository.getAllCopiesByBookId(id);
        copies.forEach(copies::add);
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
