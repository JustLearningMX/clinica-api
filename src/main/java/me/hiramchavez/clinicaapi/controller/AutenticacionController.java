package me.hiramchavez.clinicaapi.controller;

import jakarta.validation.Valid;
import me.hiramchavez.clinicaapi.Model.Usuario;
import me.hiramchavez.clinicaapi.dto.DatosAutenticacionUsuarioRecord;
import me.hiramchavez.clinicaapi.dto.DatosJwtTokenRecord;
import me.hiramchavez.clinicaapi.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    private final AuthenticationManager authenticationManager;
    private TokenService tokenService;

    @Autowired
    public AutenticacionController(
      AuthenticationManager authenticationManager,
        TokenService tokenService
    ) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuarioRecord datosAutenticacionUsuarioRecord) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(
          datosAutenticacionUsuarioRecord.login(),
          datosAutenticacionUsuarioRecord.clave()
        );

        var usuarioAutenticado = authenticationManager.authenticate(authToken);
        var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        return ResponseEntity.ok(new DatosJwtTokenRecord(JWTtoken));
    }
}
