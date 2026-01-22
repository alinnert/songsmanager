package dev.alinnert.songsmanager.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.function.Consumer;

public class PersistenceService implements AutoCloseable
{
	private final EntityManagerFactory emf;

	public PersistenceService() {
		emf = Persistence.createEntityManagerFactory(
				"songsmanager-persistence");
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
