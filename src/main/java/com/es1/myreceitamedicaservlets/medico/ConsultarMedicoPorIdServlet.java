package com.es1.myreceitamedicaservlets.medico;

import com.es1.myreceitamedicaservlets.utils.JsonUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import unioeste.geral.receitamedica.bo.medico.Medico;
import unioeste.geral.receitamedica.bo.paciente.Paciente;
import unioeste.geral.receitamedica.service.UCMedicoServicos;
import unioeste.geral.receitamedica.service.UCPacienteServicos;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/medico/consultar")
public class ConsultarMedicoPorIdServlet extends HttpServlet {

    private final JsonUtils jsonUtils = new JsonUtils();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            UCMedicoServicos ucMedicoServicos = new UCMedicoServicos();

            Long id = Long.parseLong(request.getParameter("id"));

            Medico medico = ucMedicoServicos.consultarMedicoPorId(id);

            String medicoJsonString = jsonUtils.convertEntityToJson(medico);

            PrintWriter out = response.getWriter();
            out.print(medicoJsonString);
            out.flush();
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("{\"erro\":\"Erro interno: " + e.getMessage() + "\"}");
        }
    }
}
