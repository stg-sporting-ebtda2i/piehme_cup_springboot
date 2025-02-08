package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.entities.SchoolYear;
import com.stgsporting.piehmecup.exceptions.OsraNotFound;
import com.stgsporting.piehmecup.repositories.OsraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OsraService {
    @Autowired
    private OsraRepository osraRepository;

    public SchoolYear getOsraByName(String name){
        Optional<SchoolYear> osra = osraRepository.findByName(name);
        if(osra.isEmpty())
            throw new OsraNotFound("Osra not found");
        return osra.get();
    }
}
