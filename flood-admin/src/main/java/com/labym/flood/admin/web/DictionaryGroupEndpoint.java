package com.labym.flood.admin.web;


import com.labym.flood.admin.model.dto.DictionaryDTO;
import com.labym.flood.admin.model.entity.Dictionary;
import com.labym.flood.admin.model.entity.DictionaryGroup;
import com.labym.flood.admin.repository.DictionaryGroupRepository;
import com.labym.flood.exception.BadRequestException;
import com.labym.flood.exception.FloodException;
import com.labym.flood.exception.NotFoundException;
import com.labym.flood.web.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Transactional(readOnly = true)
@RestController
@RequestMapping("/api/dictionary/groups")
public class DictionaryGroupEndpoint {

    private final DictionaryGroupRepository dictionaryGroupRepository;

    public DictionaryGroupEndpoint(DictionaryGroupRepository dictionaryGroupRepository) {
        this.dictionaryGroupRepository = dictionaryGroupRepository;
    }

    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody DictionaryGroup group){
        if (dictionaryGroupRepository.existsByNameOrCode(group.getName(),group.getCode())) {
            throw  new BadRequestException("dictionary group's name or code already used");
        }
        group.setCreateAt(ZonedDateTime.now());
        dictionaryGroupRepository.save(group);
        log.info("created dictionary group:{}",group);
    }


    @GetMapping
    public ResponseEntity list(){
      return   ResponseEntity.ok(dictionaryGroupRepository.findAll());
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        dictionaryGroupRepository.deleteById(id);
    }


    @GetMapping(path = "/{id}/dictionaries")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity dictionaries(@PathVariable Long id){
        Optional<List<DictionaryDTO>> dtos = dictionaryGroupRepository.findById(id)
                .map(group -> group.getDictionaries().stream().map(DictionaryDTO::from).collect(Collectors.toList()));

        return   ResponseUtil.wrapOrNotFound(dtos);
    }
}
