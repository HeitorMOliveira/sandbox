package creativity.sandbox.service;

import creativity.sandbox.domain.author.AuthorCreationDTO;
import creativity.sandbox.domain.author.AuthorDTO;
import creativity.sandbox.domain.author.AuthorUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuthorService {

    AuthorDTO findById(int id);

    AuthorDTO save(AuthorCreationDTO author);

    Page<AuthorDTO> findAll(Pageable pageable);

    void delete(int id);

    void update(int id, AuthorUpdateDTO newAuthor);

    AuthorDTO findByName(String name);


}
