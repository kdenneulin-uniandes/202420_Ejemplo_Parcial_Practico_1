package co.edu.uniandes.dse.parcialprueba.services;

import java.util.ArrayList;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialprueba.repositories.MedicoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MedicoService {

    @Autowired
    MedicoRepository medicoRepository; 

    @Transactional
    public List<MedicoEntity> createMedicos(List<MedicoEntity> medicos) throws IllegalOperationException {
        List<MedicoEntity> medicosCreados = new ArrayList<>(); 
        for(MedicoEntity medi : medicos) {
            if(medi.getNombre() == null) {
                throw new IllegalOperationException("Nombre no valido"); 
            }
            if(medi.getApellido() == null) {
                throw new IllegalOperationException("Apellido no valido"); 
            }
            if(medi.getRegistroMedico() == null) {
                throw new IllegalOperationException("Registro medico no valido"); 
            }
            if(!medi.getRegistroMedico().startsWith("RM")) {
                throw new IllegalOperationException("Registo medico tiene que empezar con RM"); 
            }
            medicoRepository.save(medi); 
            medicosCreados.add(medi); 

        }

        return medicosCreados; 

    }
    
}
