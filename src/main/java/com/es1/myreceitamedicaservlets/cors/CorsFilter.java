package com.es1.myreceitamedicaservlets.cors;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpFilter;

import java.io.IOException;

@WebFilter("/*") // Aplica o filtro para todas as rotas
public class CorsFilter extends HttpFilter implements Filter {

    private static final long serialVersionUID = 1L;

    public CorsFilter() {
        super();
    }

    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        // Inicialização, se necessário
    }

    @Override
    public void destroy() {
        // Limpeza de recursos, se necessário
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // Permite todas as origens e métodos
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "*");
        res.setHeader("Access-Control-Allow-Headers", "*");

        // Trata requisições preflight (OPTIONS)
        if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
            res.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        chain.doFilter(request, response); // Continua a cadeia de filtros
    }
}