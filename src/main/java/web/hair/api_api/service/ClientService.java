package web.hair.api_api.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import web.hair.api_api.domain.Client;
import web.hair.api_api.model.ClientDTO;
import web.hair.api_api.repos.ClientRepository;
import web.hair.api_api.util.NotFoundException;


@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(final ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<ClientDTO> findAll() {
        final List<Client> clients = clientRepository.findAll(Sort.by("clientId"));
        return clients.stream()
                .map(client -> mapToDTO(client, new ClientDTO()))
                .toList();
    }

    public ClientDTO get(final Long clientId) {
        return clientRepository.findById(clientId)
                .map(client -> mapToDTO(client, new ClientDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ClientDTO clientDTO) {
        final Client client = new Client();
        mapToEntity(clientDTO, client);
        return clientRepository.save(client).getClientId();
    }

    public void update(final Long clientId, final ClientDTO clientDTO) {
        final Client client = clientRepository.findById(clientId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(clientDTO, client);
        clientRepository.save(client);
    }

    public void delete(final Long clientId) {
        clientRepository.deleteById(clientId);
    }

    private ClientDTO mapToDTO(final Client client, final ClientDTO clientDTO) {
        clientDTO.setClientId(client.getClientId());
        clientDTO.setName(client.getName());
        clientDTO.setSurname(client.getSurname());
        clientDTO.setPhone(client.getPhone());
        clientDTO.setEmail(client.getEmail());
        return clientDTO;
    }

    private Client mapToEntity(final ClientDTO clientDTO, final Client client) {
        client.setName(clientDTO.getName());
        client.setSurname(clientDTO.getSurname());
        client.setPhone(clientDTO.getPhone());
        client.setEmail(clientDTO.getEmail());
        return client;
    }

    public boolean emailExists(final String email) {
        return clientRepository.existsByEmailIgnoreCase(email);
    }

}
