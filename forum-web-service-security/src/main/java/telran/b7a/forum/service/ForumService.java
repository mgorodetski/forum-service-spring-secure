package telran.b7a.forum.service;

import java.time.LocalDate;
import java.util.Set;

import telran.b7a.forum.dto.NewCommentDto;
import telran.b7a.forum.dto.NewPostDto;
import telran.b7a.forum.dto.PostDto;

public interface ForumService {
	PostDto addNewPost(NewPostDto newPost, String author);

	PostDto getPost(String id);

	PostDto removePost(String id);

	PostDto updatePost(NewPostDto postUpdateDto, String id);

	void addLike(String id);

	PostDto addComment(String id, String author, NewCommentDto newCommentDto);
	
	Iterable<PostDto> findPostsByAuthor(String author);
	
	Iterable<PostDto> findPostsByTags(Set<String> tags);
	
	Iterable<PostDto> findPostsByPeriod(LocalDate fromDate, LocalDate toDate);
}
