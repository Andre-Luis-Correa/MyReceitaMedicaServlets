package com.es1.myreceitamedicaservlets.endereco;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import unioeste.geral.endereco.bo.bairro.Bairro;
import unioeste.geral.endereco.bo.cidade.Cidade;
import unioeste.geral.endereco.bo.endereco.Endereco;
import unioeste.geral.endereco.bo.logradouro.Logradouro;
import unioeste.geral.endereco.bo.tipologradouro.TipoLogradouro;
import unioeste.geral.endereco.bo.unidadefederativa.UnidadeFederativa;
import unioeste.geral.endereco.exception.EnderecoException;
import unioeste.geral.endereco.service.UCEnderecoGeralServicos;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

// Anotação indicando que todas as requisições que começarem com /endereco
// serão tratadas por esta servlet.
@WebServlet("/endereco/*")
public class EnderecoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Instância do Gson para serializar/deserializar JSON
    // Você pode personalizar o GsonBuilder se precisar de mais configurações.
    private final Gson gson = new GsonBuilder().create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        // Configura o tipo de retorno e encoding
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // pathInfo vai conter o que vier após /endereco
        // Exemplo: /endereco/porCep --> pathInfo = "/porCep"
        String pathInfo = request.getPathInfo();

        try {
            // Se nada for especificado (ex.: /endereco sem nada depois),
            // podemos tratar como operação não encontrada ou listar algo genérico.
            if (pathInfo == null || pathInfo.equals("/")) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().println("{\"erro\":\"Operação não encontrada\"}");
                return;
            }

            switch (pathInfo) {

                // --------------------
                // 1) Obter Endereço(s) por CEP
                // Exemplo de URL: GET /endereco/cep?cep=12345-678
                // --------------------
                case "/cep": {
                    String cep = request.getParameter("cep");
                    List<Endereco> enderecos = UCEnderecoGeralServicos.obterEnderecoPorCep(cep);
                    String json = gson.toJson(enderecos);
                    response.getWriter().write(json);
                    break;
                }

                // --------------------
                // 2) Obter Endereço por ID
                // Exemplo de URL: GET /endereco/id?id=10
                // --------------------
                case "/id": {
                    String idParam = request.getParameter("id");
                    Long id = idParam != null && !idParam.isEmpty() ? Long.valueOf(idParam) : null;
                    Endereco endereco = UCEnderecoGeralServicos.obterEnderecoPorId(id);
                    String json = gson.toJson(endereco);
                    response.getWriter().write(json);
                    break;
                }

                // --------------------
                // 3) Obter Endereço Externo (via CEP API)
                // Exemplo de URL: GET /endereco/externo?cep=12345-678
                // --------------------
                case "/externo": {
                    String cep = request.getParameter("cep");
                    Endereco endereco = UCEnderecoGeralServicos.obterEnderecoExterno(cep);
                    String json = gson.toJson(endereco);
                    response.getWriter().write(json);
                    break;
                }

                // --------------------
                // 4) Obter Cidade por ID
                // Exemplo de URL: GET /endereco/cidade?id=5
                // --------------------
                case "/cidade": {
                    String idParam = request.getParameter("id");
                    Long id = idParam != null && !idParam.isEmpty() ? Long.valueOf(idParam) : null;
                    Cidade cidade = UCEnderecoGeralServicos.obterCidade(id);
                    String json = gson.toJson(cidade);
                    response.getWriter().write(json);
                    break;
                }

                // --------------------
                // 5) Obter Lista de Unidades Federativas
                // Exemplo de URL: GET /endereco/ufs
                // --------------------
                case "/ufs": {
                    List<UnidadeFederativa> listaUf = UCEnderecoGeralServicos.obterListaDeUnidadesFederativas();
                    String json = gson.toJson(listaUf);
                    response.getWriter().write(json);
                    break;
                }

                // --------------------
                // 6) Obter Lista de Cidades
                // Exemplo de URL: GET /endereco/cidades
                // --------------------
                case "/cidades": {
                    List<Cidade> listaCidades = UCEnderecoGeralServicos.obterListaDeCidades();
                    String json = gson.toJson(listaCidades);
                    response.getWriter().write(json);
                    break;
                }

                // --------------------
                // 7) Obter Lista de Bairros
                // Exemplo de URL: GET /endereco/bairros
                // --------------------
                case "/bairros": {
                    List<Bairro> listaBairros = UCEnderecoGeralServicos.obterListaDeBairros();
                    String json = gson.toJson(listaBairros);
                    response.getWriter().write(json);
                    break;
                }

                // --------------------
                // 8) Obter Lista de Logradouros
                // Exemplo de URL: GET /endereco/logradouros
                // --------------------
                case "/logradouros": {
                    List<Logradouro> listaLogradouros = UCEnderecoGeralServicos.obterListaDeLogradouros();
                    String json = gson.toJson(listaLogradouros);
                    response.getWriter().write(json);
                    break;
                }

                // --------------------
                // 9) Obter Lista de Tipos de Logradouro
                // Exemplo de URL: GET /endereco/tiposLogradouro
                // --------------------
                case "/tipos-logradouro": {
                    List<TipoLogradouro> listaTipos = UCEnderecoGeralServicos.obterListaDeTipoLogradouros();
                    String json = gson.toJson(listaTipos);
                    response.getWriter().write(json);
                    break;
                }

                // --------------------
                // 10) Obter Lista de Enderecos
                // Exemplo de URL: GET /endereco/enderecos
                // --------------------
                case "/enderecos": {
                    List<Endereco> listaEnderecos = UCEnderecoGeralServicos.obterListaDeEnderecos();
                    String json = gson.toJson(listaEnderecos);
                    response.getWriter().write(json);
                    break;
                }

                // Se nenhum dos casos acima corresponder:
                default:
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().println("{\"ERRO\":\"Operação não encontrada\"}");
                    break;
            }

        } catch (EnderecoException e) {
            // Erros de validação ou de regra de negócio
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("{\"erro\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            // Erros genéricos
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("{\"erro\":\"Erro interno: " + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // pathInfo vai conter o que vier após /endereco no POST
        // Exemplo: /endereco/cadastrar --> pathInfo = "/cadastrar"
        String pathInfo = request.getPathInfo();

        try {
            // Exemplo de rota para cadastrar: POST /endereco/cadastrar
            if (pathInfo != null && pathInfo.equals("/cadastrar")) {
                // Le o corpo (JSON) da requisição
                String body = lerBody(request);

                // Converte o JSON para um objeto Endereco
                Endereco novoEndereco = gson.fromJson(body, Endereco.class);

                // Chama o método de serviço para cadastrar
                Endereco enderecoCadastrado = UCEnderecoGeralServicos.cadastrarEndereco(novoEndereco);

                // Converte o resultado para JSON e escreve na resposta
                String json = gson.toJson(enderecoCadastrado);
                response.getWriter().write(json);

            } else {
                // Se não for /cadastrar, retornamos 404
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().println("{\"erro\":\"Operação POST não encontrada\"}");
            }

        } catch (EnderecoException e) {
            // Erros de validação ou de regra de negócio
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("{\"erro\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            // Erros genéricos
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("{\"erro\":\"Erro interno: " + e.getMessage() + "\"}");
        }
    }

    /**
     * Lê todo o corpo da requisição (que está em formato texto/JSON)
     * e retorna como String.
     */
    private String lerBody(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                sb.append(linha);
            }
        }
        return sb.toString();
    }
}
