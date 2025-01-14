package lescano.forohub.entities;

import lescano.forohub.dto.subCategory.CreateSubCategoryDTO;
import lescano.forohub.dto.subCategory.UpdateSubcategoryDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "sub_category")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 100)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "category_id")
    private Category category;

    @OneToMany(mappedBy = "subCategory", fetch = FetchType.LAZY)
    private List<Course> courses;

    public SubCategory (CreateSubCategoryDTO data, Category category){
        this.name=data.name();
        this.category=category;
    }
    public void updateSubCategory(UpdateSubcategoryDTO data){
        if(data.name() != null){
            this.name= data.name();
        }
    }

}
