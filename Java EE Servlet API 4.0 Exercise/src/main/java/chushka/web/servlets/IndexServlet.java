package chushka.web.servlets;

import chushka.domain.models.ProductModel;
import chushka.service.ProductService;
import chushka.util.HtmlReader;
import chushka.util.ModelMapper;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/")
public class IndexServlet extends HttpServlet {
    private final static String INDEX_HTML_FILE_PATH =
            "C:\\Users\\Nike\\Desktop\\Java Web Development Basics\\Java EE Servlet API 4.0 Exercise\\src\\main\\resources\\views\\index.html";
    private final ProductService productService;
    private final ModelMapper modelMapper;
    private final HtmlReader htmlReader;

    @Inject
    public IndexServlet(ProductService productService, ModelMapper modelMapper, HtmlReader htmlReader) {
        this.productService = productService;
        this.modelMapper = modelMapper;
        this.htmlReader = htmlReader;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String htmlContent = this.htmlReader.readHtmlFile(INDEX_HTML_FILE_PATH);
        List<ProductModel> productModels = this.productService.getAllProducts();
        System.out.println();
        if (productModels != null) {
            StringBuilder allProducts = new StringBuilder();
            allProducts.append("<ul>").append(System.lineSeparator());
            productModels.forEach(productModel ->
                    allProducts.append(String.format("<li><a href=\"/product/details?name=%s\">%s<a/></li><br/>",
                            productModel.getName(), productModel.getName())));
            allProducts.append("</ul>");
            htmlContent = htmlContent
                    .replace("{{ListOfProducts}}", allProducts.toString());

        } else {
            htmlContent = htmlContent
                    .replace("{{ListOfProducts}}", "");
        }
        resp.getWriter().println(htmlContent);
    }
}
