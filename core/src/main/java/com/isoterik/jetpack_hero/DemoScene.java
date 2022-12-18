package com.isoterik.jetpack_hero;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.isoterik.racken.Component;
import com.isoterik.racken.GameObject;
import com.isoterik.racken.Scene;
import com.isoterik.racken._2d.components.debug.BoxDebugRenderer;
import com.isoterik.racken._2d.components.renderer.SpriteRenderer;
import com.isoterik.racken.animation.Animator;
import com.isoterik.racken.animation.FrameAnimation;
import com.isoterik.racken.animation.ICondition;
import com.isoterik.racken.animation.conditions.NumericCompoundCondition;
import com.isoterik.racken.utils.SpriteUtils;

public class DemoScene extends Scene {
    private float horizontalSpeed, verticalSpeed;
    private ICondition.DataSource<Float> horizontalSpeedSource, verticalSpeedSource;

    public DemoScene() {
        setBackgroundColor(Color.BLACK);
        //setRenderDebugLines(true);

        Array<TextureRegion> idleFrames = SpriteUtils.getSpriteSequence("Hero/Idle/Hero Boy Idle",
                ".png", 1, 13, 1, true);
        Array<TextureRegion> jumpFrames = SpriteUtils.getSpriteSequence("Hero/Jump/Hero Boy Jump",
                ".png", 1, 5, 1, true);

        FrameAnimation idleAnimation = new FrameAnimation(.5f, idleFrames);
        idleAnimation.setPlayMode(FrameAnimation.NORMAL);
        FrameAnimation jumpAnimation = new FrameAnimation(.5f, jumpFrames);
        jumpAnimation.setPlayMode(FrameAnimation.NORMAL);

        Animator<FrameAnimation> animator = new Animator<>(idleAnimation, jumpAnimation);
        GameObject hero = newSpriteObject("Hero", idleFrames.get(0));
        hero.addComponent(animator);
        hero.getComponent(SpriteRenderer.class).setFlipX(true);
        hero.addComponent(new Movement());
        hero.addComponent(new BoxDebugRenderer());

        addGameObject(hero);

        NumericCompoundCondition jumpCondition = new NumericCompoundCondition(verticalSpeedSource)
                .greaterThan(0f);

        animator.addTransition(idleAnimation, jumpAnimation, true, jumpCondition);
    }

    private class Movement extends Component {
        @Override
        public void start() {
            horizontalSpeedSource = new ICondition.DataSource<>(0f);
            verticalSpeedSource = new ICondition.DataSource<>(0f);
        }

        @Override
        public void update(float deltaTime) {
            float speed = .1f;

            if (input.isKeyJustPressed(Input.Keys.RIGHT))
                horizontalSpeed += speed;
            if (input.isKeyJustPressed(Input.Keys.LEFT))
                horizontalSpeed -= speed;
            if (input.isKeyJustPressed(Input.Keys.UP))
                verticalSpeed += speed;
            if (input.isKeyJustPressed(Input.Keys.DOWN))
                verticalSpeed -= speed;

            horizontalSpeedSource.set(horizontalSpeed);
            verticalSpeedSource.set(verticalSpeed);

            gameObject.transform.position.x += horizontalSpeed * deltaTime;
            gameObject.transform.position.y += verticalSpeed * deltaTime;
        }
    }
}
