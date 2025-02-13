package com.es1.myreceitamedicaservlets.endereco;

import com.es1.myreceitamedicaservlets.utils.JsonUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import unioeste.geral.endereco.bo.endereco.Endereco;
import unioeste.geral.endereco.bo.unidadefederativa.UnidadeFederativa;
import unioeste.geral.endereco.service.UCEnderecoGeralServicos;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/endereco/ufs")
public class ObterUnidadesFederativasServlet extends HttpServlet {

    private final JsonUtils jsonUtils = new JsonUtils();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            List<UnidadeFederativa> unidadeFederativaList = UCEnderecoGeralServicos.obterListaDeUnidadesFederativas();

            String ufsJsonString = jsonUtils.convertEntityToJson(unidadeFederativaList);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            PrintWriter out = response.getWriter();
            out.print(ufsJsonString);
            out.flush();
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("{\"erro\":\"Erro interno: " + e.getMessage() + "\"}");
        }
    }
}
