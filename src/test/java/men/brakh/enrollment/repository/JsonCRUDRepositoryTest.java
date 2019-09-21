package men.brakh.enrollment.repository;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import men.brakh.enrollment.exception.ResourceNotFoundException;
import men.brakh.enrollment.model.BaseEntity;
import men.brakh.enrollment.repository.impl.JsonCRUDRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class JsonCRUDRepositoryTest {
    private final static String entity = "test";
    private final static Path filePath = Paths.get("db/" + entity + ".json");
    private JsonCRUDRepository<TestEntity, Integer> crudRepository;

    @Getter
    @Setter
    @EqualsAndHashCode
    @ToString
    static class TestEntity implements BaseEntity<Integer> {
        private Integer id;
        private String field;

        @Override
        public TestEntity clone()  {
            try {
                return (TestEntity) super.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    @Before
    public void setUp() throws Exception {
        crudRepository = new JsonCRUDRepository<TestEntity, Integer>(TestEntity.class, entity, true) {
        };
    }

    @After
    public void tearDown() throws Exception {
        Files.delete(filePath);
    }


    @Test
    public void create() {
        TestEntity testEntity = new TestEntity();
        testEntity.setField("A");

        TestEntity savedEntity = crudRepository.create(testEntity);
        assertEquals((int) savedEntity.getId(), 1);

        assertEquals(crudRepository.findAll().size(), 1);

        TestEntity expected = crudRepository.findAll().get(0);
        assertEquals(expected, savedEntity);

        TestEntity savedEntity2 = crudRepository.create(testEntity);
        assertEquals((int) savedEntity2.getId(), 2);
        assertEquals(crudRepository.findAll().size(), 2);
    }

    @Test
    public void update() throws ResourceNotFoundException {
        TestEntity testEntity = new TestEntity();
        testEntity.setField("A");
        crudRepository.create(testEntity);
        crudRepository.create(testEntity);
        testEntity.setField("B");
        crudRepository.create(testEntity);

        TestEntity updating = new TestEntity();
        updating.setField("C");
        updating.setId(2);
        crudRepository.update(updating);

        assertEquals(crudRepository.findById(2).orElse(null), updating);
    }

    @Test
    public void delete() {
        TestEntity testEntity = new TestEntity();
        testEntity.setField("A");
        TestEntity testEntity1 = crudRepository.create(testEntity);
        testEntity.setField("B");
        TestEntity testEntity2 = crudRepository.create(testEntity);

        crudRepository.delete(1);

        assertEquals(crudRepository.findAll().size(), 1);
        assertEquals(crudRepository.findAll().get(0), testEntity2);
    }

    @Test
    public void findById() {
        TestEntity testEntity = new TestEntity();
        testEntity.setField("A");
        TestEntity testEntity1 = crudRepository.create(testEntity);
        testEntity.setField("B");
        TestEntity testEntity2 = crudRepository.create(testEntity);

        assertEquals(crudRepository.findById(1).orElse(null), testEntity1);
        assertEquals(crudRepository.findById(2).orElse(null), testEntity2);
    }

    private String replaceTrashCharacters(String json) {
        return json.replaceAll("[\\n\\t\\r ]", "");
    }

    @Test
    public void findAll() throws IOException {
        TestEntity testEntity = new TestEntity();
        testEntity.setField("Kek");
        crudRepository.create(testEntity);

        String json = new String(Files.readAllBytes(filePath));

        String expectedJson = replaceTrashCharacters("[{\"id\":1,\"field\":\"Kek\"}]");

        assertEquals(expectedJson, replaceTrashCharacters(json));
    }
}