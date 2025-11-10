package com.Main.Ecommerce.comment.service;
import com.Main.Ecommerce.comment.Comment;
import com.Main.Ecommerce.comment.CommentRepository;
import com.Main.Ecommerce.comment.dto.CreateRequest;
import com.Main.Ecommerce.product.Product;
import com.Main.Ecommerce.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@RequiredArgsConstructor
@Component
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ProductRepository productRepository;

    @Override
    public Comment publishComment(Long id) {
        Comment findComment=commentRepository.findById(id).orElseThrow(()->new RuntimeException("موجود نیست"));
        findComment.setPublished(true);
        return commentRepository.save(findComment);

    }

    @Override
    public Comment createComment(Long id ,String email, CreateRequest createRequest) {
        Product findProduct = productRepository.findById(id).orElseThrow(() -> new RuntimeException("موجود نیست"));
        Comment comment = Comment.builder().writer(email).text(createRequest.text()).product(findProduct).build();
        return commentRepository.save(comment);

    }

    @Override
    public Comment unpublishComment(Long id) {
        Comment findComment=commentRepository.findById(id).orElseThrow(()->new RuntimeException("موجود نیست"));
        findComment.setPublished(false);
        return commentRepository.save(findComment);

    }

    @Override
    public void deleteComment(Long id) {
        Comment findComment=commentRepository.findById(id).orElseThrow(()->new RuntimeException("موجود نیست"));
        findComment.setProduct(null);
        commentRepository.deleteById(id);
    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }
}
