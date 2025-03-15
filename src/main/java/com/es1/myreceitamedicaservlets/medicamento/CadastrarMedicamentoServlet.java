package com.es1.myreceitamedicaservlets.medicamento;

import com.es1.myreceitamedicaservlets.utils.JsonUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import unioeste.geral.receitamedica.bo.medicamento.Medicamento;
import unioeste.geral.receitamedica.exception.ReceitaMedicaException;
import unioeste.geral.receitamedica.service.UCMedicamentoServicos;

import java.io.IOException;

@WebServlet("/medicamento/cadastrar")
public class CadastrarMedicamentoServlet extends HttpServlet {

    private final JsonUtils jsonUtils = new JsonUtils();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            UCMedicamentoServicos ucMedicamentoServicos = new UCMedicamentoServicos();

            String body = jsonUtils.lerBody(request);

            Medicamento medicamento = jsonUtils.convertJsonToEntity(body, Medicamento.class);

            Medicamento medicamentoCadastrado = ucMedicamentoServicos.cadastrarMedicamento(medicamento);

            String json = jsonUtils.convertEntityToJson(medicamentoCadastrado);
            response.getWriter().write(json);

        } catch (ReceitaMedicaException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("{\"erro\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("{\"erro\":\"Erro interno: " + e.getMessage() + "\"}");
        }
    }
}
