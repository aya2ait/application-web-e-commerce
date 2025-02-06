package ma.fstt.Entities;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Test {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            Vitrine vitrine = new Vitrine();
            vitrine.setNom_vitrine("Vitrine Test");
            em.persist(vitrine);
            em.getTransaction().commit();
            System.out.println("Vitrine ajoutée avec succès !");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }

}
