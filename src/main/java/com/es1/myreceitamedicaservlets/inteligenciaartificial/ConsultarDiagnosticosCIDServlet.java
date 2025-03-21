package com.es1.myreceitamedicaservlets.inteligenciaartificial;

import com.es1.myreceitamedicaservlets.utils.JsonUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import unioeste.geral.receitamedica.service.UCInteligenciaArtificial;
import unioeste.geral.receitamedica.utils.RespostaInteligenciaArtificial;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/inteligencia-artificial")
public class ConsultarDiagnosticosCIDServlet extends HttpServlet {

    private final JsonUtils jsonUtils = new JsonUtils();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            UCInteligenciaArtificial ucInteligenciaArtificial = new UCInteligenciaArtificial();

            String prompt = request.getParameter("prompt");

            RespostaInteligenciaArtificial respostaInteligenciaArtificial = ucInteligenciaArtificial.obterRespostaDaInteligenciaArtifical(prompt);

            String respostaIAJsonString = jsonUtils.convertEntityToJson(respostaInteligenciaArtificial);

            PrintWriter out = response.getWriter();
            out.print(respostaIAJsonString);
            out.flush();
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("{\"erro\":\"Erro interno: " + e.getMessage() + "\"}");
        }
    }
}
