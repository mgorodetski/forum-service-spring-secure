package telran.b7a.forum.service;

import java.time.LocalDate;
import java.time.chrono.ChronoPeriod;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.stream.Collectors;


import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import telran.b7a.forum.dao.PostRepository;
import telran.b7a.forum.dto.NewCommentDto;
import telran.b7a.forum.dto.NewPostDto;
import telran.b7a.forum.dto.PostDto;
import telran.b7a.forum.dto.exceptions.PostNotFoundException;
import telran.b7a.forum.model.Comment;
import telran.b7a.forum.model.Post;
import telran.b7a.forum.service.logging.PostLogger;

@Service
public class ForumServiceImpl implements ForumService {

	PostRepository forum;
	ModelMapper modelMapper;

	@Autowired
	public ForumServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
		this.forum = postRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public PostDto addNewPost(NewPostDto newPost, String author) {
		Post post = new Post(newPost.getTitle(), newPost.getContent(), author, newPost.getTags());
		return modelMapper.map(forum.save(post), PostDto.class);
	}

	@Override
	public PostDto getPost(String id) {
		Post post = forum.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostDto removePost(String id) {
		Post post = forum.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		forum.delete(post);
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	@PostLogger
	public PostDto updatePost(NewPostDto postUpdateDto, String id) {
		Post post = forum.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		String content = postUpdateDto.getContent();
		if (content != null) {
			post.setContent(content);
		}
		String title = postUpdateDto.getTitle();
		if (title != null) {
			post.setTitle(title);
		}
		Set<String> tags = postUpdateDto.getTags();
		if (tags != null) {
			tags.forEach(post::addTag);
		}
		forum.save(post);
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	@PostLogger
	public void addLike(String id) {
		Post post = forum.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		post.addLike();
		forum.save(post);

	}

	@Override
	public PostDto addComment(String id, String author, NewCommentDto newCommentDto) {
		Post post = forum.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		String message = newCommentDto.getMessage();
		if (message != null || author != null) {
			Comment comment = new Comment(author, message);
			post.addComment(comment);
		}
		forum.save(post);
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public Iterable<PostDto> findPostsByTags(Set<String> tags) {
		return forum.findAllByTags(tags).map(post -> modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());

	}

	@Override
	public Iterable<PostDto> findPostsByAuthor(String author) {
		return forum.findAllByAuthor(author).map(post -> modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public Iterable<PostDto> findPostsByPeriod(LocalDate fromDate, LocalDate toDate) {
		return forum.findAllByPeriod(fromDate, toDate).map(post -> modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
	}

	// Example using
//	@Override
//	public Iterable<PostDto> findPostsByAuthor(String author) {
//		Post post = new Post();
//		post.setAuthor(author);
//		Example<Post> postExample = Example.of(post, ExampleMatcher.matchingAny().withIgnoreCase());
//		Iterable<PostDto> postsFound = postRepository.findAll(postExample).stream()
//				.map(p -> modelMapper.map(post, PostDto.class))
//				.collect(Collectors.toList());
//		return postsFound;
//	}
//
//
	// .atStartOfDay()
////	@Override
//	public Iterable<PostDto> findPostsByPeriod(LocalDate fromDate, LocalDate toDate) {
//		LocalDateTime fromLDT = fromDate.atStartOfDay();
//		LocalDateTime toLDT = toDate.atStartOfDay();
//		Iterable<PostDto> postsFound = postRepository.findAllByPeriod(fromLDT, toLDT).stream()
//				.map(p -> modelMapper.map(post, PostDto.class))
//				.collect(Collectors.toList());
//				
//	}
//	@Override
//	public Iterable<PostDto> findPostsByAuthor(String author) {
//		
//		return null;
//	}

}
