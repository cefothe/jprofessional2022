package io.nexo.jprofessional.javabeer.controller;

import io.nexo.jprofessional.javabeer.controller.dto.JavaBeerEventDTO;
import io.nexo.jprofessional.javabeer.domain.JavaBeerEvent;
import io.nexo.jprofessional.javabeer.repostitory.JavaBeerEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/javabeer")
public class JavaBeerEventController {

    private final JavaBeerEventRepository javaBeerEventRepository;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public JavaBeerEventDTO create(@RequestBody JavaBeerEventDTO javaBeerEventDTO){
        return Optional.of(javaBeerEventRepository.save(
                        dtoToEntity(javaBeerEventDTO)))
                .map(event-> entityToDTO(event))
                .orElseThrow();
    }

    @GetMapping
    public List<JavaBeerEventDTO> getAll(){
       return javaBeerEventRepository.findAll()
               .stream()
               .map(this::entityToDTO)
               .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public JavaBeerEventDTO update(@PathVariable("id") Long id, @RequestBody JavaBeerEventDTO javaBeerEventDTO){
        return javaBeerEventRepository.findById(id)
                .map(javaBeerEvent -> {
                    javaBeerEvent.setLocation(javaBeerEventDTO.getLocation());
                    javaBeerEvent.setSponsor(javaBeerEventDTO.getSponsor());
                    javaBeerEvent.setStartAt(javaBeerEventDTO.getStartAt());
                    return javaBeerEvent;
                }).map(javaBeerEventRepository::save)
                .map(this::entityToDTO)
                .orElseThrow();
    }

    @ResponseStatus(HttpStatus.GONE)
    @DeleteMapping("/{id}")
    public void update(@PathVariable("id") Long id){
         javaBeerEventRepository.deleteById(id);
    }

    private JavaBeerEventDTO entityToDTO(JavaBeerEvent event) {
        return JavaBeerEventDTO.builder()
                .id(event.getId())
                .location(event.getLocation())
                .sponsor(event.getSponsor())
                .startAt(event.getStartAt())
                .build();
    }

    private JavaBeerEvent dtoToEntity(JavaBeerEventDTO javaBeerEventDTO) {
        return JavaBeerEvent.builder()
                .location(javaBeerEventDTO.getLocation())
                .sponsor(javaBeerEventDTO.getSponsor())
                .startAt(javaBeerEventDTO.getStartAt())
                .build();
    }
}
