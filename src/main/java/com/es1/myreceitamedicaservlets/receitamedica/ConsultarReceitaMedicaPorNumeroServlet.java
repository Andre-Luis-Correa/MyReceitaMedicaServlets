package com.es1.myreceitamedicaservlets.receitamedica;

import com.es1.myreceitamedicaservlets.utils.JsonUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import unioeste.geral.receitamedica.bo.receitamedica.ReceitaMedica;
import unioeste.geral.receitamedica.service.UCReceitaMedicaServicos;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/receita-medica/consultar")
public class ConsultarReceitaMedicaPorNumeroServlet extends HttpServlet {

    private final JsonUtils jsonUtils = new JsonUtils();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            UCReceitaMedicaServicos ucReceitaMedicaServicos = new UCReceitaMedicaServicos();

            Integer numero = Integer.valueOf(request.getParameter("numero"));

            ReceitaMedica receitaMedica = ucReceitaMedicaServicos.consultarReceitaMedicaPorNumero(numero);

            String receitaMedicaJsonString = jsonUtils.convertEntityToJson(receitaMedica);

            PrintWriter out = response.getWriter();
            out.print(receitaMedicaJsonString);
            out.flush();
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("{\"erro\":\"Erro interno: " + e.getMessage() + "\"}");
        }
    }
}
