# 1. Usamos a imagem base do Eclipse Temurin (Adoptium) com Java 23
FROM eclipse-temurin:23-jdk

# 2. Define variáveis para o Tomcat
ENV TOMCAT_VERSION=11.0.2
ENV CATALINA_HOME=/usr/local/tomcat
ENV PATH="${CATALINA_HOME}/bin:${PATH}"

# 3. Baixa e instala o Tomcat manualmente
RUN apt-get update && apt-get install -y wget && \
    wget -q https://dlcdn.apache.org/tomcat/tomcat-11/v${TOMCAT_VERSION}/bin/apache-tomcat-${TOMCAT_VERSION}.tar.gz && \
    tar -xzf apache-tomcat-${TOMCAT_VERSION}.tar.gz && \
    mv apache-tomcat-${TOMCAT_VERSION} ${CATALINA_HOME} && \
    rm -rf apache-tomcat-${TOMCAT_VERSION}.tar.gz && \
    rm -rf ${CATALINA_HOME}/webapps/*  # Remove apps padrão do Tomcat

# 4. Copia o WAR gerado para dentro do Tomcat
COPY target/api.war ${CATALINA_HOME}/webapps/api.war

# 5. Expondo a porta 8080 do Tomcat
EXPOSE 8080

# 6. Comando para iniciar o Tomcat
CMD ["catalina.sh", "run"]
