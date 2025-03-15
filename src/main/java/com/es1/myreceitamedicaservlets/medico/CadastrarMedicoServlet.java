package com.es1.myreceitamedicaservlets.medico;

import com.es1.myreceitamedicaservlets.utils.JsonUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import unioeste.geral.receitamedica.bo.medico.Medico;
import unioeste.geral.receitamedica.bo.paciente.Paciente;
import unioeste.geral.receitamedica.exception.ReceitaMedicaException;
import unioeste.geral.receitamedica.service.UCMedicoServicos;
import unioeste.geral.receitamedica.service.UCPacienteServicos;

import java.io.IOException;

@WebServlet("/medico/cadastrar")
public class CadastrarMedicoServlet extends HttpServlet {

    private final JsonUtils jsonUtils = new JsonUtils();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            UCMedicoServicos ucMedicoServicos = new UCMedicoServicos();

            String body = jsonUtils.lerBody(request);

            Medico novoMedico = jsonUtils.convertJsonToEntity(body, Medico.class);

            Medico medicoCadastrado = ucMedicoServicos.cadastrarMedico(novoMedico);

            String json = jsonUtils.convertEntityToJson(medicoCadastrado);
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
