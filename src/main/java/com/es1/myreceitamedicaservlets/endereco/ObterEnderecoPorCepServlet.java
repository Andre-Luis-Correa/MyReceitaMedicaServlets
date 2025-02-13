package com.es1.myreceitamedicaservlets.endereco;

import com.es1.myreceitamedicaservlets.utils.JsonUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import unioeste.geral.endereco.bo.endereco.Endereco;
import unioeste.geral.endereco.service.UCEnderecoGeralServicos;
import unioeste.geral.receitamedica.bo.paciente.Paciente;
import unioeste.geral.receitamedica.service.UCPacienteServicos;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/endereco/cep")
public class ObterEnderecoPorCepServlet extends HttpServlet {

    private final JsonUtils jsonUtils = new JsonUtils();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String cep = request.getParameter("cep");

            List<Endereco> enderecoList = UCEnderecoGeralServicos.obterEnderecoPorCep(cep);

            String enderecoJsonString = jsonUtils.convertEntityToJson(enderecoList);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            PrintWriter out = response.getWriter();
            out.print(enderecoJsonString);
            out.flush();
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("{\"erro\":\"Erro interno: " + e.getMessage() + "\"}");
        }
    }
}
