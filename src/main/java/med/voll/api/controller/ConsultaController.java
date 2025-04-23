package med.voll.api.controller;

import org.springframework.web.bind.annotation.*;
import med.voll.api.domian.consulta.DadosCadastroConsulta;
import med.voll.api.domian.consulta.DadosCancelamentoConsulta;
import med.voll.api.domian.consulta.AgendaDeConsultasService;
import med.voll.api.domian.consulta.DetalhesConsultaDto;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    private final AgendaDeConsultasService agendaDeConsultasService;

    public ConsultaController(AgendaDeConsultasService agendaDeConsultasService) {
        this.agendaDeConsultasService = agendaDeConsultasService;
    }

    @PostMapping
    public DetalhesConsultaDto agendar(@RequestBody DadosCadastroConsulta dados) {
        return agendaDeConsultasService.agendar(dados);
    }

    @DeleteMapping("/{id}")
    public DetalhesConsultaDto cancelar(@PathVariable Long id, @RequestBody DadosCancelamentoConsulta dados) {
        return agendaDeConsultasService.cancelar(id, dados);
    }
}