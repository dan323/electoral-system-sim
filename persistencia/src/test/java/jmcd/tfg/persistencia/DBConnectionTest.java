package jmcd.tfg.persistencia;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.internal.SessionImpl;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.FileInputStream;

public final class DBConnectionTest {

    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;
    private static IDatabaseConnection connection;
    private static IDataSet dataset;

    @BeforeClass
    public static void initEntityManager() throws Exception {
        entityManagerFactory = Persistence.createEntityManagerFactory("test-persistencia");
        entityManager = entityManagerFactory.createEntityManager();
        connection = new DatabaseConnection(((SessionImpl) (entityManager.getDelegate())).connection());
        connection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new HsqldbDataTypeFactory());

        ClassLoader loader = DBConnectionTest.class.getClassLoader();
        dataset = new XmlDataSet(new FileInputStream(loader.getResource("data.xml").getFile()));
    }

    @AfterClass
    public static void closeEntityManager() {
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    public void cleanDB() throws Exception {
        DatabaseOperation.CLEAN_INSERT.execute(connection, dataset);
    }

}
