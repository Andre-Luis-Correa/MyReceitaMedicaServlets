package com.es1.myreceitamedicaservlets.endereco;

import com.es1.myreceitamedicaservlets.utils.JsonUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import unioeste.geral.endereco.bo.endereco.Endereco;
import unioeste.geral.endereco.exception.EnderecoException;
import unioeste.geral.endereco.service.UCEnderecoGeralServicos;

import java.io.IOException;

@WebServlet("/endereco/cadastrar")
public class CadastrarEnderecoServlet extends HttpServlet {

    private final JsonUtils jsonUtils = new JsonUtils();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            String body = jsonUtils.lerBody(request);

            Endereco novoEndereco = jsonUtils.convertJsonToEntity(body, Endereco.class);

            Endereco enderecoCadastrado = UCEnderecoGeralServicos.cadastrarEndereco(novoEndereco);

            String json = jsonUtils.convertEntityToJson(enderecoCadastrado);
            response.getWriter().write(json);

        } catch (EnderecoException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("{\"erro\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("{\"erro\":\"Erro interno: " + e.getMessage() + "\"}");
        }
    }
}
