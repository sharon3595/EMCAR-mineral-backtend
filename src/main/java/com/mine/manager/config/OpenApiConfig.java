package com.mine.manager.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Mine Manager API",
                version = "1.0.0",
                description = "Documentación oficial del sistema de gestión minera (Monolito)",
                contact = @Contact(
                        name = "Soporte Técnico",
                        email = "soporte@minemanager.com"
                )
        ),
        servers = {
                @Server(
                        description = "Servidor Producción (Render)",
                        url = "https://emcar-mineral-backtend.onrender.com"
                ),
                @Server(
                        description = "Servidor Producción (Render)",
                        url = "https://comcar-app-9lxoj.ondigitalocean.app/api"
                ),
                @Server(
                        description = "Servidor Local",
                        url = "http://localhost:8080"
                )
        },
        security = {
                @SecurityRequirement(name = "bearerAuth")
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "Ingrese el token JWT obtenido en el login",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}