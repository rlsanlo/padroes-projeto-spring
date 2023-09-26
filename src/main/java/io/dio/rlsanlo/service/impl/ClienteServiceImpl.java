package io.dio.rlsanlo.service.impl;

import io.dio.rlsanlo.model.Cliente;
import io.dio.rlsanlo.model.repository.ClienteRepository;
import io.dio.rlsanlo.model.repository.EnderecoRepository;
import io.dio.rlsanlo.service.ClienteService;
import io.dio.rlsanlo.service.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ViaCepService viaCepService;


    @Override
    public Iterable<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

    @Override
    public void inserir(Cliente cliente) {
        salvarClienteComCep(cliente);
    }

    @Override
    public void atualizar(Long id, Cliente cliente) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if (clienteOptional.isPresent()) {
            salvarClienteComCep(cliente);
        }
    }

    @Override
    public void deletar(Long id) {
        clienteRepository.deleteById(id);
    }

    private void salvarClienteComCep(Cliente cliente) {
        viaCepService.buscaEnderecoPorCep(cliente.getEndereco().getCep());
        enderecoRepository.save(cliente.getEndereco());
        clienteRepository.save(cliente);
    }
}
