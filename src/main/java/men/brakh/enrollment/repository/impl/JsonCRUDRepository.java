package men.brakh.enrollment.repository.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import men.brakh.enrollment.exception.ResourceNotFoundException;
import men.brakh.enrollment.jsonadapters.TimestampJsonAdapter;
import men.brakh.enrollment.model.BaseEntity;
import men.brakh.enrollment.repository.CRUDRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class JsonCRUDRepository<T extends BaseEntity, I> implements CRUDRepository<T, I> {
    private final static Logger logger = LoggerFactory.getLogger(JsonCRUDRepository.class);

    private AtomicInteger lastId = new AtomicInteger();

    private static final String dbPath = "db/";
    private final String fileName;
    private final String filePathString;
    private final boolean autogenerateIntId;
    private final Class<T> entityClass;


    private Gson gson;

    public JsonCRUDRepository(final Class<T> entityClass,
                              final String fileName,
                              final boolean autogenerateIntId) {
        this.entityClass = entityClass;
        this.fileName = fileName;
        this.autogenerateIntId = autogenerateIntId;

        final Path filePath = Paths.get(dbPath + fileName + ".json");
        filePathString = filePath.toString();

        GsonBuilder gsonBuilder = new GsonBuilder();
        configureGson(gsonBuilder);
        gson = gsonBuilder.create();

        if (!Files.exists(filePath)) {
            try {
                Files.createDirectories(filePath.getParent());
                Files.createFile(filePath);
                logger.info(filePath + " db file was created");
            } catch (IOException e) {
                logger.error("db file creation exception ", e);
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

                logger.debug("Db " + fileName + " loaded. Last id: " + maxId);
            }
        }
    }



    protected void configureGson(final GsonBuilder gson) {
        gson.registerTypeAdapter(Date.class, new TimestampJsonAdapter());
    }

    private int generateId() {
        return lastId.incrementAndGet();
    }

    private JsonArray loadJsonArray() throws FileNotFoundException {
        return gson.fromJson(new FileReader(filePathString), JsonArray.class);
    }

    private List<T> loadList()  {
        try {
            JsonArray list = loadJsonArray();

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
    public final T create(T entity) {
        logger.debug(entity + " saved");
        List<T> list = loadList();

        T copiedEntity = (T) entity.clone();

        if (autogenerateIntId)
            copiedEntity.setId(generateId());
        list.add(copiedEntity);
        saveList(list);
        return copiedEntity;
    }

    @Override
    public final T update(T updatedEntity) throws ResourceNotFoundException {
        logger.debug(updatedEntity + " updated");

        List<T> entities = loadList();

        T copiedEntity = (T) updatedEntity.clone();

        boolean isUpdated = false;

        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).getId().equals(copiedEntity.getId())) {
                entities.set(i, copiedEntity);
                isUpdated = true;
            }
        }

        if (!isUpdated) throw new ResourceNotFoundException();
        saveList(entities);
        return updatedEntity;
    }

    @Override
    public final void delete(I id) {
        logger.debug("Entity " + fileName + " with id " + id + " deleted");
        List<T> entities = loadList()
                .stream()
                .filter(entity -> !entity.getId().equals(id))
                .collect(Collectors.toList());

        saveList(entities);
    }

    @Override
    public final Optional<T> findById(I id) {
        return loadList()
                .stream()
                .filter(t -> t.getId().equals(id))
                .map(this::postProcessEntity)
                .findFirst();
    }

    @Override
    public final List<T> findAll() {
        return loadList()
                .stream()
                .map(this::postProcessEntity)
                .collect(Collectors.toList());
    }

    public List<T> find(final Predicate<T> filter) {
        return findAll()
                .stream()
                .filter(filter)
                .collect(Collectors.toList());
    }


    /**
     * Entity post processing.
     */
    protected T postProcessEntity(T entity) {
        return entity;
    }
}
