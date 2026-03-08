package com.caio.agendamento_horarios.controller;

import com.caio.agendamento_horarios.infastruture.entity.Agendamento;
import com.caio.agendamento_horarios.service.AgendamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/agendamentos")
@RequiredArgsConstructor
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    @PostMapping
    public ResponseEntity<Agendamento> salvarAgendamento(@RequestBody Agendamento agendamento) {
        return ResponseEntity.accepted().body(agendamentoService.salvarAgendamento(agendamento));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarAgendamento(@RequestParam String cliente,
                                                   @RequestParam LocalDateTime dataHoraAgendamento) {
        agendamentoService.deletarAgendamento(dataHoraAgendamento, cliente);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Agendamento> BuscarAgendamentosDia(@RequestParam LocalDate data){
        return ResponseEntity.ok().body(agendamentoService.buscarAgendamentoDia(data));
    }

    @PutMapping
    public ResponseEntity<Agendamento> AlterarAgendamentos(@RequestBody Agendamento agendamento,
                                                           @RequestParam String cliente,
                                                           @RequestParam LocalDateTime dataHoraAgendamento){
        return ResponseEntity.accepted().body(agendamentoService.alterarAgendamento(agendamento,
                cliente, dataHoraAgendamento));
    }
}