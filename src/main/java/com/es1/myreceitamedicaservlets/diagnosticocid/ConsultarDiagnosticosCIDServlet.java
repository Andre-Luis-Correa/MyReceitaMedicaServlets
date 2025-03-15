package com.es1.myreceitamedicaservlets.diagnosticocid;

import com.es1.myreceitamedicaservlets.utils.JsonUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import unioeste.geral.receitamedica.bo.diagnosticocid.DiagnosticoCID;
import unioeste.geral.receitamedica.service.UCDiagnosticoCIDServicos;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/diagnostico-cid")
public class ConsultarDiagnosticosCIDServlet extends HttpServlet {

    private final JsonUtils jsonUtils = new JsonUtils();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            UCDiagnosticoCIDServicos ucDiagnosticoCIDServicos = new UCDiagnosticoCIDServicos();

            List<DiagnosticoCID> diagnosticoCIDS = ucDiagnosticoCIDServicos.obterListaDeDiagnosticosCID();

            String cidJsonString = jsonUtils.convertEntityToJson(diagnosticoCIDS);

            PrintWriter out = response.getWriter();
            out.print(cidJsonString);
            out.flush();
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("{\"erro\":\"Erro interno: " + e.getMessage() + "\"}");
        }
    }
}
