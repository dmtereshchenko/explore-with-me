package ru.practicum.main.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.Constant;
import ru.practicum.main.comment.dto.CommentDto;
import ru.practicum.main.comment.dto.NewCommentDto;
import ru.practicum.main.comment.mapper.CommentMapper;
import ru.practicum.main.comment.model.Comment;
import ru.practicum.main.comment.repository.CommentRepository;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.storage.EventRepository;
import ru.practicum.main.exception.CommentNotFoundException;
import ru.practicum.main.exception.EventNotFoundException;
import ru.practicum.main.exception.SomethingWentWrongException;
import ru.practicum.main.exception.UserNotFoundException;
import ru.practicum.main.user.storage.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class PrivateCommentServiceImpl implements PrivateCommentService {

    private final CommentRepository commentRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    public CommentDto create(Long userId, Long eventId, NewCommentDto newCommentDto) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
        if (event.getState() != Constant.State.PUBLISHED)
            throw new SomethingWentWrongException("Нельзя оставить комментарий к неопубликованному событию.");
        return CommentMapper.toDto(commentRepository.save(CommentMapper.toComment(
                newCommentDto,
                userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId)),
                event)
        ));
    }

    @Override
    public CommentDto update(Long userId, Long commentId, NewCommentDto newCommentDto) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException(commentId));
        if (!userId.equals(comment.getAuthor().getId()))
            throw new SomethingWentWrongException("Нельзя редактировать чужой комментарий.");
        return CommentMapper.toDto(commentRepository.save(CommentMapper.toCommentUpdated(newCommentDto, comment)));
    }

    @Override
    public void delete(Long userId, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException(commentId));
        if (!userId.equals(comment.getAuthor().getId()))
            throw new SomethingWentWrongException("Нельзя удалить чужой комментарий.");
        commentRepository.delete(comment);
    }
}
