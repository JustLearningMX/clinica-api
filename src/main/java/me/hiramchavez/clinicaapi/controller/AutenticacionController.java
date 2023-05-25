package me.hiramchavez.clinicaapi.controller;

import jakarta.validation.Valid;
import me.hiramchavez.clinicaapi.dto.DatosAutenticacionUsuarioRecord;
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

    @Autowired
    public AutenticacionController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuarioRecord datosAutenticacionUsuarioRecord) {
        Authentication token = new UsernamePasswordAuthenticationToken(
          datosAutenticacionUsuarioRecord.login(),
          datosAutenticacionUsuarioRecord.clave()
        );

        authenticationManager.authenticate(token);
        return ResponseEntity.ok().build();
    }
}