package me.hiramchavez.clinicaapi.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.hiramchavez.clinicaapi.repository.UsuarioRepository;
import me.hiramchavez.clinicaapi.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    private TokenService tokenService;
    private UsuarioRepository usuarioRepository;

    @Autowired
    public SecurityFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain
    ) throws ServletException, IOException {

        /*CODIGO ANTERIOR: //Validar que el token no sea nulo o vacío
        if (token == null || token.isBlank()) {
            throw new RuntimeException("No se ha enviado el token");
        }*/

        //Obtener token del header
        var authHeader = request.getHeader("Authorization");

        /*CODIGO NUEVO*/
        if (authHeader != null) {

            var token = authHeader.replace("Bearer ", ""); //Obtener token sin la palabra Bearer
            var nombreUsuario = tokenService.getSubject(token); //Obtener el username (login: hiram.chavez) del token

            if (nombreUsuario != null) { //Si el subject no es nulo, entonces el usuario esta logueado
                var usuario = usuarioRepository.findByLogin(nombreUsuario); //Obtener el usuario de la base de datos

                var authentication = new UsernamePasswordAuthenticationToken( //Crear un objeto de autenticación
                  usuario, //El usuario
                  null, //No se necesita la contraseña porque ya se autentico
                  usuario.getAuthorities() //Obtener los roles del usuario
                );

                //Establecer el objeto de autenticación en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        }
        //Implementar si el usuario esta logueado o no
        filterChain.doFilter(request, response);
    }
}
