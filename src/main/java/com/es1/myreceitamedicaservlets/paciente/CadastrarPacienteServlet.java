package com.es1.myreceitamedicaservlets.paciente;

import com.es1.myreceitamedicaservlets.utils.JsonUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import unioeste.geral.receitamedica.bo.paciente.Paciente;
import unioeste.geral.receitamedica.exception.ReceitaMedicaException;
import unioeste.geral.receitamedica.service.UCPacienteServicos;

import java.io.IOException;

@WebServlet("/paciente/cadastrar")
public class CadastrarPacienteServlet extends HttpServlet {

    private final JsonUtils jsonUtils = new JsonUtils();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            UCPacienteServicos ucPacienteServicos = new UCPacienteServicos();

            String body = jsonUtils.lerBody(request);

            Paciente novoPaciente = jsonUtils.convertJsonToEntity(body, Paciente.class);

            Paciente pacienteCadastrado = ucPacienteServicos.cadastrarPaciente(novoPaciente);

            String json = jsonUtils.convertEntityToJson(pacienteCadastrado);
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
