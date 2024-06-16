package com.gigi.springdata.id;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;

public class CustomIdGenerator implements IdentifierGenerator, Configurable {

    private String prefix;
    private String idColumnName;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void configure(Type type, Properties properties, ServiceRegistry serviceRegistry) throws MappingException {
        this.prefix = properties.getProperty("prefix");
        this.idColumnName = properties.getProperty("isColumnName");
    }
    @Override
    @Transactional
    public Serializable generate (SharedSessionContractImplementor session, Object object){
        if( prefix == null){
            throw new IllegalArgumentException("Prefix is not configured");
        }

        //search database for the highest id number, then increment. This usually returns one value so getSingleResult() shows intent but if there is no result, it will throw NoResultException
        //To handle scenarios where there are no records, use a default so method changes to getResultStream().findFirst().orElse....

        /*String query = "SELECT e."+idColumnName + " FROM "+ object.getClass().getSimpleName() + " e WHERE e."+idColumnName +" LIKE :prefix ORDER BY e." +idColumnName +" DESC";
        String lastId = (String) entityManager.createQuery(query)
                .setParameter("prefix", prefix+"%")
                .setMaxResults(1)
                .getSingleResult();

        int lastNumber = 0;
        if(lastId != null && !lastId.isEmpty()){
            lastNumber = Integer.parseInt(lastId.substring((prefix.length())));
        }
    */

        String query = "SELECT e." + idColumnName + " FROM " + object.getClass().getSimpleName() + " e WHERE e." + idColumnName + " LIKE :prefix ORDER BY e." + idColumnName + " DESC";
        String lastId = (String) entityManager.createQuery(query)
                .setParameter("prefix", prefix + "%")
                .setMaxResults(1)
                .getResultStream()
                .<String>findFirst()
                .orElse (prefix + "000000");

        int lastNumber = Integer.parseInt(lastId.substring(prefix.length()));

        return prefix + String.format("%06d", lastNumber + 1);
    }
}