package fr.eni.encheres.models.bll;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.models.bll.exceptions.BLLException;
import fr.eni.encheres.models.bo.Categorie;
import fr.eni.encheres.models.bo.Enchere;
import fr.eni.encheres.models.dal.EnchereDAO;
import fr.eni.encheres.models.dal.exception.DALException;
import fr.eni.encheres.models.dal.jdbc.EnchereDAOJdbcImpl;

public class EnchereManager {

	private EnchereDAO enchereDAO;
	
	public EnchereManager() {
		enchereDAO = new EnchereDAOJdbcImpl();
	}
	
	public List<Enchere> getEncheres() throws BLLException {
		BLLException exceptions = new BLLException();
		
		List<Enchere> listeE = new ArrayList<Enchere>();
		
		try {
			listeE = enchereDAO.getEncheres();
		} catch (DALException e) {
			exceptions.ajoutErreur(e.getMessage());
			throw exceptions;
		} catch (SQLException e) {
			exceptions.ajoutErreur(e.getMessage());
			throw exceptions;
		}
		return listeE;
	}
	
	public List<Enchere> getEncheres(String nomCategorie) throws BLLException {
		BLLException exceptions = new BLLException();
		
		List<Enchere> listeE = new ArrayList<Enchere>();
		
		try {
			listeE = enchereDAO.getEncheres(nomCategorie);
		} catch (DALException e) {
			exceptions.ajoutErreur(e.getMessage());
			throw exceptions;
		} catch (SQLException e) {
			exceptions.ajoutErreur(e.getMessage());
			throw exceptions;
		}
		return listeE;
	}
	
	public List<Categorie> getCategories() throws BLLException {
		BLLException exceptions = new BLLException();
		
		List<Categorie> listeC = new ArrayList<Categorie>();
		
		try {
			listeC = enchereDAO.getCategories();
		} catch (DALException e) {
			exceptions.ajoutErreur(e.getMessage());
			throw exceptions;
		} catch (SQLException e) {
			exceptions.ajoutErreur(e.getMessage());
			throw exceptions;
		}
		return listeC;
	}
	
	public void ajouterArticle(String nomArt, String descArt, int prixArt, LocalDate dateDebVente, LocalDate dateFinVente, LocalTime heureDebVente, LocalTime heureFinVente) throws BLLException {
		BLLException exceptions = new BLLException();
		
		Enchere enchere = new Enchere(1, 1, nomArt, descArt, prixArt, dateDebVente, dateFinVente);
		try {
			enchereDAO.insertNouvelArticle(enchere);
		} catch (DALException e) {
			exceptions.ajoutErreur(e.getMessage());
			throw exceptions;
		} catch (SQLException e) {
			exceptions.ajoutErreur(e.getMessage());
			throw exceptions;
		}
	}
	
}
