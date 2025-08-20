// src/main/java/com/sistema/avaliacao/config/DataInitializer.java

package com.sistema.avaliacao.config;

import com.sistema.avaliacao.enums.Perfil;
import com.sistema.avaliacao.model.Administrador;
import com.sistema.avaliacao.model.Funcao;
import com.sistema.avaliacao.repositories.AdministradorRepository;
import com.sistema.avaliacao.repositories.FuncaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class DataInitializer {

    @Autowired
    private FuncaoRepository funcaoRepository;

    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner loadData() {
        return args -> {
            // Passo 1: Verifica e cria as Funções (Perfis) se não existirem
            if (funcaoRepository.count() == 0) {
                System.out.println(">>> Nenhuma função encontrada. Criando funções padrão...");
                funcaoRepository.saveAll(List.of(
                        new Funcao(null, Perfil.ALUNO),
                        new Funcao(null, Perfil.PROFESSOR),
                        new Funcao(null, Perfil.COORDENADOR),
                        new Funcao(null, Perfil.ADMINISTRADOR)
                ));
                System.out.println(">>> Funções padrão criadas com sucesso.");
            } else {
                System.out.println(">>> Funções já existem no banco de dados.");
            }

            // Passo 2: Verifica e cria o usuário Administrador se não existir
            if (administradorRepository.count() == 0) {
                System.out.println(">>> Nenhum administrador encontrado. Criando usuário administrador padrão...");

                // Busca a função de Administrador que acabamos de garantir que existe
                Funcao adminFuncao = funcaoRepository.findByPerfil(Perfil.ADMINISTRADOR)
                        .orElseThrow(() -> new RuntimeException("Erro: Função ADMINISTRADOR não encontrada no banco."));

                Administrador admin = new Administrador();
                admin.setNome("Administrador Padrão");
                admin.setEmailInstitucional("admin@sistema.com");
                admin.setDataNascimento(LocalDate.of(2000, 1, 1));
                admin.setMatriculaAdministrativa("ADM001");
                admin.setFuncao(adminFuncao);
                
                // Criptografa a senha padrão
                admin.setSenha(passwordEncoder.encode("admin123"));

                administradorRepository.save(admin);
                System.out.println(">>> Administrador padrão (ADM001 / admin123) criado com sucesso.");
            } else {
                System.out.println(">>> Usuário administrador já existe no banco de dados.");
            }
        };
    }
}