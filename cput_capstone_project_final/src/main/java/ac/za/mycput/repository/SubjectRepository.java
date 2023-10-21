package ac.za.mycput.repository;

import ac.za.mycput.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SubjectRepository extends JpaRepository<Subject,Long> {
    Subject findBySubjectName(String email);
}
