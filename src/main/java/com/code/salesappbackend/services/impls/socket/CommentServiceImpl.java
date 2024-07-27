package com.code.salesappbackend.services.impls.socket;

import com.code.salesappbackend.dtos.requests.socket.CommentDto;
import com.code.salesappbackend.dtos.responses.PageResponse;
import com.code.salesappbackend.dtos.responses.comment.CommentResponse;
import com.code.salesappbackend.exceptions.DataNotFoundException;
import com.code.salesappbackend.exceptions.MediaTypeNotSupportException;
import com.code.salesappbackend.models.socket.Comment;
import com.code.salesappbackend.models.socket.CommentMedia;
import com.code.salesappbackend.models.product.Product;
import com.code.salesappbackend.models.user.User;
import com.code.salesappbackend.models.enums.MediaType;
import com.code.salesappbackend.repositories.*;
import com.code.salesappbackend.repositories.product.ProductRepository;
import com.code.salesappbackend.repositories.socket.CommentMediaRepository;
import com.code.salesappbackend.repositories.socket.CommentRepository;
import com.code.salesappbackend.repositories.user.UserRepository;
import com.code.salesappbackend.services.impls.BaseServiceImpl;
import com.code.salesappbackend.services.interfaces.socket.CommentService;
import com.code.salesappbackend.utils.S3Upload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class CommentServiceImpl extends BaseServiceImpl<Comment, Long> implements CommentService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CommentRepository commentRepository;
    private final S3Upload s3Upload;
    private static final Set<String> IMAGE_EXTENSIONS = new HashSet<>(Arrays.asList("jpg", "jpeg", "png", "gif", "bmp", "tiff", "webp"));
    private static final Set<String> VIDEO_EXTENSIONS = new HashSet<>(Arrays.asList("mp4", "avi", "mov", "wmv", "flv", "mkv", "webm"));
    private final CommentMediaRepository commentMediaRepository;

    public CommentServiceImpl(BaseRepository<Comment, Long> repository, UserRepository userRepository, ProductRepository productRepository, CommentRepository commentRepository, S3Upload s3Upload, CommentMediaRepository commentMediaRepository) {
        super(repository, Comment.class);
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.commentRepository = commentRepository;
        this.s3Upload = s3Upload;
        this.commentMediaRepository = commentMediaRepository;
    }

    @Override
    @Transactional(rollbackFor = {DataNotFoundException.class, IOException.class})
    public CommentResponse addComment(CommentDto commentDto) throws DataNotFoundException, IOException, MediaTypeNotSupportException {
        Product product = productRepository.findById(commentDto.getProductId())
                .orElseThrow(() -> new DataNotFoundException("Product not found"));
        Comment comment = mapToDto(commentDto, product);
        commentRepository.save(comment);
        List<CommentMedia> commentMediaList = null;
        if(commentDto.getMedia() != null) {
            commentMediaList = uploadMedia(commentDto.getMedia(), comment);
        }
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setComment(comment);
        commentResponse.setCommentMedia(commentMediaList);
        calculateRating(product);
        return commentResponse;
    }

    private void calculateRating(Product product) {
        List<Comment> comments = commentRepository.findAllByProductId(product.getId());
        float totalRating = 0;
        for (Comment comment : comments) {
            totalRating += comment.getRating();
        }
        float averageRating = totalRating / comments.size();
        product.setAvgRating(averageRating);
        product.setNumberOfRating(comments.size());
        productRepository.save(product);
    }

    @Override
    public PageResponse<List<CommentResponse>> getPageData(int pageNo, int pageSize, String[] search, String[] sort) {
        PageResponse<?> pageResponse = super.getPageData(pageNo, pageSize, search, sort);
        List<Comment> comments = (List<Comment>) pageResponse.getData();
        List<CommentResponse> commentResponses = comments.stream().map(cmt ->
                CommentResponse.builder()
                        .comment(cmt)
                        .commentMedia(commentMediaRepository.findAllByCommentId(cmt.getId()))
                        .build())
                .toList();
        PageResponse<List<CommentResponse>> result = new PageResponse<>();
        result.setData(commentResponses);
        result.setPageNo(pageResponse.getPageNo());
        result.setTotalPage(pageResponse.getTotalPage());
        result.setTotalElement(pageResponse.getTotalElement());
        return result;
    }

    private Comment mapToDto(CommentDto commentDto, Product product) throws DataNotFoundException {
        User user = userRepository.findByEmail(commentDto.getEmail())
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        return Comment.builder()
                .commentDate(LocalDateTime.now())
                .user(user)
                .product(product)
                .rating(commentDto.getRating())
                .textContent(commentDto.getContent())
                .build();
    }

    private List<CommentMedia> uploadMedia(List<MultipartFile> files, Comment comment) throws IOException, MediaTypeNotSupportException {
        List<CommentMedia> commentMediaList = new ArrayList<>();
        for (MultipartFile file : files) {
            String path = s3Upload.uploadFile(file);
            CommentMedia commentMedia = CommentMedia.builder()
                    .comment(comment)
                    .path(path)
                    .mediaType(getMedia(file))
                    .build();
            commentMediaList.add(commentMediaRepository.save(commentMedia));
        }
        return commentMediaList;
    }

    private MediaType getMedia(MultipartFile file) throws MediaTypeNotSupportException {
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (IMAGE_EXTENSIONS.contains(extension)) {
            return MediaType.IMAGE;
        } else if (VIDEO_EXTENSIONS.contains(extension)) {
            return MediaType.VIDEO;
        }
        throw new MediaTypeNotSupportException("media type not supported");
    }

}
