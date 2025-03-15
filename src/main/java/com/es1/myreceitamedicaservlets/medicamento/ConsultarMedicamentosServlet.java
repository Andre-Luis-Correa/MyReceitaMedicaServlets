package com.es1.myreceitamedicaservlets.medicamento;

import com.es1.myreceitamedicaservlets.utils.JsonUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import unioeste.geral.receitamedica.bo.diagnosticocid.DiagnosticoCID;
import unioeste.geral.receitamedica.bo.medicamento.Medicamento;
import unioeste.geral.receitamedica.service.UCDiagnosticoCIDServicos;
import unioeste.geral.receitamedica.service.UCMedicamentoServicos;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/medicamento")
public class ConsultarMedicamentosServlet extends HttpServlet {

    private final JsonUtils jsonUtils = new JsonUtils();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            UCMedicamentoServicos ucMedicamentoServicos = new UCMedicamentoServicos();

            List<Medicamento> medicamentoList = ucMedicamentoServicos.obterListaDeMedicamentos();

            String medicamentosJsonString = jsonUtils.convertEntityToJson(medicamentoList);

            PrintWriter out = response.getWriter();
            out.print(medicamentosJsonString);
            out.flush();
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("{\"erro\":\"Erro interno: " + e.getMessage() + "\"}");
        }
    }
}
