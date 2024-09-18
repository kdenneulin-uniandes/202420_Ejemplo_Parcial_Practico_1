package co.edu.uniandes.dse.parcialprueba.services;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import co.edu.uniandes.dse.parcialprueba.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(MedicoEspecialidadService.class)
public class MedicoEspecialidadServiceTest {

    @Autowired 
    private MedicoEspecialidadService medicoEspecialidadService; 

    @Autowired
    private TestEntityManager entityManager; 

    private List<MedicoEntity> medicos = new ArrayList<>() ;
    private List<EspecialidadEntity> especialidades = new ArrayList<>() ;

    private PodamFactory factory = new PodamFactoryImpl(); 

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData(){
        entityManager.getEntityManager().createQuery("delete from EspecialidadEntity");
        entityManager.getEntityManager().createQuery("delete from MedicoEntity");
    }

    private void insertData(){
        for (int i = 0; i < 3 ; i++){
            EspecialidadEntity especialidadEntity = factory.manufacturePojo(EspecialidadEntity.class );
            entityManager.persist(especialidadEntity);
            especialidades.add(especialidadEntity); 
        }

        for (int i = 0; i < 3 ; i++){
            MedicoEntity medicoEntity = factory.manufacturePojo(MedicoEntity.class );
            medicoEntity.setRegistroMedico("RM"+i); 
            entityManager.persist(medicoEntity);
            medicos.add(medicoEntity);
        }
    }

    @Test
    void testAddEspecialidad() throws IllegalOperationException, EntityNotFoundException {
        EspecialidadEntity result = medicoEspecialidadService.addEspecialidad(medicos.getFirst().getId(), especialidades.getFirst().getId()); 
        assertNotNull(result);
        assertEquals(result.getNombre(), especialidades.getFirst().getNombre());
        assertEquals(result.getDescripcion(), especialidades.getFirst().getDescripcion());
        assertEquals(result.getMedicos(), especialidades.getFirst().getMedicos());
        assertEquals(result.getId(), especialidades.getFirst().getId());
    }

    @Test 
    void testAddEspecialidadNotFoundMedico() throws EntityNotFoundException {
        try {
            medicoEspecialidadService.addEspecialidad((long) 13, especialidades.getFirst().getId());
        } catch (EntityNotFoundException e) {
            assertEquals("Medico not found", e.getMessage());
        }
    }

    @Test 
    void testAddEspecialidadNotFoundEspecialidad() throws EntityNotFoundException {
        try {
            medicoEspecialidadService.addEspecialidad(medicos.getFirst().getId(), (long) 13);
        } catch (EntityNotFoundException e) {
            assertEquals("Especialidad not found", e.getMessage());
        }
    }    
}
