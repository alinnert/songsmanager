package dev.alinnert.songsmanager.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class PersistenceService implements AutoCloseable
{
    private static final String FILENAME = "persistence.properties";
    private final EntityManagerFactory entityManagerFactory;

    public PersistenceService() {
        entityManagerFactory = Persistence.createEntityManagerFactory(
            "songsmanager-persistence", loadProperties());
    }

    private Properties loadProperties() {
        var path = Path.of(FILENAME);
        if (!Files.exists(path)) {
            throw new IllegalStateException(
                "%s not found! To fix this copy %s.example in project directory to %s and fill in the required values.".formatted(
                    FILENAME, FILENAME, FILENAME));
        }
        var properties = new Properties();

        try (var stream = Files.newInputStream(path)) {
            properties.load(stream);
        } catch (IOException e) {
            throw new RuntimeException(
                "Failed to load %s".formatted(FILENAME), e);
        }

        return properties;
    }

    public EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    @Override
    public void close() {
        entityManagerFactory.close();
    }
}
