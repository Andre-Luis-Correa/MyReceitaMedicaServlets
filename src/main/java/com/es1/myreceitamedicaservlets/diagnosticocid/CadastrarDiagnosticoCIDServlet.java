package com.es1.myreceitamedicaservlets.diagnosticocid;

import com.es1.myreceitamedicaservlets.utils.JsonUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import unioeste.geral.receitamedica.bo.diagnosticocid.DiagnosticoCID;
import unioeste.geral.receitamedica.exception.ReceitaMedicaException;
import unioeste.geral.receitamedica.service.UCDiagnosticoCIDServicos;

import java.io.IOException;

@WebServlet("/diagnostico-cid/cadastrar")
public class CadastrarDiagnosticoCIDServlet extends HttpServlet {

    private final JsonUtils jsonUtils = new JsonUtils();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            UCDiagnosticoCIDServicos ucDiagnosticoCIDServicos = new UCDiagnosticoCIDServicos();

            String body = jsonUtils.lerBody(request);

            DiagnosticoCID novoDiagnosticoCID = jsonUtils.convertJsonToEntity(body, DiagnosticoCID.class);

            DiagnosticoCID diagnosticoCIDCadastrado = ucDiagnosticoCIDServicos.cadastrarDiagnosticoCID(novoDiagnosticoCID);

            String json = jsonUtils.convertEntityToJson(diagnosticoCIDCadastrado);
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
