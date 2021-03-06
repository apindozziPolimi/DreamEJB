package managersTest;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.embeddable.EJBContainer;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NamedQuery;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.mockito.stubbing.Answer;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Any;

import exceptions.CredentialsException;
import managers.*;
import model.*;


public class IncentiveManagerTest {
	@InjectMocks
	IncentiveManager manager;
	 
	 EntityManager entityManager;
	 
	 @Mock
	 private Incentive inc;
	 
	 @Mock
	 private Farmer f;
	 
	 
	 @Mock
	 private Usr usr;
	 @Mock
	 private Agronomistreport ar;
	 
	 @Mock
	 private Production prod;
	 
	 @Mock
	 TypedQuery<Incentive> query;
	

	@Before
	public void setUp() throws Exception {
		entityManager = mock(EntityManager.class);
		inc = new Incentive();
		manager= new IncentiveManager();
		manager.setEm(entityManager);
		inc.setIdincentive(0);
		inc.setAmount(0);
		inc.setProduction(prod);
	    query = mock(TypedQuery.class);
		when((query).setParameter(anyInt(), any())).thenReturn(query);
	   
	}

	@After
	public void tearDown() throws Exception {
    }
	

	@Test
	public void getAllTest() throws NonUniqueResultException, CredentialsException, NamingException {
		TypedQuery<Incentive> query = mock(TypedQuery.class);
	     when(entityManager.createNamedQuery("Incentive.findAll", Incentive.class)).thenReturn(query);
	     List<Incentive> incs = new LinkedList<Incentive>();
	     when((query).getResultList()).thenReturn(incs);
	     List<Incentive> incsChecked =manager.getAll();
	     
	    verify(entityManager).createNamedQuery("Incentive.findAll", Incentive.class);
	    verify(query).getResultList();
		assertSame(incs, incsChecked);
	}

}

