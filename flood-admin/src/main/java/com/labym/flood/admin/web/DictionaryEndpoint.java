package com.labym.flood.admin.web;


import com.labym.flood.admin.model.dto.DictionaryDTO;
import com.labym.flood.admin.model.entity.Dictionary;
import com.labym.flood.admin.model.entity.DictionaryGroup;
import com.labym.flood.admin.repository.DictionaryGroupRepository;
import com.labym.flood.admin.repository.DictionaryRepository;
import com.labym.flood.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;

@Slf4j
@Transactional(readOnly = true)
@RestController
@RequestMapping("/api/dictionaries")
public class DictionaryEndpoint {

    private final DictionaryRepository dictionaryRepository;
    private final DictionaryGroupRepository dictionaryGroupRepository;

    public DictionaryEndpoint(DictionaryRepository dictionaryRepository, DictionaryGroupRepository dictionaryGroupRepository) {
        this.dictionaryRepository = dictionaryRepository;
        this.dictionaryGroupRepository = dictionaryGroupRepository;
    }

    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody DictionaryDTO dictionaryDTO) {
        if (dictionaryRepository.existsByNameOrCode(dictionaryDTO.getName(), dictionaryDTO.getCode())) {
            throw new BadRequestException("dictionary 's name or code already used");
        }
        Dictionary dictionary = new Dictionary();
        dictionary.setCode(dictionaryDTO.getCode());
        dictionary.setName(dictionaryDTO.getName());
        dictionary.setValue(dictionaryDTO.getValue());

        DictionaryGroup group = dictionaryGroupRepository.findById(dictionaryDTO.getGroupId())
                .orElseThrow(() -> new BadRequestException("dictionary group  not exists"));


        dictionary.setGroup(group);
        dictionaryRepository.save(dictionary);
        log.info("created dictionary group:{}", dictionaryDTO);
    }


    @GetMapping
    public ResponseEntity list() {
        return ResponseEntity.ok(dictionaryRepository.findAll());
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        dictionaryRepository.deleteById(id);
    }

}
