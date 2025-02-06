package ma.fstt.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.Data;
import ma.fstt.Entities.Vitrine;

import java.util.List;

@Named
@RequestScoped
@Data
public class VitrineBean {

    // Attributs pour les formulaires JSF
    private String nomVitrine;

    // Gestion de l'EntityManager
    private EntityManagerFactory emf;
    private EntityManager em;

    // Constructeur : Initialisation de l'EntityManager
    public VitrineBean() {
        emf = Persistence.createEntityManagerFactory("default"); // Nom du persistence-unit
        em = emf.createEntityManager();
    }

    public void save() {
        try {
            // Démarrer une transaction si elle n'est pas déjà active
            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }

            // Créer une nouvelle instance de Vitrine
            Vitrine vitrine = new Vitrine();
            vitrine.setNom_vitrine(nomVitrine);

            // Persist la vitrine
            em.persist(vitrine);
            em.getTransaction().commit(); // Commit de la transaction
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // Annuler la transaction en cas d'erreur
            }
            e.printStackTrace();
        }
    }


    // Méthode pour lister toutes les vitrines
    public List<Vitrine> lister() {
        return em.createQuery("SELECT v FROM Vitrine v", Vitrine.class).getResultList();
    }

    // Méthode pour supprimer une vitrine
    public void supprimer(Long id) {
        try {
            Vitrine vitrine = em.find(Vitrine.class, id);
            if (vitrine != null) {
                em.getTransaction().begin();
                em.remove(vitrine);
                em.getTransaction().commit();
            } else {
                System.out.println("Vitrine non trouvée !");
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }

    // Réinitialisation des champs après une opération
    private void resetForm() {
        nomVitrine = null;
    }

    // Fermer l'EntityManager quand le Bean est détruit (bonne pratique)
    public void close() {
        if (em != null && em.isOpen()) {
            em.close();
        }
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
