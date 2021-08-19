package fr.eni.encheres.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;

/**
 * Servlet implementation class connexion
 */
@WebServlet("/connexion")
public class connexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public connexion() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		session.setAttribute("session", "off");
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/connexion.jsp");
		if (rd != null) {
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		try {
			HttpSession session = request.getSession();
			session.setAttribute("session", "on");
			
			request.setAttribute("pseudo", username );
			request.setAttribute("mdp", password);
			
			
			
		} catch (BLLException e) {
			
		}
		

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/connexion.jsp");
		if (rd != null) {
			rd.forward(request, response);
		}
	}

}
