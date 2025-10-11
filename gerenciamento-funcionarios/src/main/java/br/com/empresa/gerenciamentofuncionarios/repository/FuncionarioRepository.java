package br.com.empresa.gerenciamentofuncionarios.repository;

import br.com.empresa.gerenciamentofuncionarios.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
}