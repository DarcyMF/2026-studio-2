package com.csse3200.game.components.minigames.cyclopsMinigame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.events.EventHandler;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.rendering.AnimationRenderComponent;
import com.csse3200.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(GameExtension.class)
class CyclopsAnimationControllerTest {
  private EventHandler minigameEvents;
  private AnimationRenderComponent animator;

  @BeforeEach
  void beforeEach() {
    minigameEvents = new EventHandler();
    ServiceLocator.registerCyclopsMinigameEventHandler(minigameEvents);

    TextureAtlas atlas = mock(TextureAtlas.class);
    for (String name : new String[] {"sleep", "idle"}) {
      Array<AtlasRegion> regions = new Array<>(1);
      regions.add(mock(AtlasRegion.class));
      when(atlas.findRegions(name)).thenReturn(regions);
    }
    animator = new AnimationRenderComponent(atlas);
    animator.addAnimation("sleep", 1f, PlayMode.LOOP);
    animator.addAnimation("idle", 1f, PlayMode.LOOP);

    CyclopsAnimationController controller = new CyclopsAnimationController();
    new Entity().addComponent(animator).addComponent(controller);
    controller.create();
  }

  @Test
  void startsAsleep() {
    assertEquals("sleep", animator.getCurrentAnimation());
  }

  @Test
  void wakesWhenThePlayerIsCaught() {
    minigameEvents.trigger(CyclopsMinigameLogic.CYCLOPS_WAKE_EVENT);

    assertEquals("idle", animator.getCurrentAnimation());
  }

  @Test
  void fallsBackAsleepWhenTheMinigameSaysSo() {
    minigameEvents.trigger(CyclopsMinigameLogic.CYCLOPS_WAKE_EVENT);
    minigameEvents.trigger(CyclopsMinigameLogic.CYCLOPS_SLEEP_EVENT);

    assertEquals("sleep", animator.getCurrentAnimation());
  }
}
