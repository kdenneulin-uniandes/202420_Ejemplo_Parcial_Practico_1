package co.edu.uniandes.dse.parcialprueba.services;

import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialprueba.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialprueba.repositories.EspecialidadRepository;
import co.edu.uniandes.dse.parcialprueba.repositories.MedicoRepository;
import jakarta.transaction.Transactional;
import co.edu.uniandes.dse.parcialprueba.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MedicoEspecialidadService {
    @Autowired
    EspecialidadRepository especialidadRepository; 

    @Autowired
    MedicoRepository medicoRepository; 

    @Transactional
    public EspecialidadEntity addEspecialidad(Long idMedico, Long idEspecialidad) throws EntityNotFoundException {
        log.info("Inicia proceso de agregarle la especialidad "+idEspecialidad+"al medico "+idMedico);
        Optional<MedicoEntity> medicoEntity = medicoRepository.findById(idMedico) ; 
        Optional<EspecialidadEntity> especialidadEntity = especialidadRepository.findById(idEspecialidad); 
        if(medicoEntity.isEmpty()) throw new EntityNotFoundException("Medico not found");
        if(especialidadEntity.isEmpty()) throw new EntityNotFoundException("Especialidad not found");
        medicoEntity.get().getEspecialidades().add(especialidadEntity.get()); 
        log.info("Finaliza el proceso"); 
        return especialidadEntity.get();
    }
    
}
