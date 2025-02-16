package com.es1.myreceitamedicaservlets.endereco;

import com.es1.myreceitamedicaservlets.utils.JsonUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import unioeste.geral.endereco.bo.cidade.Cidade;
import unioeste.geral.endereco.service.UCEnderecoGeralServicos;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/endereco/cidade/id")
public class ObterCidadePorIdServlet extends HttpServlet {

    private final JsonUtils jsonUtils = new JsonUtils();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            Long id = Long.valueOf(request.getParameter("id"));

            Cidade cidade = UCEnderecoGeralServicos.obterCidade(id);

            String cidadeJsonString = jsonUtils.convertEntityToJson(cidade);

            PrintWriter out = response.getWriter();
            out.print(cidadeJsonString);
            out.flush();
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("{\"erro\":\"Erro interno: " + e.getMessage() + "\"}");
        }
    }
}
