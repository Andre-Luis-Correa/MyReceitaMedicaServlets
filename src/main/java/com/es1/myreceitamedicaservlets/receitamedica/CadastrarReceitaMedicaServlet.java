package com.es1.myreceitamedicaservlets.receitamedica;

import com.es1.myreceitamedicaservlets.utils.JsonUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import unioeste.geral.receitamedica.bo.diagnosticocid.DiagnosticoCID;
import unioeste.geral.receitamedica.bo.receitamedica.ReceitaMedica;
import unioeste.geral.receitamedica.exception.ReceitaMedicaException;
import unioeste.geral.receitamedica.service.UCDiagnosticoCIDServicos;
import unioeste.geral.receitamedica.service.UCReceitaMedicaServicos;

import java.io.IOException;

@WebServlet("/receita-medica/cadastrar")
public class CadastrarReceitaMedicaServlet extends HttpServlet {

    private final JsonUtils jsonUtils = new JsonUtils();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            UCReceitaMedicaServicos ucReceitaMedicaServicos = new UCReceitaMedicaServicos();

            String body = jsonUtils.lerBody(request);

            ReceitaMedica novaReceitaMedica = jsonUtils.convertJsonToEntity(body, ReceitaMedica.class);

            ReceitaMedica receitaMedicaCadastrada = ucReceitaMedicaServicos.cadastrarReceitaMedica(novaReceitaMedica);

            String json = jsonUtils.convertEntityToJson(receitaMedicaCadastrada);
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
