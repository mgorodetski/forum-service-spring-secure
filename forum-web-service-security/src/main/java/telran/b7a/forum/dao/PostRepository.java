package telran.b7a.forum.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import telran.b7a.forum.model.Post;

public interface PostRepository extends MongoRepository<Post, String> {

	Stream<Post> findAllByAuthor(String author);

	@Query("{tags: { $in: ?0 }}")
	Stream<Post> findAllByTags(Set<String> tags);

	@Query("{dateCreated: { $gte:?0, $lte:?1 }}")
	Stream<Post> findAllByPeriod(LocalDate fromDate, LocalDate toDate);

}
