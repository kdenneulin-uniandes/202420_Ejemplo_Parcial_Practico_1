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

import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(MedicoService.class)
public class MedicoServiceTest {
    @Autowired
    private MedicoService medicoService; 

    @Autowired
    private TestEntityManager entityManager; 

    private List<MedicoEntity> medicos = new ArrayList<>();

    private PodamFactory factory = new PodamFactoryImpl(); 

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData(){
        entityManager.getEntityManager().createQuery("delete from MedicoEntity");
    }

    private void insertData(){
        for (int i = 0; i < 3 ; i++){
            MedicoEntity medicoEntity = factory.manufacturePojo(MedicoEntity.class );
            medicoEntity.setRegistroMedico("RM"+i); 
            entityManager.persist(medicoEntity);
            medicos.add(medicoEntity);
        }

    }

    @Test
    void testCreateMedicos() throws IllegalOperationException {
        List<MedicoEntity> medicosTest = new ArrayList<>(); 
        for (int i = 0; i < 5 ; i++){
            MedicoEntity medicoEntity = factory.manufacturePojo(MedicoEntity.class );
            medicoEntity.setRegistroMedico("RM"+i); 
            medicosTest.add(medicoEntity);
        }
        medicoService.createMedicos(medicosTest);
        for(MedicoEntity medi: medicosTest) {
            MedicoEntity result = medi ;
            MedicoEntity entity = entityManager.find(MedicoEntity.class, result.getId()); 
            assertNotNull(entity);
            assertEquals(result.getNombre(), entity.getNombre());
            assertEquals(result.getApellido(), entity.getApellido());
            assertEquals(result.getEspecialidades(), entity.getEspecialidades());
            assertEquals(result.getId(), entity.getId());
            assertEquals(result.getRegistroMedico(), entity.getRegistroMedico());
        }
    }

    @Test 
    void testCreateMedicosWithRegistroMedicoMal() {
        List<MedicoEntity> medicosTest = new ArrayList<>(); 
        for (int i = 0; i < 5 ; i++){
            MedicoEntity medicoEntity = factory.manufacturePojo(MedicoEntity.class );
            medicosTest.add(medicoEntity);
        }
        try {
            medicoService.createMedicos(medicosTest);
        } catch (IllegalOperationException e) {
            assertEquals("Registo medico tiene que empezar con RM", e.getMessage());
        }
    }


}
