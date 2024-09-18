package co.edu.uniandes.dse.parcialprueba.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.parcialprueba.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialprueba.repositories.EspecialidadRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EspecialidadService {

    @Autowired
    EspecialidadRepository especialidadRepository; 

    @Transactional
    public EspecialidadEntity createEspecialidad(EspecialidadEntity especialidad) throws IllegalOperationException{
        if(especialidad.getNombre() == null) {
            throw new IllegalOperationException("Nombre no valido"); 
        }
        if(especialidad.getDescripcion() == null) {
            throw new IllegalOperationException("Descripcion tiene que ser valida"); 
        }
        if(especialidad.getDescripcion().length() < 10) {
            throw new IllegalOperationException("Descripcion tiene que ser minimo 10 caracteres"); 
        }
        return especialidadRepository.save(especialidad); 
    }
    
}
