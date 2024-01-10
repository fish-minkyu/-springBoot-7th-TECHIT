package com.example.jpademo;

import com.example.jpademo.entity.ArticleEntity;
import com.example.jpademo.repo.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class DemoController {
  private final ArticleRepository repository;

  @GetMapping("/test/{id}")
  public String test(
    @PathVariable("id") Long id
  ) {
    // CREATE
    // 1. 새로운 ArticleEntitiy 인스턴스를 만든다.
    ArticleEntity article = new ArticleEntity();
    // 2. 인스턴스에 내가 저장하고 싶은 필요한 데이터를 넣어준다.
    article.setTitle("Test");
    article.setContent("Lorem Ipsum");
    article.setWriter("minkyu");
    // 3. repository를 이용해서 저장한다.
    repository.save(article);

    // READ ALL
    List<ArticleEntity> articles = repository.findAll();
    for (ArticleEntity entity: articles) {
      System.out.println(entity.toString());
    }

    // READ ONE
    Optional<ArticleEntity> optionalArticle = repository.findById(4L);
    if (optionalArticle.isPresent()) {
      System.out.println("found: 4L");
      System.out.println(optionalArticle.get());
    }

    // 어떤 ArticeEntitiy가 있고, title을 바꾼다.
    // UPDATE
    Optional<ArticleEntity> targetOptional = repository.findById(id);
    if (targetOptional.isPresent()) {
      System.out.println("target found");
      ArticleEntity target = targetOptional.get();
      // title을 "updated title"
      target.setTitle("updated title");
      repository.save(target);
    }

    // DELETE
    /*targetOptional = repository.findById(id);
    if (targetOptional.isPresent()) {
      System.out.println("delete target found");
      ArticleEntity target = targetOptional.get();
      repository.delete(target);
    }*/
    repository.deleteById(id);

    return "done";
  }
}
