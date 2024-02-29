package hibernateControllers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import model.Product;
import model.Warehouse;

public class CustomHib extends GenericHib {
    public CustomHib(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    public void deleteProduct(int id) {

        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            var product = em.find(Product.class, id);
            //Kai turiu objekta, galiu ji "nulinkint"

            Warehouse warehouse = product.getWarehouse();
            if (warehouse != null) {
                warehouse.getInStockProducts().remove(product);
                em.merge(warehouse);
            }

            em.remove(product);
            em.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
