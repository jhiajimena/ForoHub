package lescano.forohub.entities;


import lescano.forohub.dto.category.CreateCategoryDTO;
import lescano.forohub.dto.category.UpdateCategoryDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name= "category")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SubCategory> subCategories;

    public Category(CreateCategoryDTO data) {
        this.name = data.name();
    }

    public void updateCategory (UpdateCategoryDTO data){
        if(data.name() != null){
            this.name= data.name();
        }
    }
}