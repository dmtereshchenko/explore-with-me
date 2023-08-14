package ru.practicum.main.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.comment.dto.CommentDto;
import ru.practicum.main.comment.dto.CommentRequestByParams;
import ru.practicum.main.comment.mapper.CommentMapper;
import ru.practicum.main.comment.repository.CommentRepository;
import ru.practicum.main.exception.CommentNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PublicCommentServiceImpl implements PublicCommentService {

    private final CommentRepository repository;


    @Override
    public CommentDto get(Long commentId) {
        return CommentMapper.toDto(repository.findById(commentId).orElseThrow(() -> new CommentNotFoundException(commentId)));
    }

    @Override
    public List<CommentDto> getAll(CommentRequestByParams request) {
        return repository.findAllByRequest(request).stream()
                .map(CommentMapper::toDto)
                .collect(Collectors.toList());
    }
}
