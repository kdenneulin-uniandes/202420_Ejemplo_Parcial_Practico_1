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
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(EspecialidadService.class)
public class EspecialidadServiceTest {
    @Autowired
    private EspecialidadService especialidadService; 

    @Autowired
    private TestEntityManager entityManager; 

    private PodamFactory factory = new PodamFactoryImpl(); 

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData(){
        entityManager.getEntityManager().createQuery("delete from EspecialidadEntity");
    }

    private void insertData(){
        for (int i = 0; i < 3 ; i++){
            EspecialidadEntity especialidadEntity = factory.manufacturePojo(EspecialidadEntity.class );
            entityManager.persist(especialidadEntity);
        }

    }

    @Test
    void testCreateEspecialidad() throws IllegalOperationException {
        EspecialidadEntity especialidadEntity = factory.manufacturePojo(EspecialidadEntity.class );
        especialidadEntity.setDescripcion("EZRGVd zgoundaof fzocnz"); 
        especialidadService.createEspecialidad(especialidadEntity);
        EspecialidadEntity result = especialidadEntity ;
        EspecialidadEntity entity = entityManager.find(EspecialidadEntity.class, result.getId()); 
        assertNotNull(entity);
        assertEquals(result.getNombre(), entity.getNombre());
        assertEquals(result.getDescripcion(), entity.getDescripcion());
        assertEquals(result.getMedicos(), entity.getMedicos());
        assertEquals(result.getId(), entity.getId());
    }

    @Test 
    void testCreateEspecialidadDescripcionMenos10() {
        EspecialidadEntity especialidadEntity = factory.manufacturePojo(EspecialidadEntity.class );
        especialidadEntity.setDescripcion("leal"); 
        try {
            especialidadService.createEspecialidad(especialidadEntity);
        } catch (IllegalOperationException e) {
            assertEquals("Descripcion tiene que ser minimo 10 caracteres", e.getMessage());
        }
    }


}
