package facades;

import entities.Products;
import entities.TagNames;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import utils.EMF_Creator;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class ProductFacade {

    private static ProductFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    public ProductFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static ProductFacade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new ProductFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    //TODO Remove/Change this before use
    public long getProductCount() {
        EntityManager em = emf.createEntityManager();
        try {
            long productCount = (long) em.createQuery("SELECT COUNT(p) FROM Products p").getSingleResult();
            return productCount;
        } finally {
            em.close();
        }

    }

    public List getAllProducts() {
        EntityManager em = emf.createEntityManager();
        try {
            Query products = em.createNamedQuery("Products.findAll");
            return products.getResultList();
        } finally {
            em.close();
        }

    }

    public List getProductsfromQuery(String query) {
        List results = new ArrayList();
        query = query.replace("å", "aa");
        query = query.replace("æ", "ae");
        //query = query.replace("ø", "oe");

        EntityManager em = emf.createEntityManager();
        String[] querySplit;
        if (query.contains("+")) {
            querySplit = query.split("\\+");
            StringJoiner joiner = new StringJoiner(",");
            for (String q : querySplit) {
                q = "'" + q + "'";
                joiner.add(q);
            }
            query = joiner.toString();
        } else {
            query = "'" + query + "'";
        }
        try {
            Query products = em.createNativeQuery("SELECT * FROM products p JOIN tags t on p.product_id=t.product_id "
                    + "JOIN tag_names tn on t.tag_id=tn.tag_id "
                    + "WHERE tag_name IN (" + query + ")");
            results = products.getResultList();
            System.out.println(query);
        } finally {
            em.close();
        }
        return results;
    }

    public static void main(String[] args) {
        ProductFacade facade = ProductFacade.getFacade(EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE));
        System.out.println(facade.getProductCount());
    }

}
