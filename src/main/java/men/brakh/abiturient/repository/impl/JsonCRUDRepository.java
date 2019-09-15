package men.brakh.abiturient.repository.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import men.brakh.abiturient.exception.RecourseNotFoundException;
import men.brakh.abiturient.model.BaseEntity;
import men.brakh.abiturient.repository.CRUDRepository;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public abstract class JsonCRUDRepository<T extends BaseEntity, I> implements CRUDRepository<T, I> {
    private AtomicInteger lastId = new AtomicInteger();

    private static final String dbPath = "db/";
    private final String fileName;
    private final String filePathString;
    private final boolean autogenerateIntId;
    private final Class<T> entityClass;


    private Gson gson;

    public JsonCRUDRepository(Class<T> entityClass, final String fileName, final boolean autogenerateIntId) {
        this.entityClass = entityClass;
        this.fileName = fileName;
        this.autogenerateIntId = autogenerateIntId;

        final Path filePath = Paths.get(dbPath + fileName + ".json");
        filePathString = filePath.toString();

        gson = new GsonBuilder().setDateFormat(DateFormat.LONG).create();

        if (!Files.exists(filePath)) {
            try {
                Files.createDirectories(filePath.getParent());
                Files.createFile(filePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            lastId.set(0);
        } else {
            if(autogenerateIntId) {
                List<T> list = loadList();
                int maxId = list
                        .stream()
                        .filter(entity -> entity.getId() instanceof Integer)
                        .map(BaseEntity::getId)
                        .map(id -> (int) id)
                        .max(Comparator.comparingInt(integer -> integer))
                        .orElse(0);

                lastId.set(maxId);
            }
        }
        configureGson(gson);
    }

    protected void configureGson(Gson gson) {

    }

    private int generateId() {
        return lastId.incrementAndGet();
    }


    private List<T> loadList()  {
        try {
            JsonArray list = gson.fromJson(new FileReader(filePathString), JsonArray.class);

            if (list == null) {
                return new ArrayList<>();
            }

            List<T> entitiesList =  new ArrayList<T>();
            for(final JsonElement json: list){
                T entity = gson.fromJson(json, entityClass);
                entitiesList.add(entity);
            }

            return entitiesList;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveList(List<T> list) {
        try(PrintWriter printWriter = new PrintWriter(filePathString)) {
            printWriter.print(gson.toJson(list));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T create(T entity) {
        List<T> list = loadList();

        T copiedEntity = (T) entity.clone();

        if (autogenerateIntId)
            copiedEntity.setId(generateId());
        list.add(copiedEntity);
        saveList(list);
        return copiedEntity;
    }

    @Override
    public T update(T updatedEntity) throws RecourseNotFoundException {
        List<T> entities = loadList();

        T copiedEntity = (T) updatedEntity.clone();

        boolean isUpdated = false;

        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).getId().equals(copiedEntity.getId())) {
                entities.set(i, copiedEntity);
                isUpdated = true;
            }
        }

        if (!isUpdated) throw new RecourseNotFoundException();
        saveList(entities);
        return updatedEntity;
    }

    @Override
    public void delete(I id) {
        List<T> entities = loadList()
                .stream()
                .filter(entity -> !entity.getId().equals(id))
                .collect(Collectors.toList());

        saveList(entities);
    }

    @Override
    public Optional<T> findById(I id) {
        return loadList()
                .stream()
                .filter(t -> t.getId().equals(id))
                .map(this::postProcessEntity)
                .findFirst();
    }

    @Override
    public List<T> findAll() {
        return loadList()
                .stream()
                .map(this::postProcessEntity)
                .collect(Collectors.toList());
    }

    /**
     * Entity post processing.
     */
    protected T postProcessEntity(T entity) {
        return entity;
    }
}
