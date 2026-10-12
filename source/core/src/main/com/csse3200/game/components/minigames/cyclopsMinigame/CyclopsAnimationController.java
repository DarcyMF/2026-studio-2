package com.csse3200.game.components.minigames.cyclopsMinigame;

import com.csse3200.game.components.Component;
import com.csse3200.game.events.EventHandler;
import com.csse3200.game.rendering.AnimationRenderComponent;
import com.csse3200.game.services.ServiceLocator;

/**
 * Plays the cyclops's animations. He sleeps until the player gets caught, then stays awake until
 * the minigame sends him back to sleep.
 */
public class CyclopsAnimationController extends Component {
  private AnimationRenderComponent animator;

  @Override
  public void create() {
    super.create();
    animator = this.entity.getComponent(AnimationRenderComponent.class);
    animateSleep();

    EventHandler minigameEvents = ServiceLocator.getCyclopsMinigameEventHandler();
    minigameEvents.addListener(CyclopsMinigameLogic.CYCLOPS_WAKE_EVENT, this::animateAwake);
    minigameEvents.addListener(CyclopsMinigameLogic.CYCLOPS_SLEEP_EVENT, this::animateSleep);
  }

  private void animateSleep() {
    animator.startAnimation("sleep");
  }

  private void animateAwake() {
    animator.startAnimation("idle");
  }
}
