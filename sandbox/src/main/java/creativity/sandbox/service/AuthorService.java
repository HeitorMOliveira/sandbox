package creativity.sandbox.service;

import creativity.sandbox.domain.author.AuthorCreationDTO;
import creativity.sandbox.domain.author.AuthorDTO;
import creativity.sandbox.domain.author.AuthorUpdateDTO;

import java.util.List;

public interface AuthorService {

    AuthorDTO findById(int id);

    AuthorDTO save(AuthorCreationDTO author);

    List<AuthorDTO> findAll();

    void delete(int id);

    void update(int id, AuthorUpdateDTO newAuthor);

    AuthorDTO findByName(String name);


}
