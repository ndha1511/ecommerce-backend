package com.code.salesappbackend.services.impls;

import com.code.salesappbackend.models.Comment;
import com.code.salesappbackend.repositories.BaseRepository;
import com.code.salesappbackend.services.interfaces.CommentService;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl extends BaseServiceImpl<Comment, Long> implements CommentService {
    public CommentServiceImpl(BaseRepository<Comment, Long> repository) {
        super(repository);
    }
}
