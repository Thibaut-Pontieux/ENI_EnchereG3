package fr.eni.encheres.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.encheres.models.bll.EnchereManager;
import fr.eni.encheres.models.bll.exceptions.BLLException;
import fr.eni.encheres.models.bo.Categorie;
import fr.eni.encheres.models.bo.Enchere;

/**
 * Servlet implementation class Home
 */
@WebServlet("/encheres")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private EnchereManager enchereMgr;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Home() {
        super();
        this.enchereMgr = new EnchereManager();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Categorie> listeC = new ArrayList<Categorie>();
		List<Enchere>   listeE = new ArrayList<Enchere>();
		try {
			listeC = enchereMgr.getCategories();
			listeE = enchereMgr.getEncheres();
			request.setAttribute("categoriesEncheres", listeC);
			request.setAttribute("ListeEncheres", listeE);
		} catch (BLLException e) {
			request.setAttribute("erreurs", e.getListeMessagesErreur());
		}
		HttpSession session = request.getSession();
		
		if(session.getAttribute("langue") == null) {
			session.setAttribute("langue", "FRANCE");
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/home.jsp");
		if (rd != null) {
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String categorieEnchere = request.getParameter("catEnchere");
		String search			= request.getParameter("search");
		List<Enchere> listeE = new ArrayList<Enchere>();
		List<Categorie> listeC = new ArrayList<Categorie>();
		try {
			// Création d'attributs contenant les catégories d'enchères et la catégorie sélectionnée par l'utilisateur
			request.setAttribute("selectedEnchere", categorieEnchere);
			listeC = enchereMgr.getCategories();
			request.setAttribute("categoriesEncheres", listeC);
			request.setAttribute("search", search);
			// Si l'utilisateur filtre sur "Tout" alors on affiche toutes les enchères
			if (categorieEnchere.equals("Tout"))
				listeE = enchereMgr.getEncheres("",search);
			// Sinon on filtre suivant la catégorie sélectionnée
			else
				listeE = enchereMgr.getEncheres(categorieEnchere, search);
			request.setAttribute("ListeEncheres", listeE);

		} catch (BLLException e) {
			request.setAttribute("erreurs", e.getListeMessagesErreur());
		}
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/home.jsp");
		if (rd != null) {
			rd.forward(request, response);
		}
	}

}
