package com.banco.simulador.controller;


import com.banco.simulador.model.Usuarios;
import com.banco.simulador.services.ContaService;
import com.banco.simulador.services.UsuarioService;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.banco.simulador.repository.UsuarioRepository;

import java.util.List;

@RestController
//@RequestMapping("/api/usuarios")
public class UsuarioController {


     @Autowired
     private UsuarioRepository repository;
     @Autowired
     private UsuarioService usuarioService;
     @Autowired
     private ContaService contaService;


     @GetMapping("/lista")
     @PreAuthorize("hasRole('USER')")
     public List<Usuarios> listarUsuarios(){
         List<Usuarios> usuarios = (List<Usuarios>)repository.findAll();
         return usuarios;
     }

     @PostMapping("/salvar")
     public String salvar(@RequestBody Usuarios model){
         boolean validarCpf = usuarioService.isValidaCpf(model.getCpf());
         boolean validarEmail = usuarioService.isValidarEmail(model.getEmail());
         if(validarCpf & validarEmail){
             String senha = usuarioService.criptografarSenha(model);
             model.setPassword(senha);
             model = contaService.inicializarConta(model);
             repository.save(model);
             return "Salvo com sucesso.";
         }
         return "Erro ao salvar.";

     }

    @DeleteMapping("/{id}")
    public String deletar(@PathVariable long id){
        repository.deleteById(id);
        return "deletado com sucesso";
    }

    @PutMapping("/{id}")
    public String update(@RequestBody Usuarios model, @PathVariable int id){
        if(model.getId() == id){
            repository.save(model);
            return "alterado com sucesso";
        }
        return "id da url diferente do body";
    }
}
