package com.es1.myreceitamedicaservlets.pessoa;

import com.es1.myreceitamedicaservlets.utils.JsonUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import unioeste.geral.pessoa.bo.ddd.DDD;
import unioeste.geral.pessoa.service.ServicosPessoasGerais;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/pessoa/ddds")
public class ObterDDDServlet extends HttpServlet {

    private final JsonUtils jsonUtils = new JsonUtils();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            ServicosPessoasGerais servicosPessoasGerais = new ServicosPessoasGerais();

            List<DDD> dddList = servicosPessoasGerais.obterTodosDDD();

            String dddJsonString = jsonUtils.convertEntityToJson(dddList);

            PrintWriter out = response.getWriter();
            out.print(dddJsonString);
            out.flush();
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("{\"erro\":\"Erro interno: " + e.getMessage() + "\"}");
        }
    }
}
