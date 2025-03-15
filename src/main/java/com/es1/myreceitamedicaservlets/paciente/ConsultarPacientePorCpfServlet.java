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
public class ConsultarPacientePorCpfServlet extends HttpServlet {

    private final JsonUtils jsonUtils = new JsonUtils();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            UCPacienteServicos ucPacienteServicos = new UCPacienteServicos();

            String cpf = request.getParameter("cpf");

            Paciente paciente = ucPacienteServicos.consultarPacientePorCpf(cpf);

            String pacienteJsonString = jsonUtils.convertEntityToJson(paciente);

            PrintWriter out = response.getWriter();
            out.print(pacienteJsonString);
            out.flush();
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("{\"erro\":\"Erro interno: " + e.getMessage() + "\"}");
        }
    }
}
