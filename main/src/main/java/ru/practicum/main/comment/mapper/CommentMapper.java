package ru.practicum.main.comment.mapper;

import ru.practicum.Constant;
import ru.practicum.main.comment.dto.CommentDto;
import ru.practicum.main.comment.dto.CommentShortDto;
import ru.practicum.main.comment.dto.NewCommentDto;
import ru.practicum.main.comment.model.Comment;
import ru.practicum.main.event.mapper.EventMapper;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.user.mapper.UserMapper;
import ru.practicum.main.user.model.User;

import java.time.LocalDateTime;

public class CommentMapper {

    public static Comment toComment(NewCommentDto newCommentDto, User author, Event event) {
        return new Comment(
                null,
                author,
                event,
                newCommentDto.getText(),
                LocalDateTime.parse(LocalDateTime.now().format(Constant.FORMATTER), Constant.FORMATTER),
                null
        );
    }

    public static Comment toCommentUpdated(NewCommentDto newCommentDto, Comment comment) {
        return new Comment(
                comment.getId(),
                comment.getAuthor(),
                comment.getEvent(),
                newCommentDto.getText(),
                comment.getCreated(),
                LocalDateTime.parse(LocalDateTime.now().format(Constant.FORMATTER), Constant.FORMATTER)
        );
    }

    public static CommentDto toDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                UserMapper.toShortDto(comment.getAuthor()),
                EventMapper.toShortDto(comment.getEvent()),
                comment.getText(),
                comment.getCreated(),
                comment.getUpdated()
        );
    }

    public static CommentShortDto toShortDto(Comment comment) {
        return new CommentShortDto(
                comment.getId(),
                UserMapper.toShortDto(comment.getAuthor()),
                comment.getText(),
                comment.getCreated(),
                comment.getUpdated()
        );
    }
}
