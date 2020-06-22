package controller;

import Collection.ProductSearchPredicate;
import model.Product;
import service.IProducService;
import service.ProductServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@WebServlet(name = "ProductManager", urlPatterns = "/products")
public class ProductServlet extends HttpServlet {
	private IProducService producService = new ProductServiceImpl();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		if (action == null)
			action = "";

		switch (action){
			case "create":
				createProduct(req, resp);
				break;

			case "edit":
				editProduct(req, resp);
				break;

			case "delete":
				deleteCustomer(req, resp);
				break;

			case "search":
				searchProduct(req, resp);
				break;

			default:
				break;
		}
	}

	private void deleteCustomer(HttpServletRequest req, HttpServletResponse resp) {
		int id = Integer.parseInt(req.getParameter("id"));
		Product product = producService.findByID(id);
		if (product == null) {
			forwardDispatcher(req, resp, "error-404.jsp");
		}else{
			producService.remove(id);
			try {
				resp.sendRedirect("/products");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void searchProduct(HttpServletRequest req, HttpServletResponse resp) {
		String search = req.getParameter("search");

		if (search == null || search.length() == 0){
			req.setAttribute("message", "Please enter the product name you want to search");
		}else{
			ProductSearchPredicate check = new ProductSearchPredicate();
			check.setCheck(search);
			List<Product> results = producService.findAll().stream().filter(check).collect(Collectors.toList());
			System.out.println(results);
			System.out.println(results.size());

			req.setAttribute("results", results);
		}
		forwardDispatcher(req, resp, "product/search.jsp");
	}

	private void editProduct(HttpServletRequest req, HttpServletResponse resp) {
		int id = Integer.parseInt(req.getParameter("id"));
		String name     = req.getParameter("name");
		Float price     = Float.parseFloat(req.getParameter("price"));
		String desc     = req.getParameter("description");
		String producer = req.getParameter("producer");

		Product product = producService.findByID(id);

		if(product == null)
		{
			forwardDispatcher(req, resp, "error-404.jsp");
		} else {
			if(name   == null    || name.length() == 0     ||
				price == null    || price <= 0             ||
				desc  == null    || desc.length() == 0     ||
				producer == null || producer.length() == 0)
			{
				req.setAttribute("message", "Please enter all fields");
			}else{
				product.setName(name);
				product.setPrice(price);
				product.setDescription(desc);
				product.setProducer(producer);
				req.setAttribute("message", "Product information was updated");
			}
			req.setAttribute("product", product);
			forwardDispatcher(req, resp, "product/edit.jsp");
		}
	}

	private void createProduct(HttpServletRequest req, HttpServletResponse resp) {
		String name     = req.getParameter("name");
		Float price     = Float.parseFloat(req.getParameter("price"));
		String desc     = req.getParameter("description");
		String producer = req.getParameter("producer");

		int id = (int)(Math.random() * 10000);

		if (name  == null    || name.length() == 0     ||
			price == null    || price <= 0             ||
			desc  == null    || desc.length() == 0     ||
			producer == null || producer.length() == 0
		){
			req.setAttribute("message", "Please enter all fields");
		} else {
			Product customer = new Product(id, name, price, desc, producer);
			producService.save(customer);
			req.setAttribute("message", "New product was created");
		}

		forwardDispatcher(req, resp, "product/create.jsp");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		if (action == null)
			action = "";

		switch (action){
			case "create":
				showCreateForm(req, resp);
				break;

			case "edit":
				showEditForm(req, resp);
				break;

			case "delete":
				showDeleteForm(req, resp);
				break;

			case "view":
				viewProduct(req, resp);
				break;

			case "search":
				showSearchProduct(req, resp);
				break;

			default:
				showListProduct(req, resp);
				break;
		}
	}

	private void showSearchProduct(HttpServletRequest req, HttpServletResponse resp) {
		forwardDispatcher(req, resp, "product/search.jsp");
	}

	private void viewProduct(HttpServletRequest req, HttpServletResponse resp) {
		int id = Integer.parseInt(req.getParameter("id"));
		Product product = producService.findByID(id);
		if (product == null) {
			forwardDispatcher(req, resp, "error-404.jsp");
		}else{
			req.setAttribute("product", product);
			forwardDispatcher(req, resp, "product/view.jsp");
		}
	}

	private void showDeleteForm(HttpServletRequest req, HttpServletResponse resp) {
		int id = Integer.parseInt(req.getParameter("id"));
		Product product = producService.findByID(id);
		if (product == null) {
			forwardDispatcher(req, resp, "error-404.jsp");
		}else{
			req.setAttribute("product", product);
			forwardDispatcher(req, resp, "product/delete.jsp");
		}
	}

	private void showEditForm(HttpServletRequest req, HttpServletResponse resp) {
		int id = Integer.parseInt(req.getParameter("id"));
		Product product = producService.findByID(id);
		if (product == null){
			forwardDispatcher(req, resp, "error-404.jsp");
		}else{
			req.setAttribute("product", product);
			forwardDispatcher(req, resp, "product/edit.jsp");
		}
	}

	private void showCreateForm(HttpServletRequest req, HttpServletResponse resp) {
		forwardDispatcher(req, resp, "product/create.jsp");
	}

	private void showListProduct(HttpServletRequest req, HttpServletResponse resp) {
		List<Product> products = this.producService.findAll();
		req.setAttribute("products", products);

		forwardDispatcher(req, resp, "product/list.jsp");
	}

	private void forwardDispatcher(HttpServletRequest req, HttpServletResponse resp, String layout){
		RequestDispatcher dispatcher = req.getRequestDispatcher(layout);
		try {
			dispatcher.forward(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
