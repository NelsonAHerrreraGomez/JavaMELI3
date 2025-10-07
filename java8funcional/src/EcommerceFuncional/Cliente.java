package EcommerceFuncional;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Cliente {
    private final String nome;
    private final String documento;
    private final LocalDate dataNascimento;

    public Cliente(String nome, String documento, String dataNascimentoStr) {
        this.nome = Objects.requireNonNull(nome, "nome é obrigatório");
        this.documento = Objects.requireNonNull(documento, "documento é obrigatório");
        this.dataNascimento = parseData(dataNascimentoStr);
    }

    private LocalDate parseData(String dataStr) {
        List<DateTimeFormatter> formatos = Arrays.asList(
                DateTimeFormatter.ISO_LOCAL_DATE,       // yyyy-MM-dd
                DateTimeFormatter.ofPattern("d-M-uuuu"),// d-M-yyyy
                DateTimeFormatter.ofPattern("d/M/uuuu"),// d/M/yyyy
                DateTimeFormatter.ofPattern("dd-MM-uuuu"),
                DateTimeFormatter.ofPattern("dd/MM/uuuu")
        );
        for (DateTimeFormatter f : formatos) {
            try {
                return LocalDate.parse(dataStr.trim(), f);
            } catch (DateTimeParseException ignored) {
            }
        }
        throw new IllegalArgumentException("Formato de data inválido: " + dataStr);
    }

    public String getNome() { return nome; }
    public String getDocumento() { return documento; }
    public LocalDate getDataNascimento() { return dataNascimento; }

    public int getIdade() {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    public boolean isMaiorDeIdade() {
        return getIdade() >= 18;
    }

    @Override
    public String toString() {
        return String.format("Cliente{name='%s', doc='%s', nasc=%s, idade=%d}", nome, documento, dataNascimento, getIdade());
    }
}
