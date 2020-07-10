package com.majida.mbook.service;

import com.majida.mbook.entity.Book;
import com.majida.mbook.entity.Copy;
import com.majida.mbook.repository.BookRepository;
import com.majida.mbook.repository.CopyRepository;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


/**
 * Test the Copy Service Implementation: test the service logic
 *
 * @author majida
 */

@RunWith(MockitoJUnitRunner.class)
public class CopyServiceTest {


    private static final Long COPY_ID = 3L;
    private static final Long BOOK_ID = 1L;


    @Mock
    private CopyRepository repoMock;

    @InjectMocks
    private CopyService copyService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private Copy copy;
    private Book book;

    @Before
    public void init() {
        book = new Book();
        book.setId(BOOK_ID);
        copy = new Copy();
        copy.setId(COPY_ID);
        copy.setReferenceNumber(1234);
        copy.setIsAvailable(1);
        copy.setBook(book);


    }

    @Test
    public void getAllCopies() {
        // Data preparation
        List<Copy> copies = Arrays.asList(copy, copy, copy);
        Mockito.when(repoMock.findAll()).thenReturn(copies);

        // Method call
        List<Copy> copyList = copyService.getAllCopies();

        // Verification
        Assert.assertThat(copyList, Matchers.hasSize(3));
        Mockito.verify(repoMock, Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(repoMock);
    }

    @Test
   public void getCopiesByBookId() {


        // Data preparation
        List<Copy> copies = Arrays.asList(copy,copy,copy);
        Mockito.when(repoMock.getAllCopiesByBookId(ArgumentMatchers.anyLong())).thenReturn(copies);

        // Method call
        List<Copy> copyList = copyService.getCopiesByBookId(BOOK_ID);

        // Verification
        Assert.assertThat(copyList, Matchers.hasSize(3));
        Mockito.verify(repoMock, Mockito.times(1)).getAllCopiesByBookId(ArgumentMatchers.anyLong());
        Mockito.verifyNoMoreInteractions(repoMock);

    }

    @Test
   public void getCopyAndCopyExists() {

        // Data preparation
        Mockito.when(repoMock.findById(COPY_ID)).thenReturn(Optional.of(copy));

        // Method call
        Optional<Copy> copy = copyService.getCopy(COPY_ID);

        // Verification
        Assert.assertNotNull(copy);
        Mockito.verify(repoMock, Mockito.times(1)).findById(ArgumentMatchers.anyLong());
        Mockito.verifyNoMoreInteractions(repoMock);
    }


    @Test
   public void updateCopyAndCopyNotExists() {

        // Method call
        copyService.updateCopy(COPY_ID, copy);
        repoMock.save(copy);

        // Verification
        Mockito.verify(repoMock, Mockito.times(2)).save(ArgumentMatchers.any());
        Mockito.verifyNoMoreInteractions(repoMock);
    }

    @Test
   public void deleteCopyAndCopyNotExists() {
        // Method call
        copyService.deleteCopy(COPY_ID);


        // Verification
        Mockito.verify(repoMock, Mockito.times(1)).deleteById(ArgumentMatchers.anyLong());
        Mockito.verifyNoMoreInteractions(repoMock);
    }
}