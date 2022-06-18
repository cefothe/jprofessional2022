package io.nexo.jprofessional.javabeer.repostitory;

import io.nexo.jprofessional.javabeer.domain.JavaBeerEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JavaBeerEventRepository extends JpaRepository<JavaBeerEvent, Long> {
}
