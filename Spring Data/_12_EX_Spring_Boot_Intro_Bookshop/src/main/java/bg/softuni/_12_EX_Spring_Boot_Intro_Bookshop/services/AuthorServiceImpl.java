package bg.softuni._12_EX_Spring_Boot_Intro_Bookshop.services;

import bg.softuni._12_EX_Spring_Boot_Intro_Bookshop.entities.Author;
import bg.softuni._12_EX_Spring_Boot_Intro_Bookshop.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author getRandomAuthor() {
        long size = this.authorRepository.count(); //общата бройка

        //10 -> [0..9] -> [1..10]
        int authorId = new Random().nextInt((int) size) + 1;

        return this.authorRepository.findById(authorId).get();
    }
}
