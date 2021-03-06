package managers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import model.*;
import exceptions.CredentialsException;

@Stateless
@LocalBean

/*
 * 
 * Check the login's credentials
 * It connects to the model to make requests to the database 
 * 
 */
public class LoginManager {

	@PersistenceContext(unitName = "DreamEJB")
	private EntityManager em;
	
	public void setEm(EntityManager em) {
		this.em=em;
	}
	
/*
 * 
 * Checks if exists an users with the credentials.
 * Creates a query with the email end the encrypted password.
 * Return a User or null if it's not right.
 * 
 */
	public Usr checkCredentials(String email, String password) throws CredentialsException, NonUniqueResultException {
    	List<Usr> cList = null;
    	MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	md.update(password.getBytes());
    	byte byteData[] = md.digest();
    	 StringBuffer sb = new StringBuffer();
         for (int i = 0; i < byteData.length; i++)
             sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		try {
			cList = em.createNamedQuery("Usr.checkCredentials", Usr.class).setParameter(1, email).setParameter(2, sb.toString()).getResultList();
			System.out.println("ciao");
			} catch (PersistenceException e) {
			throw new CredentialsException("Could not verify credentials");
		}
		if (cList.isEmpty())
			return null;
		else if (cList.size() == 1)
			return cList.get(0);
		throw new NonUniqueResultException("More than one user registered with same credentials");
    }
}
