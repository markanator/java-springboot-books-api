package com.ambro.authors.repositories;

import com.ambro.authors.TestDataUtil;
import com.ambro.authors.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorRepositoryIntegrationTest {

    @Autowired
    private IAuthorRepository underTest;

    @Autowired
    public AuthorRepositoryIntegrationTest(IAuthorRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatAuthorCanBeCreatedAndRecalled() {
        // Create and save the Author
        Author author = TestDataUtil.createTestAuthorA();
        author = underTest.save(author);

        // Verify the saved entity has an ID
        assertThat(author.getId()).isNotNull();

        // Retrieve and validate
        Optional<Author> result = underTest.findById(author.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);
    }

    @Test
    public void testThatFindManyReturnsAllAuthors(){
        Author authorA = TestDataUtil.createTestAuthorA();
        underTest.save(authorA);
        Author authorb = TestDataUtil.createTestAuthorB();
        underTest.save(authorb);
        Author authorc = TestDataUtil.createTestAuthorC();
        underTest.save(authorc);

        Iterable<Author> result = underTest.findAll();
        assertThat(result)
                .hasSize(3)
                .containsExactly(authorA, authorb, authorc);
    }

    @Test
    public void testThatUpdateAuthorUpdatesCorrectly(){
        Author authorA = TestDataUtil.createTestAuthorA();
        underTest.save(authorA);

        authorA.setName("<NAME>");
        underTest.save(authorA);

        Optional<Author> result = underTest.findById(authorA.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(authorA);
    }

    @Test
    public void testThatDeleteAuthorDeletesCorrectly(){
        // Arrange
        Author authorA = TestDataUtil.createTestAuthorA();
        underTest.save(authorA);
        // Act
        underTest.deleteById(authorA.getId());
        // Assert
        Optional<Author> result = underTest.findById(authorA.getId());
        assertThat(result).isEmpty();
    }
}
