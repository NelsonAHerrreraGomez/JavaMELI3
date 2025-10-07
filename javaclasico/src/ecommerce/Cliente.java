package ecommerce;

import java.time.LocalDate;
import java.time.Period;

public class Cliente {
    private String nome;
    private String documento;
    private LocalDate dataNascimento;

    public Cliente(String nome, String documento, LocalDate dataNascimento) {
        this.nome = nome;
        this.documento = documento;
        this.dataNascimento = dataNascimento;
    }

    public boolean isMaiorDeIdade() {
        return Period.between(dataNascimento, LocalDate.now()).getYears() >= 18;
    }

    public String getNome() {
        return nome;
    }

    public String getDocumento() {
        return documento;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    @Override
    public String toString() {
        return nome + " | Documento: " + documento + " | Nascimento: " + dataNascimento;
    }
}
