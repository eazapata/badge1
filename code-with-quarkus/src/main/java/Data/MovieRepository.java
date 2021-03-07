package Data;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface MovieRepository extends CrudRepository<Movie, Long> {

    List<Movie> findByTitleContainingAndFilmRatingGreaterThanEqual(String title,double filmRating);

    List<Movie> findByFilmRatingGreaterThanEqual(double filmRating);

    List<Movie> findByTitleContaining(String title);

    @Query("select u from Movie u where u.id = ?1")
    Movie findOne(long id);
}
