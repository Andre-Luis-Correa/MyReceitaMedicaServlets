package com.es1.myreceitamedicaservlets.paciente;

import com.es1.myreceitamedicaservlets.utils.JsonUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import unioeste.geral.receitamedica.bo.paciente.Paciente;
import unioeste.geral.receitamedica.service.UCPacienteServicos;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/paciente/consultar")
public class ConsultarPacienteServlet extends HttpServlet {

    private final JsonUtils jsonUtils = new JsonUtils();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Long id = Long.parseLong(request.getParameter("id"));

            // Consultando o paciente
            Paciente paciente = UCPacienteServicos.consultarPaciente(id);

            // Convertendo o objeto Paciente para JSON
            String pacienteJsonString = jsonUtils.convertEntityToJson(paciente);

            // Configurando o tipo de conteúdo e a codificação de caracteres da resposta
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // Escrevendo a string JSON na resposta
            PrintWriter out = response.getWriter();
            out.print(pacienteJsonString);
            out.flush();
        } catch (Exception e) {
            // Erros genéricos
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("{\"erro\":\"Erro interno: " + e.getMessage() + "\"}");
        }
    }
}
