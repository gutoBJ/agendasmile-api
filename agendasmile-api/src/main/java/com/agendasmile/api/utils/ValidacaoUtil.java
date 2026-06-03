package com.agendasmile.api.utils;

public class ValidacaoUtil {

    // Construtor privado — classe utilitária não deve ser instanciada!
    private ValidacaoUtil() {}

    public static void validarNome(String nome) { // ← adiciona static!
        for (char c : nome.toCharArray()) {
            if (!Character.isLetter(c) && !Character.isWhitespace(c))
                throw new IllegalArgumentException("Nome deve conter apenas letras!");
        }
    }

    public static void validarTelefone(String telefone) {
        if (telefone != null && !telefone.isBlank()) {
            String apenasNumeros = telefone.replaceAll("\\D", "");
            if (apenasNumeros.length() < 10 || apenasNumeros.length() > 11)
                throw new IllegalArgumentException("Telefone inválido! Deve ter 10 ou 11 dígitos!");
        }
    }
}
