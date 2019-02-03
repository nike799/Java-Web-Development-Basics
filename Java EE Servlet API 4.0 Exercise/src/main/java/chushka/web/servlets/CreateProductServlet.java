package chushka.web.servlets;

import chushka.domain.models.ProductModel;
import chushka.service.ProductService;
import chushka.util.HtmlReader;
import chushka.util.ModelMapper;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet("/create/product")
public class CreateProductServlet extends HttpServlet {
    private final static String CREATE_PRODUCT_HTML_FILE_PATH =
            "C:\\Users\\Nike\\Desktop\\Java Web Development Basics\\Java EE Servlet API 4.0 Exercise\\src\\main\\resources\\views\\create-product.html";
    private final ProductService productService;
    private final ModelMapper modelMapper;
    private final HtmlReader htmlReader;

    @Inject
    public CreateProductServlet(ProductService productService, ModelMapper modelMapper, HtmlReader htmlReader) {
        this.productService = productService;
        this.modelMapper = modelMapper;
        this.htmlReader = htmlReader;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String htmlContent = this.htmlReader.readHtmlFile(CREATE_PRODUCT_HTML_FILE_PATH);
        resp.getWriter().println(htmlContent);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductModel productModel = new ProductModel();
        productModel.setName(req.getParameter("name"));
        productModel.setDescription(req.getParameter("description"));
        productModel.setType(req.getParameter("type"));

        if (req.getSession().getAttribute("products") == null) {
            req.getSession().setAttribute("products", new LinkedHashMap<>());
        }
        ((Map) req.getSession().getAttribute("products")).putIfAbsent(productModel.getName(), productModel);
        this.productService.saveProduct(productModel);
        resp.sendRedirect("/");
    }
}
