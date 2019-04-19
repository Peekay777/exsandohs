package com.koutsios.exsandohs.service;

import static com.koutsios.exsandohs.model.MarkType.X;
import static com.koutsios.exsandohs.util.GameServiceUtils.createBoard;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import com.koutsios.exsandohs.model.Game;
import com.koutsios.exsandohs.model.player.ComputerPlayer;
import com.koutsios.exsandohs.model.player.Player;
import java.util.Random;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class RandomServiceTest {

  @TestConfiguration
  static class RandomServiceTestConfiguration {
    @Bean
    public RandomService randomService() {
      return new RandomService();
    }
  }

  @Autowired
  private RandomService subject;

  @MockBean
  private Random random;

  @Test
  @Repeat(value = 20)
  public void getRandomInt() {
    int min = 0;
    int max = 5;
    when(random.nextInt(anyInt())).thenCallRealMethod();

    int actual = subject.getRandomInt(max);

    assertThat(actual, allOf(greaterThanOrEqualTo(min), lessThan(max)));
  }

  @Test
  public void randomEmptySquareId() {
    Player player = new ComputerPlayer();
    player.setMark(X);
    Game game = Game.builder()
        .playerEx(player)
        .board(createBoard())
        .build();
    when(random.nextInt(anyInt())).thenReturn(0);

    String actual = subject.randomEmptySquareId(game);

    assertEquals("00", actual);
  }
}