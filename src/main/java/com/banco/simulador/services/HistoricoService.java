package com.banco.simulador.services;

import ch.qos.logback.core.status.StatusBase;
import com.banco.simulador.model.Historico;
import com.banco.simulador.model.Usuarios;
import com.banco.simulador.repository.HistoricoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service
public class HistoricoService {

    @Autowired
    private HistoricoRepository historicoRepository;

    public void salvarHistorico(Usuarios usuarioOrigem, Long idDestino, BigDecimal valorTransferencia){
        Historico historico = new Historico();
        try {
            Date dataAtual = new Date();
            historico.setDataInicial(dataAtual);
            historico.setDescricao("Tranferencia para:"+idDestino);
            historico.setSaldoFinal(usuarioOrigem.getConta().getSaldo());
            historico.setUsuarios(usuarioOrigem);
            historico.setValor(valorTransferencia);
            historicoRepository.save(historico);
        }catch (Exception e){
           System.out.println(e.getMessage());
        }


    }

    public List<Historico> buscarHistoricoMes(Integer mes,Long idUsuario){

        return  historicoRepository.findByMes(mes, idUsuario);

    }
}
