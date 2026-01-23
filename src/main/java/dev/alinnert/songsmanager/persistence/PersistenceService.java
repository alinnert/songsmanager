package dev.alinnert.songsmanager.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.function.Consumer;

public final class PersistenceService implements AutoCloseable
{
	private static final String FILENAME = "persistence.properties";
	private final EntityManagerFactory entityManagerFactory;
	private final EntityManager entityManager;

	public PersistenceService() {
		entityManagerFactory = Persistence.createEntityManagerFactory(
			"songsmanager-persistence", loadProperties());
		entityManager = entityManagerFactory.createEntityManager();
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	private Properties loadProperties() {
		var path = Path.of(FILENAME);
		if (!Files.exists(path)) {
			var msg = "%s not found! To fix this copy %s.example to %s and fill in the required values.".formatted(
				FILENAME, FILENAME, FILENAME);
			throw new IllegalStateException(msg);
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

	public void runWithTransaction(Consumer<EntityManager> consumer) {
		EntityTransaction tx = entityManager.getTransaction();

		try {
			tx.begin();
			consumer.accept(entityManager);
			tx.commit();
		} catch (RuntimeException | Error e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			throw e;
		} finally {
			entityManager.close();
		}
	}

	@Override
	public void close() {
		entityManagerFactory.close();
	}
}
