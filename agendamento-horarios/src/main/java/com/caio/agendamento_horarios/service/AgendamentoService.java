package com.caio.agendamento_horarios.service;

import com.caio.agendamento_horarios.infastruture.entity.Agendamento;
import com.caio.agendamento_horarios.infastruture.repository.AgendamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

// Exceções personalizadas
class HorarioJaPreenchidoException extends RuntimeException {
    public HorarioJaPreenchidoException(String message) {
        super(message);
    }
}

class HorarioNaoEncontradoException extends RuntimeException {
    public HorarioNaoEncontradoException(String message) {
        super(message);
    }
}

@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;

    public Agendamento salvarAgendamento(Agendamento agendamento) {
        LocalDateTime horaAgendamento = agendamento.getDataHoraAgendamento();
        LocalDateTime horaFim = horaAgendamento.plusHours(1);

        Agendamento agendado = agendamentoRepository.findByServicoAndDataHoraAgendamentoBetween(agendamento.getServico(),
                horaAgendamento, horaFim);

        if (Objects.nonNull(agendado)) {
            throw new HorarioJaPreenchidoException("Horário já está preenchido");
        }
        return agendamentoRepository.save(agendamento);
    }

    public void deletarAgendamento(LocalDateTime dataHoraAgendamento, String cliente) {
        agendamentoRepository.deleteByDataHoraAgendamentoAndCliente(dataHoraAgendamento, cliente);
    }

    public Agendamento buscarAgendamentoDia(LocalDate data) {
        LocalDateTime primeiraHoraDia = data.atStartOfDay();
        LocalDateTime horaFinalDia = data.atTime(23, 59, 59);
        return agendamentoRepository.findByDataHoraAgendamentoBetween(primeiraHoraDia, horaFinalDia);
    }

    public Agendamento alterarAgendamento(Agendamento agendamento, String cliente, LocalDateTime dataHoraAgendamento) {
        Agendamento agenda = agendamentoRepository.findByDataHoraAgendamentoAndCliente(dataHoraAgendamento, cliente);

        if (Objects.isNull(agenda)) {
            throw new HorarioNaoEncontradoException("Horário não encontrado para o cliente");
        }
        agendamento.setId(agenda.getId());
        return agendamentoRepository.save(agendamento);
    }
}