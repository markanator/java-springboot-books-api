package com.ambro.authors.services;

import com.ambro.authors.domain.entities.AuthorEntity;

public interface AuthorService {
    AuthorEntity createAuthor(AuthorEntity author);
}