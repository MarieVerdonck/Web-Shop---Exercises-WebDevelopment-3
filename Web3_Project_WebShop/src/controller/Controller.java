package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.DomainException;
import domain.Person;
import domain.PersonErrorListPair;
import domain.Product;
import domain.ProductErrorListPair;
import domain.ShopService;

/**
 * Servlet implementation class Controller
 */
@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ShopService service;

	@Override
	public void init() throws ServletException {
		super.init();
		ServletContext context = getServletContext();

		Properties properties = new Properties();
		//More flexible for if properties contextparam increase
		Enumeration<String> parameterNames = context.getInitParameterNames();
		while (parameterNames.hasMoreElements()) {
			String propertyName = parameterNames.nextElement();
			properties.setProperty(propertyName, context.getInitParameter(propertyName));
		}
		service = new ShopService(properties);
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Controller() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.handleRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.handleRequest(request, response);
	}

	void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String destination = "index.jsp";
		if (action != null) {
			switch (action) {
			case "overviewUsers":
				destination = showUsers(request, response);
				break;
			case "overviewProducts":
				destination = showProducts(request, response);
				break;
			case "addProduct":
				destination = showAddProductPage(request, response);
				break;
			case "addProductToDB":
				destination = tryToAddProduct(request, response);
				break;
			case "updateProductPage":
				destination = goToUpdateProductPage(request, response);
				break;
			case "updateProduct":
				destination = updateProduct(request, response);
				break;
			case "deleteProductPage":
				destination = goToDeleteProductPage(request, response);
				break;
			case "deleteProduct":
				destination = deleteProduct(request, response);
				break;
			case "deletePersonPage":
				destination = goToDeletePersonPage(request, response);
				break;
			case "deletePerson":
				destination = deletePerson(request, response);
				break;
			case "checkPasswordPage":
				destination = goToCheckPasswordPage(request, response);
				break;
			case "checkPassword":
				destination = checkPassword(request, response);
				break;
			case "signUp":
				destination = showSignUpPage(request, response);
				break;
			case "signUpPerson":
				destination = tryToSignUp(request, response);
				break;
			case "changeColour":
				changeColour(request, response);
				return;
			default:
				destination = "index.jsp";
			}
		}
		RequestDispatcher view = request.getRequestDispatcher(destination);
		view.forward(request, response);
	}

	private String showUsers(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("persons", service.getPersons());
		return "personoverview.jsp";
	}

	private String showProducts(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("products", service.getProducts());
		return "productoverview.jsp";
	}

	private String showAddProductPage(HttpServletRequest request, HttpServletResponse response) {
		return "addProduct.jsp";
	}

	private String tryToAddProduct(HttpServletRequest request, HttpServletResponse response) {
		ArrayList<String> errors = this.getProductErrorList(request, response).getErrors();
		Product newProduct = this.getProductErrorList(request, response).getProduct();
		String destination = this.showProducts(request, response);
		if (errors.size() <= 0) {
			try {
				service.addProduct(newProduct);
				destination = this.showProducts(request, response);
			} catch (Exception e) {
				errors.add(e.getMessage());
			}
		}
		if (errors.size() > 0) {
			request.setAttribute("errors", errors);
			request.setAttribute("product", newProduct);
			destination = showAddProductPage(request, response);
		}

		return destination;
	}

	private String goToUpdateProductPage(HttpServletRequest request, HttpServletResponse response) {
		Product product = service.getProduct(request.getParameter("id"));
		request.setAttribute("product", product);
		return "updateProduct.jsp";
	}

	private String updateProduct(HttpServletRequest request, HttpServletResponse response) {
		ArrayList<String> errors = this.getProductErrorList(request, response).getErrors();
		Product newProduct = this.getProductErrorList(request, response).getProduct();
		String destination = this.showProducts(request, response);
		if (errors.size() <= 0) {
			Product product = service.getProduct(request.getParameter("productId"));
			product.setName(newProduct.getName());
			product.setDescription(newProduct.getDescription());
			product.setPrice(newProduct.getPrice());
			service.updateProducts(product);
			destination = this.showProducts(request, response);
		} else {
			request.setAttribute("errors", errors);
			request.setAttribute("product", newProduct);
			destination = showAddProductPage(request, response);
		}

		return destination;
	}

	private String goToDeletePersonPage(HttpServletRequest request, HttpServletResponse response) {
		Person person = service.getPerson(request.getParameter("id"));
		request.setAttribute("person", person);
		return "deletePerson.jsp";
	}

	private String deletePerson(HttpServletRequest request, HttpServletResponse response) {
		if (request.getParameter("submit").equals("Delete")) {
			service.deletePerson(request.getParameter("id"));
		}
		return showUsers(request, response);
	}

	private String goToCheckPasswordPage(HttpServletRequest request, HttpServletResponse response) {
		Person person = service.getPerson(request.getParameter("id"));
		request.setAttribute("person", person);
		return "checkPassword.jsp";
	}

	private String checkPassword(HttpServletRequest request, HttpServletResponse response) {
		String responseMessage = "";
		// Generate errors
		String password = request.getParameter("password");

		Person person = service.getPerson(request.getParameter("id"));

		System.out.println("testperson ignore next salt");
		Person newPerson = new Person();

		try {
			newPerson.setPassword(password);
		} catch (IllegalArgumentException e) {
			responseMessage = e.getMessage();
		}

		if (responseMessage.equals("")) {
			System.out.println("PW in DB: " + person.getPassword());
			if (person.isCorrectPassword(password)) {
				responseMessage = "Correct!";
			} else {
				responseMessage = "Wrong. Try again.";
			}
		}
		request.setAttribute("person", person);
		request.setAttribute("response", responseMessage);

		return "checkPassword.jsp";
	}

	private String goToDeleteProductPage(HttpServletRequest request, HttpServletResponse response) {
		Product product = service.getProduct(request.getParameter("id"));
		request.setAttribute("product", product);
		return "deleteProduct.jsp";
	}

	private String deleteProduct(HttpServletRequest request, HttpServletResponse response) {
		if (request.getParameter("submit").equals("Delete")) {
			service.deleteProduct(request.getParameter("id"));
		}
		return showProducts(request, response);
	}

	private String showSignUpPage(HttpServletRequest request, HttpServletResponse response) {
		return "signUp.jsp";
	}

	private String tryToSignUp(HttpServletRequest request, HttpServletResponse response) {
		ArrayList<String> errors = this.getPersonErrorList(request, response).getErrors();
		Person newPerson = this.getPersonErrorList(request, response).getPerson();
		String destination = "index.jsp";
		if (errors.size() <= 0) {
			try {
				System.out.println("pw of created person: " + newPerson.getPassword());
				service.addPerson(newPerson);
				destination = "index.jsp";
			} catch (Exception e) {
				errors.add(e.getMessage());
			}
		}
		if (errors.size() > 0) {
			request.setAttribute("errors", errors);
			request.setAttribute("newPerson", newPerson);
			destination = showSignUpPage(request, response);
		}

		return destination;
	}
	
	private void changeColour(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//TODO Return to referer page, not index.jsp
		System.out.println("Lets change the colour");
		String destination = "index.jsp";
		String colour = "yellow";
		Cookie[] cookies = request.getCookies();
		if (cookies.length!=0) {
			for (Cookie cookie: cookies) {
				if (cookie.getName().equals("colour")) {
					colour = cookie.getValue();
				}
			}
		}
		if (colour.equals("yellow")) {
			colour = "red";
		} else {
			colour = "yellow";
		}
		System.out.println("Colour cookie now: " + colour);
		System.out.println("Request uri: " + request.getRequestURI());
		Cookie cookie = new Cookie("colour", colour);
		response.addCookie(cookie);
		//referer
		String referer = request.getHeader("Referer");
		System.out.println("refer: "+ referer);
		response.sendRedirect(referer);
		//return destination;
	}

	private ProductErrorListPair getProductErrorList(HttpServletRequest request, HttpServletResponse response) {
		ArrayList<String> errors = new ArrayList<String>();

		String name = request.getParameter("name");
		String description = request.getParameter("description");
		String price = request.getParameter("price");

		Product newProduct = new Product();
		try {
			newProduct.setName(name);
		} catch (DomainException e) {
			errors.add(e.getMessage());
		}
		try {
			newProduct.setDescription(description);
		} catch (DomainException e) {
			errors.add(e.getMessage());
		}
		try {
			newProduct.setPrice(price);
		} catch (DomainException e) {
			errors.add(e.getMessage());
		}
		try {
			try {
				Double doubleprice = Double.parseDouble(price);
				newProduct.setPrice(doubleprice);
			} catch (Exception e) {
				errors.add("Price is not a valid number.");
			}
		} catch (DomainException e) {
			errors.add(e.getMessage());
		}
		return new ProductErrorListPair(newProduct, errors);
	}

	private PersonErrorListPair getPersonErrorList(HttpServletRequest request, HttpServletResponse response) {
		ArrayList<String> errors = new ArrayList<String>();

		String userid = request.getParameter("userid");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		Person newPerson = new Person();
		try {
			newPerson.setUserid(userid);
		} catch (IllegalArgumentException e) {
			errors.add(e.getMessage());
		}
		try {
			newPerson.setFirstName(firstName);
		} catch (IllegalArgumentException e) {
			errors.add(e.getMessage());
		}
		try {
			newPerson.setLastName(lastName);
		} catch (IllegalArgumentException e) {
			errors.add(e.getMessage());
		}
		try {
			newPerson.setEmail(email);
		} catch (IllegalArgumentException e) {
			errors.add(e.getMessage());
		}
		try {
			newPerson.setPassword(password);
		} catch (IllegalArgumentException e) {
			errors.add(e.getMessage());
		}
		return new PersonErrorListPair(newPerson, errors);
	}

}
