package com.es1.myreceitamedicaservlets.receitamedica;

import com.es1.myreceitamedicaservlets.utils.JsonUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import unioeste.geral.receitamedica.bo.medicamento.Medicamento;
import unioeste.geral.receitamedica.bo.receitamedica.ReceitaMedica;
import unioeste.geral.receitamedica.service.UCMedicamentoServicos;
import unioeste.geral.receitamedica.service.UCReceitaMedicaServicos;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/receita-medica")
public class ConsultarReceitasMedicasServlet extends HttpServlet {

    private final JsonUtils jsonUtils = new JsonUtils();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            UCReceitaMedicaServicos ucReceitaMedicaServicos = new UCReceitaMedicaServicos();

            List<ReceitaMedica> receitaMedicas = ucReceitaMedicaServicos.obterListaDeReceitasMedicas();

            String receitasMedicasJsonString = jsonUtils.convertEntityToJson(receitaMedicas);

            PrintWriter out = response.getWriter();
            out.print(receitasMedicasJsonString);
            out.flush();
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("{\"erro\":\"Erro interno: " + e.getMessage() + "\"}");
        }
    }
}
