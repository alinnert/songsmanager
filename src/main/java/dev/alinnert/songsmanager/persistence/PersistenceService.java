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
	private final EntityManagerFactory emf;

	public PersistenceService() {
		emf = Persistence.createEntityManagerFactory(
			"songsmanager-persistence", loadProperties());
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

	public void run(Consumer<EntityManager> consumer) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			consumer.accept(em);
			tx.commit();
		} catch (RuntimeException | Error e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			throw e;
		} finally {
			em.close();
		}
	}

	@Override
	public void close() {
		emf.close();
	}
}
