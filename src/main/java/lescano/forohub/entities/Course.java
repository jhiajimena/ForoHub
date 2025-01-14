package lescano.forohub.entities;

import lescano.forohub.dto.course.UpdateCourseDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name="course")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String name;

    @Column
    private String description;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="subcategory_id",nullable = false)
    private SubCategory subCategory;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Topic> topics;

    public void updateCourse (UpdateCourseDTO data){
        if(data.name() != null){
            this.name = data.name();
        }
        if(data.description() != null){
            this.description = data.description();
        }
    }
}
