package lescano.forohub.entities;

import lescano.forohub.dto.response.UpdateResponse;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="response")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Response {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String message;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate = LocalDateTime.now();

    @Column(name="is_solution", nullable = false)
    private boolean isSolution = false;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="topic_id")
    private  Topic topic;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="author_id")
    private ForumUser author;

    public void updateResponse (UpdateResponse data){
        if(data.message() != null){
            this.message = data.message();
        }
    }
}
