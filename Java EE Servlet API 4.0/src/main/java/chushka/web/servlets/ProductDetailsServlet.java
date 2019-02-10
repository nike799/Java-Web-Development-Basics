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

@WebServlet("/product/details")
public class ProductDetailsServlet extends HttpServlet {
    private final static String PRODUCT_DETAILS_HTML_FILE_PATH =
            "C:\\Users\\Nike\\Desktop\\Java Web Development Basics\\Java EE Servlet API 4.0 Exercise\\src\\main\\resources\\views\\product-details.html";
    private final ProductService productService;
    private final ModelMapper modelMapper;
    private final HtmlReader htmlReader;

    @Inject
    public ProductDetailsServlet(ProductService productService, ModelMapper modelMapper, HtmlReader htmlReader) {
        this.productService = productService;
        this.modelMapper = modelMapper;
        this.htmlReader = htmlReader;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productName = req.getQueryString().split("=")[1];
        ProductModel productModel = this.productService.getProductModelByName(productName);
        String htmlContent = this.htmlReader.readHtmlFile(PRODUCT_DETAILS_HTML_FILE_PATH);
        if (productModel != null) {
            htmlContent = htmlContent
                    .replace("{{name}}", productModel.getName())
                    .replace("{{description}}", productModel.getDescription())
                    .replace("{{type}}", productModel.getType().toUpperCase());

        }
        resp.getWriter().println(htmlContent);
    }
}
