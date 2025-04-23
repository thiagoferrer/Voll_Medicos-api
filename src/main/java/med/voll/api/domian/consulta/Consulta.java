package med.voll.api.domian.consulta;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;
import med.voll.api.domian.medico.Medico;
import med.voll.api.domian.paciente.Paciente;

import java.time.LocalDateTime;

@Table(name = "consultas")
@Entity(name = "Consulta")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private Medico medico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    private LocalDateTime data;

    @Enumerated(EnumType.STRING)
    @Column(name = "status") // Já está implícito, mas pode deixar explícito
    private StatusConsulta status;

    @Enumerated(EnumType.STRING)
    @Column(name = "motivo_cancelamento") // Use o nome exato do banco
    private MotivoCancelamento motivoCancelamento;

    public Consulta(DadosCadastroConsulta dados, Medico medico, Paciente paciente) {
        this.medico = medico;
        this.paciente = paciente;
        this.data = dados.data();
        this.status = StatusConsulta.AGENDADA;
    }

    public void cancelar(@Valid DadosCancelamentoConsulta dados) {
        this.status = StatusConsulta.CANCELADA;
        this.motivoCancelamento = dados.motivoCancelamento();
    }

    public void atualizarInformacoes(DadosAtualizacaoConsulta dados) {
        if (dados.novaData() != null) {
            this.data = dados.novaData();
        }
    }
}