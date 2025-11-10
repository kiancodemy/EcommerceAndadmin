package com.Main.Ecommerce.comment.service;

import com.Main.Ecommerce.auth.repository.CustomerRepository;
import com.Main.Ecommerce.auth.service.customer.CustomerServiceImpl;
import com.Main.Ecommerce.comment.Comment;
import com.Main.Ecommerce.comment.CommentRepository;
import com.Main.Ecommerce.comment.dto.CreateRequest;
import com.Main.Ecommerce.product.Product;
import com.Main.Ecommerce.product.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("dev")
class CommentServiceImplTest {

    @MockitoBean
    private CommentRepository commentRepository;

    @MockitoBean
    private ProductRepository productRepository;

    @Autowired
    private CommentServiceImpl commentService;


    @Test
    void it_Should_PublishComment_Successfully() {

        ///given
        ArgumentCaptor<Comment> commentArgumentCaptor = ArgumentCaptor.forClass(Comment.class);
        given(commentRepository.findById(2L)).willReturn(Optional.of(Comment.builder().isPublished(false).id(2L).build()));
        /// when
        commentService.publishComment(2L);

        /// then
        then(commentRepository).should().save(commentArgumentCaptor.capture());
        Comment comment = commentArgumentCaptor.getValue();
        assertThat(comment.isPublished()).isTrue();


    }

    @Test
    void it_Should_CreateComment_Successfully() {

        ///given
        CreateRequest createRequest=new CreateRequest("kian");
        ArgumentCaptor<Comment> commentArgumentCaptor = ArgumentCaptor.forClass(Comment.class);
        given(productRepository.findById(2L)).willReturn(Optional.of(Product.builder().id(2L).build()));

        /// when
        commentService.createComment(2L,"a",createRequest);
        then(commentRepository).should().save(commentArgumentCaptor.capture());
        Comment comment = commentArgumentCaptor.getValue();
        assertThat(comment.getText()).isEqualTo("kian");
        assertThat(comment.getWriter()).isEqualTo("a");

        /// then


    }

    @Test
    void it_Should_UnpublishComment_Successfully() {

        ///given
        ArgumentCaptor<Comment> commentArgumentCaptor = ArgumentCaptor.forClass(Comment.class);
        given(commentRepository.findById(2L)).willReturn(Optional.of(Comment.builder().isPublished(true).id(2L).build()));
        /// when
        commentService.unpublishComment(2L);

        /// then
        then(commentRepository).should().save(commentArgumentCaptor.capture());
        Comment comment = commentArgumentCaptor.getValue();
        assertThat(comment.isPublished()).isFalse();


    }

    @Test
    void it_Should_DeleteComment() {

        ///given
        ArgumentCaptor<Comment> commentArgumentCaptor = ArgumentCaptor.forClass(Comment.class);

        given(commentRepository.findById(2L)).willReturn(Optional.of(Comment.builder().isPublished(false).id(2L).build()));
        /// when
        commentService.deleteComment(2L);
    }

}