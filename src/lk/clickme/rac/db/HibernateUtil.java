package lk.clickme.rac.db;


import lk.clickme.rac.entity.Customer;
import lk.clickme.rac.entity.Payment;
import lk.clickme.rac.entity.RentalDetail;
import lk.clickme.rac.entity.Vehicle;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.File;

public class HibernateUtil {

    private static SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory(){
        File dbdata = new File("Resource/application.properties");
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().loadProperties(dbdata).build();

        Metadata metadata = new MetadataSources(registry)
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Payment.class)
                .addAnnotatedClass(RentalDetail.class)
                .addAnnotatedClass(Vehicle.class)
                .buildMetadata();

        return metadata.getSessionFactoryBuilder().build();
    }

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }
}
