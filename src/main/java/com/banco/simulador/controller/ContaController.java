package com.banco.simulador.controller;


import com.banco.simulador.services.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;

@RestController
public class ContaController {

    @Autowired
    private ContaService contaService;

    @PostMapping("/transferencia")
    public String tranferencia(@RequestParam Long idOrigem, @RequestParam Long idDestino, @RequestParam double valor) throws Exception {
        contaService.transferir(idOrigem,idDestino,valor);
        return "Tranferencia feita com sucesso!";
    }


}
